package com.sapirn_moshet.arkanoid;

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
//    private static final String CHANNEL_ID = "channel_main";
//    private static final CharSequence CHANNEL_NAME = "Main Channel";
//    private NotificationManager notificationManager;
//    private int notificationID;

    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


//        notificationsSetup();
        broadcastSetup();
//        notify1();
        setContentView(R.layout.activity_main);



    }
    /*
    private void notificationsSetup() {
        // 1. Get reference Notification Manager system Service
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // 2. Create Notification-Channel. ONLY for Android 8.0 (OREO API level 26) and higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, 	// Constant for Channel ID
                    CHANNEL_NAME, 	// Constant for Channel NAME
                    NotificationManager.IMPORTANCE_HIGH);  // for popup use: IMPORTANCE_HIGH

            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationID = 1;
    }
    */

   /*
    public void notify1()
    {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        // 3. Create & show the Notification. on Build.VERSION < OREO notification avoid CHANEL_ID
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notify_bell)
                .setContentTitle( "notification battery charged! ("+ notificationID +")")
                .setContentText("Your battery is less than 10% please charge your phone. !")
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(notificationID, notification);
    }
    */
    private void broadcastSetup()
    {
        // 1. Create a new Class that extends Broadcast Receiver

        // 2. Create BatteryReceiver object
        batteryReceiver = new MyReceiver();

        // 3. Create IntentFilter for BATTERY_CHANGED & AIRPLANE_MODE_CHANGED broadcast
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

//        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // 4. Register the receiver to start listening for battery change messages
        registerReceiver(batteryReceiver, filter);
        Log.d("mylog", ">>>>> onStart");
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        // 5. Un-Register the receiver to stop listening for battery change messages
        unregisterReceiver(batteryReceiver);
    }
}