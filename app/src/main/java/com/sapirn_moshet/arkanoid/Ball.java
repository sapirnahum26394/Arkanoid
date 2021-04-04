package com.sapirn_moshet.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Ball
{
    private float x, y, radius, dx, dy;
    private Paint ballPaint;

    public Ball(float x, float y, float radius, int color)
    {
        Random r = new Random();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = r.nextInt(10-(-10) - 10 );
        this.dy = (r.nextInt(10-(1) + 1 ))*-1;

        ballPaint = new Paint();
        ballPaint.setColor(color);
        ballPaint.setStrokeWidth(10);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    public void move(int w, int h,float pT,float pR,float pL)
    {
        Log.d("mylog", ">>> new ball");

        x = x + dx;
        y = y + dy;
        // check border left or right
        if(x-radius<0 || x+radius>w)
            dx = -dx;
        // bottom or top
        if(y+radius>h || y-radius<0)
            dy = -dy;
        // collision with paddle
        if(y+radius>pT && ((x-radius)>pL && (x-radius)<pR))
            dy = -dy;
    }

    public float getX()
    {
        return x;
    }
    public float getRadius() { return radius; }

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

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, ballPaint);
    }

    public boolean collideWith(Ball other)
    {
        double dist = Math.sqrt((this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y));

        if(dist<(this.radius+other.radius))
            return true;
        return false;
    }

    public boolean isInside(float tx, float ty)
    {
        double dist = Math.sqrt((this.x - tx)*(this.x - tx) + (this.y - ty)*(this.y - ty));

        if(dist<(this.radius))
            return true;
        return false;
    }

    public void changeRndColor()
    {
        ballPaint.setColor(new Random().nextInt());
    }
}
