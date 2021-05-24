package com.example.studybreakapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.TreeMap;

public class PaintByNumbers extends AppCompatActivity {

    /*
    The below array is an array of 117 common colors. These colors are the only possible colors
    for the paint-by-numbers grids, to ensure that not too many colors are used in the final
    picture.
     */

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

    private ImageView imgBeingPainted;
    private Bitmap bitmapFromImg;
    private int[][][] pixelGrid;
    private TableLayout paintingGrid;
    private RelativeLayout wholeScreen;
    private int selectedColor = Color.WHITE;
    private ArrayList<Integer> mostCommonColors;
    private TableRow colorSelection;
    private TableRow colorLabels;
    private String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M"};
    private Button timeLeft;
    private Button backToSelectImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_by_numbers);
        imgBeingPainted = findViewById(R.id.ImgBeingPainted);
        timeLeft = findViewById(R.id.time_left);
        backToSelectImg = findViewById(R.id.backToSelectImage);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS",
                Context.MODE_PRIVATE);
        String oneMinute = sharedPreferences.getString("Value", "");

        backToSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaintByNumbers.this, SelectImage.class);
                startActivity(intent);
            }
        });

        /*
        The below code allows the user to click a button to see a toast that alerts them
        how much time is remaining on their break.
         */

        timeLeft.setOnClickListener(new View.OnClickListener() {
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
        The below code provides a one-minute warning to the user through a toast.
         */

        if(oneMinute.equals("one"))
        {
            Context context = getApplicationContext();
            CharSequence text = "One minute remaining.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        /*
        The below if statements load a different image depending on what text was passed from the
        SelectImage activity. This ensures the right image is loaded for painting.
         */

        if (getIntent().getStringExtra("image").equals("1")) {
            imgBeingPainted.setImageResource(R.drawable.farmcropped);
        }
        if (getIntent().getStringExtra("image").equals("2")) {
            imgBeingPainted.setImageResource(R.drawable.plainscropped);
        }
        if (getIntent().getStringExtra("image").equals("3")) {
            imgBeingPainted.setImageResource(R.drawable.sunflower);
        }
        if (getIntent().getStringExtra("image").equals("4")) {
            imgBeingPainted.setImageResource(R.drawable.redpandacropped);
        }
        if (getIntent().getStringExtra("image").equals("5")) {
            imgBeingPainted.setImageResource(R.drawable.penguinv2);
        }
        if (getIntent().getStringExtra("image").equals("6")) {
            imgBeingPainted.setImageResource(R.drawable.koala);
        }
        if (getIntent().getStringExtra("image").equals("7")) {
            imgBeingPainted.setImageResource(R.drawable.vintagecarcropped);
        }
        if (getIntent().getStringExtra("image").equals("8")) {
            imgBeingPainted.setImageResource(R.drawable.treecropped);
        }
        imgBeingPainted.setScaleType(ImageView.ScaleType.FIT_XY); //Make images fit grid properly
        wholeScreen = findViewById(R.id.MainLayout);
        paintingGrid= findViewById(R.id.PaintGrid);
        /*
        The below for loops create a grid of 850 textViews that comprise the paint-by-numbers grid.
        This is done with a TableLayout that has 34 rows and 25 columns.
         */
        for (int i = 0; i < 34; i++) {
            TableRow paintingGridRow = new TableRow(paintingGrid.getContext());
            paintingGridRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            paintingGrid.addView(paintingGridRow);
            for (int j = 0; j < 25; j++) {
                TextView paintingGridCell = new TextView(paintingGrid.getContext());
                TableRow.LayoutParams rp = new TableRow.LayoutParams(0,
                        TableRow.LayoutParams.WRAP_CONTENT);
                rp.weight = 1;
                paintingGridCell.setLayoutParams(rp);
                paintingGridCell.setText("A");
                paintingGridCell.setGravity(Gravity.CENTER);
                paintingGridRow.addView(paintingGridCell);
            }
        }
        bitmapFromImg = loadBitmapFromView(imgBeingPainted); //Convert image to bitmap
        pixelGrid = getPixelGrid(bitmapFromImg); //Compress image into pixel grid
        mostCommonColors = getMostCommonColors(pixelGrid); //Get most common colors from pixel grid
        pixelGrid = modifiedPixelGrid(pixelGrid, mostCommonColors); //Compress pixel grid further
        wholeScreen.setOnTouchListener(screenSourceOnTouchListener);
        colorSelection = findViewById(R.id.ColorSelection);
        /*
        The below for loop creates a row of colors that represent the colors available in the
        paint-by-numbers grid. This is a row of at most 13 textViews, all of which are present
        in the actual paint-by-numbers picture.
         */
        for (int i = 0; i < mostCommonColors.size(); i++) {
            TextView colorSelectionCell = new TextView(colorSelection.getContext());
            TableRow.LayoutParams rp = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT);
            rp.weight = 1;
            colorSelectionCell.setLayoutParams(rp);
            colorSelectionCell.setText("");
            colorSelectionCell.setBackgroundColor(mostCommonColors.get(i));
            colorSelection.addView(colorSelectionCell);
        }
        colorLabels = findViewById(R.id.ColorLabels);
        /*
        The below code labels each of the colors in the above row with a letter A through M,
        to correspond with the label of cells with that color in the
        paint-by-numbers grid.
         */
        for (int i = 0; i < mostCommonColors.size(); i++) {
            TextView colorLabelCell = new TextView(colorSelection.getContext());
            TableRow.LayoutParams rp = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT);
            rp.weight = 1;
            colorLabelCell.setLayoutParams(rp);
            colorLabelCell.setText(alphabet[i]);
            colorLabelCell.setGravity(Gravity.CENTER);
            colorLabels.addView(colorLabelCell);
        }
        /*
        The below for loops set each cell in the paint-by-numbers grid with a letter A through M
        depending on which color that cell should be colored with.
         */
        for (int i = 0; i < pixelGrid.length; i++) {
            for (int j = 0; j < pixelGrid[i].length; j++) {
                TableRow paintingGridRow = (TableRow) paintingGrid.getChildAt(i);
                TextView paintingGridCell = (TextView) paintingGridRow.getChildAt(j);
                for (int k = 0; k < mostCommonColors.size(); k++) {
                    if (Color.rgb(pixelGrid[i][j][0], pixelGrid[i][j][1], pixelGrid[i][j][2])
                            == mostCommonColors.get(k)) {
                        paintingGridCell.setText(alphabet[k]);
                    }
                }
            }
        }
    }

    /**
     * Creates a bitmap representing the image in a given imageView.
     * @param v This is the inputted imageView
     * @return Bitmap This is the bitmap representing the same image as v
     */

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    /**
     * Method that autofills the paint-by-numbers grid. This was purely for testing purposes, to
     * see how well the paint-by-numbers generation algorithm approximated the actual picture.
     */

    public void autoFill() {
        for (int i = 0; i < 34; i++) {
            TableRow paintingGridRow = (TableRow) paintingGrid.getChildAt(i);
            for (int j = 0; j < 25; j++) {
                TextView paintingGridCell = (TextView) paintingGridRow.getChildAt(j);
                for (int k = 0; k < mostCommonColors.size(); k++) {
                    if (alphabet[k].equals(paintingGridCell.getText())) {
                        paintingGridCell.setBackgroundColor(mostCommonColors.get(k));
                        paintingGridCell.setText("");
                    }
                }
            }
        }
    }

    /**
     * Creates a naive pixel grid from a given bitmap. The method does this by partitioning
     * the bitmap into 30 pixel by 30 pixel squares and assigning each square one of 117 common
     * colors.
     * @param bitmap The Bitmap being compressed into a pixel grid.
     * @return int[][][] The compressed pixel grid represented by a 2D grid of squares, each
     * represented by a set of three integers, the red, green, and blue values of the color in
     * that square.
     */

    public int[][][] getPixelGrid(Bitmap bitmap) {
        int height = bitmap.getHeight()/30;
        int width = bitmap.getWidth()/30;
        int[][][] pixelGrid = new int[height][width][3];
        /*
        This code receives each pixel of the bitmap, finds the closest color to it in the array of
        117 common colors commonColors, and keeps track of how often each color shows up in an
        array.
        */
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
                /*
                The most common color in each 30 by 30 group of pixels becomes the color of one
                cell in the final paint-by-numbers grid (which is stored in pixelGrid).
                 */
                int maxPos = 0;
                for (int k = 1; k < commonColors.length; k++) {
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

    /**
     * Takes a pixel grid with 117 colors and finds the 13 most common colors in the grid.
     * @param pixelGrid The given naive pixel grid.
     * @return ArrayList<Integer> A list of at most 13 of the most common colors in the grid.
     */

    public ArrayList<Integer> getMostCommonColors(int[][][] pixelGrid) {
        /*
        Find number of occurrences of each color inside the naive pixelGrid with at most 117
        different colors.
         */
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
        /*
        Colors are put into a TreeMap to be sorted, then the (up to) 13 most common colors are
        separated.
         */
        TreeMap<Integer, Integer> sorted = new TreeMap<>();
        for (int i = 0; i < commonColors.length; i++) {
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

    /**
     * Takes a naive pixel grid with at most 117 colors and converts it to a pixel grid with at most 13 colors.
     * @param pixelGrid The naive pixel grid with at most 117 colors
     * @param mostCommonColors The (up to) 13 most common colors in the grid.
     * @return int[][][] A compressed pixel grid with at most 13 colors
     */

    public int[][][] modifiedPixelGrid(int[][][] pixelGrid, ArrayList<Integer> mostCommonColors) {
        /*
        This code takes the naive pixel grid with at most 117 colors and finds the closest color
        to each cell from the up to 13 most common colors found using the getMostCommonColors
        method.
         */
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

    View.OnTouchListener screenSourceOnTouchListener= new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            int[] posOnPic = new int[2];
            int[] tablePos = new int[2];
            paintingGrid.getLocationOnScreen(tablePos);
            posOnPic[0] = (int)(event.getRawX()-tablePos[0]);
            posOnPic[1] = (int)(event.getRawY()-tablePos[1]);
            TableRow paintingGridRow = (TableRow) paintingGrid.getChildAt(0);
            TextView paintingGridCell = (TextView) paintingGridRow.getChildAt(0);
            int leftInit = paintingGridCell.getLeft();
            int rightInit = paintingGridCell.getRight();
            int topInit = paintingGridCell.getTop();
            int bottomInit = paintingGridCell.getBottom();
            /*
            This code checks which cell of the paint-by-numbers grid was actually pressed.
             */
            for (int i = 0; i < pixelGrid.length; i++) {
                for (int j = 0; j < pixelGrid[i].length; j++) {
                    int left = leftInit + j*(rightInit - leftInit);
                    int right =  (j+1)*(rightInit - leftInit) ;
                    int top = topInit + i*(bottomInit - topInit);
                    int bottom = (i+1)*(bottomInit - topInit);
                    Rect rect = new Rect(left, top, right, bottom);
                    if (rect.contains(posOnPic[0], posOnPic[1])) {
                        paintingGridRow = (TableRow) paintingGrid.getChildAt(i);
                        paintingGridCell = (TextView) paintingGridRow.getChildAt(j);
                        for (int k = 0; k < mostCommonColors.size(); k++) {
                            if (selectedColor == mostCommonColors.get(k)) {
                                if (paintingGridCell.getText() == alphabet[k]) {
                                    paintingGridCell.setText("");
                                    paintingGridCell.setBackgroundColor(selectedColor);
                                }
                            }
                        }
                    }
                }
            }

            /*
            This code checks which color in the selectColor row was pressed, if applicable.
            If a color was pressed, the user's selected color switches to that color.
             */

            int[] rowPos = new int[2];
            int[] posOnSelect = new int[2];
            colorSelection.getLocationOnScreen(rowPos);
            posOnSelect[0] = (int)(event.getRawX() - rowPos[0]);
            posOnSelect[1] = (int)(event.getRawY() - rowPos[1]);
            for (int i = 0; i < mostCommonColors.size(); i++) {
                TextView colorSelectionCell = (TextView) colorSelection.getChildAt(i);
                int left = colorSelectionCell.getLeft();
                int right = colorSelectionCell.getRight();
                int top = colorSelectionCell.getTop();
                int bottom = colorSelectionCell.getBottom();
                Rect rect = new Rect(left, top, right, bottom);
                if (rect.contains(posOnSelect[0], posOnSelect[1])) {
                    selectedColor = mostCommonColors.get(i);
                }
            }

            return true;
        }
    };
}
