package com.sapirn_moshet.ex2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;
    private GameView game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        broadcastSetup();
        setContentView(R.layout.activity_main);
        game = (GameView) findViewById(R.id.game_view_ID);

    }

    private void broadcastSetup()
    {
        batteryReceiver = new MyReceiver();
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(batteryReceiver);
    }

    protected void onResume()
    {
        super.onResume();
        this.game.resumeView();
        Log.d("mylog", ">>> onResume()");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.game.saveView();
        Log.d("mylog", ">>> onPause()");
    }
    protected void onDestroy() {
        super.onDestroy();
        this.game.destroy();
        Log.d("mylog", ">>> onDestroy()");
    }

}