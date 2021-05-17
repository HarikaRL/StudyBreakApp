package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import android.widget.Button;

public class StudyTime extends AppCompatActivity {

   private TextView mTextViewCountDown;

   private CountDownTimer mCountDownTimer;

   private Boolean mTimerRunning;

    private long mStartTimeInMillis;
   private long mTimeLeftInMillis;
   private long mEndTime;

   private Button goals;

    private SharedPreferences mPreferences;
    private SharedPreferences newPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    /**
     * calls the back button override method
     * calls the countdown method
     * gets the study time requested from the main activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);

        onBackPressed();

        mTextViewCountDown = findViewById(R.id.timer);
        goals = findViewById(R.id.goals_button);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String studyTime = mPreferences.getString(getString(R.string.studyTime), "");

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToGoals();
            }
        });

        long millisInput = Long.parseLong(studyTime)*60000;

        if(millisInput == 0){
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
     * Takes the study time input and begins a countdown timer
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
                        moveToBreakTime();
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

                SharedPreferences sharedPreferences = getSharedPreferences("NEW_PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("timeLeft", mTextViewCountDown.getText().toString());
                editor.apply();

                if(minutes == 1 && seconds == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "One minute remaining.";
                    int duration = Toast.LENGTH_SHORT;

                    String oneMinLeft = "one";
                    editor.putString("timeL", oneMinLeft);
                    editor.apply();

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    String one = "";
                    editor.putString("timeL",one);
                    editor.apply();
                }
            }

    /**
     * saves the values so that the timer can continue running when the activity has been left
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("endTime", mEndTime);
    }

    /**
     * ensures that the timer continues to run when the activity has been left
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();

        if(mTimerRunning){
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }

    /**
     * method to move from the study time activity to the break time activity
     */
            private void moveToBreakTime() {
                Intent in = new Intent(StudyTime.this, BreakTime.class);
                startActivity(in);
            }

    /**
     * method to move from the study time activity to the goals page
     */
    private void moveToGoals()  {
        Intent intent = new Intent(StudyTime.this, Goals.class);
        startActivity(intent);
            }

    /**
     * overrides the back button so that it can't be pressed
     */
    @Override
    public void onBackPressed() {
    }
    }