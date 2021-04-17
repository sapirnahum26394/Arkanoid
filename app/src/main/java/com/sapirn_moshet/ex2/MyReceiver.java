package com.sapirn_moshet.ex2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private NotificationManager notificationManager;


    @Override

    public void onReceive(Context context, Intent intent)
    {
        notificationsSetup(context);
        int batteryLevel = intent.getIntExtra("level", 0);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (batteryLevel <= 10 && status == 4 ){
            Log.d("mylog", ">>>>> UNDER 10 LEVEL and battery is not charged");
            showNotification(context);
        }


    }
    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notify_bell)
//                .setContentTitle( "notification battery charged! ("+ notificationID +")")
                .setContentTitle( "notification battery charged!")
                .setContentText("Your battery is less than 10% please charge your phone. !")
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(1, notification);

    }
    private void notificationsSetup(Context context) {
        // 1. Get reference Notification Manager system Service
        notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        // 2. Create Notification-Channel. ONLY for Android 8.0 (OREO API level 26) and higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, 	// Constant for Channel ID
                    CHANNEL_NAME, 	// Constant for Channel NAME
                    NotificationManager.IMPORTANCE_HIGH);  // for popup use: IMPORTANCE_HIGH

            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
}