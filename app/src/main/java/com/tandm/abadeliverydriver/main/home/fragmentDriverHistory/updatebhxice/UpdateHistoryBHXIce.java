package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatebhxice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBooking;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateHistoryBHXIce extends AppCompatActivity {

    private static final String TAG = "UpdateHistoryBHXIce";

    @BindView(R.id.tbNhanHang)
    Toolbar tbNhanHang;
    @BindView(R.id.tvToolbar)
    TextView tvToolbar;
    @BindView(R.id.tvThoiGianNhanHang)
    TextView tvThoiGianNhanHang;
    @BindView(R.id.tvMaCuaHangNhanHang)
    TextView tvMaCuaHangNhanHang;
    @BindView(R.id.edtIceGiao)
    TextInputEditText edtIceGiao;
    @BindView(R.id.edtIceUpdateGiao)
    TextInputEditText edtIceUpdateGiao;
    @BindView(R.id.edtIceUpdateNotes)
    TextInputEditText edtIceUpdateNotes;
    long longTimeCompleted, longTimeCurrent;
    private Calendar calendar;

    View view;

    ProgressDialog progressDialog;
    String strStoreCode, strToken, strDate, strDate2, strName, strCustomerCode, strAtmShipmentId, strOrderreleaseId, strRowID, reportDate, reportDate2, strDateTime,strDateTimeMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_history_b_h_x_ice);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        setSupportActionBar(tbNhanHang);
        Utilities.showBackIcon(getSupportActionBar());
        strToken = "Bearer " + LoginPrefer.getObject(this).access_token;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        strDate2 = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
//        strDate = Utilities.formatDate_yyyyMMdd2(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 09:00:00";
        strDateTimeMB = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 10:30:00";
        try {
            if(LoginPrefer.getObject(UpdateHistoryBHXIce.this).Region.equals("MN")){
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
            }else {
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTimeMB);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        longTimeCurrent = System.currentTimeMillis();
        Bundle b = getIntent().getExtras();
        if (b == null) {
            strStoreCode = "";
            strDate = "";
            strDate2 = "";
            strAtmShipmentId = "";
            strOrderreleaseId = "";
            strCustomerCode = "";
        } else {
            strStoreCode = b.getString("storecode", "");
            strDate = Utilities.formatDate_ddMMyyyy(b.getString("date"));
            strDate2 = b.getString("date2");
            strAtmShipmentId = b.getString("atmshipmentid");
            strOrderreleaseId = b.getString("orderreleaseid");
            strCustomerCode = b.getString("customer");
            tvMaCuaHangNhanHang.setText(strStoreCode);
            tvThoiGianNhanHang.setText(strDate);
        }


        getHistoryIceBHX();

    }

    private void getHistoryIceBHX() {

        progressDialog = Utilities.getProgressDialog(UpdateHistoryBHXIce.this, "Đang tải..");
        progressDialog.show();

        MyRetrofit.initRequest(UpdateHistoryBHXIce.this).getHistoryDeliveryNote(strToken, strDate2, strStoreCode, strAtmShipmentId, strOrderreleaseId,strCustomerCode).enqueue(new Callback<List<ItemChild>>() {
            @Override
            public void onResponse(Call<List<ItemChild>> call, Response<List<ItemChild>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        for (int i = 0; i < response.body().size(); i++) {
                            edtIceGiao.setText(response.body().get(i).actual_Received);
                            strRowID = response.body().get(i).rowId;
                        }
                    }
                }
                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<List<ItemChild>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(UpdateHistoryBHXIce.this, new NoInternet(), TAG, view);
            }
        });
    }

    //Giao Đá
    @OnClick(R.id.btnSendBHXICE)
    public void sendBHXICE(View view) {
        if (longTimeCurrent < longTimeCompleted) {
            AlertDialog.Builder b = new AlertDialog.Builder(UpdateHistoryBHXIce.this);
            b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bấm OK để gửi thông tin")
                    .setCancelable(false)
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            List<ItemBooking> item2List = new ArrayList<>();
                            if (edtIceGiao.getText().toString().equals("")) {

                            } else {
                                item2List.add(new ItemBooking(strRowID,
                                        Integer.parseInt(edtIceUpdateGiao.getText().toString()),
                                        0.0,
                                        edtIceUpdateNotes.getText().toString(),
                                        LoginPrefer.getObject(UpdateHistoryBHXIce.this).MaNhanVien,
                                        "",
                                        "",
                                        "",
                                        "",
                                        0,
                                        0,
                                        0,
                                        strAtmShipmentId));


                                progressDialog = Utilities.getProgressDialog(UpdateHistoryBHXIce.this, "Đang gửi..");
                                progressDialog.show();

                                MyRetrofit.initRequest(UpdateHistoryBHXIce.this).updateBill(strToken, item2List).enqueue(new Callback<ItemBill>() {
                                    @Override
                                    public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                        if (response.isSuccessful() && response != null) {
                                            Toast.makeText(UpdateHistoryBHXIce.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            getHistoryIceBHX();
                                            edtIceUpdateGiao.setText("0");
                                        } else {
                                            Toast.makeText(UpdateHistoryBHXIce.this, "Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ItemBill> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(UpdateHistoryBHXIce.this, new NoInternet(), TAG, view);

                                    }
                                });
                            }
                        }
                    }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            Dialog dialog = b.create();
            dialog.show();
        } else {
            Toast.makeText(UpdateHistoryBHXIce.this, "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
        }

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
