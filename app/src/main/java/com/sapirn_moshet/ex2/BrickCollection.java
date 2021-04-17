package com.sapirn_moshet.ex2;

import android.graphics.Canvas;
import android.graphics.Color;

public class BrickCollection {
    private Brick bricks[][];
    private int ROWS,COLS,color;
    private float hight,width,bricks_height;

    public BrickCollection(int ROWS, int COLS,float hight,float width, int color){
        this.COLS = COLS;
        this.ROWS = ROWS;
        this.hight = hight;
        this.color = color;
        this.width = width;
        this.bricks_height = 0;
        this.bricks = new Brick[ROWS][COLS];
        this.createBricks();

    }
    public void draw(Canvas canvas) {
        for (int i=0;i<this.ROWS;i++) {
            for (int j = 0; j < this.COLS; j++) {
                if (bricks[i][j]!=null) {
                    bricks[i][j].draw(canvas);
                }
            }
        }
    }
    public void createBricks(){
        float x=0,y=250;
        for (int i=0;i<this.ROWS;i++) {
            for (int j = 0; j < this.COLS; j++) {
                bricks[i][j] = new Brick(x, y, this.hight / 20, this.width / this.COLS, this.color);
                x = x + this.width / this.COLS + 5;
            }
            x=0;
            y = y+this.hight/20+5;
        }
        this.bricks_height = y;
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
    public float getHeight()
    {
        return this.bricks_height;
    }
}