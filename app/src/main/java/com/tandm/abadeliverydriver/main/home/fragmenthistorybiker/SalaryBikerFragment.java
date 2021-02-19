package com.tandm.abadeliverydriver.main.home.fragmenthistorybiker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.adapter.PayslipBikerAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.model.PayslipBiker;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaryBikerFragment extends Fragment {

    View view;
    Unbinder unbinder;
    @BindView(R.id.rvPayslip)
    RecyclerView rvPayslip;
    PayslipBikerAdapter adapter;
    String token, MaNhanVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_salary_biker, container, false);
        unbinder = ButterKnife.bind(this, view);
        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        MaNhanVien = LoginPrefer.getObject(getContext()).MaNhanVien;

        MyRetrofit.initRequest(getContext()).GetPayslipBiker(token,MaNhanVien).enqueue(new Callback<List<PayslipBiker>>() {
            @Override
            public void onResponse(Call<List<PayslipBiker>> call, Response<List<PayslipBiker>> response) {
                if (response.isSuccessful() && response.body() != null){

                    adapter = new PayslipBikerAdapter(new RecyclerViewItemClick<PayslipBiker>() {
                        @Override
                        public void onClick(PayslipBiker item, int position, int number) {
                            switch (number){
                                case 0:
                                    Intent i = new Intent(getContext(), DetailPayslipBikerActivity.class);
                                    i.putExtra("items", item);
                                    startActivity(i);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(PayslipBiker item, int position, int number) {

                        }
                    });

                    adapter.replace(response.body());
                    if (rvPayslip != null){
                        rvPayslip.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PayslipBiker>> call, Throwable t) {

            }
        });

        return view;
    }
}