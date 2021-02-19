package com.tandm.abadeliverydriver.main.home.fragmenthomedriver;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter.StoreBikerAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ShipmentBikerForDriver;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreBikerFragment extends DialogFragment {

    private Unbinder unbinder;
    private View view;

    @BindView(R.id.rvListStoreBikerForDriver)
    RecyclerView rvListStoreBikerForDriver;

    StoreBikerAdapter adapter;

    String strToken, strRouteNo, strCustomerCode, strDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_store_biker, container, false);
        unbinder = ButterKnife.bind(this,view);
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        strRouteNo = (String) getArguments().getSerializable("routeno");
        strCustomerCode = (String) getArguments().getSerializable("customer");
        strDate = (String) getArguments().getSerializable("date");
//        strDate = Utilities.formatDateTime_yyyyMMddFromMili(System.currentTimeMillis());
        MyRetrofit.initRequest(getContext()).getStopBikerFromDriver(strToken,strRouteNo, strCustomerCode,strDate).enqueue(new Callback<List<ShipmentBikerForDriver>>() {
            @Override
            public void onResponse(Call<List<ShipmentBikerForDriver>> call, Response<List<ShipmentBikerForDriver>> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().size() != 0){
                        adapter = new StoreBikerAdapter(response.body());
                        adapter.replace(response.body());
                        rvListStoreBikerForDriver.setAdapter(adapter);
                    }else {
                        Snackbar.make(view,"Không có dữ liệu", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(view,"Thất bại", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentBikerForDriver>> call, Throwable t) {
                Snackbar.make(view,"Vui lòng kiểm tra kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
