package com.sapirn_moshet.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

public class BrickCollection {
    private Brick bricks[][];
    private int ROWS,COLS;
    private float hight,width;
    public BrickCollection(int ROWS, int COLS,float hight,float width){
        this.COLS = COLS;
        this.ROWS = ROWS;
        this.hight = hight;
        this.width = width;
        this.bricks = new Brick[ROWS][COLS];

    }
    public void draw(Canvas canvas)
    {
        float x=0,y=250;
        for (int i=0;i<this.ROWS;i++) {
            for (int j = 0; j < this.COLS; j++) {
                Log.d("mylog", ">>> i: "+ i+" j: "+ j);
                bricks[i][j] = new Brick(x,y,this.hight/20,this.width/this.COLS, Color.RED);
                x = x+this.width/this.COLS+5;

                bricks[i][j].draw(canvas);
            }
            x=0;
            y = y+this.hight/20+5;
        }
    }
}
