package com.tandm.abadeliverydriver.main.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.tandm.abadeliverydriver.main.home.fragmenthomedriver.HomeDriverFragment.NOTIFICATION_CHANNEL_ID;


public class NotificationADCAD extends BroadcastReceiver {

//    public static final String NOTIFICATION_ID = "com.tandm.abadeliverydriver.main.service.ABA1";
//    public static final String NOTIFICATION = "ABA1";

    public static String NOTIFICATION_ID = "com.tandm.abadeliverydriver.main.service.ABA";
    public static String NOTIFICATION = "ABA";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "ABA", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        assert notificationManager != null;
        notificationManager.notify(id, notification);
    }
}
