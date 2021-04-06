package com.sapirn_moshet.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;

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
        this.createBricks();

    }
    public void draw(Canvas canvas)
    {
        for (int i=0;i<this.ROWS;i++) {
            for (int j = 0; j < this.COLS; j++) {
                if (bricks[i][j]!=null) {
                    bricks[i][j].draw(canvas);
                }
            }
        }
    }
    private void createBricks(){
        float x=0,y=250;
        for (int i=0;i<this.ROWS;i++) {
            for (int j = 0; j < this.COLS; j++) {
                bricks[i][j] = new Brick(x, y, this.hight / 20, this.width / this.COLS, Color.RED);
                x = x + this.width / this.COLS + 5;
            }
            x=0;
            y = y+this.hight/20+5;
        }
    }
    public boolean collideWith(Ball ball) {
        for (int i=0;i<this.ROWS;i++) {
            for (int j = 0; j < this.COLS; j++) {
                if (bricks[i][j]!=null) {
                    if (bricks[i][j].collideWith(ball)) {
                        bricks[i][j] = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
