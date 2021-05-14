package com.example.studybreakapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
    TableLayout tl;
    RelativeLayout rLayout;
    int selectedColor = Color.WHITE;
    int[] mostCommonColors;
    TableRow tRow;
    String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_by_numbers);
        img = findViewById(R.id.ImgBeingPainted);
        rLayout = findViewById(R.id.MainLayout);
        tl = findViewById(R.id.PaintGrid);
        for (int i = 0; i < 34; i++) {
            TableRow tr = new TableRow(tl.getContext());
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            tl.addView(tr);
            for (int j = 0; j < 25; j++) {
                TextView t = new TextView(tl.getContext());
                TableRow.LayoutParams rp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
                rp.weight = 1;
                t.setLayoutParams(rp);
                t.setText("A");
                t.setGravity(Gravity.CENTER);
                tr.addView(t);
            }
        }
        bit = loadBitmapFromView(img);
        pixelGrid = getPixelGrid(bit);
        mostCommonColors = getMostCommonColors(pixelGrid);
        pixelGrid = modifiedPixelGrid(pixelGrid, mostCommonColors);
        bit = clearImg(bit);
        img.setImageBitmap(bit);
        rLayout.setOnTouchListener(imgSourceOnTouchListener);
        viewCoords = new int[2];
        img.getLocationOnScreen(viewCoords);
        tRow = findViewById(R.id.ColorSelection);
        for (int i = 0; i < 13; i++) {
            TextView t = new TextView(tRow.getContext());
            TableRow.LayoutParams rp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            rp.weight = 1;
            t.setLayoutParams(rp);
            t.setText("");
            t.setBackgroundColor(mostCommonColors[i]);
            tRow.addView(t);
        }
        TableRow cLabels = findViewById(R.id.ColorLabels);
        for (int i = 0; i < 13; i++) {
            TextView t = new TextView(tRow.getContext());
            TableRow.LayoutParams rp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            rp.weight = 1;
            t.setLayoutParams(rp);
            t.setText(alphabet[i]);
            t.setGravity(Gravity.CENTER);
            cLabels.addView(t);
        }
        for (int i = 0; i < 34; i++) {
            for (int j = 0; j < 25; j++) {
                TableRow tr = (TableRow) tl.getChildAt(i);
                TextView tv = (TextView) tr.getChildAt(j);
                for (int k = 0; k < 13; k++) {
                    if (Color.rgb(pixelGrid[j][i][0], pixelGrid[j][i][1], pixelGrid[j][i][2]) == mostCommonColors[k]) {
                        tv.setText(alphabet[k]);
                    }
                }
            }
        }
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

    public int[] getMostCommonColors(int[][][] pixelGrid) {
        int[] colorCounts = new int[117];
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 34; j++) {
                for (int k = 0; k < 117; k++) {
                    if (Color.rgb(pixelGrid[i][j][0], pixelGrid[i][j][1], pixelGrid[i][j][2]) == commonColors[k]) {
                        colorCounts[k]++;
                    }
                }
            }
        }
        TreeMap<Integer, Integer> sorted = new TreeMap<>();
        for (int i = 0; i < 117; i++) {
            sorted.put(colorCounts[i], commonColors[i]);
        }
        int[] mostCommonColors = new int[13];
        for (int i = 0; i < 13; i++) {
            mostCommonColors[i] = sorted.get(sorted.lastKey());
            sorted.remove(sorted.lastKey());
        }
        return mostCommonColors;
    }

    public int[][][] modifiedPixelGrid(int[][][] pixelGrid, int[] mostCommonColors) {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 34; j++) {
                int minDist = 1000000000;
                int nearestColor = Color.WHITE;
                for (int k = 0; k < 13; k++) {
                        if ((int)(Math.pow(pixelGrid[i][j][0]-Color.red(mostCommonColors[k]),2)+Math.pow(pixelGrid[i][j][1]-Color.green(mostCommonColors[k]),2)+Math.pow(pixelGrid[i][j][2] - Color.blue(mostCommonColors[k]),2)) < minDist) {
                            minDist = (int)(Math.pow(pixelGrid[i][j][0]-Color.red(mostCommonColors[k]),2)+Math.pow(pixelGrid[i][j][1]-Color.green(mostCommonColors[k]),2)+Math.pow(pixelGrid[i][j][2] - Color.blue(mostCommonColors[k]),2));
                            nearestColor = mostCommonColors[k];
                        }
                }
                pixelGrid[i][j][0] = Color.red(nearestColor);
                pixelGrid[i][j][1] = Color.green(nearestColor);
                pixelGrid[i][j][2] = Color.blue(nearestColor);
            }
        }
        return pixelGrid;
    }

    View.OnTouchListener imgSourceOnTouchListener= new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            int[] posOnPic = new int[2];
            int[] tablePos = new int[2];
            tl.getLocationOnScreen(tablePos);
            posOnPic[0] = (int)(event.getRawX()-tablePos[0]);
            posOnPic[1] = (int)(event.getRawY()-tablePos[1]);
            TableRow tr = (TableRow) tl.getChildAt(0);
            TextView t = (TextView) tr.getChildAt(0);
            int leftInit = t.getLeft();
            int rightInit = t.getRight();
            int topInit = t.getTop();
            int bottomInit = t.getBottom();
            for (int i = 0; i < 34; i++) {
                for (int j = 0; j < 25; j++) {
                    int left = leftInit + j*(rightInit - leftInit);
                    int right =  (j+1)*(rightInit - leftInit) ;
                    int top = topInit + i*(bottomInit - topInit);
                    int bottom = (i+1)*(bottomInit - topInit);
                    Rect rect = new Rect(left, top, right, bottom);
                    if (rect.contains(posOnPic[0], posOnPic[1])) {
                        tr = (TableRow) tl.getChildAt(i);
                        t = (TextView) tr.getChildAt(j);
                        for (int k = 0; k < 13; k++) {
                            if (selectedColor == mostCommonColors[k]) {
                                if (t.getText() == alphabet[k]) {
                                    t.setText("");
                                    t.setBackgroundColor(selectedColor);
                                }
                            }
                        }
                    }
                }
            }

            int[] rowPos = new int[2];
            int[] posOnSelect = new int[2];
            tRow.getLocationOnScreen(rowPos);
            posOnSelect[0] = (int)(event.getRawX() - rowPos[0]);
            posOnSelect[1] = (int)(event.getRawY() - rowPos[1]);
            for (int i = 0; i < 13; i++) {
                TextView e = (TextView) tRow.getChildAt(i);
                int left = e.getLeft();
                int right = e.getRight();
                int top = e.getTop();
                int bottom = e.getBottom();
                Rect rect = new Rect(left, top, right, bottom);
                if (rect.contains(posOnSelect[0], posOnSelect[1])) {
                    selectedColor = mostCommonColors[i];
                }
            }

            return true;
        }};

}
