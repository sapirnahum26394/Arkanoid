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
    private Ball ball;
    private BrickCollection bricks;
    private Paint textPaint;
    private int bgColor;
    private boolean isDraging;
    private final int COLS,ROWS;
    private int lives=3;
    private int score=0;
    Thread thread_paddle,thread_ball,thread_bricks,thread_col_pad,thread_col_floor;
    boolean move_ball,collideBrick,collidePaddle,collideFloor;
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("mylog", ">>> GameView");

        Random r = new Random();
        ROWS =  r.nextInt(6-2) + 2;
        COLS =  r.nextInt(7-3) + 3;
        Log.d("mylog", ">>> row: "+ ROWS);
        Log.d("mylog", ">>> col: "+ COLS);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        isDraging = false;
        gameState = GET_READY;
        bgColor = Color.BLACK;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        Log.d("mylog", ">>> onSizeChanged");
        Log.d("mylog", ">>> h: "+ h);

        super.onSizeChanged(w, h, oldw, oldh);

        paddle = new Paddle(w/2,h-150,h/40,w/COLS, Color.YELLOW);
        // TODO: check why on every move of the ball the ball constructor os call
        ball = new Ball(w/2,(h-150-h/20),h/40, Color.BLUE);
        bricks = new BrickCollection(this.ROWS,this.COLS,h, w);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        if (gameState == GET_READY) {
            canvas.drawText("!PLAY to Click", getWidth() / 2, getHeight() / 2, textPaint);
            paddle.draw(canvas);
            ball.draw(canvas);
            bricks.draw(canvas);
        }

        if (gameState == PLAYING) {
            paddle.draw(canvas);
            ball.draw(canvas);
            bricks.draw(canvas);
            checkCollitionBrick();
            checkCollitionPaddle();
            checkCollitionFloor();
            moveBall();
            invalidate();
        }

        if (gameState == GAME_OVER) {
            // TODO: check if need to call the constructor or need to reset variables
            canvas.drawText("GAME OVER", getWidth() / 2, getHeight() / 2, textPaint);
        }
    }
    private void checkCollitionFloor() {
        collideFloor = true;
        if(thread_col_floor == null) {
            Log.d("mylog", ">>> new thread_col_floor");
            thread_col_floor = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (collideFloor) {
                        if(ball.collideWith(getHeight(),getWidth())){
                            move_ball = false;
                            lives--;
                            initGame();
                            Log.d("sapir", "lives: "+lives);
                            if (lives==0) {
                                gameState = GAME_OVER;
                                collideFloor=false;
                            }
                            postInvalidate();
                        }
                        SystemClock.sleep(10);
                    }
                    thread_col_floor.interrupt();
                    thread_col_floor=null;
                }
            });
            thread_col_floor.start();
        }
    }
    private void initGame() {
        paddle.setX((float)getWidth()/2);
        ball.setX((float)getWidth()/2);
        ball.setY(getHeight()-150-getHeight()/20);
    }
    private void checkCollitionPaddle() {
        collidePaddle = true;
        if(thread_col_pad == null) {
            Log.d("mylog", ">>> new thread_col_paddle");
            thread_col_pad = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (collidePaddle) {
                        if(paddle.collideWith(ball)){
                            move_ball = false;
                            ball.setDY(ball.getDY()*(-1));
                            postInvalidate();
                        }
                        SystemClock.sleep(10);
                    }
                    thread_col_pad.interrupt();
                    thread_col_pad=null;
                }
            });
            thread_col_pad.start();
        }
    }
    private void checkCollitionBrick(){
        collideBrick = true;
        if(thread_bricks == null) {
            Log.d("mylog", ">>> new thread_bricks");
            thread_bricks = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (collideBrick) {
                        if(bricks.collideWith(ball)){
                            setScore();
                            Log.d("sapir", "score: "+score);
                            move_ball = false;
                            postInvalidate();
                        }
                        SystemClock.sleep(10);
                    }
                    thread_bricks.interrupt();
                    thread_bricks=null;
                }
            });
            thread_bricks.start();
        }
    }
    private void moveBall(){
        move_ball = true;
        if(thread_ball == null) {
            Log.d("mylog", ">>> new thread_ball");
            thread_ball = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (move_ball) {
                        ball.move(getWidth(),getHeight());
                        postInvalidate();
                        SystemClock.sleep(10);
                    }
                    thread_ball.interrupt();
                    thread_ball=null;
                }
            });
            thread_ball.start();
        }
    }
    private void movePaddle(float tx){
        if(!isDraging) {
            isDraging = true;
            if(thread_paddle == null) {
                thread_paddle = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isDraging) {
                            paddle.move(getWidth(),tx);
                            postInvalidate();
                            SystemClock.sleep(1);
                        }
                        thread_paddle.interrupt();
                        thread_paddle=null;
                    }
                });
                thread_paddle.start();
            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        float tx = event.getX();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(gameState == GET_READY || gameState == GAME_OVER)
                {
                    gameState = PLAYING;
                    this.score=0;
                    this.lives=3;
                    initGame();
                    bricks.createBricks();
                    invalidate();
                }
                else
                {
                    movePaddle(tx);
                }
                break;
                // TODO ask Ilan what to to do in movement
            case MotionEvent.ACTION_MOVE:
                isDraging = false;
                movePaddle(tx);
                break;
            case MotionEvent.ACTION_UP:
                isDraging = false;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + event.getAction());
        }
        return true;
    }
    public void setScore(){
        this.score+=5*lives;
    }
    public void drawLive(){

    }
    public void drawScore(){

    }
}