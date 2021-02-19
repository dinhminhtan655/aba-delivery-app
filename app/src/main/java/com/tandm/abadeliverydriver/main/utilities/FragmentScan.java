package com.tandm.abadeliverydriver.main.utilities;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

public class FragmentScan extends IntentIntegrator {


    Fragment fragment;

    public FragmentScan(Fragment fragment) {
        super(fragment.getActivity());

        this.fragment =fragment;
    }


    @Override
    protected void startActivityForResult(Intent intent, int code) {
        fragment.startActivityForResult(intent, code);
    }

}
