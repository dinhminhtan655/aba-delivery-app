package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.adapter.PayslipAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model.ItemPayslip;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model.PayslipList;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaySlipListFragment extends Fragment {

    View view;
    Unbinder unbinder;

    @BindView(R.id.rvPayslip)
    RecyclerView rvPayslip;
    PayslipAdapter adapter;
    String token, MaNhanVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pay_slip_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        MaNhanVien = LoginPrefer.getObject(getContext()).MaNhanVien;
        MyRetrofit.initRequest(getContext()).getPayslip(token, MaNhanVien).enqueue(new Callback<PayslipList>() {
            @Override
            public void onResponse(Call<PayslipList> call, Response<PayslipList> response) {

                adapter = new PayslipAdapter(new RecyclerViewItemClick<ItemPayslip>() {
                    @Override
                    public void onClick(ItemPayslip item, int position, int number) {
                        switch (number){
                            case 0:
                                Intent i = new Intent(getContext(), DetailPayslipActivity.class);
                                i.putExtra("items", item);
                                startActivity(i);
                                break;
                        }
                    }

                    @Override
                    public void onLongClick(ItemPayslip item, int position, int number) {

                    }
                });
                adapter.replace(response.body().items);
                if (rvPayslip != null){
                    rvPayslip.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<PayslipList> call, Throwable t) {

            }
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
