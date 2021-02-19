package com.tandm.abadeliverydriver.main.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpinOPFeeType {

    public static final String SPIN_CUS = "spin_op_fee_type";

    private static SharedPreferences preferences;



    public static void SaveIntFeeType(Context context, int value){
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SPIN_CUS, value);
        editor.commit();
    }

    public static int LoadIntFeeType(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getInt(SPIN_CUS, 0);
    }
}
