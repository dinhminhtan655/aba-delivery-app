package com.tandm.abadeliverydriver.main.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.Shipment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;
import com.tandm.abadeliverydriver.main.login.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginPrefer {

    public static String OBJECT_USER = "user";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String CHECK_SAVE_PASS = "checksaveaccount";
    public static String CHECK_SAVE_NOTIFI = "checksavenotifi";
    public static String SAVE_LIST_P = "savelistp";
    public static String SAVE_SET_ALARM = "savesetalarm";



    public static boolean saveObject(User user, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(OBJECT_USER, json);
        editor.apply();
        return true;
    }

    public static User getObject(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(OBJECT_USER, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public static boolean deleteObject(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().remove(OBJECT_USER).apply();
        return true;
    }

    public static boolean saveUsername(String username, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
        return true;
    }

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERNAME, null);
    }

    public static boolean savePass(String pass, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, pass);
        editor.apply();
        return true;
    }

    public static String getPass(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PASSWORD, null);
    }

    public static boolean saveCheckBox(boolean c, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHECK_SAVE_PASS, c);
        editor.apply();
        return true;
    }

    public static boolean getCheckBox(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(CHECK_SAVE_PASS, false);
    }

    public static void deleteCheckBox(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().remove(CHECK_SAVE_PASS).apply();
    }

//    public static boolean saveNotifi(boolean s, Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(CHECK_SAVE_NOTIFI, s);
//        editor.apply();
//        return true;
//    }
//
//    public static boolean getNotifi(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return sharedPreferences.getBoolean(CHECK_SAVE_NOTIFI, false);
//    }
//
//    public static void deleteNotifi(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        sharedPreferences.edit().remove(CHECK_SAVE_NOTIFI).apply();
//    }


    public static void saveListP(List<Shipment> list, Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonText = gson.toJson(list);
        editor.putString(SAVE_LIST_P, jsonText);
        editor.apply();
    }

    public static List<Shipment> getListP(Context context){
        List<Shipment> list = new ArrayList<Shipment>();
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonText = sharedPreferences.getString(SAVE_LIST_P, null);
        Type type = new TypeToken<List<StoreDriver>>(){}.getType();
        list = gson.fromJson(jsonText, type);

        if (list == null){
            list = new ArrayList<>();
        }

        return list;
    }

    public static void deleteListP(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().remove(SAVE_LIST_P).apply();
    }


    public static boolean saveSetAlarm(boolean b, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SAVE_SET_ALARM, b);
        editor.apply();
        return true;
    }

    public static boolean getSetAlarm(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(SAVE_SET_ALARM, false);
    }



}
