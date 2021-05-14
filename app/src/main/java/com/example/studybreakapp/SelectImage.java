package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectImage extends AppCompatActivity {
    private Button goals;
    private Button paint;
    private Button timerCount;

    private SharedPreferences nPreferences;

    @Override
    /**
     * allows user to display time left
     * sets button to go to paint activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        timerCount = findViewById(R.id.time_left);

        paint = findViewById(R.id.testpaint);

        paint.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { paint(); }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        String oneMinute = sharedPreferences.getString("Value", "");

        timerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeNew = sharedPreferences.getString("timeCountdown", "");

                Context context = getApplicationContext();
                CharSequence text = "Time Left: " + timeNew;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        if(oneMinute == "one")
        {
            Context context = getApplicationContext();
            CharSequence text = "One minute remaining.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * method to move from the select image activity to the paint activity
     */
    private void paint() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        startActivity(intent);
    }
}