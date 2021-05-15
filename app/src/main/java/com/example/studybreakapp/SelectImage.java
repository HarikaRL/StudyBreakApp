package com.example.studybreakapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SelectImage extends AppCompatActivity {
    private Button goals;
    private Button paint;
    private Button timerCount;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;
    private ImageView image10;

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

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);
        image8 = findViewById(R.id.image8);
        image9 = findViewById(R.id.image9);
        image10 = findViewById(R.id.image10);

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

        image9.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image9(); }
        });

        image10.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { image10(); }
        });

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

    private void image1() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "1");
        startActivity(intent);
    }

    private void image2() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "2");
        startActivity(intent);
    }

    private void image3() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "3");
        startActivity(intent);
    }

    private void image4() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "4");
        startActivity(intent);
    }

    private void image5() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "6");
        startActivity(intent);
    }

    private void image6() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "6");
        startActivity(intent);
    }

    private void image7() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "7");
        startActivity(intent);
    }

    private void image8() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "8");
        startActivity(intent);
    }

    private void image9() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "9");
        startActivity(intent);
    }

    private void image10() {
        Intent intent = new Intent(SelectImage.this, PaintByNumbers.class);
        intent.putExtra("image", "10");
        startActivity(intent);
    }
}