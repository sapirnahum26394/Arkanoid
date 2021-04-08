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

        if(((bx-br)<(this.x+this.w) && (bx+br)>(this.x))&&((by-br)<=(this.y+this.h) && (by+br)>=(this.y))){
            ball.setDY(ball.getDY()*(-1));
            return true;
        }
        else if(((by+br)<=(this.y) && (by-br)>=(this.y+this.h)) && ((bx-br)<=(this.x+this.w) && (bx+br)>=this.x)){
            Log.d("mylog", "***************************");
            // TODO : check boundaries
            ball.setDX(ball.getDX()*(-1));
            ball.setDY(ball.getDY()*(-1));
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
