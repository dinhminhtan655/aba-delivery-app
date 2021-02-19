package com.tandm.abadeliverydriver.main.api;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class RetrofitError {

    private static Snackbar snackbar;

    public static void errorAction(Context context, Throwable error, String TAG, View view) {
        if (error instanceof NoInternet) {
            snackbar = Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG);
        }else if (error instanceof SocketTimeoutException || error instanceof ConnectException){
            snackbar = Snackbar.make(view, "Không thể kết nối đến máy chủ", Snackbar.LENGTH_LONG);
        }else {
            snackbar = Snackbar.make(view, "Hệ thống có lỗi", Snackbar.LENGTH_LONG);
        }
        snackbar.show();
    }
}
