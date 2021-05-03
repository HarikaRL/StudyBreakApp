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

public class StudyTime extends AppCompatActivity {

   private TextView mTextViewCountDown;

   private CountDownTimer mCountDownTimer;

   private Boolean mTimerRunning;

    private long mStartTimeInMillis;
   private long mTimeLeftInMillis;

   private Button home;

   private Button goals;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);

        mTextViewCountDown = findViewById(R.id.timer);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String studyTime = mPreferences.getString(getString(R.string.studyTime), "");

        home = (Button) (findViewById(R.id.home_button));
        goals = (Button) (findViewById(R.id.goals_button));

        long millisInput = Long.parseLong(studyTime)*60000;

        if(millisInput == 0){
            Toast.makeText(this, "please enter a positive number", Toast.LENGTH_SHORT).show();
            return;
        }
        setTime(millisInput);
        startTimer();
        }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

            private void startTimer() {
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

                        home.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToHome();
                            }
                        });

                        goals.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToGoals();
                            }
                        });
                    }
                }.start();
                mTimerRunning = true;
            }

            private void resetTimer() {
                mTimeLeftInMillis = mStartTimeInMillis;
                updateCountDownText();
            }

            private void updateCountDownText() {
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                mTextViewCountDown.setText(timeLeftFormatted);
            }

            private void moveToHome() {
                Intent in = new Intent(StudyTime.this, MainActivity.class);
                startActivity(in);
            }

    private void moveToGoals() {
        Intent i = new Intent(StudyTime.this, Goals.class);
        startActivity(i);
    }
    }