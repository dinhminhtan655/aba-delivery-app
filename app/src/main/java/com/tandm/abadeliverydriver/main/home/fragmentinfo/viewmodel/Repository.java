package com.tandm.abadeliverydriver.main.home.fragmentinfo.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.model.UserRating;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = "Repository";
    private List<UserRating> userRatings = new ArrayList<>();
    private MutableLiveData<List<UserRating>> mutableLiveData = new MutableLiveData<>();

    public Repository() {
    }

    public MutableLiveData<List<UserRating>> getStartUserRatin(Context context, String token, String driverCode, int month, int year) {
        MyRetrofit.initRequest(context).GetZNSRating(token, driverCode, month, year).enqueue(new Callback<List<UserRating>>() {
            @Override
            public void onResponse(Call<List<UserRating>> call, Response<List<UserRating>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        mutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserRating>> call, Throwable t) {
                Toast.makeText(context, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
        return mutableLiveData;

    }
}
