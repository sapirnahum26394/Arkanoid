package com.sapirn_moshet.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Brick {
    private float x, y, h, w, dx, dy;
    private Paint brickPaint;

    public Brick(float x, float y, float h, float w, int color)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        brickPaint = new Paint();
        brickPaint.setColor(color);
        brickPaint.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawRect(x,y,x+w,y+h,brickPaint);
    }

    public boolean collideWith(Ball ball) {
        float bx = ball.getX();
        float by = ball.getY();
        float br = ball.getRadius();

        double leftCornerUp =Math.sqrt((bx - this.x)*(bx - this.x) + (by - this.y)*(by - this.y));
        double rightCornerUp =Math.sqrt((bx - (this.x+this.w))*(bx - (this.x+this.w)) + (by - this.y)*(by - this.y));
        double leftCornerDown =Math.sqrt((bx - this.x)*(bx - this.x) + (by - (this.y+this.h))*(by - (this.y+this.h)));
        double rightCornerDown =Math.sqrt((bx - (this.x+this.w))*(bx - (this.x+this.w)) + (by - (this.y+this.h))*(by - (this.y+this.h)));

        if(((bx-br)>=(this.x) && (bx+br)<=(this.x+this.w)) && ((by-br)<=(this.y+this.h) && (by+br)>=(this.y))){
            ball.setDY(ball.getDY() * (-1));
            return true;
        }
        else if(leftCornerUp<=(br)||rightCornerUp<=(br)||leftCornerDown<=(br)||rightCornerDown<=(br)) {
            ball.setDY(ball.getDY() * (-1));
            ball.setDX(ball.getDX() * (-1));
            return true;
        }
        return false;
    }

    public float getX()
    {
        return x;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public float getY()
    {
        return y;
    }
    public void setY(float y)
    {
        this.y = y;
    }
    public float getW()
    {
        return w;
    }
    public void setW(float w)
    {
        this.w = w;
    }
    public float getH()
    {
        return h;
    }
    public void setH(float y)
    {
        this.h = h;
    }

}
