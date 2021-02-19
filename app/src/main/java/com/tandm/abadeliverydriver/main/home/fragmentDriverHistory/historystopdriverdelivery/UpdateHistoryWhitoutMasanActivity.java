package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.ViewModel.ItemsViewModel;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter.HistoryDeliveryNotesAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HisTotalCartonAndTray;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBooking;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassList;
import com.tandm.abadeliverydriver.main.utilities.Customer;
import com.tandm.abadeliverydriver.main.utilities.ScrollingLinearLayoutManager;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateHistoryWhitoutMasanActivity extends AppCompatActivity {


    private static final String TAG = "UpdateHistoryWhitoutMas";

    @BindView(R.id.tvThoiGianNhanHang)
    TextView tvThoiGianNhanHang;
    @BindView(R.id.tvMaCuaHangNhanHang)
    TextView tvMaCuaHangNhanHang;
    @BindView(R.id.tbNhanHang)
    Toolbar tbNhanHang;
    @BindView(R.id.rvUpdateNhanHang)
    RecyclerView rvUpdateNhanHang;
    @BindView(R.id.tvSoLuongCB)
    TextView tvSoLuongCB;
    //    @BindView(R.id.tvSoLuongTotal)
    //    TextView tvSoLuongTotal;
    @BindView(R.id.btnCheckAll)
    ImageButton btnCheckAll;
    @BindView(R.id.btnSend)
    ImageButton btnSend;
    String strStoreCode, strToken, strDate, strDate2, strAtmShipmentId, strOrderreleaseId, strCustomer, reportDate, reportDate2, strDateTime, strDateTimeMB,strDateTimeMB3F;

    ItemsViewModel itemsViewModel;
    private List<ItemChild> itemString;
    private List<ItemChild> list;
    private List<ItemChild> listSend;
    private List<ItemChild> listFinal;
    HistoryDeliveryNotesAdapter adapter;
    int i = 0;
    ProgressDialog progressDialog;
    long longTimeCompleted, longTimeCurrent;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_history_whitout_masan);
        ButterKnife.bind(this);
        setSupportActionBar(tbNhanHang);
        Utilities.showBackIcon(getSupportActionBar());
        rvUpdateNhanHang.setNestedScrollingEnabled(false);
        rvUpdateNhanHang.setHasFixedSize(true);
        rvUpdateNhanHang.setItemViewCacheSize(20);
        int duration = getResources().getInteger(R.integer.scroll_duration);
        rvUpdateNhanHang.setLayoutManager(new ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, duration));

        strToken = "Bearer " + LoginPrefer.getObject(this).access_token;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        itemString = new ArrayList<ItemChild>();
        Bundle b = getIntent().getExtras();


        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        strDate = Utilities.formatDate_yyyyMMdd2(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 09:00:00";
        strDateTimeMB = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 10:30:00";
        strDateTimeMB3F = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 17:00:00";
        if (b == null) {
            strStoreCode = "";
            strDate = "";
            strDate2 = "";
            strAtmShipmentId = "";
            strOrderreleaseId = "";
            strCustomer = "";
        } else {
            strStoreCode = b.getString("storecode", "");
//            strDate = Utilities.formatDate_ddMMyyyy(b.getString("date"));
            strDate2 = b.getString("date2");
            strAtmShipmentId = b.getString("atmshipmentid");
            strOrderreleaseId = b.getString("orderreleaseid");
            strCustomer = b.getString("customer");
            tvMaCuaHangNhanHang.setText(strStoreCode);
            tvThoiGianNhanHang.setText(strDate2);
        }
        try {
            if (LoginPrefer.getObject(UpdateHistoryWhitoutMasanActivity.this).Region.equals("MN")) {
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
            } else {
                if (strCustomer.equals(Customer.THREEF)){
                    longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTimeMB3F);
                }else {
                    longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTimeMB);
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        longTimeCurrent = System.currentTimeMillis();



        itemsViewModel = ViewModelProviders.of(UpdateHistoryWhitoutMasanActivity.this).get(ItemsViewModel.class);
        itemString = populateList();


        adapter = new HistoryDeliveryNotesAdapter(itemString, new PassCheckBox() {
            @Override
            public void passCongCB(int cb) {
                i += cb;
                tvSoLuongCB.setText(String.valueOf(i));
            }

            @Override
            public void passTruCB(int cb) {
                i -= cb;
                tvSoLuongCB.setText(String.valueOf(i));
            }
        });

        btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.selectAll();

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i < listSend.size()) {
                    AlertDialog.Builder b = new AlertDialog.Builder(UpdateHistoryWhitoutMasanActivity.this);
                    b.setTitle("Thông báo").setIcon(R.drawable.warning).setMessage("Vui lòng xác nhận đầy đủ đơn hàng!").show();
                } else {
                    listFinal = new ArrayList<>();
                    HistoryDeliveryNotesAdapter adapter = new HistoryDeliveryNotesAdapter(new PassList() {
                        @Override
                        public void passList(List<ItemChild> list) {
                            listFinal = list;
                        }
                    });
                    adapter.Send();

                    if (LoginPrefer.getObject(UpdateHistoryWhitoutMasanActivity.this).isBiker) {
                        MyRetrofit.initRequest(UpdateHistoryWhitoutMasanActivity.this).GetHisTrayAndCarton(strToken, strAtmShipmentId, strOrderreleaseId, strStoreCode, strCustomer).enqueue(new Callback<HisTotalCartonAndTray>() {
                            @Override
                            public void onResponse(Call<HisTotalCartonAndTray> call, Response<HisTotalCartonAndTray> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    final Dialog dialog = new Dialog(UpdateHistoryWhitoutMasanActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.dialog_carton_tray_vin);

                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.copyFrom(dialog.getWindow().getAttributes());
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                    LinearLayout edtNewzealand = dialog.findViewById(R.id.edtNewzealand);
                                    LinearLayout lnedtDiaSLCarton = dialog.findViewById(R.id.lnedtDiaSLCarton);
                                    LinearLayout lnedtDiaSLKhay = dialog.findViewById(R.id.lnedtDiaSLKhay);
                                    TextView tvDiaSLThungTuKho = dialog.findViewById(R.id.tvDiaSLThungTuKho);
                                    TextInputEditText edtDiaSLCarton = dialog.findViewById(R.id.edtDiaSLCarton);
                                    TextInputEditText edtDiaSLKhay = dialog.findViewById(R.id.edtDiaSLKhay);
                                    TextInputEditText edtDiaSLProductRecall = dialog.findViewById(R.id.edtDiaSLProductRecall);
                                    Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                    Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);

                                    lnedtDiaSLCarton.setVisibility(View.VISIBLE);
                                    lnedtDiaSLKhay.setVisibility(View.VISIBLE);

                                    if (!strCustomer.equals(Customer.NEWZEALAND)) {
                                        edtNewzealand.setVisibility(View.GONE);
                                    } else {
                                        edtNewzealand.setVisibility(View.VISIBLE);
                                    }

                                    tvDiaSLThungTuKho.setText(String.valueOf(response.body().totalCartonMasan));
                                    edtDiaSLCarton.setText(String.valueOf(response.body().realNumDelivered) == null ? "0" : String.valueOf(response.body().realNumDelivered));
                                    edtDiaSLKhay.setText(String.valueOf(response.body().totalTray));
                                    edtDiaSLProductRecall.setText(String.valueOf(response.body().productRecall));

                                    btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (longTimeCurrent < longTimeCompleted) {
                                                if (edtDiaSLCarton.getText().toString() == null || edtDiaSLKhay.getText().toString() == null ||
                                                        edtDiaSLCarton.getText().toString().equals("") || edtDiaSLKhay.getText().toString().equals("") || edtDiaSLKhay.getText().toString().equals("")) {
                                                    Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Số khay, số thùng và số thu hồi không được để trống", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (Integer.parseInt(edtDiaSLCarton.getText().toString()) < 0 && Integer.parseInt(edtDiaSLKhay.getText().toString()) < 0) {
                                                        Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Số lượng giao không thể nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        List<ItemBooking> item2List = new ArrayList<>();
                                                        int i = 0;

                                                        for (ItemChild item : listSend) {
                                                            item2List.add(new ItemBooking(listSend.get(i).rowId,
                                                                    listFinal.get(i).getTvSLGiaoCHValue(),
                                                                    ((listSend.get(i).soKi / listSend.get(i).soBich) * listFinal.get(i).getTvSLGiaoCHValue()),
                                                                    listFinal.get(i).getEditTextNote(),
                                                                    LoginPrefer.getObject(UpdateHistoryWhitoutMasanActivity.this).MaNhanVien,
                                                                    "",
                                                                    "",
                                                                    "",
                                                                    "",
                                                                    listFinal.get(i).getTvSLThieuValue(),
                                                                    listFinal.get(i).getTvSLDuValue(),
                                                                    listFinal.get(i).getTvSLTraVeValue(),
                                                                    ""));
                                                            i++;
                                                        }

                                                        progressDialog = Utilities.getProgressDialog(UpdateHistoryWhitoutMasanActivity.this, "Đang gửi..");
                                                        progressDialog.show();

                                                        MyRetrofit.initRequest(UpdateHistoryWhitoutMasanActivity.this).updateBill(strToken, item2List).enqueue(new Callback<ItemBill>() {
                                                            @Override
                                                            public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                                                if (response.isSuccessful() && response != null) {

                                                                    MyRetrofit.initRequest(UpdateHistoryWhitoutMasanActivity.this).UpdateHisTotalCartonAndTray(strToken, edtDiaSLCarton.getText().toString(), edtDiaSLKhay.getText().toString(),
                                                                            edtDiaSLProductRecall.getText().toString(), strOrderreleaseId, strStoreCode, strAtmShipmentId, strCustomer).enqueue(new Callback<Integer>() {
                                                                        @Override
                                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                            if (response.body() == 1) {
                                                                                Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                                                dialog.dismiss();
                                                                            } else {
                                                                                Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                            }

                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Integer> call, Throwable t) {
                                                                            RetrofitError.errorAction(UpdateHistoryWhitoutMasanActivity.this, new NoInternet(), TAG, view);
                                                                        }
                                                                    });
                                                                }
                                                                Utilities.dismissDialog(progressDialog);
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ItemBill> call, Throwable t) {
                                                                Utilities.dismissDialog(progressDialog);
                                                                RetrofitError.errorAction(UpdateHistoryWhitoutMasanActivity.this, new NoInternet(), TAG, view);

                                                            }
                                                        });
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                    dialog.show();
                                    dialog.getWindow().setAttributes(lp);
                                }
                            }

                            @Override
                            public void onFailure(Call<HisTotalCartonAndTray> call, Throwable t) {

                            }
                        });
                    } else {
                        if (longTimeCurrent < longTimeCompleted) {

                            MyRetrofit.initRequest(UpdateHistoryWhitoutMasanActivity.this).GetHisTrayAndCarton(strToken, strAtmShipmentId, strOrderreleaseId, strStoreCode, strCustomer).enqueue(new Callback<HisTotalCartonAndTray>() {
                                @Override
                                public void onResponse(Call<HisTotalCartonAndTray> call, Response<HisTotalCartonAndTray> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        final Dialog dialog = new Dialog(UpdateHistoryWhitoutMasanActivity.this);
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.dialog_carton_tray_vin);

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialog.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                        LinearLayout lnEdtTitle = dialog.findViewById(R.id.lnEdtTitle);
                                        LinearLayout lnedtDiaSLKhay = dialog.findViewById(R.id.lnedtDiaSLKhay);
                                        LinearLayout lnedtDiaSLCarton = dialog.findViewById(R.id.lnedtDiaSLCarton);
                                        TextView tvDiaSLThungTuKho = dialog.findViewById(R.id.tvDiaSLThungTuKho);
                                        TextView tvTitleSLThungTuKho = dialog.findViewById(R.id.tvTitleSLThungTuKho);
                                        TextInputEditText edtDiaSLCarton = dialog.findViewById(R.id.edtDiaSLCarton);
                                        TextInputEditText edtDiaSLKhay = dialog.findViewById(R.id.edtDiaSLKhay);
                                        TextInputEditText edtDiaSLProductRecall = dialog.findViewById(R.id.edtDiaSLProductRecall);
                                        Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                        Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);


                                        lnedtDiaSLKhay.setVisibility(View.GONE);
                                        lnedtDiaSLCarton.setVisibility(View.GONE);
                                        lnEdtTitle.setVisibility(View.GONE);
                                        tvTitleSLThungTuKho.setVisibility(View.GONE);

                                        tvDiaSLThungTuKho.setText(String.valueOf(response.body().totalCartonMasan));
                                        edtDiaSLCarton.setText(String.valueOf(response.body().realNumDelivered) == null ? "0" : String.valueOf(response.body().realNumDelivered));
                                        edtDiaSLKhay.setText(String.valueOf(response.body().totalTray));
                                        edtDiaSLProductRecall.setText(String.valueOf(response.body().productRecall));

                                        btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (longTimeCurrent < longTimeCompleted) {
                                                    if (edtDiaSLCarton.getText().toString() == null || edtDiaSLKhay.getText().toString() == null ||
                                                            edtDiaSLCarton.getText().toString().equals("") || edtDiaSLKhay.getText().toString().equals("") || edtDiaSLKhay.getText().toString().equals("")) {
                                                        Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Số khay, số thùng và số thu hồi không được để trống", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (Integer.parseInt(edtDiaSLCarton.getText().toString()) < 0 && Integer.parseInt(edtDiaSLKhay.getText().toString()) < 0) {
                                                            Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Số lượng giao không thể nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            List<ItemBooking> item2List = new ArrayList<>();
                                                            int i = 0;

                                                            for (ItemChild item : listSend) {
                                                                item2List.add(new ItemBooking(listSend.get(i).rowId,
                                                                        listFinal.get(i).getTvSLGiaoCHValue(),
                                                                        ((listSend.get(i).soKi / listSend.get(i).soBich) * listFinal.get(i).getTvSLGiaoCHValue()),
                                                                        listFinal.get(i).getEditTextNote(),
                                                                        LoginPrefer.getObject(UpdateHistoryWhitoutMasanActivity.this).MaNhanVien,
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        listFinal.get(i).getTvSLThieuValue(),
                                                                        listFinal.get(i).getTvSLDuValue(),
                                                                        listFinal.get(i).getTvSLTraVeValue(),
                                                                        ""));
                                                                i++;
                                                            }

                                                            progressDialog = Utilities.getProgressDialog(UpdateHistoryWhitoutMasanActivity.this, "Đang gửi..");
                                                            progressDialog.show();

                                                            MyRetrofit.initRequest(UpdateHistoryWhitoutMasanActivity.this).updateBill(strToken, item2List).enqueue(new Callback<ItemBill>() {
                                                                @Override
                                                                public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                                                    if (response.isSuccessful() && response != null) {

                                                                        MyRetrofit.initRequest(UpdateHistoryWhitoutMasanActivity.this).UpdateHisTotalCartonAndTray(strToken, edtDiaSLCarton.getText().toString(), edtDiaSLKhay.getText().toString(),
                                                                                edtDiaSLProductRecall.getText().toString(), strOrderreleaseId, strStoreCode, strAtmShipmentId, strCustomer).enqueue(new Callback<Integer>() {
                                                                            @Override
                                                                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                                if (response.body() == 1) {
                                                                                    Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                                                    dialog.dismiss();
                                                                                } else {
                                                                                    Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                                }

                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<Integer> call, Throwable t) {
                                                                                RetrofitError.errorAction(UpdateHistoryWhitoutMasanActivity.this, new NoInternet(), TAG, view);
                                                                            }
                                                                        });
                                                                    }
                                                                    Utilities.dismissDialog(progressDialog);
                                                                }

                                                                @Override
                                                                public void onFailure(Call<ItemBill> call, Throwable t) {
                                                                    Utilities.dismissDialog(progressDialog);
                                                                    RetrofitError.errorAction(UpdateHistoryWhitoutMasanActivity.this, new NoInternet(), TAG, view);

                                                                }
                                                            });
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                        dialog.show();
                                        dialog.getWindow().setAttributes(lp);
                                    }
                                }

                                @Override
                                public void onFailure(Call<HisTotalCartonAndTray> call, Throwable t) {

                                }
                            });


                        } else {
                            Toast.makeText(UpdateHistoryWhitoutMasanActivity.this, "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
                        }

                    }


                }


            }

        });

        rvUpdateNhanHang.setAdapter(adapter);
        rvUpdateNhanHang.invalidate();
    }


    private List<ItemChild> populateList() {
        list = new ArrayList<>();
        listSend = new ArrayList<ItemChild>();

        itemsViewModel.getAllItems3(strDate2, strStoreCode, strAtmShipmentId, strOrderreleaseId, strCustomer, UpdateHistoryWhitoutMasanActivity.this).observe(this, new Observer<List<ItemChild>>() {
            @Override
            public void onChanged(List<ItemChild> itemChildren) {
                listSend = itemChildren;
                for (int i = 0; i < itemChildren.size(); i++) {
                    ItemChild itemChild = new ItemChild();
                    itemChild.setTvSLGiaoCHValue(Integer.parseInt(itemChildren.get(i).actual_Received));
//                    itemChild.setEditTextNote(itemChildren.get(i).notes);
                    itemChild.setTvSLThieuValue(itemChildren.get(i).thieu);
                    itemChild.setTvSLDuValue(itemChildren.get(i).thua);
                    itemChild.setTvSLTraVeValue(itemChildren.get(i).trave);
                    list.add(itemChild);
                }
                adapter.setItemChildren(UpdateHistoryWhitoutMasanActivity.this, itemChildren);
            }
        });

        return list;
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


    @Override
    public void onAttachedToWindow() {
        rvUpdateNhanHang.setFocusableInTouchMode(true);
        super.onAttachedToWindow();
    }
}
