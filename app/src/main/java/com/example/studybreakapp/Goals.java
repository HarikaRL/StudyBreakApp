package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Goals extends AppCompatActivity {

    private TextView mTextViewCountDown;

    private SharedPreferences mPreferences;
    private SharedPreferences nPreferences;
    private SharedPreferences.Editor mEditor;

    private Button activity;
    private Button timeLeft;

    private TextView timerCountdown;
    private TextView timeNum;

    public static final int ADD_GOAL_REQUEST = 1;

    private GoalViewModel goalViewModelIncomplete;
    private GoalViewModel goalViewModelComplete;

    @Override
    /**
     * allows user to display time left
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String breakTime = mPreferences.getString(getString(R.string.breakTime), "");

        timeLeft = findViewById(R.id.time_left);

        SharedPreferences prefsNew = getSharedPreferences("NEW_PREFS", Context.MODE_PRIVATE);

        timeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeNew = prefsNew.getString("timeLeft", "");

                Context context = getApplicationContext();
                CharSequence text = "Time Left: " + timeNew;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        String oneMinute = prefsNew.getString("timeL", "");
        if(oneMinute == "one")
        {
            Context context = getApplicationContext();
            CharSequence text = "One minute remaining.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        FloatingActionButton buttonAddGoal = findViewById(R.id.button_add_goal);
        buttonAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Goals.this, AddGoalActivity.class);
                startActivityForResult(intent, ADD_GOAL_REQUEST);
            }
        });

        RecyclerView recyclerViewIncomplete = findViewById(R.id.recycler_view_incomplete);
        recyclerViewIncomplete.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIncomplete.setHasFixedSize(true);

        GoalAdapter adapterIncomplete = new GoalAdapter();
        recyclerViewIncomplete.setAdapter(adapterIncomplete);

        goalViewModelIncomplete = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(GoalViewModel.class);
        goalViewModelIncomplete.getIncompleteGoals().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable List<Goal> goals) {
                adapterIncomplete.setGoals(goals);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                goalViewModelIncomplete.complete(adapterIncomplete.getGoalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(Goals.this, "Goal marked as complete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewIncomplete);


        RecyclerView recyclerViewComplete = findViewById(R.id.recycler_view_complete);
        recyclerViewComplete.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComplete.setHasFixedSize(true);

        GoalAdapter adapterComplete = new GoalAdapter();
        recyclerViewComplete.setAdapter(adapterComplete);

        goalViewModelComplete = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(GoalViewModel.class);
        goalViewModelComplete.getCompleteGoals().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(@Nullable List<Goal> goals) {
                adapterComplete.setGoals(goals);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GOAL_REQUEST && resultCode == RESULT_OK) {
            String text = data.getStringExtra(AddGoalActivity.EXTRA_TEXT);

            Goal goal = new Goal(text);
            goalViewModelIncomplete.insert(goal);

            Toast.makeText(this, "Goal added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Goal not added", Toast.LENGTH_SHORT).show();
        }
    }

}