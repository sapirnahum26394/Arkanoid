package com.sapirn_moshet.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Paddle {
    private float x, y, height, width, dx;
    private Paint paddlePaint;
    public Paddle(float x, float y, float h, float w, int color)    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.dx = 6;
        paddlePaint = new Paint();
        paddlePaint.setColor(color);
        paddlePaint.setTextAlign(Paint.Align.CENTER);
        paddlePaint.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas){
        canvas.drawRect(x-width/2,y-height,x+width/2,y,paddlePaint);
    }
    public boolean collideWith(Ball ball) {
//        if(get_right() >= ball.get_left() && get_left() <= ball.get_right()
//                    && get_bottom() >= ball.get_top() && get_top() <= ball.get_bottom())
//            return true;
//        if (ball.getX()<x)
//            return true;
//        return false;
//    }

        float bx = ball.getX();
        float by = ball.getY();
        float br = ball.getRadius();

        double Distance_x = Math.abs(bx - this.x) + Math.abs(ball.getDX());
        double Distance_y = Math.abs(by - this.y + this.height / 2) + Math.abs(ball.getDY());
        double corners = (Distance_x - this.width / 2) * (Distance_x - this.width / 2) + (Distance_y - this.height / 2) * (Distance_y - this.height / 2);

        if (Distance_x > (this.width / 2 + br)) {
            return false;
        }
        if (Distance_y > (this.height / 2 + br)) {
            return false;
        }

        if (Distance_y <= (this.height / 2 + br) && (by + br - ball.getDY()) <= (y - height)) {
            return true;
        }
        if (Distance_x <= (this.width / 2 + br)) {
            return true;
        }
        if (corners <= (br * br)) {
            return true;
        }

        return false;
    }
    public boolean checkDisqualified(Ball ball) {
        float by = ball.getY();
        float bx = ball.getX();
        float br = ball.getRadius();
        if ((by+br)>=(y-height) && ((bx-br)==(x-width/2) || (bx+br)==(x+width/2)))
            {
                return true;
            }
        return false;
    }
    public void move(int w,float tx) {
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
    public void setX(float x)
    {
        this.x = x;
    }

    public float get_left() {
        return x-width/2;
    }

    public float get_right() {
        return x+width/2;
    }

    public float get_top() {
        return y-height;
    }

    public float get_bottom() {
        return y;
    }
}
