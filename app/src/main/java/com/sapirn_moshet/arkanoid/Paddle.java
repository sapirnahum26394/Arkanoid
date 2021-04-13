package com.sapirn_moshet.arkanoid;

        import android.graphics.Canvas;
        import android.graphics.Paint;

public class Paddle {
    private float x, y, hight, width, dx;
    private Paint paddlePaint;
    public Paddle(float x, float y, float h, float w, int color)    {
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
    public void draw(Canvas canvas){
        canvas.drawRect(x-width/2,y-hight,x+width/2,y,paddlePaint);
    }
    public boolean collideWith(Ball ball) {
        float bx = ball.getX();
        float by = ball.getY();
        float br = ball.getRadius();
        // TODO : check boundaries

        double leftCorner =Math.sqrt((bx - (this.x-this.width/2))*(bx - (this.x-this.width/2)) + (by - (this.y-this.hight))*(by - (this.y-this.hight)));
        double rightCorner =Math.sqrt((bx - (this.x+this.width/2))*(bx - (this.x+this.width/2)) + (by - (this.y-this.hight))*(by - (this.y-this.hight)));

        if(((bx-br+ball.getDX())>=(this.x-this.width/2) && (bx+br+ball.getDX())<=(this.x+this.width/2)) && ((by+br+ball.getDY())>=(this.y-this.hight) && (by-br+ball.getDY())<=(this.y))){
            return true;
        }
        else if(((by+br+ball.getDY())<=(this.y) && (by-br+ball.getDY())>=(this.y-this.hight)) && ((bx-br+ball.getDX())<=(this.x-this.width/2) && (bx+br+ball.getDX())>=(this.x+this.width/2))){
            return true;
        }
        else if(leftCorner<=(br))
            return true;
        else if(rightCorner<=(br))
            return true;

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

    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
