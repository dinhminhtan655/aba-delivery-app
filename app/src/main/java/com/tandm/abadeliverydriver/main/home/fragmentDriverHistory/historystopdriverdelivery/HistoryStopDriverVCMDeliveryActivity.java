package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter.HistoryStopDriverDelivery2Adapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery2;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatebhxice.UpdateHistoryBHXIce;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatehistorykhay.UpdateHistoryKhay;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Customer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryStopDriverVCMDeliveryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryStopDriverVCMDeliveryActivity";
    View view;
    ProgressDialog progressDialog;
    @BindView(R.id.tvToolbarHisStopDriverVCM)
    TextView tvToolbarHisStopDriverVCM;
    @BindView(R.id.tbHisStopDriverVCM)
    Toolbar tbHisStopDriverVCM;
    @BindView(R.id.rvHisStopDriverVCM)
    RecyclerView rvHisStopDriverVCM;
    HistoryStopDriverDelivery2Adapter adapter;
    String strAtm_Shipment_Id, strTimeCompleted, strToken, strStartTime, reportDate, reportDate2, strDate, strDateTime, strDateTimeMB;
    int real_num = 0;
    long longTimeCompleted, longTimeCurrent;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_stop_driver_vcmdelivery);
        ButterKnife.bind(this);
        setSupportActionBar(tbHisStopDriverVCM);
        Utilities.showBackIcon(getSupportActionBar());
        rvHisStopDriverVCM.setNestedScrollingEnabled(false);
        Bundle b = getIntent().getExtras();
        view = getWindow().getDecorView().getRootView();
        strToken = "Bearer " + LoginPrefer.getObject(HistoryStopDriverVCMDeliveryActivity.this).access_token;

        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        strDate = Utilities.formatDate_yyyyMMdd2(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 09:00:00";
        strDateTimeMB = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 10:30:00";
        try {
            if (LoginPrefer.getObject(HistoryStopDriverVCMDeliveryActivity.this).Region.equals("MN")) {
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
            } else {
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTimeMB);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        longTimeCurrent = System.currentTimeMillis();

        if (b == null) {
            tvToolbarHisStopDriverVCM.setText("");
        } else {
            strAtm_Shipment_Id = b.getString("atmshipmentid");
            strTimeCompleted = b.getString("time");
            strStartTime = b.getString("time2");
            Log.d("time123", strTimeCompleted);
            tvToolbarHisStopDriverVCM.setText(strAtm_Shipment_Id);
        }
        getHisStopDriverVCM(strAtm_Shipment_Id);
    }


    private void getHisStopDriverVCM(String atm_Shipment_Id) {

        progressDialog = Utilities.getProgressDialog(HistoryStopDriverVCMDeliveryActivity.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(HistoryStopDriverVCMDeliveryActivity.this)) {
            RetrofitError.errorAction(HistoryStopDriverVCMDeliveryActivity.this, new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(HistoryStopDriverVCMDeliveryActivity.this).getHistoryStopDriverDelivery2("Bearer " + LoginPrefer.getObject(HistoryStopDriverVCMDeliveryActivity.this).access_token, atm_Shipment_Id).enqueue(new Callback<List<HistoryStopDriverDelivery2>>() {
            @Override
            public void onResponse(Call<List<HistoryStopDriverDelivery2>> call, Response<List<HistoryStopDriverDelivery2>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Utilities.dismissDialog(progressDialog);
                    adapter = new HistoryStopDriverDelivery2Adapter(new RecyclerViewItemClick<HistoryStopDriverDelivery2>() {
                        @Override
                        public void onClick(HistoryStopDriverDelivery2 item, int position, int number) {
                            switch (number) {
                                case 0:
                                    if (item.khachHang.equals(Customer.VCMFRESH) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAMEAT_0_5)
                                            || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.CP) && item.packaged_Item_XID.equals(Customer.ABAMEAT_0_5)
                                            || item.khachHang.equals(Customer.CP) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.NEWZEALAND) && item.packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)
                                            || item.khachHang.equals(Customer.NEWZEALAND) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.THREEF) && item.packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
                                            || item.khachHang.equals(Customer.XX6020) && item.packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
                                    ) {
                                        Intent i = new Intent(HistoryStopDriverVCMDeliveryActivity.this, UpdateHistoryWhitoutMasanActivity.class);
                                        i.putExtra("date", item.getDateDeliveryTime(strTimeCompleted));
                                        i.putExtra("date2", item.planneD_ARRIVAL);
                                        i.putExtra("storecode", item.store_Code);
                                        i.putExtra("atmshipmentid", item.atM_Shipment_ID);
                                        i.putExtra("orderreleaseid", item.orderrelease_id);
                                        i.putExtra("customer", item.khachHang);
                                        startActivity(i);
                                    } else if (item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAICE_0)) {
                                        Intent i = new Intent(HistoryStopDriverVCMDeliveryActivity.this, UpdateHistoryBHXIce.class);
                                        i.putExtra("date", item.getDateDeliveryTime(strTimeCompleted));
                                        i.putExtra("date2", strStartTime);
                                        i.putExtra("storecode", item.store_Code);
                                        i.putExtra("atmshipmentid", item.atM_Shipment_ID);
                                        i.putExtra("orderreleaseid", item.orderrelease_id);
                                        i.putExtra("customer", item.khachHang);
                                        startActivity(i);
                                    } else if (item.khachHang.equals(Customer.VCM) && item.packaged_Item_XID.equals(Customer.ABALOCAL_FRUIT_VEGETABLE_9_14) || item.khachHang.equals(Customer.VCM) && item.packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)
                                            || item.khachHang.equals(Customer.VCM) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5) || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAVEGETABLE_0_5)
                                            || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAVEGETABLE_12_17) || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABATRAY)
                                            || item.khachHang.equals(Customer.VCMFRESH) && item.packaged_Item_XID.equals(Customer.ABATRAY)) {
                                        Intent i = new Intent(HistoryStopDriverVCMDeliveryActivity.this, UpdateHistoryKhay.class);
                                        i.putExtra("date", item.getDateDeliveryTime(strTimeCompleted));
                                        i.putExtra("date2", strStartTime);
                                        i.putExtra("storecode", item.store_Code);
                                        i.putExtra("atmshipmentid", item.atM_Shipment_ID);
                                        i.putExtra("orderreleaseid", item.orderrelease_id);
                                        i.putExtra("customer", item.khachHang);
                                        startActivity(i);
                                    }

                                    else if (item.khachHang.equals(Customer.MASAN) || item.khachHang.equals(Customer.TOKYODELI)) {

                                        final Dialog dialog = new Dialog(HistoryStopDriverVCMDeliveryActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.dialog_carton_tray_vin);

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialog.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                        LinearLayout lnEdtTitle = dialog.findViewById(R.id.lnEdtTitle);
                                        LinearLayout edtNewzealand = dialog.findViewById(R.id.edtNewzealand);
                                        LinearLayout lnedtDiaSLKhay = dialog.findViewById(R.id.lnedtDiaSLKhay);
                                        LinearLayout lnedtDiaSLCarton = dialog.findViewById(R.id.lnedtDiaSLCarton);
                                        TextView tvDiaSLThungTuKho = dialog.findViewById(R.id.tvDiaSLThungTuKho);
                                        TextView tvTitleSLThungTuKho = dialog.findViewById(R.id.tvTitleSLThungTuKho);
                                        TextInputEditText edtDiaSLCarton = dialog.findViewById(R.id.edtDiaSLCarton);
                                        TextInputEditText edtDiaSLKhay = dialog.findViewById(R.id.edtDiaSLKhay);
                                        TextInputEditText edtDiaSLProductRecall = dialog.findViewById(R.id.edtDiaSLProductRecall);
                                        Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                        Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);


                                        lnedtDiaSLKhay.setVisibility(View.VISIBLE);
                                        lnedtDiaSLCarton.setVisibility(View.VISIBLE);
                                        lnEdtTitle.setVisibility(View.GONE);
                                        tvTitleSLThungTuKho.setVisibility(View.GONE);
                                        edtNewzealand.setVisibility(View.GONE);

                                        edtDiaSLCarton.setText(String.valueOf(item.real_Num_Delivered));
                                        edtDiaSLKhay.setText(String.valueOf(item.totalTray));
                                        tvDiaSLThungTuKho.setText(String.valueOf(item.totalCartonMasan));

                                        btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });


                                        btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.d("tatata",longTimeCurrent +"/"+ longTimeCompleted);
                                                if (longTimeCurrent < longTimeCompleted) {
                                                    MyRetrofit.initRequest(HistoryStopDriverVCMDeliveryActivity.this).updateHistoryCarton(strToken, item.atM_Shipment_ID, item.orderrelease_id, Integer.parseInt(edtDiaSLCarton.getText().toString() == null ? "0" : edtDiaSLCarton.getText().toString()), true,
                                                            Integer.parseInt(edtDiaSLKhay.getText().toString() == null ? "0" : edtDiaSLKhay.getText().toString()), 0,
                                                            0, 0).enqueue(new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body() == 1) {
                                                                    Toast.makeText(HistoryStopDriverVCMDeliveryActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                                    dialog.dismiss();
                                                                    getHisStopDriverVCM(strAtm_Shipment_Id);
                                                                } else {
                                                                    Toast.makeText(HistoryStopDriverVCMDeliveryActivity.this, "Thất bại thử lại", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Integer> call, Throwable t) {
                                                            Toast.makeText(HistoryStopDriverVCMDeliveryActivity.this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(HistoryStopDriverVCMDeliveryActivity.this, "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                        dialog.show();
                                        dialog.getWindow().setAttributes(lp);

                                    }


                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(HistoryStopDriverDelivery2 item, int position, int number) {

                        }
                    });
                    adapter.replace(response.body());
                    rvHisStopDriverVCM.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<HistoryStopDriverDelivery2>> call, Throwable t) {
                RetrofitError.errorAction(HistoryStopDriverVCMDeliveryActivity.this, new NoInternet(), TAG, view);
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
