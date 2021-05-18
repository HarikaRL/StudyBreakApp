package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SelectImage extends AppCompatActivity {
    private Button timerCount;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;

    private SharedPreferences nPreferences;

    @Override
    /**
     * allows user to display time left
     * sets button to go to paint activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        /*
        The below code assigns variables to all necessary buttons and imageViews present in the
        xml.
         */

        timerCount = findViewById(R.id.time_left);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);

        /*
        The below 8 click listeners set a click listener for each image on the select image screen.
        Each links to a separate method that starts the PaintByNumbers activity with the selected
        image.
         */

        image1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image1(); }
        });

        image2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image2(); }
        });

        image3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image3(); }
        });

        image4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image4(); }
        });

        image5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image5(); }
        });

        image6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image6(); }
        });

        image7.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image7(); }
        });

        image8.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image8(); }
        });

        /*
        The below code, copy-pasted from other files, allows the time to be checked during the
        activity by creating a toast whenever a button is clicked.
         */

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS",
                Context.MODE_PRIVATE);
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

        /*
        The below code creates a toast that gives the user a warning when there is one minute
        left in their break.
         */

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
     * This method starts the PaintByNumbers activity and passes the information that image 1
     * was selected, so that it can be loaded in the next activity.
     */

    private void image1() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "1"); //Pass extra text to show image 1 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 2
     * was selected, so that it can be loaded in the next activity.
     */

    private void image2() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "2"); //Pass extra text to show image 2 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 3
     * was selected, so that it can be loaded in the next activity.
     */

    private void image3() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "3"); //Pass extra text to show image 3 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 4
     * was selected, so that it can be loaded in the next activity.
     */

    private void image4() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "4"); //Pass extra text to show image 4 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 5
     * was selected, so that it can be loaded in the next activity.
     */

    private void image5() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "5"); //Pass extra text to show image 5 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 6
     * was selected, so that it can be loaded in the next activity.
     */

    private void image6() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "6"); //Pass extra text to show image 6 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 7
     * was selected, so that it can be loaded in the next activity.
     */

    private void image7() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "7"); //Pass extra text to show image 7 was selected
        startActivity(intent);
    }

    /**
     * This method starts the PaintByNumbers activity and passes the information that image 8
     * was selected, so that it can be loaded in the next activity.
     */

    private void image8() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "8"); //Pass extra text to show image 8 was selected
        startActivity(intent);
    }
}