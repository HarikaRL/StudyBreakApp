package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

// Note: doesn't completely work right now; I need to access the bitmap created in the onCreate
// method in my touch listener in order to change color on touch, but I'm not sure how to do that.
// Those lines (lines 112 and 113) are commented out right now.

public class PaintByNumbers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = findViewById(R.id.ImgBeingPainted);
        Bitmap bit = Bitmap.createBitmap(1000, 1920, Bitmap.Config.ARGB_8888);
        int[][][] pixelGrid = getPixelGrid(bit);
        bit = clearImg(bit);
        img.setImageBitmap(bit);
        img.setOnTouchListener(imgSourceOnTouchListener);
    }

    public static int getScreenHeight() {
        float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        float aspectRatio  = screenWidth/screenHeight;

        int modifiedScreenHeight = 1000;
        return modifiedScreenHeight;
    }

    public static int getScreenWidth() {
        float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        float aspectRatio  = screenWidth/screenHeight;
        int modifiedScreenHeight = 1000;
        int modifiedScreenWidth = (int) (modifiedScreenHeight * aspectRatio);
        return modifiedScreenWidth;
    }

    public int[][][] getPixelGrid(Bitmap bitmap) {
        int height = getScreenHeight()/30;
        int width = getScreenWidth()/30;
        int[][][] pixelGrid = new int[height][width][3];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int redSum = 0;
                int blueSum = 0;
                int greenSum = 0;
                for (int k = 0; k < 30; k++) {
                    for (int l = 0; l < 30; l++) {
                        int pixel = bitmap.getPixel(30*i+k, 30*j + l);
                        redSum += Color.red(pixel);
                        blueSum += Color.blue(pixel);
                        greenSum += Color.green(pixel);
                    }
                }
                pixelGrid[i][j][0] = Math.round((float)redSum/900);
                pixelGrid[i][j][1] = Math.round((float)greenSum/900);
                pixelGrid[i][j][2] = Math.round((float)blueSum/900);
            }
        }
        return pixelGrid;
    }

    public Bitmap clearImg(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int[] pixels = new int[height*width];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = Color.WHITE;
        }
        Bitmap ret = Bitmap.createBitmap(width, height, bitmap.getConfig());
        ret.setPixels(pixels, 0, width, 0,0, width, height);
        return ret;
    }

    public void setColor(int x, int y, Bitmap bitmap, int[][][] pixelGrid) {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                bitmap.setPixel(30*x + i, 30*y + i, Color.rgb(pixelGrid[x][y][0], pixelGrid[x][y][1], pixelGrid[x][y][2]));
            }
        }
    }

    public int[] getCoarseTouchPosition(View v, MotionEvent e) {
        int[] pos = new int[2];
        pos[0] = (int)(e.getRawX()/30);
        pos[1] = (int)(e.getRawY()/30);
        return pos;
    }

    View.OnTouchListener imgSourceOnTouchListener= new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            int[] pos = getCoarseTouchPosition(view, event);

            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    // bit.setPixel(pos[0]*30+i, pos[1]*30+i, Color.argb(pixelGrid[pos[0]][pos[1]][0], pixelGrid[pos[0]][pos[1]][1], pixelGrid[pos[0]][pos[1]][2]));
                    // img.setImageBitmap(bit);
                }
            }

            return true;
        }};

}
