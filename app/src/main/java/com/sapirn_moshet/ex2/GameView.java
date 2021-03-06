package com.sapirn_moshet.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
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
    private Paint textPaint,scorePaint,circlePaint_R_Fill,circlePaint_W_Fill,circlePaint_B_Fill;
    private boolean paddleMoving;
    private final int COLS,ROWS;
    private boolean run_game;
    private Thread main_thread;
    private float tx;
    private MediaPlayer beep;

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
        scorePaint.setColor(Color.parseColor("#ED553B"));
        scorePaint.setTextSize(60);

        circlePaint_R_Fill = new Paint();
        circlePaint_R_Fill.setStyle(Paint.Style.FILL);
        circlePaint_R_Fill.setColor(Color.parseColor("#ED553B"));
        circlePaint_R_Fill.setStrokeWidth(3);

        circlePaint_W_Fill = new Paint();
        circlePaint_W_Fill.setStyle(Paint.Style.FILL);
        circlePaint_W_Fill.setColor(Color.WHITE);
        circlePaint_W_Fill.setStrokeWidth(6);

        circlePaint_B_Fill = new Paint();
        circlePaint_B_Fill.setStyle(Paint.Style.FILL);
        circlePaint_B_Fill.setColor(Color.BLACK);
        circlePaint_B_Fill.setStrokeWidth(6);

        paddleMoving = false;
        gameState = GET_READY;
        bgColor = Color.BLACK;
        beep = MediaPlayer.create(context, R.raw.beep);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        if(paddle == null)
            paddle = new Paddle(w/2,h-150,h/40,w/COLS, Color.parseColor("#3CAEA3"));
        if(ball == null)
            ball = new Ball(w/2,(h-150-h/20),h/40, Color.WHITE);
        if(bricks == null)
            bricks = new BrickCollection(this.ROWS,this.COLS,h, w);

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
//                gameState = GET_READY;
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
            Log.d("mylog", ">>> beep sound");
            beep.start();

            setScore();
            countBricks--;
            if (countBricks == 0) { //win
                gameState = GAME_OVER;
                Log.d("mylog", ">>> CHECK COLLIDE WIN");
            }
        }
        if(ball.collideWith(getHeight()-50) || paddle.checkDisqualified(ball)){
            lives--;
            if (lives>0){

                initGame();
                gameState = GET_READY;
            }
            else
                gameState = GAME_OVER;
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
                    run_game = true;
                    gameState = PLAYING;
                    initGame();
                    game_thread();
                }
                else if(gameState == GAME_OVER){
                    Log.d("mylog", ">>> GAME OVER CHECK");
                    ball.setSpeed();
                    gameState =  GET_READY;
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

        }
        return true;
    }
    public void setScore(){
        this.score += 5 * lives;
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
    public void destroy() {
        this.run_game=false;
        if(beep!=null)
            beep.stop();
    }
}
