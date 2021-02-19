package com.tandm.abadeliverydriver.main.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tandm.abadeliverydriver.main.suco.fragment.Problem;
import com.tandm.abadeliverydriver.main.suco.fragment.ProblemChild;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    ProblemRepository problemRepository;



    public MainViewModel(@NonNull Application application) {
        super(application);
        problemRepository = new ProblemRepository();

    }

    public LiveData<List<ProblemChild>> getAllProblem(Context context){
        return problemRepository.getMutableLiveData(context);
    }
}
