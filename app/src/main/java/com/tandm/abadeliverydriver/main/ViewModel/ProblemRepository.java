package com.tandm.abadeliverydriver.main.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.suco.fragment.Problem;
import com.tandm.abadeliverydriver.main.suco.fragment.ProblemChild;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProblemRepository {

    private static final String TAG = "ProblemRepository";
    private ArrayList<ProblemChild> problemChildren = new ArrayList<>();
    private MutableLiveData<List<ProblemChild>> mutableLiveData = new MutableLiveData<>();

    public ProblemRepository() {

    }


    public MutableLiveData<List<ProblemChild>> getMutableLiveData(Context context){
        MyRetrofit.initRequest(context).getProblemList("Bearer " + LoginPrefer.getObject(context).access_token).enqueue(new Callback<Problem>() {
            @Override
            public void onResponse(Call<Problem> call, Response<Problem> response) {
                if (response.isSuccessful() && response != null){
                    Problem problem = response.body();
                    if (problem != null && problem.getProblems() != null){
                        problemChildren = (ArrayList<ProblemChild>) problem.getProblems();
                        mutableLiveData.setValue(problemChildren);
                    }
                }

            }

            @Override
            public void onFailure(Call<Problem> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }


}
