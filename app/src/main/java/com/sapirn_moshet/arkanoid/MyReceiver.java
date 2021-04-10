package com.sapirn_moshet.arkanoid;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private NotificationManager notificationManager;
    private int notificationID;

    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
//        Log.d("mylog", ">>>>> Action: " + action);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int batteryLevel = intent.getIntExtra("level", 0);
        if ( batteryLevel < 10 && status == 4) //UNCHARGING
            Log.d("mylog", ">>>>> NOT CHARGING AND ALSO UNDER 10 BATTERY LEVEL");



    }


}