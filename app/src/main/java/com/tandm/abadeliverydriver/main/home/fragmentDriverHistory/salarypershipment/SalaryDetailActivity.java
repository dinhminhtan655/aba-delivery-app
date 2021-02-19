package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.adapter.SalaryDetailAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.ItemsDetail;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.SalaryDetailList;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.Items;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaryDetailActivity extends AppCompatActivity {


    private static final String TAG = "SalaryDetailActivity";
    View view;
    String strShipmentID;
    @BindView(R.id.rvSalaryDetail)
    RecyclerView rvSalaryDetail;
    @BindView(R.id.tvLuongChiTiet)
    TextView tvLuongChiTiet;
    @BindView(R.id.tvTotalSalaryDetail)
    TextView tvTotalSalaryDetail;
    SalaryDetailAdapter adapter;
    String token;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_detail);
        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        view = getWindow().getDecorView().getRootView();
        token = "Bearer " + LoginPrefer.getObject(this).access_token;
        if (b != null){
            Items items = (Items) getIntent().getSerializableExtra("items");
            strShipmentID = items.shipmenT_ID;
            tvLuongChiTiet.setText("Lương chi tiết " + "\n" + strShipmentID);
        }else {
            strShipmentID = "";
        }


        progressDialog = ProgressDialog.show(SalaryDetailActivity.this,"", "Đang tải...", false, true);

        if (!WifiHelper.isConnected(SalaryDetailActivity.this)){
            RetrofitError.errorAction(SalaryDetailActivity.this, new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }


        MyRetrofit.initRequest(SalaryDetailActivity.this).getSalaryDetail(token, strShipmentID).enqueue(new Callback<SalaryDetailList>() {
            @Override
            public void onResponse(Call<SalaryDetailList> call, Response<SalaryDetailList> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter = new SalaryDetailAdapter();
                    adapter.replace(response.body().items);
                    rvSalaryDetail.setAdapter(adapter);

                    int salaryTotal = 0;
                    for (ItemsDetail s : response.body().items){
                        salaryTotal += (int)s.salarY_AMOUNT;
                    }

                    tvTotalSalaryDetail.setText("Tạm tính: " + Utilities.formatNumber(String.valueOf(salaryTotal))+" vnđ");
                }

                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<SalaryDetailList> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });






    }
}
