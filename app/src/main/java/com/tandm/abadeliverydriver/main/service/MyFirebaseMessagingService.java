package com.tandm.abadeliverydriver.main.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tandm.abadeliverydriver.BuildConfig;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.receiver.AcceptShipmentReceiver;
import com.tandm.abadeliverydriver.main.splash.SplashActivity;

import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "com.tandm.abadeliverydriver.main.service.ABA";
    private static final String CHANNEL_NAME = "ABADeli";
    private final static String default_notification_channel_id = "default";
    Uri sound;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Map<String, String> receivedMap = remoteMessage.getData();
            String youtubeURL = receivedMap.get("body");
            String title = receivedMap.get("title");

            if (youtubeURL.contains("TIN BÁO CHUYẾN:")) {
                generateNotification2(youtubeURL, title, m);
            } else {
                generateNotification(youtubeURL, title, m);
            }

        }

    }

    private void generateNotification(String body, String title, int id) {

        Intent intent;
        String[] parts = body.split(" ");
        String lastWord = parts[parts.length - 6];
        Log.d("nhacnho", lastWord.trim());
        if (body.contains("SE Nhắc Nhở")) {
            intent = new Intent(this, SplashActivity.class);
            intent.putExtra("atmshipmentid", lastWord);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.co_chuyen);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, default_notification_channel_id)
                .setSmallIcon(R.drawable.logoaba)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setOngoing(false)
                .setSound(sound)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(pendingIntent);

        createNotificationChannel(notificationBuilder, id);

    }


    private void generateNotification2(String body, String title, int id) {

        Intent acceptShipmentIntent = new Intent(this, AcceptShipmentReceiver.class);
        acceptShipmentIntent.setAction(AcceptShipmentReceiver.ACTION_ACCEPT_SHIPMENT);
        acceptShipmentIntent.putExtra("id", id);

        PendingIntent acceptShipmentPendingIntent = PendingIntent.getBroadcast(this, 0, acceptShipmentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.co_chuyen);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id)
                .addAction(R.drawable.remove, "Kiểm Tra", acceptShipmentPendingIntent)
                .setSmallIcon(R.drawable.logoaba)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .setSound(sound)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setTicker(body)
                .setWhen(System.currentTimeMillis());

        createNotificationChannel(builder, id);
    }

    private void createNotificationChannel(NotificationCompat.Builder notificationBuilder, int idNotification) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationChannel.setSound(sound, audioAttributes);
            notificationBuilder.setChannelId(CHANNEL_ID);
            assert mNotificationManager != null;
            mNotificationManager.cancelAll();
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;

//        if (NOTIFICATION_ID > 1073741824) {
//            NOTIFICATION_ID = 0;
//        }
//        mNotificationManager.notify(NOTIFICATION_ID++, notificationBuilder.build());

        mNotificationManager.notify(idNotification, notificationBuilder.build());
    }


}
