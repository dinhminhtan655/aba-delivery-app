package com.tandm.abadeliverydriver.main.home.fragmentinfo.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tandm.abadeliverydriver.main.home.fragmentinfo.model.UserRating;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    Repository repository;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public LiveData<List<UserRating>> getUserRating(Context context, String token, String driverCode, int month, int year){
        return repository.getStartUserRatin(context,token,driverCode,month,year);
    }
}
