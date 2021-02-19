package com.tandm.abadeliverydriver.main.nhanhang;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.ViewModel.ItemsViewModel;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.MyRetrofit2;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBooking;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.nhanhang.model.TotalCartonVin;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassList;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment2;
import com.tandm.abadeliverydriver.main.utilities.Customer;
import com.tandm.abadeliverydriver.main.utilities.ScrollingLinearLayoutManager;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.zalo.TemplateData;
import com.tandm.abadeliverydriver.main.zalo.TemplateDataParent2;
import com.tandm.abadeliverydriver.main.zalo.Zalo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NhanHangActivity extends AppCompatActivity {
    public static final String TAG = "NhanHangActivity";


    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    @BindView(R.id.tbNhanHang)
    Toolbar tbNhanHang;
    @BindView(R.id.rvNhanHang)
    RecyclerView rvNhanHang;
    @BindView(R.id.tvThoiGianNhanHang)
    TextView tvThoiGianNhanHang;
    @BindView(R.id.tvMaCuaHangNhanHang)
    TextView tvMaCuaHangNhanHang;
    @BindView(R.id.tvCuaHangNhanHang)
    TextView tvCuaHangNhanHang;
    @BindView(R.id.tvDiaChiNhanHang)
    TextView tvDiaChiNhanHang;
    @BindView(R.id.tvSoLuongCB)
    TextView tvSoLuongCB;
    @BindView(R.id.tvSoLuongTotal)
    TextView tvSoLuongTotal;
    @BindView(R.id.btnCheckAll)
    ImageButton btnCheckAll;
    @BindView(R.id.btnSend)
    ImageButton btnSend;
    @BindView(R.id.cbNhanGium)
    CheckBox cbNhanGium;
    @BindView(R.id.edtMaCHNhanGium)
    TextInputEditText edtMaCHNhanGium;
    @BindView(R.id.edtLyDoNhanGium)
    TextInputEditText edtLyDoNhanGium;
    @BindView(R.id.edtSLThungXop)
    TextInputEditText edtSLThungXop;
    @BindView(R.id.btnTotal)
    Button btnTotal;

    String strStoreCode, strToken, strDate, strDate2, strName, strCustomerCode, strMaCHNhanGium = "", strLyDoNhanGium = "", strShipment = "", strOrdeR_RELEASE_XID = "", strPackaged_Item_XID = "", strLat = "0.0", strLng = "0.0";
    int strTotal;


    ItemsViewModel itemsViewModel;
    private List<ItemChild> itemString;
    private List<ItemChild> list;
    private List<ItemChild> listSend;
    private List<ItemChild> listFinal;
    NhanHangAdapter nhanHangAdapter;
    //    List<Problem2sChild> listProblem2s;
    int i = 0;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_hang);
        ButterKnife.bind(this);
        setSupportActionBar(tbNhanHang);
        Utilities.showBackIcon(getSupportActionBar());
        rvNhanHang.setNestedScrollingEnabled(false);
        rvNhanHang.setHasFixedSize(true);
        rvNhanHang.setItemViewCacheSize(20);
        int duration = getResources().getInteger(R.integer.scroll_duration);
        rvNhanHang.setLayoutManager(new ScrollingLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, duration));
        strToken = LoginPrefer.getObject(this).access_token;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Bundle b = getIntent().getExtras();
        strDate = Utilities.formatDate_MMddyyyy(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
//        strDate2 = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        Log.e("date", strDate2 + " and " + strDate);
        itemString = new ArrayList<ItemChild>();
//        listProblem2s = new ArrayList<Problem2sChild>();

        if (b == null) {
            tvThoiGianNhanHang.setText("");
            tvMaCuaHangNhanHang.setText("");
            tvCuaHangNhanHang.setText("");
            tvDiaChiNhanHang.setText("");
        } else {
            strStoreCode = b.getString("storecode");
            strDate2 = b.getString("date");
            strName = b.getString("storeaddress");
            strTotal = b.getInt("storetotal");
            strCustomerCode = b.getString("khachhang");
            strShipment = b.getString("atmshipmentid");
            strOrdeR_RELEASE_XID = b.getString("orderreleasexid");
            strPackaged_Item_XID = b.getString("packageditemxid");
            tvThoiGianNhanHang.setText(Utilities.formatDate_ddMMyyyy(strDate2));
            tvMaCuaHangNhanHang.setText(strStoreCode);
            tvCuaHangNhanHang.setText(b.getString("storename"));
            tvDiaChiNhanHang.setText(strName);
        }

        if (!strCustomerCode.equals(Customer.BHX)) {
            edtSLThungXop.setVisibility(View.GONE);
        }

        itemsViewModel = ViewModelProviders.of(NhanHangActivity.this).get(ItemsViewModel.class);
        itemString = populateList();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            Utilities.openSettings(NhanHangActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        nhanHangAdapter = new NhanHangAdapter(itemString, new PassCheckBox() {
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

                nhanHangAdapter.selectAll();

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissions()) {
                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
//                        init();
                    } else {
//                        startLocationUpdates();
//                        if (mCurrentLocation == null) {
//                            Snackbar.make(view, "Chưa định vị đc vui lòng thử lại", Snackbar.LENGTH_LONG).show();
//                        } else {
                        if (i < listSend.size()) {
                            AlertDialog.Builder b = new AlertDialog.Builder(NhanHangActivity.this);
                            b.setTitle("Thông báo").setIcon(R.drawable.warning).setMessage("Vui lòng xác nhận đầy đủ đơn hàng!").show();
                        } else {
                            listFinal = new ArrayList<>();
                            NhanHangAdapter nhanHangAdapter = new NhanHangAdapter(new PassList() {
                                @Override
                                public void passList(List<ItemChild> list) {
                                    listFinal = list;
                                }
                            });
                            nhanHangAdapter.Send();
                            if (LoginPrefer.getObject(NhanHangActivity.this).isBiker) {
                                MyRetrofit.initRequest(NhanHangActivity.this).GetTotalCartonVin("Bearer " + strToken, strDate2, strCustomerCode, strStoreCode).enqueue(new Callback<TotalCartonVin>() {
                                    @Override
                                    public void onResponse(Call<TotalCartonVin> call, Response<TotalCartonVin> response) {
                                        if (response.isSuccessful() && response.body() != null) {
//                                                if (response.body().atM_OrderReleaseID != null) {
                                            final Dialog dialog = new Dialog(NhanHangActivity.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.dialog_carton_tray_vin);


                                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                            lp.copyFrom(dialog.getWindow().getAttributes());
                                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                            LinearLayout edtNewzealand = dialog.findViewById(R.id.edtNewzealand);
                                            LinearLayout lnedtDiaSLKhay = dialog.findViewById(R.id.lnedtDiaSLKhay);
                                            TextView tvDiaSLThungTuKho = dialog.findViewById(R.id.tvDiaSLThungTuKho);
                                            TextInputEditText edtDiaSLCarton = dialog.findViewById(R.id.edtDiaSLCarton);
                                            TextInputEditText edtDiaSLKhay = dialog.findViewById(R.id.edtDiaSLKhay);
                                            TextInputEditText edtDiaSLProductRecall = dialog.findViewById(R.id.edtDiaSLProductRecall);
                                            Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                            Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);

                                            lnedtDiaSLKhay.setVisibility(View.VISIBLE);
                                            if (!strCustomerCode.equals(Customer.NEWZEALAND)) {
                                                edtNewzealand.setVisibility(View.GONE);
                                            } else {
                                                edtNewzealand.setVisibility(View.VISIBLE);
                                            }


                                            tvDiaSLThungTuKho.setText(String.valueOf(response.body().boxQuantity));
                                            edtDiaSLCarton.setText(String.valueOf(response.body().boxQuantity) == null ? "0" : String.valueOf(response.body().boxQuantity));
                                            edtDiaSLKhay.setText(String.valueOf(response.body().boxQuantity) == null ? "0" : String.valueOf(response.body().boxQuantity));
                                            edtDiaSLProductRecall.setText("0");


                                            btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (edtDiaSLCarton.getText().toString() == null || edtDiaSLKhay.getText().toString() == null ||
                                                            edtDiaSLCarton.getText().toString().equals("") || edtDiaSLKhay.getText().toString().equals("") || edtDiaSLProductRecall.getText().toString().equals("")) {
                                                        Toast.makeText(NhanHangActivity.this, "Số khay, số thùng và số thu hồi không được để trống", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (Integer.parseInt(edtDiaSLCarton.getText().toString()) < 0 && Integer.parseInt(edtDiaSLKhay.getText().toString()) < 0) {
                                                            Toast.makeText(NhanHangActivity.this, "Số lượng giao không thể nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            List<ItemBooking> item2List = new ArrayList<>();
                                                            int i = 0;

                                                            for (ItemChild item : listSend) {
                                                                Log.e("kg", String.valueOf(((listSend.get(i).soKi / listSend.get(i).soBich) * listFinal.get(i).getTvSLGiaoCHValue())));
                                                                item2List.add(new ItemBooking(
                                                                        listSend.get(i).xdocK_Doc_Entry,
                                                                        listFinal.get(i).getTvSLGiaoCHValue(),
                                                                        ((listSend.get(i).soKi / listSend.get(i).soBich) * listFinal.get(i).getTvSLGiaoCHValue()),
                                                                        listFinal.get(i).getEditTextNote(),
                                                                        LoginPrefer.getObject(NhanHangActivity.this).MaNhanVien,
                                                                        strLat,
                                                                        strLng,
                                                                        strMaCHNhanGium,
                                                                        strLyDoNhanGium,
                                                                        listFinal.get(i).getTvSLThieuValue(),
                                                                        listFinal.get(i).getTvSLDuValue(),
                                                                        listFinal.get(i).getTvSLTraVeValue(),
                                                                        strShipment
                                                                ));
                                                                i++;
                                                            }

                                                            progressDialog = Utilities.getProgressDialog(NhanHangActivity.this, "Đang gửi..");
                                                            progressDialog.show();

                                                            MyRetrofit.initRequest(NhanHangActivity.this).updateBooking("Bearer " + strToken, item2List).enqueue(new Callback<ItemBill>() {
                                                                @Override
                                                                public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                                                    if (response.isSuccessful() && response != null) {
                                                                        if (response.code() == 200) {
                                                                            MyRetrofit.initRequest(NhanHangActivity.this).UpdateStateTripDriver("Bearer " + strToken, strStoreCode, strDate2, strCustomerCode, LoginPrefer.getObject(NhanHangActivity.this).userName,
                                                                                    strLat,
                                                                                    strLng,
                                                                                    edtDiaSLKhay.getText().toString() == null ? "0" : edtDiaSLKhay.getText().toString(), edtDiaSLCarton.getText().toString() == null ? "0" : edtDiaSLCarton.getText().toString(), tvDiaSLThungTuKho.getText().toString(), edtDiaSLProductRecall.getText().toString() == null ? "0" : edtDiaSLProductRecall.getText().toString(), strShipment, strOrdeR_RELEASE_XID, LoginPrefer.getObject(NhanHangActivity.this).userName).enqueue(new Callback<Integer>() {
                                                                                @Override
                                                                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                                    if (response.isSuccessful() && response.body() != null) {
                                                                                        if (response.body() == 1) {
                                                                                            Snackbar.make(view, "Đã gửi!", Snackbar.LENGTH_LONG).show();
                                                                                            sendZalo("84903631331");

//                                                                                                MyRetrofit2.initRequest2().SendZaloRating(templateDataParentRating).enqueue(new Callback<Zalo>() {
//                                                                                                    @Override
//                                                                                                    public void onResponse(Call<Zalo> call, Response<Zalo> response) {
//                                                                                                        if (response.isSuccessful() && response.body() != null) {
//                                                                                                            if (response.body().message.equals("Success")) {
//                                                                                                                Toast.makeText(NhanHangActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
//                                                                                                            }
//                                                                                                        }
//                                                                                                    }
//
//                                                                                                    @Override
//                                                                                                    public void onFailure(Call<Zalo> call, Throwable t) {
//                                                                                                        Toast.makeText(NhanHangActivity.this, "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
//                                                                                                    }
//                                                                                                });

                                                                                            Intent intent = new Intent(NhanHangActivity.this, MainActivity.class);
                                                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                            startActivity(intent);
                                                                                            finish();
                                                                                        } else {
                                                                                            Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                                        }
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<Integer> call, Throwable t) {
                                                                                    RetrofitError.errorAction(NhanHangActivity.this, new NoInternet(), TAG, view);
                                                                                }
                                                                            });
                                                                        } else if (response.code() == 500) {
                                                                            AlertDialog.Builder b = new AlertDialog.Builder(NhanHangActivity.this);
                                                                            b.setTitle("Thất bại!").setMessage("Vui lòng thử lại");
                                                                            b.setCancelable(true);
                                                                            Dialog dialog = b.create();
                                                                            dialog.show();
                                                                        } else {
                                                                            AlertDialog.Builder b = new AlertDialog.Builder(NhanHangActivity.this);
                                                                            b.setTitle("Thất bại!").setMessage("Vui lòng thử lại");
                                                                            b.setCancelable(true);
                                                                            Dialog dialog = b.create();
                                                                            dialog.show();
                                                                        }

                                                                    }
                                                                    Utilities.dismissDialog(progressDialog);
                                                                }

                                                                @Override
                                                                public void onFailure(Call<ItemBill> call, Throwable t) {
                                                                    Utilities.dismissDialog(progressDialog);
                                                                    RetrofitError.errorAction(NhanHangActivity.this, new NoInternet(), TAG, view);
                                                                }
                                                            });

                                                        }
                                                    }


                                                }
                                            });

                                            dialog.show();
                                            dialog.getWindow().setAttributes(lp);

                                        }
                                    }
//                                        }

                                    @Override
                                    public void onFailure(Call<TotalCartonVin> call, Throwable t) {
                                        RetrofitError.errorAction(NhanHangActivity.this, new NoInternet(), TAG, view);
                                    }
                                });
                            } else {

                                final Dialog dialog = new Dialog(NhanHangActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_carton_tray_vin);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                LinearLayout edtNewzealand = dialog.findViewById(R.id.edtNewzealand);
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

                                lnedtDiaSLKhay.setVisibility(View.VISIBLE);
                                lnedtDiaSLCarton.setVisibility(View.VISIBLE);
                                lnEdtTitle.setVisibility(View.GONE);
                                tvTitleSLThungTuKho.setVisibility(View.GONE);
                                if (!strCustomerCode.equals(Customer.NEWZEALAND)) {
                                    edtNewzealand.setVisibility(View.GONE);
                                } else {
                                    edtNewzealand.setVisibility(View.VISIBLE);
                                }

                                if (strCustomerCode.equals(Customer.BHX)) {
                                    lnedtDiaSLCarton.setVisibility(View.GONE);
                                }

                                edtDiaSLCarton.setText(edtSLThungXop.getText().toString().equals("") ? "0" : edtSLThungXop.getText().toString());
                                edtDiaSLKhay.setText("0");
                                edtDiaSLProductRecall.setText("0");

                                btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (edtDiaSLCarton.getText().toString() == null || edtDiaSLKhay.getText().toString() == null ||
                                                edtDiaSLCarton.getText().toString().equals("") || edtDiaSLKhay.getText().toString().equals("")) {
                                            Toast.makeText(NhanHangActivity.this, "Số khay và số thùng không được để trống", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (Integer.parseInt(edtDiaSLCarton.getText().toString()) < 0 && Integer.parseInt(edtDiaSLKhay.getText().toString()) < 0) {
                                                Toast.makeText(NhanHangActivity.this, "Số lượng giao không thể nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                                            } else {
                                                List<ItemBooking> item2List = new ArrayList<>();
                                                int i = 0;

                                                for (ItemChild item : listSend) {
                                                    Log.e("kg", String.valueOf(((listSend.get(i).soKi / listSend.get(i).soBich) * listFinal.get(i).getTvSLGiaoCHValue())));
                                                    item2List.add(new ItemBooking(listSend.get(i).xdocK_Doc_Entry,
                                                            listFinal.get(i).getTvSLGiaoCHValue(),
                                                            ((listSend.get(i).soKi / listSend.get(i).soBich) * listFinal.get(i).getTvSLGiaoCHValue()),
                                                            listFinal.get(i).getEditTextNote(),
                                                            LoginPrefer.getObject(NhanHangActivity.this).MaNhanVien,
                                                            strLat,
                                                            strLng,
                                                            strMaCHNhanGium,
                                                            strLyDoNhanGium,
                                                            listFinal.get(i).getTvSLThieuValue(),
                                                            listFinal.get(i).getTvSLDuValue(),
                                                            listFinal.get(i).getTvSLTraVeValue(),
                                                            strShipment
                                                    ));
                                                    i++;
                                                }

                                                progressDialog = Utilities.getProgressDialog(NhanHangActivity.this, "Đang gửi..");
                                                progressDialog.show();

                                                MyRetrofit.initRequest(NhanHangActivity.this).updateBooking("Bearer " + strToken, item2List).enqueue(new Callback<ItemBill>() {
                                                    @Override
                                                    public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                                        if (response.isSuccessful() && response != null) {
                                                            if (response.code() == 200) {
                                                                MyRetrofit.initRequest(NhanHangActivity.this).UpdateStateTripDriver("Bearer " + strToken, strStoreCode, strDate2, strCustomerCode, LoginPrefer.getObject(NhanHangActivity.this).userName,
                                                                        strLat,
                                                                        strLng,
                                                                        edtDiaSLKhay.getText().toString() == null ? "0" : edtDiaSLKhay.getText().toString(), edtDiaSLCarton.getText().toString() == null ? "0" : edtDiaSLCarton.getText().toString(), tvDiaSLThungTuKho.getText().toString(), edtDiaSLProductRecall.getText().toString() == null ? "0" : edtDiaSLProductRecall.getText().toString(), strShipment, strOrdeR_RELEASE_XID, LoginPrefer.getObject(NhanHangActivity.this).userName).enqueue(new Callback<Integer>() {
                                                                    @Override
                                                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                        if (response.isSuccessful() && response.body() != null) {
                                                                            if (response.body() == 1) {
                                                                                Snackbar.make(view, "Đã gửi!", Snackbar.LENGTH_LONG).show();
                                                                                Intent intent = new Intent(NhanHangActivity.this, MainActivity.class);
                                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            } else {
                                                                                Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Integer> call, Throwable t) {
                                                                        RetrofitError.errorAction(NhanHangActivity.this, new NoInternet(), TAG, view);
                                                                    }
                                                                });
                                                            } else if (response.code() == 500) {
                                                                AlertDialog.Builder b = new AlertDialog.Builder(NhanHangActivity.this);
                                                                b.setTitle("Thất bại!").setMessage("Vui lòng thử lại");
                                                                b.setCancelable(true);
                                                                Dialog dialog = b.create();
                                                                dialog.show();
                                                            } else {
                                                                AlertDialog.Builder b = new AlertDialog.Builder(NhanHangActivity.this);
                                                                b.setTitle("Thất bại!").setMessage("Vui lòng thử lại");
                                                                b.setCancelable(true);
                                                                Dialog dialog = b.create();
                                                                dialog.show();
                                                            }

                                                        }
                                                        Utilities.dismissDialog(progressDialog);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ItemBill> call, Throwable t) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(NhanHangActivity.this, new NoInternet(), TAG, view);
                                                    }
                                                });

                                            }
                                        }


                                    }
                                });

                                dialog.show();
                                dialog.getWindow().setAttributes(lp);

                            }


                        }


//                        }
                    }

                } else {
                    Dexter.withActivity(NhanHangActivity.this)
                            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
//                                    startLocationUpdates();
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    if (response.isPermanentlyDenied()) {
                                        Utilities.openSettings(NhanHangActivity.this);
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();
                }


            }

        });


        cbNhanGium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edtMaCHNhanGium.setEnabled(true);
                    edtLyDoNhanGium.setEnabled(true);
                } else {
                    edtMaCHNhanGium.setEnabled(false);
                    edtLyDoNhanGium.setEnabled(false);
                    edtMaCHNhanGium.setText("");
                    edtLyDoNhanGium.setText("");
                }
            }
        });


        edtMaCHNhanGium.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                strMaCHNhanGium = edtMaCHNhanGium.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edtLyDoNhanGium.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                strLyDoNhanGium = edtLyDoNhanGium.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rvNhanHang.setAdapter(nhanHangAdapter);
        rvNhanHang.invalidate();

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


    private List<ItemChild> populateList() {
        list = new ArrayList<>();
        listSend = new ArrayList<ItemChild>();

//        if (strCustomerCode.equals(Customer.VCMFRESH) || strCustomerCode.equals(Customer.BHX) || strCustomerCode.equals(Customer.CP) || strCustomerCode.equals(Customer.NEWZEALAND) || strCustomerCode.equals(Customer.THREEF)|| strCustomerCode.equals(Customer.XX6020)) {
        //strDate2
        itemsViewModel.getAllItems(strStoreCode, strDate2, strCustomerCode, strOrdeR_RELEASE_XID, NhanHangActivity.this).observe(this, new Observer<List<ItemChild>>() {
            @Override
            public void onChanged(List<ItemChild> itemChildren) {
                listSend = itemChildren;
                int c = 0;
                int t = 0;
                for (int i = 0; i < itemChildren.size(); i++) {
                    ItemChild itemChild = new ItemChild();
                    itemChild.setTvSLGiaoCHValue(itemChildren.get(i).soBich);
                    itemChild.setEditTextNote(itemChildren.get(i).notes);
                    itemChild.setTvSLThieuValue(0);
                    itemChild.setTvSLDuValue(0);
                    itemChild.setTvSLTraVeValue(0);
                    list.add(itemChild);
                    t += itemChildren.get(i).soBich;
                    c++;
                }
                btnTotal.setText("Tổng: " + t);
                tvSoLuongTotal.setText(String.valueOf(c));
                nhanHangAdapter.setItemChildren(NhanHangActivity.this, itemChildren);

            }
        });
//        }

        return list;
    }

    @Override
    public void onAttachedToWindow() {
        rvNhanHang.setFocusableInTouchMode(true);
        super.onAttachedToWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToFragment event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            Toast.makeText(NhanHangActivity.this, data, Toast.LENGTH_SHORT).show();
            strLat = String.valueOf(event.getLocation().getLatitude());
            strLng = String.valueOf(event.getLocation().getLongitude());
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation2(SendLocationToFragment2 event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            Toast.makeText(NhanHangActivity.this, data + "/aaa2", Toast.LENGTH_SHORT).show();
            strLat = String.valueOf(event.getLocation().getLatitude());
            strLng = String.valueOf(event.getLocation().getLongitude());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    private void sendZalo(String phone) {

        TemplateDataParent2 templateDataParentRating = new TemplateDataParent2(phone, "202474", new TemplateData("", "", "", "", ""), "3");

        MyRetrofit2.initRequest2().SendZaloRating(templateDataParentRating).enqueue(new Callback<Zalo>() {
            @Override
            public void onResponse(Call<Zalo> call, Response<Zalo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().message.equals("Success")) {
                        Toast.makeText(NhanHangActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zalo> call, Throwable t) {
                Toast.makeText(NhanHangActivity.this, "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
