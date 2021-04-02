package com.sapirn_moshet.arkanoid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {

    private static int GET_READY = 1;
    private static int PLAYING = 2;
    private static int GAME_OVER = 3;

    private int gameState;
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
