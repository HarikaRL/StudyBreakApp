package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuInflater;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;

/**
 * The AddGoalActivity allows the user to add new goals by entering the goal text into a text field
 */
public class AddGoalActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT =
            "com.example.studybreakapp.EXTRA_TEXT";

    private EditText editText;

    /**
     * onCreate method sets the format according to activity_add_goal layout file and sets the
     * text to the edit_text TextView in the layout file
     * @param savedInstanceState the saved stat of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        editText = findViewById(R.id.edit_text);
    }

    /**
     * Saves a goal by checking whether the text entered by the user is empty. If so, method
     * displays a toast message prompting the user to enter the goal. If not, method stores
     * the text entered and sets the activity result to ok.
     */
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

    /**
     * Creates the menu for the page
     * @param menu the menu to add
     * @return boolean indicating if the menu was successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_goal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Calls the saveGoal method when the check mark in the menu is clicked
     * @param item the menu item that indicates the goal should be saved, in this case the check mark
     * @return true if the goal was successfully saved, otherwise defaults to the superclass method
     */
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