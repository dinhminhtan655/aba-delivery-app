package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter.HistoryStopDriverDeliveryAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryStopDriverDeliveryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryStopDriverDeliveryActivity";
    View view;
    ProgressDialog progressDialog;
    @BindView(R.id.tvToolbarHisStopDriver)
    TextView tvToolbarHisStopDriver;
    @BindView(R.id.tbHisStopDriver)
    Toolbar tbHisStopDriver;
    @BindView(R.id.rvHisStopDriver)
    RecyclerView rvHisStopDriver;
    @BindView(R.id.tvSoThungDriver)
    TextView tvSoThungDriver;

    HistoryStopDriverDeliveryAdapter adapter;

    String strAtm_Shipment_Id, strTimeCompleted;
    long longTimeCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_stop_driver_delivery);
        ButterKnife.bind(this);
        setSupportActionBar(tbHisStopDriver);
        Utilities.showBackIcon(getSupportActionBar());
        Bundle b = getIntent().getExtras();
        view = getWindow().getDecorView().getRootView();
        if (b == null) {
            tvToolbarHisStopDriver.setText("");
        } else {
            strAtm_Shipment_Id = b.getString("atmshipmentid");
            strTimeCompleted = b.getString("time");
            try {
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond(strTimeCompleted);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d("time123", strTimeCompleted);
            tvToolbarHisStopDriver.setText(strAtm_Shipment_Id);
        }

        getHisStopDriver(strAtm_Shipment_Id);
    }

    private void getHisStopDriver(String atm_Shipment_Id) {
        progressDialog = Utilities.getProgressDialog(HistoryStopDriverDeliveryActivity.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(HistoryStopDriverDeliveryActivity.this)) {
            RetrofitError.errorAction(HistoryStopDriverDeliveryActivity.this, new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(HistoryStopDriverDeliveryActivity.this).getHistoryStopDriverDelivery("Bearer " + LoginPrefer.getObject(HistoryStopDriverDeliveryActivity.this).access_token, atm_Shipment_Id).enqueue(new Callback<List<HistoryStopDriverDelivery>>() {
            @Override
            public void onResponse(Call<List<HistoryStopDriverDelivery>> call, Response<List<HistoryStopDriverDelivery>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int i = 0;
                    Utilities.dismissDialog(progressDialog);
                    adapter = new HistoryStopDriverDeliveryAdapter(new RecyclerViewItemClick<HistoryStopDriverDelivery>() {
                        @Override
                        public void onClick(HistoryStopDriverDelivery item, int position, int number) {
                            switch (number){
                                case 0:
                                    if ((longTimeCompleted + 18000000) > System.currentTimeMillis()){
                                        Intent i = new Intent(HistoryStopDriverDeliveryActivity.this, UpdateHistoryWhitoutMasanActivity.class);
                                        startActivity(i);
                                    }else {
                                        Toast.makeText(HistoryStopDriverDeliveryActivity.this, "Đã hết thời gian chỉnh sửa1!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }

                        }

                        @Override
                        public void onLongClick(HistoryStopDriverDelivery item, int position, int number) {

                        }
                    });
                    adapter.replace(response.body());
                    rvHisStopDriver.setAdapter(adapter);
                    for (HistoryStopDriverDelivery his : response.body()){
                        i += his.real_Num_Delivered;
                        tvSoThungDriver.setText(String.valueOf(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HistoryStopDriverDelivery>> call, Throwable t) {
                RetrofitError.errorAction(HistoryStopDriverDeliveryActivity.this, new NoInternet(), TAG, view);
                Utilities.dismissDialog(progressDialog);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
