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

public class Goals extends AppCompatActivity {

    private TextView mTextViewCountDown;


    private SharedPreferences mPreferences;
    private SharedPreferences nPreferences;
    private SharedPreferences.Editor mEditor;

    private Button activity;
    private Button timeLeft;

    private TextView timerCountdown;
    private TextView timeNum;

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

    }

}