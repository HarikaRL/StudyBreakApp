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
    ArrayList<Integer> mostCommonColors;
    TableRow tRow;
    String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_by_numbers);
        img = findViewById(R.id.ImgBeingPainted);
        if (getIntent().getStringExtra("image").equals("1")) {
            img.setImageResource(R.drawable.penguinv2);
        }
        if (getIntent().getStringExtra("image").equals("2")) {
            img.setImageResource(R.drawable.koala);
        }
        if (getIntent().getStringExtra("image").equals("3")) {
            img.setImageResource(R.drawable.tree);
        }
        if (getIntent().getStringExtra("image").equals("4")) {
            img.setImageResource(R.drawable.plains);
        }
        if (getIntent().getStringExtra("image").equals("5")) {
            img.setImageResource(R.drawable.cookiev2);
        }
        if (getIntent().getStringExtra("image").equals("6")) {
            img.setImageResource(R.drawable.beach);
        }
        if (getIntent().getStringExtra("image").equals("7")) {
            img.setImageResource(R.drawable.redpandacropped);
        }
        if (getIntent().getStringExtra("image").equals("8")) {
            img.setImageResource(R.drawable.sunflower);
        }
        if (getIntent().getStringExtra("image").equals("9")) {
            img.setImageResource(R.drawable.rainbowjersey);
        }
        if (getIntent().getStringExtra("image").equals("10")) {
            img.setImageResource(R.drawable.mclarencropped);
        }
        img.setScaleType(ImageView.ScaleType.FIT_XY);
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
        for (int i = 0; i < 34; i++) {
            for (int j = 0; j < 25; j++) {
                Log.d("color", pixelGrid[i][j][0] + " " + pixelGrid[i][j][1] + " " + pixelGrid[i][j][2]);
            }
        }
        mostCommonColors = getMostCommonColors(pixelGrid);
        pixelGrid = modifiedPixelGrid(pixelGrid, mostCommonColors);
        bit = clearImg(bit);
        img.setImageBitmap(bit);
        rLayout.setOnTouchListener(imgSourceOnTouchListener);
        viewCoords = new int[2];
        img.getLocationOnScreen(viewCoords);
        tRow = findViewById(R.id.ColorSelection);
        for (int i = 0; i < mostCommonColors.size(); i++) {
            TextView t = new TextView(tRow.getContext());
            TableRow.LayoutParams rp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            rp.weight = 1;
            t.setLayoutParams(rp);
            t.setText("");
            t.setBackgroundColor(mostCommonColors.get(i));
            tRow.addView(t);
        }
        TableRow cLabels = findViewById(R.id.ColorLabels);
        for (int i = 0; i < mostCommonColors.size(); i++) {
            TextView t = new TextView(tRow.getContext());
            TableRow.LayoutParams rp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
            rp.weight = 1;
            t.setLayoutParams(rp);
            t.setText(alphabet[i]);
            t.setGravity(Gravity.CENTER);
            cLabels.addView(t);
        }
        for (int i = 0; i < pixelGrid.length; i++) {
            for (int j = 0; j < pixelGrid[i].length; j++) {
                TableRow tr = (TableRow) tl.getChildAt(i);
                TextView tv = (TextView) tr.getChildAt(j);
                for (int k = 0; k < mostCommonColors.size(); k++) {
                    if (Color.rgb(pixelGrid[i][j][0], pixelGrid[i][j][1], pixelGrid[i][j][2]) == mostCommonColors.get(k)) {
                        tv.setText(alphabet[k]);
                    }
                }
            }
        }
        autofill();
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    public void autofill() {
        for (int i = 0; i < 34; i++) {
            TableRow tr = (TableRow) tl.getChildAt(i);
            for (int j = 0; j < 25; j++) {
                TextView tv = (TextView) tr.getChildAt(j);
                for (int k = 0; k < mostCommonColors.size(); k++) {
                    if (alphabet[k].equals(tv.getText())) {
                        tv.setBackgroundColor(mostCommonColors.get(k));
                    }
                }
            }
        }
    }

    public int[][][] getPixelGrid(Bitmap bitmap) {
        int height = (int)bitmap.getHeight()/30;
        int width = (int)bitmap.getWidth()/30;
        int[][][] pixelGrid = new int[height][width][3];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] colorCounts = new int[117];
                for (int k = 0; k < 30; k++) {
                    for (int l = 0; l < 30; l++) {
                        int pixel = bitmap.getPixel(30 * j + l, 30 * i + k);
                        int minDist = 1000000000;
                        int nearestPos = 0;
                        for (int m = 0; m < commonColors.length; m++) {
                            if ((int)(Math.pow(Color.red(pixel)-Color.red(commonColors[m]),2)+Math.pow(Color.green(pixel)-Color.green(commonColors[m]),2)+Math.pow(Color.blue(pixel) - Color.blue(commonColors[m]),2)) < minDist) {
                                minDist = (int)(Math.pow(Color.red(pixel)-Color.red(commonColors[m]),2)+Math.pow(Color.green(pixel)-Color.green(commonColors[m]),2)+Math.pow(Color.blue(pixel) - Color.blue(commonColors[m]),2));
                                nearestPos = m;
                            }
                        }
                        colorCounts[nearestPos]++;
                    }
                }
                int maxPos = 0;
                for (int k = 1; k < 117; k++) {
                    if (colorCounts[k] > colorCounts[maxPos]) {
                        maxPos = k;
                    }
                }
                pixelGrid[i][j][0] = Color.red(commonColors[maxPos]);
                pixelGrid[i][j][1] = Color.green(commonColors[maxPos]);
                pixelGrid[i][j][2] = Color.blue(commonColors[maxPos]);
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

    public ArrayList<Integer> getMostCommonColors(int[][][] pixelGrid) {
        int[] colorCounts = new int[commonColors.length];
        for (int i = 0; i < pixelGrid.length; i++) {
            for (int j = 0; j < pixelGrid[i].length; j++) {
                for (int k = 0; k < commonColors.length; k++) {
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
        ArrayList<Integer> mostCommonColors = new ArrayList<Integer>();
        for (int i = 0; i < 13; i++) {
            if (sorted.size() == 0) {
                break;
            }
            mostCommonColors.add(sorted.get(sorted.lastKey()));
            sorted.remove(sorted.lastKey());
        }
        return mostCommonColors;
    }

    public int[][][] modifiedPixelGrid(int[][][] pixelGrid, ArrayList<Integer> mostCommonColors) {
        for (int i = 0; i < pixelGrid.length; i++) {
            for (int j = 0; j < pixelGrid[i].length; j++) {
                int minDist = 1000000000;
                int nearestColor = Color.WHITE;
                for (int k = 0; k < mostCommonColors.size(); k++) {
                        if ((int)(Math.pow(pixelGrid[i][j][0]-Color.red(mostCommonColors.get(k)),2)+Math.pow(pixelGrid[i][j][1]-Color.green(mostCommonColors.get(k)),2)+Math.pow(pixelGrid[i][j][2] - Color.blue(mostCommonColors.get(k)),2)) < minDist) {
                            minDist = (int)(Math.pow(pixelGrid[i][j][0]-Color.red(mostCommonColors.get(k)),2)+Math.pow(pixelGrid[i][j][1]-Color.green(mostCommonColors.get(k)),2)+Math.pow(pixelGrid[i][j][2] - Color.blue(mostCommonColors.get(k)),2));
                            nearestColor = mostCommonColors.get(k);
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
                        for (int k = 0; k < mostCommonColors.size(); k++) {
                            if (selectedColor == mostCommonColors.get(k)) {
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
            for (int i = 0; i < mostCommonColors.size(); i++) {
                TextView e = (TextView) tRow.getChildAt(i);
                int left = e.getLeft();
                int right = e.getRight();
                int top = e.getTop();
                int bottom = e.getBottom();
                Rect rect = new Rect(left, top, right, bottom);
                if (rect.contains(posOnSelect[0], posOnSelect[1])) {
                    selectedColor = mostCommonColors.get(i);
                }
            }

            return true;
        }};

}
