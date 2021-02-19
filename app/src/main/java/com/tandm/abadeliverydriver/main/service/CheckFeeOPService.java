package com.tandm.abadeliverydriver.main.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.tandm.abadeliverydriver.main.receiver.CheckFeeOPBroadcastReceiver;
import com.tandm.abadeliverydriver.main.utilities.Utilities;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class CheckFeeOPService extends IntentService {


    public CheckFeeOPService() {
        super("CheckFeeOPService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, CheckFeeOPBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Utilities.triggerAtMillis, Utilities.intervalMillis, pendingIntent);
    }


}
