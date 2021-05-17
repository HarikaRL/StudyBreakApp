package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import android.content.Context;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.IntentFilter;

public class BreakTime extends AppCompatActivity {

    private TextView mTextViewCountDown;

    private CountDownTimer mCountDownTimer;

    private Boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private Button activity;
    private Button goals;

    private SharedPreferences mPreferences;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String text;

    private SharedPreferences nPreferences;
    private SharedPreferences.Editor mEditor;
    Bundle bundle = new Bundle();

    /**
     * calls the back button override method
     * calls the countdown method
     * gets the break time requested from the main activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_time);

        onBackPressed();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String breakTime = mPreferences.getString(getString(R.string.breakTime), "");

        mTextViewCountDown = findViewById(R.id.t);
//        goals = (Button) (findViewById(R.id.button_goals));
        activity = (Button) (findViewById(R.id.button_activity));

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToActivity();
            }
        });

        long millisInput = Long.parseLong(breakTime) * 60000;

        if (millisInput == 0) {
            Toast.makeText(this, "please enter a positive number", Toast.LENGTH_SHORT).show();
            return;
        }
        setTime(millisInput);
        startTimer();
    }

    /**
     * sets mStartTimeInMillis equal to milliseconds and calls the reset timer method
     * @param milliseconds
     */
    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    /**
     * Takes the break time input and begins a countdown timer
     */
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTextViewCountDown.setText("Done!");
                moveToHome();
            }
        }.start();
        mTimerRunning = true;
    }

    /**
     * calls the update countdown text method
     */
    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }

    /**
     * formats and updates the countdown timer
     * sends the time left to the other activities
     */
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("timeCountdown", mTextViewCountDown.getText().toString());
        editor.apply();

        if(minutes == 1 && seconds == 0) {
            Context context = getApplicationContext();
            CharSequence text = "One minute remaining.";
            int duration = Toast.LENGTH_SHORT;

            String oneMinLeft = "one";
            editor.putString("Value", oneMinLeft);
            editor.apply();

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            }

         else {
             String one = "";
             editor.putString("Value",one);
             editor.apply();
        }
    }

    @Override
    /**
     * saves the values so that the timer can continue running when the activity has been left
     */
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();
    }

    @Override
    /**
     * calls the update countdown text method
     * prevents the countdown timer from restarting
     */
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }
    }

    /**
     * method to move from the break time activity to the home page
     */
    private void moveToHome() {
        Intent intent = new Intent(BreakTime.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * method to move from the break time activity to the main activity
     */
    private void moveToActivity() {
        Intent intent = new Intent(BreakTime.this, SelectImage.class);
        startActivity(intent);
    }

    @Override
    /**
     * overrides the back button so that it can't be pressed
     */
    public void onBackPressed() {
    }

}
