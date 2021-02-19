package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatehistorykhay;

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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatehistorykhay.adapter.HistoryKhayDeliveryNotesAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery.HistoryTrayDeliveryNotesChild;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery.HistoryTrayDeliveryNotesParent;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassList2;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

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

public class UpdateHistoryKhay extends AppCompatActivity {

    private static final String TAG = "UpdateHistoryKhay";

    @BindView(R.id.tbNhanHang)
    Toolbar tbNhanHang;
    @BindView(R.id.tvToolbar)
    TextView tvToolbar;
    @BindView(R.id.tvSoLuongCBVCM)
    TextView tvSoLuongCBVCM;
    @BindView(R.id.tvSoLuongTotalVCM)
    TextView tvSoLuongTotalVCM;
    @BindView(R.id.tvThoiGianNhanHang)
    TextView tvThoiGianNhanHang;
    @BindView(R.id.tvMaCuaHangNhanHang)
    TextView tvMaCuaHangNhanHang;
    @BindView(R.id.rvUpdateVCM)
    RecyclerView rvUpdateVCM;

    View view;

    ProgressDialog progressDialog;
    String strStoreCode, strToken, strDate, strDate2, strAtmShipmentId, strOrderreleaseId, strRowID, strMaNhanVien, strCustomer, reportDate, reportDate2, strDateTime,strDateTimeMB;
    int i = 0;
    HistoryKhayDeliveryNotesAdapter adapter;
    List<HistoryTrayDeliveryNotesChild> list;
    List<HistoryTrayDeliveryNotesChild> listFinalVCM;

    long longTimeCompleted, longTimeCurrent;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_history_khay);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        setSupportActionBar(tbNhanHang);
        Utilities.showBackIcon(getSupportActionBar());
        list = new ArrayList<>();
        strToken = "Bearer " + LoginPrefer.getObject(this).access_token;
        strMaNhanVien = LoginPrefer.getObject(UpdateHistoryKhay.this).MaNhanVien;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        strDate2 = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        strDate = Utilities.formatDate_yyyyMMdd2(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 09:00:00";
        strDateTimeMB = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 10:30:00";
        try {
            if(LoginPrefer.getObject(UpdateHistoryKhay.this).Region.equals("MN")){
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
            } else {
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
            strAtmShipmentId = "";
            strOrderreleaseId = "";
            strCustomer = "";
        } else {
            strStoreCode = b.getString("storecode", "");
            strDate = Utilities.formatDate_ddMMyyyy(b.getString("date"));
            strAtmShipmentId = b.getString("atmshipmentid");
            strOrderreleaseId = b.getString("orderreleaseid");
            strCustomer = b.getString("customer");
            tvMaCuaHangNhanHang.setText(strStoreCode);
            tvThoiGianNhanHang.setText(strDate);
        }

        getHistoryKhay();

    }

    private void getHistoryKhay() {
        progressDialog = Utilities.getProgressDialog(UpdateHistoryKhay.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(UpdateHistoryKhay.this)) {
            RetrofitError.errorAction(UpdateHistoryKhay.this, new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(UpdateHistoryKhay.this).getHistoryTray(strAtmShipmentId, strOrderreleaseId, strToken).enqueue(new Callback<HistoryTrayDeliveryNotesParent>() {
            @Override
            public void onResponse(Call<HistoryTrayDeliveryNotesParent> call, Response<HistoryTrayDeliveryNotesParent> response) {
                if (response.isSuccessful() && response != null) {
                    HistoryTrayDeliveryNotesParent item = response.body();
                    if (item != null && item.getItems() != null) {
                        list = item.getItems();

                        Utilities.dismissDialog(progressDialog);
                        int a = 0;
                        for (HistoryTrayDeliveryNotesChild k : list) {
                            list.get(a).setEditTextUpdateKhayLayVeTuCH(String.valueOf(k.trayReceiving));
                            list.get(a).setEditTextUpdateKhayGiaoCH(String.valueOf(k.trayDelivering));
                            a++;
                            tvSoLuongTotalVCM.setText(String.valueOf(a));
                        }

                        adapter = new HistoryKhayDeliveryNotesAdapter(list, new PassCheckBox() {
                            @Override
                            public void passCongCB(int cb) {
                                i += cb;
                                tvSoLuongCBVCM.setText(String.valueOf(i));
                            }

                            @Override
                            public void passTruCB(int cb) {
                                i -= cb;
                                tvSoLuongCBVCM.setText(String.valueOf(i));
                            }
                        });

                        rvUpdateVCM.setAdapter(adapter);

                        adapter.setItemChildren(UpdateHistoryKhay.this, list);


                    }
                }

//                }
            }

            @Override
            public void onFailure(Call<HistoryTrayDeliveryNotesParent> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    //Giao Khay
    @OnClick(R.id.btnSendVCM)
    public void sendVCM(View view) {
        if (longTimeCurrent < longTimeCompleted) {
            if (i < list.size()) {
                AlertDialog.Builder b = new AlertDialog.Builder(UpdateHistoryKhay.this);
                b.setTitle("Thông báo").setIcon(R.drawable.warning).setMessage("Vui lòng xác nhận đầy đủ đơn hàng!").show();
            } else {
                listFinalVCM = new ArrayList<>();
                HistoryKhayDeliveryNotesAdapter vcmAdapter = new HistoryKhayDeliveryNotesAdapter(new PassList2() {
                    @Override
                    public void passList(List<HistoryTrayDeliveryNotesChild> list) {
                        listFinalVCM = list;
                    }
                });
                vcmAdapter.Send();
                AlertDialog.Builder b = new AlertDialog.Builder(UpdateHistoryKhay.this);
                b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bấm OK để gửi thông tin")
                        .setCancelable(false)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<HistoryTrayDeliveryNotesChild> item2List = new ArrayList<>();
                                int i = 0;
                                for (HistoryTrayDeliveryNotesChild h : list) {
                                    item2List.add(new HistoryTrayDeliveryNotesChild(
                                            list.get(i).id,
                                            Integer.parseInt(list.get(i).getEditTextUpdateKhayGiaoCH()),
                                            Integer.parseInt(list.get(i).getEditTextUpdateKhayLayVeTuCH()))
                                    );
                                    i++;
                                }

                                progressDialog = Utilities.getProgressDialog(UpdateHistoryKhay.this, "Đang gửi..");
                                progressDialog.show();

                                MyRetrofit.initRequest(UpdateHistoryKhay.this).updateTrayHistory(strToken, item2List).enqueue(new Callback<ItemBill>() {
                                    @Override
                                    public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                        if (response.isSuccessful() && response != null) {
                                            Toast.makeText(UpdateHistoryKhay.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                            tvSoLuongCBVCM.setText("0");
                                            progressDialog.dismiss();
                                            getHistoryKhay();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ItemBill> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(UpdateHistoryKhay.this, new NoInternet(), TAG, view);
                                    }
                                });

                            }
                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = b.create();
                dialog.show();
            }
        } else {
            Toast.makeText(UpdateHistoryKhay.this, "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick(R.id.btnCheckAllVCM)
    public void checkAllVCM(View view) {
        adapter.selectAll();
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
