package com.sapirn_moshet.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Ball
{
    private float x, y, radius, dx, dy;
    private Paint ballPaint;

    public Ball(float x, float y, float radius, int color) {

        this.x = x;
        this.y = y;
        this.radius = radius;
        setSpeed();
        ballPaint = new Paint();
        ballPaint.setColor(color);
        ballPaint.setStrokeWidth(10);
        ballPaint.setStyle(Paint.Style.FILL);
    }
    public void move(int w, int h) {
        x = x + dx;
        y = y + dy;
        // check border left or right
        if(x-radius<0 || x+radius>w)
            dx = -dx;
        // bottom or top
        if(y+radius>h || y-radius<0)
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
    public void setY(float y)
    {
        this.y = y;
    }
    public float getY()
    {
        return y;
    }
    public float getDY()
    {
        return dy;
    }
    public void setDY(float dy){
        this.dy = dy;
    }
    public float getDX()
    {
        return dx;
    }
    public void setDX(float dx)
    {
        this.dx = dx;
    }
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, ballPaint);
    }
//    public boolean collideWith(int height, int width) {
    public boolean collideWith(int height) {
        if((height-this.y)<=this.getRadius()){
            return true;
        }
        return false;
    }
    public void setSpeed(){
        double angle = 90;
        float speed = (float) ((Math.random()+1) * 5);
        do{
            angle = (Math.random()*(150-30)+30);
        }
        while (angle > 85 && angle < 95);
        this.dx = ((float) Math.cos(Math.toRadians(angle))*speed);
        this.dy = ((float) -Math.sin(Math.toRadians(angle))*speed);
        Log.d("mylog", "angle: "+angle+" speed: "+speed);
    }
}
