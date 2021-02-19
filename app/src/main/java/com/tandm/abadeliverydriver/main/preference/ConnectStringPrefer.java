package com.tandm.abadeliverydriver.main.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ConnectStringPrefer {

    public static String SSLORNOSSL = "sslornossl";
    public static String PORT = "port";
    public static String DOMAIN = "domain";
    public static String TESTORPRODUCT = "testorproduct";

    public static boolean saveSSLOrNoSSL(String sslOrNoSsl, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SSLORNOSSL, sslOrNoSsl);
        editor.apply();
        return true;
    }

    public static String getSSLOrNoSSL(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SSLORNOSSL, "https");
    }

    public static boolean saveDomain(String domain, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DOMAIN, domain);
        editor.apply();
        return true;
    }

    public static String getDomain(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(DOMAIN, "api-delivery.aba.com.vn:44567");
    }



}
