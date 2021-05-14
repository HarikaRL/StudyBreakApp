package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private EditText st;
    private EditText br;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mStudy, mBreak;
    private Button mStart;

    @Override
    /**
     * sets all of the buttons and texts
     * sets shared preferences
     * sets button to move to break time activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.background_button);
        st = findViewById(R.id.studyTime);
        br = findViewById(R.id.breakTime);

        st.addTextChangedListener(startWatcher);
        br.addTextChangedListener(startWatcher);

        mStudy = (EditText) findViewById(R.id.studyTime);
        mBreak = (EditText) findViewById(R.id.breakTime);
        mStart  = (Button) (findViewById(R.id.background_button));

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToBreakTime();

                String sTime = mStudy.getText().toString();
                mEditor.putString(getString(R.string.studyTime), sTime);
                mEditor.commit();

                String breakTime = mBreak.getText().toString();
                mEditor.putString(getString(R.string.breakTime), breakTime);
                mEditor.commit();

                mStart.setEnabled(true);
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
     * method to move from the break time activity to the main activity
     */
    private void moveToBreakTime() {
        Intent intent = new Intent(MainActivity.this, BreakTime.class);
        startActivity(intent);
    }

    private TextWatcher startWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        /**
         * sets the break time and study time inputs to the shared preferences so that the user doesn't always have to reenter their times
         */
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String stInput = st.getText().toString().trim();
            String brInput = br.getText().toString().trim();

            mStart.setEnabled(!stInput.isEmpty() && !brInput.isEmpty());
        }

        @Override
        /**
         * notify that text has been changed
         */
        public void afterTextChanged(Editable s) {

        }
    };
}
