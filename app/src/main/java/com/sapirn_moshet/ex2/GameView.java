package com.sapirn_moshet.ex2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.util.Random;

public class GameView extends View {
    private static int GET_READY = 1;
    private static int PLAYING = 2;
    private static int GAME_OVER = 3;
    private int gameState,lives = 3,score = 0,countBricks,bgColor;
    private Paddle paddle;
    private Ball ball;
    private BrickCollection bricks;
    private Paint textPaint,scorePaint,LivesPaint,circlePaint_R_Fill,circlePaint_W_Fill,circlePaint_B_Fill;
    private boolean paddleMoving;
    private final int COLS,ROWS;
    private boolean run_game;
    private Thread main_thread;
    private float tx;
    private int touch_sound;
    SoundPool soundPool;
    Bitmap b;
    public GameView(Context context, AttributeSet attrs) throws IOException {
        super(context, attrs);

        Random r = new Random();
        ROWS = r.nextInt(6-2) + 2;
        COLS = r.nextInt(7-3) + 3;
        countBricks = ROWS * COLS;

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        scorePaint = new Paint();
        scorePaint.setColor(Color.parseColor("#ad1457"));
        scorePaint.setTextSize(60);

        circlePaint_R_Fill = new Paint();
        circlePaint_R_Fill.setStyle(Paint.Style.FILL);
        circlePaint_R_Fill.setColor(Color.parseColor("#ad1457"));
        circlePaint_R_Fill.setStrokeWidth(3);

        circlePaint_W_Fill = new Paint();
        circlePaint_W_Fill.setStyle(Paint.Style.FILL);
        circlePaint_W_Fill.setColor(Color.WHITE);
        circlePaint_W_Fill.setStrokeWidth(6);

        circlePaint_B_Fill = new Paint();
        circlePaint_B_Fill.setStyle(Paint.Style.FILL);
        circlePaint_B_Fill.setColor(Color.BLACK);
        circlePaint_B_Fill.setStrokeWidth(6);

        LivesPaint = new Paint();
        LivesPaint.setColor(Color.GREEN);
        LivesPaint.setTextSize(60);

        paddleMoving = false;
        gameState = GET_READY;
        bgColor = Color.BLACK;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        touch_sound = soundPool.load(context,R.raw.beep3,1);

        b= BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        if(paddle==null)
            paddle = new Paddle(w/2,h-150,h/40,w/COLS, Color.parseColor("#f48fb1"));
        if(ball==null)
            ball = new Ball(w/2,(h-150-h/20),h/40, Color.WHITE);
        if(bricks==null)
            bricks = new BrickCollection(this.ROWS,this.COLS,h, w,Color.parseColor("#f06292"));
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        canvas.drawText("Score: "+score,50,120,scorePaint);
        canvas.drawText("Lives: ",getWidth()-450,120,scorePaint);
        drawLive(canvas);
        paddle.draw(canvas);
        ball.draw(canvas);
        bricks.draw(canvas);
        if (gameState == GET_READY) {
            canvas.drawText("Click to PLAY!", getWidth() / 2, bricks.getHeight()+110 , textPaint);
            run_game=true;
        }
        if (gameState == PLAYING) {
            game();
        }
        if (gameState == GAME_OVER) {
            run_game=false;
            if(this.countBricks > 0) {
                canvas.drawText("GAME OVER -You Loss!", getWidth() / 2, bricks.getHeight()+110, textPaint);
            }
            else{
                canvas.drawText("GAME OVER - You WIN!", getWidth() / 2, getHeight() / 2, textPaint);
            }
        }
    }
    private void game_thread(){
        if(main_thread == null) {
            main_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (run_game) {
                        postInvalidate();
                        SystemClock.sleep(5);
                    }
                    main_thread.interrupt();
                    main_thread=null;
                }
            });
            main_thread.start();
        }
    }
    private void game(){
        ball.move(getWidth(),getHeight());
        if(paddleMoving)
            paddle.move(getWidth(),tx);
        if(paddle.collideWith(ball)){
            ball.setDY(ball.getDY()*(-1));
        }
        if(bricks.collideWith(ball)){
            soundPool.play(touch_sound, 1, 1, 0, 0, 1);
            setScore();
            countBricks--;
            if (countBricks == 0) {
                gameState = GAME_OVER;
            }
        }
        if(ball.collideWith(getHeight(),getWidth())){
            lives--;
            initGame();
            gameState = GET_READY;
            if (lives==0) {
                gameState = GAME_OVER;
            }
        }
    }
    private void initGame() {
        paddle.setX((float)getWidth()/2);
        ball.setX((float)getWidth()/2);
        ball.setY(getHeight()-150-getHeight()/20);
    }
    public boolean onTouchEvent(MotionEvent event) {
        float tx = event.getX();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(gameState == GET_READY )
                {
                    ball.setSpeed();
                    run_game=true;
                    gameState = PLAYING;
                    initGame();
                    game_thread();
                }
                else if(gameState == GAME_OVER){
                    ball.setSpeed();
                    gameState = PLAYING;
                    run_game=true;
                    this.countBricks = ROWS * COLS;
                    this.score = 0;
                    this.lives = 3;
                    initGame();
                    bricks.createBricks();
                    game_thread();
                }
                else
                {
                    paddleMoving=true;
                    this.tx=tx;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                paddleMoving=true;
                this.tx=tx;
                break;
            case MotionEvent.ACTION_UP:
                paddleMoving = false;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + event.getAction());
        }
        return true;
    }
    public void setScore(){
        this.score += 5*lives;
    }
    public void drawLive(Canvas canvas){
        int x = getWidth()-220;

        for(int i = 0; i < 3-lives; i++){
            canvas.drawCircle(x, 100, 35, circlePaint_R_Fill);
            canvas.drawCircle(x, 100, 25, circlePaint_B_Fill);
            x += 75;
        }
        for(int i = 0; i < lives; i++){
            canvas.drawCircle(x, 100, 35, circlePaint_R_Fill);
            canvas.drawCircle(x, 100, 25, circlePaint_W_Fill);
            x += 75;
        }

    }
    public void saveView(){
        this.run_game=false;
    }
    public void resumeView() {
        this.run_game=true;
        game_thread();
    }
}