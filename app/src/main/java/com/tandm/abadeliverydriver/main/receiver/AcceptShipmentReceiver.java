package com.tandm.abadeliverydriver.main.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tandm.abadeliverydriver.main.home.MainActivity;

public class AcceptShipmentReceiver extends BroadcastReceiver {

    public static final String ACTION_ACCEPT_SHIPMENT = "com.tandm.abadeliverydriver.main.receiver.ACCEPT_SHIPMENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ACCEPT_SHIPMENT.equals(action)) {
//                String atm = intent.getStringExtra("atm");
                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
//                i.setClassName(".main.receiver.AcceptShipmentReceiver", ".main.home.MainActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("acceptshipment", true);
                context.startActivity(i);
                int id = intent.getIntExtra("id", 0);
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(id);
            }
        }
    }

}
