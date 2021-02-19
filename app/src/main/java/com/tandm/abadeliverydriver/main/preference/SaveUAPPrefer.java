package com.tandm.abadeliverydriver.main.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveUAPPrefer {

    public static String OBJECT_USER = "user1";
    public static String USERNAME = "username1";
    public static String PASSWORD = "password1";



    public static boolean saveUsername1(String username, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
        return true;
    }

    public static String getUsername1(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERNAME, null);
    }


    public static boolean savePass1(String pass, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, pass);
        editor.apply();
        return true;
    }

    public static String getPass1(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PASSWORD, null);
    }
}
