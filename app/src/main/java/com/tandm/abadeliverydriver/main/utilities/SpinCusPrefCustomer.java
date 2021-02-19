package com.tandm.abadeliverydriver.main.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpinCusPrefCustomer {

    private static final String PREF_SPINCUS = "pref_spincus_customer_biker";
    public static final String SPIN_CUS = "spin_cus_customer_biker";

    private static SharedPreferences preferences;



    public static void SaveIntCustomer(Context context, int value){
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SPIN_CUS, value);
        editor.commit();
    }

    public static int LoadIntCustomer(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getInt(SPIN_CUS, 0);
    }

}
