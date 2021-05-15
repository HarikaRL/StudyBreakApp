package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuInflater;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

public class AddGoalActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT =
            "com.example.studybreakapp.EXTRA_TEXT";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        editText = findViewById(R.id.edit_text);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Goal");
    }

    private void saveGoal() {
        String text = editText.getText().toString();

        if (text.trim().isEmpty()) {
            Toast.makeText(this,"Please insert goal text",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, text);

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_goal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_goal:
                saveGoal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}