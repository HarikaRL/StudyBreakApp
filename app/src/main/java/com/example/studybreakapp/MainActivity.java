package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button start;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mStudy, mBreak;
    private Button mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStudy = (EditText) findViewById(R.id.studyTime);
        mBreak = (EditText) findViewById(R.id.breakTime);
        mStart  = (Button) (findViewById(R.id.background_button));

        start = findViewById(R.id.background_button);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoSelectImage();

                String studyTime = mStudy.getText().toString();
                mEditor.putString(getString(R.string.studyTime), studyTime);
                mEditor.commit();

                String breakTime = mBreak.getText().toString();
                mEditor.putString(getString(R.string.breakTime), breakTime);
                mEditor.commit();
            }
        });
    }

    /**
     * Check and set the shared preferences
     */
    private void checkSharedPreferences() {
        String studyTime = mPreferences.getString(getString(R.string.studyTime), "");
        String breakTime = mPreferences.getString(getString(R.string.breakTime), "");

        mStudy.setText(studyTime);
        mBreak.setText(breakTime);

    }

    /**
     * Moves to the activity page once the start button is pressed
     */
    private void movetoSelectImage() {
        Intent intent = new Intent(MainActivity.this, SelectImage.class);
        startActivity(intent);
    }



}
