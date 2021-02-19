package com.tandm.abadeliverydriver.main.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment2;

import org.greenrobot.eventbus.EventBus;

public class GPSReceiver extends BroadcastReceiver implements LocationListener {
    LocationManager locationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")){
            Toast.makeText(context, "in android.location.PROVIDERS_CHANGED",
                    Toast.LENGTH_SHORT).show();
            getLocation(context);
            Intent pushIntent = new Intent(context, MainActivity.class);
            context.startService(pushIntent);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation(Context context) {

        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                Toast.makeText(context, "Vui lòng mở GPS(Định vị) để gửi!", Toast.LENGTH_SHORT).show();
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, (LocationListener) context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        EventBus.getDefault().postSticky(new SendLocationToFragment2(location));

//        try {
//            Geocoder geocoder = new Geocoder(, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//            String address = addresses.get(0).getAddressLine(0);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}