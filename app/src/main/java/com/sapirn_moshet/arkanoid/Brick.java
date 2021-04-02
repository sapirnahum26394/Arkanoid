package com.sapirn_moshet.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

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
        brickPaint.setStrokeWidth(10);
        brickPaint.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawRect(x,y,x+w,y+h,brickPaint);
    }

    public boolean collideWith(Ball ball)
    {
//        double dist = Math.sqrt((this.x - ball.getX())*(this.x - ball.getX()) + (this.y - ball.getY())*(this.y - ball.getY()));

//        if(dist<(this.radius+other.radius))
//            return true;
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
