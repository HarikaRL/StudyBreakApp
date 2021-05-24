package com.example.studybreakapp;

// Credit to Coding in Flow (https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118)
// for help with Room, SQL, and RecyclerView implementation

// Credit to Computer Tech (https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118)
// for help with the pop-up window


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import android.view.LayoutInflater;
import android.util.Log;


public class Goals extends AppCompatActivity {

    private TextView mTextViewCountDown;

    private SharedPreferences mPreferences;
    private SharedPreferences nPreferences;
    private SharedPreferences.Editor mEditor;

    private Button activity;
    private Button timeLeft;
    private Button backToTimer;

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
        if(oneMinute.equals("one"));
        {
            Context context = getApplicationContext();
            CharSequence text = "One minute remaining.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        /**
         * Controls help button to launch pop-up window
         */
        FloatingActionButton helpButton = findViewById(R.id.button_goals_help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUpWindow();
            }
        });


        /**
         * Links the add goal button to the add goal activity
         */
        FloatingActionButton buttonAddGoal = findViewById(R.id.button_add_goal);
        buttonAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Goals.this, AddGoalActivity.class);
                startActivityForResult(intent, ADD_GOAL_REQUEST);
            }
        });

        /**
         * Handles the display incomplete goals (recyler view, adapter, view model)
         */
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

        /**
         * Marks an incomplete goal as complete when swiped
         */
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

        /**
         * Handles the display of complete goals (recycler view, adapter, view model)
         */
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

    /**
     * When the add goal activity is exited, method inserts the new goal to the view model storing
     * incomplete goals and displays a toast message saying "Goal added." If something went wrong
     * with the add goal activity, method displays a toast message saying "Goal not added"
     * @param requestCode the request code from the add goal activity
     * @param resultCode the result code from the add goal activity
     * @param data the intent data from the add goal activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GOAL_REQUEST && resultCode == RESULT_OK) {
            String text = data.getStringExtra(AddGoalActivity.EXTRA_TEXT);

            Goal goal = new Goal(text);
            goalViewModelIncomplete.insert(goal);

            Toast.makeText(this, "Goal Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Goal Not Added", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPopUpWindow() {
        Intent help_popup = new Intent(Goals.this, GoalsHelp.class);
        startActivity(help_popup);
    }

}