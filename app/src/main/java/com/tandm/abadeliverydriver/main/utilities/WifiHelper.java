package com.tandm.abadeliverydriver.main.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class WifiHelper {

    private static NetworkInfo networkInfo;

    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } else*/
        networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
