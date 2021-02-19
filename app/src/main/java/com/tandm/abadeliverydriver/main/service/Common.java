package com.tandm.abadeliverydriver.main.service;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

public class Common {

    public static final String KEY_REQUESTING_LOCATION_UPDATES = "LocationUpdateEnable";

    public static String getLocationText(Location mLocation) {
        return mLocation == null ? "Unknow Location" : new StringBuilder()
                .append(mLocation.getLatitude())
                .append("/")
                .append(mLocation.getLongitude())
                .toString();
    }

//    public static CharSequence getLocationTitle(LocationBackgroundService locationBackgroundService) {
//        return "Location Update:";
//    }

    public static void setRequestingLocationUpdate(Context context, boolean b) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, b)
                .apply();
    }

    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }
}
