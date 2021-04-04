package com.sapirn_moshet.arkanoid;

        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.util.Log;

public class Paddle {
    private float x, y, hight, width, dx;
    private Paint paddlePaint;

    public Paddle(float x, float y, float h, float w, int color)
    {
        Log.d("mylog", ">>> new paddle");

        this.x = x;
        this.y = y;
        this.hight = h;
        this.width = w;
        this.dx = 1;
        paddlePaint = new Paint();
        paddlePaint.setColor(color);
        paddlePaint.setTextAlign(Paint.Align.CENTER);
        paddlePaint.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawRect(x-width/2,y-hight,x+width/2,y,paddlePaint);
    }

    public boolean collideWith(Ball ball) {
        double dist = Math.sqrt((this.x - ball.getX())*(this.x - ball.getX()) + (this.y - ball.getY())*(this.y - ball.getY()));

        if(dist<(this.hight/2+ball.getRadius()))
            return true;
        return false;
    }
    public void move(int w,float tx)
    {

        if( tx>0 && tx<w/2 ) {// left
            if (x - this.width / 2 > dx && x + this.width / 2 < w+dx)
                this.setX(x - dx);
        }
        else {//right
                if (x - this.width / 2 > 0 && x + this.width / 2 < w) {
                    this.setX(x + dx);
                }
            }

    }
    public float getX()    {
        return x;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public float getW()    {
        return width;
    }
    public void setW(float w)    {
        this.width = w;
    }
    public float getH()    {
        return hight;
    }
    public void setH(float y)    {
        this.hight = hight;
    }
    public float getLeft()    {
        return x-width/2;
    }
    public float getRight()    {
        return x+width/2;
    }
    public float getTop()    {
        return y-hight;
    }

}
