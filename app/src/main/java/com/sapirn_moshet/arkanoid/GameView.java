package com.sapirn_moshet.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class GameView extends View {

    private static int GET_READY = 1;
    private static int PLAYING = 2;
    private static int GAME_OVER = 3;

    private int gameState;

    private Paddle paddle;
    private Paint paddlePaint, textPaint;
    private int bgColor;
    private boolean isDraging;
    private int COLS,ROWS;
    Thread thread;
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Random r = new Random();
        ROWS =  r.nextInt(6-2) + 2;
        COLS =  r.nextInt(7-3) + 3;



        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        isDraging = false;
        gameState = GET_READY;
        bgColor = Color.BLACK;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("mylog", "onSizeChanged() w=" + w + ", h="+h);

        // create balls object
        paddle = new Paddle(w/2,h-150,h/40,w/COLS, Color.YELLOW);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);

        if (gameState == GET_READY) {
            Log.d("mylog", "width: "+String.valueOf(getWidth()));

            canvas.drawText("!PLAY to Click", getWidth() / 2, getHeight() / 2, textPaint);
        }

        if (gameState == PLAYING) {
            paddle.draw(canvas);

            // check collision
//            if (yellowBall.collideWith(redBall))
//            {
//                Log.d("mylog", "GAME_OVER yellowBall.collideWith(redBall)");
//                gameState = GAME_OVER_STATE;
//            }

            invalidate();
        }

        if (gameState == GAME_OVER)
            canvas.drawText("GAME OVER", getWidth() / 2, getHeight() / 2, textPaint);
    }
    public boolean onTouchEvent(MotionEvent event)
    {
        float tx = event.getX();
        float ty = event.getY();
        Log.d("mylog", "onTouchEvent() tx=" + (int)tx + ", ty="+ (int)ty);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(gameState == GET_READY || gameState == GAME_OVER)
                {
                    gameState = PLAYING;
                    Log.d("mylog", "width: "+String.valueOf(getWidth()));
                    paddle.setX((float)getWidth()/2);
                    invalidate();
                }
                else
                {
                    // check if red ball catch for dragging
                    if(!isDraging) {
                        isDraging = true;
                        if(thread == null) {
                            Log.d("mylog", ">>> new thread");
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (isDraging) {
                                        paddle.move(getWidth(),tx);
                                        SystemClock.sleep(100);
                                    }
                                    thread.interrupt();
                                    thread=null;
                                }
                            });
                            thread.start();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d("mylog", "MotionEvent.ACTION_MOVE ");

                if(isDraging)
                {
                    paddle.move(getWidth(),tx);
                }
                break;
            case MotionEvent.ACTION_UP:
                isDraging = false;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + event.getAction());
        }
        return true;
    }



}
