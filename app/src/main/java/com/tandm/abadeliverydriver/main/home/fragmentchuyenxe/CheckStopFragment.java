package com.tandm.abadeliverydriver.main.home.fragmentchuyenxe;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentchuyenxe.adapter.CheckStopAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckStopFragment extends DialogFragment {

    private CheckStopAdapter checkStopAdapter;
    String strAtmShipmentID, strDeliveryDate, strToken, strDriverGID;

    RecyclerView rcStop;
    Button btnClose, btnRefresh;


    ProgressDialog progressDialog;

    public static CheckStopFragment newInstance(String AtmShipmentID, String deliveryDate) {
        CheckStopFragment fragment = new CheckStopFragment();
        Bundle args = new Bundle();
        args.putString("atmshipmentid", AtmShipmentID);
        args.putString("deliverydate", deliveryDate);
        fragment.setArguments(args);
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        strAtmShipmentID = getArguments().getString("atmshipmentid");
        strDeliveryDate = Utilities.formatDate_yyyyMMdd(getArguments().getString("deliverydate"));
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        strDriverGID = LoginPrefer.getObject(getContext()).MaNhanVien;

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        dialog.setContentView(R.layout.fragment_check_stop);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        rcStop = dialog.findViewById(R.id.rcStop);
        btnClose = dialog.findViewById(R.id.btnClose);
        btnRefresh = dialog.findViewById(R.id.btnRefresh);

        btnRefresh.setEnabled(false);

        getCheckStop(strAtmShipmentID,strDeliveryDate);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckStop(strAtmShipmentID,strDeliveryDate);
            }
        });



        return dialog;
    }

    public void getCheckStop(String atmShipmentId, String deliveryDate) {

        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải..");
        progressDialog.show();

        MyRetrofit.initRequest(getContext()).getStopShipment(strToken, strDriverGID, atmShipmentId, deliveryDate).enqueue(new Callback<List<StoreDriver>>() {
            @Override
            public void onResponse(Call<List<StoreDriver>> call, Response<List<StoreDriver>> response) {
                if (response.isSuccessful() && response != null) {
                    if (response.body().size() > 0) {

                        checkStopAdapter = new CheckStopAdapter(getContext());
                        checkStopAdapter.replace(response.body());
                        rcStop.setAdapter(checkStopAdapter);
                    }
                    progressDialog.dismiss();
                    btnRefresh.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<StoreDriver>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


}