package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.provider.SyncStateContract.Constants;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private EditText st;
    private EditText br;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mStudy, mBreak;
    private Button mStart;

    @Override
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

//        String message = st.getText().toString(); --> doesn't work, produces nothing as a result // meant to get the text that the user inputted

        checkSharedPreferences();
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoSelectImage();

                String sTime = mStudy.getText().toString();
                mEditor.putString(getString(R.string.studyTime), sTime);
                mEditor.commit();

                String breakTime = mBreak.getText().toString();
                mEditor.putString(getString(R.string.breakTime), breakTime);
                mEditor.commit();

                mStart.setEnabled(true);

//                Intent sendMessage = new Intent(MainActivity.this, StudyTime.class);
//                sendMessage.putExtra("UserInput", message);
//                startActivity(sendMessage); --> doesn't work, meant to send the users input to the study time class
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

    private void movetoStudyTime() {
        Intent intent = new Intent(MainActivity.this, StudyTime.class);
        startActivity(intent);
    }

    private TextWatcher startWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String stInput = st.getText().toString().trim();
            String brInput = br.getText().toString().trim();

            mStart.setEnabled(!stInput.isEmpty() && !brInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
