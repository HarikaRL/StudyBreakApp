package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectImage extends AppCompatActivity {
    private Button home;
    private Button goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        home = findViewById(R.id.home_button);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });

        goals = findViewById(R.id.goals_button);

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goals();
            }
        });
    }

    private void home() {
        Intent intent = new Intent(SelectImage.this, MainActivity.class);
        startActivity(intent);
    }

    private void goals() {
        Intent intent = new Intent(SelectImage.this, Goals.class);
        startActivity(intent);
    }

}