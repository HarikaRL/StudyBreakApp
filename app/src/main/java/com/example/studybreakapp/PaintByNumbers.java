package com.example.studybreakapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class PaintByNumbers extends AppCompatActivity {

    int[] commonColors = {Color.rgb(51,0,0), Color.rgb(51,25,0)
    , Color.rgb(51,51,0), Color.rgb(25,51,0), Color.rgb(0,51,0),
    Color.rgb(0,51,25), Color.rgb(0,51,51), Color.rgb(0,25,51),
    Color.rgb(0,0,51), Color.rgb(25,0,51), Color.rgb(51,0,51),
    Color.rgb(51,0,25), Color.rgb(0,0,0), Color.rgb(102,0,0),
    Color.rgb(102,51,0), Color.rgb(102,102,0), Color.rgb(51,102,0),
    Color.rgb(0,102,0), Color.rgb(0,102,51), Color.rgb(0,102,102),
    Color.rgb(0,51,102), Color.rgb(0,0,102), Color.rgb(51,0,102),
    Color.rgb(102,0,102), Color.rgb(102,0,51), Color.rgb(32,32,32),
    Color.rgb(153,0,0), Color.rgb(153,76,0), Color.rgb(153,153,0),
    Color.rgb(76,153,0), Color.rgb(0,153,0), Color.rgb(0,153,76),
    Color.rgb(0,153,153), Color.rgb(0,76,153), Color.rgb(0,0,153),
    Color.rgb(76,0,153), Color.rgb(153,0,153), Color.rgb(153,0,76),
    Color.rgb(64,64,64), Color.rgb(204,0,0), Color.rgb(204,102,0),
    Color.rgb(204,204,0), Color.rgb(102,204,0), Color.rgb(0,204,0),
    Color.rgb(0,204,102), Color.rgb(0,204,204), Color.rgb(0,102,204),
    Color.rgb(0,0,204), Color.rgb(102,0,204), Color.rgb(204,0,204),
    Color.rgb(204,0,102), Color.rgb(96,96,96), Color.rgb(255,0,0),
    Color.rgb(255,128,0), Color.rgb(255,255,0), Color.rgb(128,255,0),
    Color.rgb(0,255,0), Color.rgb(0,255,128), Color.rgb(0,255,255),
    Color.rgb(0,128,255), Color.rgb(0,0,255), Color.rgb(128,0,255), Color.rgb(255,0,255),
    Color.rgb(255,0,128), Color.rgb(128,128,128), Color.rgb(255,51,51),
    Color.rgb(255,153,51), Color.rgb(255,255,51), Color.rgb(153,255,51),
    Color.rgb(51,255,51), Color.rgb(51,255,153), Color.rgb(51,255,255),
    Color.rgb(51,153,255), Color.rgb(51,51,255), Color.rgb(153,51,255),
    Color.rgb(255,51,255), Color.rgb(255,51,153), Color.rgb(160,160,160),
    Color.rgb(255,102,102), Color.rgb(255,178,102), Color.rgb(255,255,102),
    Color.rgb(178,255,102), Color.rgb(102,255,102), Color.rgb(102,255,178),
    Color.rgb(102,255,255), Color.rgb(102,178,255), Color.rgb(102,102,255),
    Color.rgb(178,102,255), Color.rgb(255,102,255), Color.rgb(255,102,178),
    Color.rgb(192,192,192), Color.rgb(255,153,153), Color.rgb(255,204,153),
    Color.rgb(255,255,153), Color.rgb(204,255,153), Color.rgb(153,255,153),
    Color.rgb(153,255,204), Color.rgb(153,255,255), Color.rgb(153,204,255),
    Color.rgb(153,153,255), Color.rgb(204,153,255), Color.rgb(255,153,255),
    Color.rgb(255,153,204), Color.rgb(224,224,224), Color.rgb(255,204,204),
    Color.rgb(255,229,204), Color.rgb(255,255,204), Color.rgb(229,255,204),
    Color.rgb(204,255,204), Color.rgb(204,255,229), Color.rgb(204,255,255),
    Color.rgb(204,229,255), Color.rgb(204,204,255), Color.rgb(229,204,255),
    Color.rgb(255,204,255), Color.rgb(255,204,229), Color.rgb(255,255,255)};

    ImageView img;
    Bitmap bit;
    int[][][] pixelGrid;
    int[] viewCoords;
    GridLayout g;
    TextView e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_by_numbers);
        g = (GridLayout) findViewById(R.id.PaintGrid);
        Log.d("Karthik", "Row count: " + g.getRowCount());
        Log.d("Karthik", "Row count: " + g.getColumnCount());
        for (int i = 0; i < g.getRowCount(); i++) {
            for (int j = 0; j < g.getColumnCount(); j++) {
                e = new TextView(g.getContext());
                e.setText("0");
                GridLayout.LayoutParams lParams = new GridLayout.LayoutParams();
                lParams.height = 0;
                lParams.width = 0;
                lParams.columnSpec = GridLayout.spec(j,1,1);
                lParams.rowSpec = GridLayout.spec(i,1,1);
                lParams.setGravity(Gravity.CENTER);
                g.addView(e, lParams);
            }
        }
        img = findViewById(R.id.ImgBeingPainted);
        bit = loadBitmapFromView(img);
        pixelGrid = getPixelGrid(bit);
        bit = clearImg(bit);
        img.setImageBitmap(bit);
        img.setOnTouchListener(imgSourceOnTouchListener);
        viewCoords = new int[2];
        img.getLocationOnScreen(viewCoords);
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    public int[][][] getPixelGrid(Bitmap bitmap) {
        int height = (int)bitmap.getHeight()/30;
        int width = (int)bitmap.getWidth()/30;
        int[][][] pixelGrid = new int[width][height][3];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int redSum = 0;
                int blueSum = 0;
                int greenSum = 0;
                for (int k = 0; k < 30; k++) {
                    for (int l = 0; l < 30; l++) {
                        int pixel = bitmap.getPixel(30 * i + k, 30 * j + l);
                        redSum += Color.red(pixel);
                        blueSum += Color.blue(pixel);
                        greenSum += Color.green(pixel);
                    }
                }
                pixelGrid[i][j][0] = Math.round((float) redSum / 900);
                pixelGrid[i][j][1] = Math.round((float) greenSum / 900);
                pixelGrid[i][j][2] = Math.round((float) blueSum / 900);
                int nearestColor = Color.WHITE;
                int minDist = 1000000000;
                for (int k = 0; k < commonColors.length; k++) {
                    if ((int)(Math.pow(pixelGrid[i][j][0]-Color.red(commonColors[k]),2)+Math.pow(pixelGrid[i][j][1]-Color.green(commonColors[k]),2)+Math.pow(pixelGrid[i][j][2] - Color.blue(commonColors[k]),2)) < minDist) {
                        minDist = (int)(Math.pow(pixelGrid[i][j][0]-Color.red(commonColors[k]),2)+Math.pow(pixelGrid[i][j][1]-Color.green(commonColors[k]),2)+Math.pow(pixelGrid[i][j][2] - Color.blue(commonColors[k]),2));
                        nearestColor = commonColors[k];
                    }
                }
                pixelGrid[i][j][0] = Color.red(nearestColor);
                pixelGrid[i][j][1] = Color.green(nearestColor);
                pixelGrid[i][j][2] = Color.blue(nearestColor);
            }
        }
        return pixelGrid;
    }

    public Bitmap clearImg(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int[] pixels = new int[height*width];
        Arrays.fill(pixels, Color.WHITE);
        Bitmap ret = Bitmap.createBitmap(width, height, bitmap.getConfig());
        ret.setPixels(pixels, 0, width, 0,0, width, height);
        return ret;
    }

    public int[] getCoarseTouchPosition(View v, MotionEvent e) {
        int[] pos = new int[2];
        pos[0] = (int)((e.getX()-viewCoords[0])/30);
        pos[1] = (int)((e.getY()-viewCoords[1])/30);
        return pos;
    }

    View.OnTouchListener imgSourceOnTouchListener= new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            int[] pos = getCoarseTouchPosition(view, event);

            if (pos[0] < 0 || pos[1] < 0) {
                return true;
            }

            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    bit.setPixel(pos[0]*30+i, pos[1]*30+j, Color.rgb(pixelGrid[pos[0]][pos[1]][0], pixelGrid[pos[0]][pos[1]][1], pixelGrid[pos[0]][pos[1]][2]));
                    img.setImageBitmap(bit);
                }
            }

            return true;
        }};

}