package com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.adapter.FeedbackBoxAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.model.FeedbackBox;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter.ShipmentOPAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.ShipmentOrderPayment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.SpinShipmentOP;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackBoxActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "FeedbackBoxActivity";
    private View view;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcFeedbackBox)
    RecyclerView rcFeedbackBox;
    @BindView(R.id.floatBtnAdd)
    FloatingActionButton floatBtnAdd;

    FeedbackBoxAdapter adapter;

    ProgressDialog progressDialog;

    ShipmentOPAdapter shipmentOPAdapter;

    String strDriverId, strToken, strAtmShipmentId, strTitle, strContent, strRegion, strFullName, strType;
    int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_box);
        ButterKnife.bind(this);

        strDriverId = LoginPrefer.getObject(FeedbackBoxActivity.this).MaNhanVien;
        strToken = "Bearer " + LoginPrefer.getObject(FeedbackBoxActivity.this).access_token;
        strFullName = LoginPrefer.getObject(FeedbackBoxActivity.this).fullName;
        strRegion = LoginPrefer.getObject(FeedbackBoxActivity.this).Region;
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getFeedback();
            }
        });
    }

    private void getFeedback() {
        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(FeedbackBoxActivity.this)) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(FeedbackBoxActivity.this).GetFeedbackBox(strToken, strDriverId).enqueue(new Callback<List<FeedbackBox>>() {
            @Override
            public void onResponse(Call<List<FeedbackBox>> call, Response<List<FeedbackBox>> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().size() > 0){
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        adapter = new FeedbackBoxAdapter(new RecyclerViewItemClick<FeedbackBox>() {
                            @Override
                            public void onClick(FeedbackBox item, int position, int number) {
                                LayoutInflater inflater = getLayoutInflater();
                                View showLayout = inflater.inflate(R.layout.dialog_watch_feedback_box, null);

                                AlertDialog.Builder b = new AlertDialog.Builder(FeedbackBoxActivity.this);
                                b.setView(showLayout);
                                b.setCancelable(false);
                                AlertDialog dialog = b.create();

                                TextView tvShipmentID = showLayout.findViewById(R.id.tvShipmentID);
                                TextView tvType = showLayout.findViewById(R.id.tvType);
                                TextInputEditText edtTitleFeedback = showLayout.findViewById(R.id.edtTitleFeedback);
                                TextInputEditText edtContentFeedback = showLayout.findViewById(R.id.edtContentFeedback);
                                Button btnCloseFeedback = showLayout.findViewById(R.id.btnCloseFeedback);

                                tvShipmentID.setText(item.atmShipmentID);
                                tvType.setText("Loại: "+item.type);
                                edtTitleFeedback.setText(item.title);
                                edtContentFeedback.setText(item.content);



                                btnCloseFeedback.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                            }

                            @Override
                            public void onLongClick(FeedbackBox item, int position, int number) {

                            }
                        });
                        adapter.replace(response.body());
                        rcFeedbackBox.setAdapter(adapter);
                    }else {
                        Toast.makeText(FeedbackBoxActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        try {
                            swipeRefreshLayout.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.e(TAG, e + "");
                        }
                    }
                }

                try {
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<FeedbackBox>> call, Throwable t) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @OnClick(R.id.floatBtnAdd)
    public void addFeedback(){

        LayoutInflater inflater = getLayoutInflater();
        View showLayout = inflater.inflate(R.layout.add_feedback_box, null);

        AlertDialog.Builder b = new AlertDialog.Builder(FeedbackBoxActivity.this);
        b.setView(showLayout);
        b.setCancelable(false);
        AlertDialog dialog = b.create();

        RadioButton rdChungTuChuyenXe = showLayout.findViewById(R.id.rdChungTuChuyenXe);
        RadioButton rdKhac = showLayout.findViewById(R.id.rdKhac);
        TextView tvTitleShipmentID = showLayout.findViewById(R.id.tvTitleShipmentID);
        AppCompatSpinner spinFeedbackATMID = showLayout.findViewById(R.id.spinFeedbackATMID);
        TextInputEditText edtTitleFeedback = showLayout.findViewById(R.id.edtTitleFeedback);
        TextInputEditText edtContentFeedback = showLayout.findViewById(R.id.edtContentFeedback);
        Button btnCloseFeedback = showLayout.findViewById(R.id.btnCloseFeedback);
        Button btnSendFeedback = showLayout.findViewById(R.id.btnSendFeedback);


        rdChungTuChuyenXe.setChecked(true);
        rdKhac.setChecked(false);

        if (rdChungTuChuyenXe.isChecked()){
            x = 1;
            tvTitleShipmentID.setVisibility(View.VISIBLE);
            spinFeedbackATMID.setVisibility(View.VISIBLE);
            strType = ""+rdChungTuChuyenXe.getText().toString();
        }else {
            x = 0;
            strAtmShipmentId = "";
            tvTitleShipmentID.setVisibility(View.GONE);
            spinFeedbackATMID.setVisibility(View.GONE);
            strType = ""+rdChungTuChuyenXe.getText().toString();
        }

        MyRetrofit.initRequest(FeedbackBoxActivity.this).GetShipmentIDFB(strToken, strDriverId).enqueue(new Callback<List<ShipmentOrderPayment>>() {
            @Override
            public void onResponse(Call<List<ShipmentOrderPayment>> call, Response<List<ShipmentOrderPayment>> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().size() > 0){
                        shipmentOPAdapter = new ShipmentOPAdapter(response.body(), FeedbackBoxActivity.this);
                        spinFeedbackATMID.setAdapter(shipmentOPAdapter);

                        spinFeedbackATMID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                SpinShipmentOP.SaveIntShipmentOP(FeedbackBoxActivity.this, position);
                                ShipmentOrderPayment shipmentOrderPayment = (ShipmentOrderPayment) shipmentOPAdapter.getItem(position);
                                strAtmShipmentId = shipmentOrderPayment.atmShipmentID;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        if (SpinShipmentOP.LoadIntShipmentOP(FeedbackBoxActivity.this) >= response.body().size()) {
                            spinFeedbackATMID.setSelection(0);
                        } else {
                            spinFeedbackATMID.setSelection(SpinShipmentOP.LoadIntShipmentOP(FeedbackBoxActivity.this));
                        }


                    }else {
                        Toast.makeText(FeedbackBoxActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentOrderPayment>> call, Throwable t) {
                RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
            }
        });


        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(FeedbackBoxActivity.this, ""+ buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                    strType = ""+buttonView.getText().toString();

                    if (buttonView.getId() == R.id.rdChungTuChuyenXe){
                        x = 1;
                        tvTitleShipmentID.setVisibility(View.VISIBLE);
                        spinFeedbackATMID.setVisibility(View.VISIBLE);
                    }else {
                        x = 0;
                        strAtmShipmentId = null;
                        tvTitleShipmentID.setVisibility(View.GONE);
                        spinFeedbackATMID.setVisibility(View.GONE);
                    }
                }
            }
        };

        rdChungTuChuyenXe.setOnCheckedChangeListener(listener);
        rdKhac.setOnCheckedChangeListener(listener);


        btnCloseFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strTitle = edtTitleFeedback.getText().toString();
                strContent = edtContentFeedback.getText().toString();

                if (x == 0){
                    if (edtTitleFeedback.getText().length() == 0 || edtContentFeedback.getText().length() == 0){
                        Toast.makeText(FeedbackBoxActivity.this, "Tiêu đề và nội dung là yêu cầu bắt buộc. Không được để trống", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog = Utilities.getProgressDialog(FeedbackBoxActivity.this, "Đang gửi...");
                        progressDialog.show();

                        if (!WifiHelper.isConnected(FeedbackBoxActivity.this)) {
                            RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
                            Utilities.dismissDialog(progressDialog);
                            return;
                        }

                        MyRetrofit.initRequest(FeedbackBoxActivity.this).AddFeedback(strToken, strFullName, strDriverId, strAtmShipmentId, strType, strContent,strTitle).enqueue(new Callback<FeedbackBox>() {
                            @Override
                            public void onResponse(Call<FeedbackBox> call, Response<FeedbackBox> response) {
                                if (response.isSuccessful() && response.body() != null){
                                    if (response.body().id > 0){
                                        dialog.dismiss();
                                        Utilities.thongBaoDialog(FeedbackBoxActivity.this, "Thành công");
                                        getFeedback();
                                        Utilities.dismissDialog(progressDialog);
                                    }else {
                                        Utilities.thongBaoDialog(FeedbackBoxActivity.this, "Thất bại");
                                        Utilities.dismissDialog(progressDialog);
                                    }

                                }else {
                                    Utilities.dismissDialog(progressDialog);
                                }
                            }

                            @Override
                            public void onFailure(Call<FeedbackBox> call, Throwable t) {
                                Utilities.dismissDialog(progressDialog);
                                RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
                            }
                        });
                    }
                }else {
                    if (edtTitleFeedback.getText().length() == 0 || edtContentFeedback.getText().length() == 0){
                        Toast.makeText(FeedbackBoxActivity.this, "Tiêu đề và nội dung là yêu cầu bắt buộc. Không được để trống", Toast.LENGTH_SHORT).show();
                    }else if(strAtmShipmentId.equals("") || strAtmShipmentId == null){
                        Toast.makeText(FeedbackBoxActivity.this, "Không có mã chuyến. Không thể góp ý ở mục chứng từ, chuyến xe", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog = Utilities.getProgressDialog(FeedbackBoxActivity.this, "Đang gửi...");
                        progressDialog.show();

                        if (!WifiHelper.isConnected(FeedbackBoxActivity.this)) {
                            RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
                            Utilities.dismissDialog(progressDialog);
                            return;
                        }

                        MyRetrofit.initRequest(FeedbackBoxActivity.this).AddFeedback(strToken, strFullName, strDriverId, strAtmShipmentId, strType, strContent,strTitle).enqueue(new Callback<FeedbackBox>() {
                            @Override
                            public void onResponse(Call<FeedbackBox> call, Response<FeedbackBox> response) {
                                if (response.isSuccessful() && response.body() != null){
                                    if (response.body().id > 0){
                                        dialog.dismiss();
                                        Utilities.thongBaoDialog(FeedbackBoxActivity.this, "Thành công");
                                        getFeedback();
                                        Utilities.dismissDialog(progressDialog);
                                    }else {
                                        Utilities.thongBaoDialog(FeedbackBoxActivity.this, "Thất bại");
                                        Utilities.dismissDialog(progressDialog);
                                    }

                                }else {
                                    Utilities.dismissDialog(progressDialog);
                                }
                            }

                            @Override
                            public void onFailure(Call<FeedbackBox> call, Throwable t) {
                                Utilities.dismissDialog(progressDialog);
                                RetrofitError.errorAction(FeedbackBoxActivity.this, new NoInternet(), TAG, view);
                            }
                        });
                    }
                }

            }
        });

        dialog.show();

    }

    private void getShipmentFB(){

    }

    @Override
    public void onRefresh() {
        getFeedback();
    }
}