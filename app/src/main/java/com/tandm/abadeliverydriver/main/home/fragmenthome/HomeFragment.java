package com.tandm.abadeliverydriver.main.home.fragmenthome;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.giaohang.model.StateStop;
import com.tandm.abadeliverydriver.main.nhanhang.NhanHangActivity;
import com.tandm.abadeliverydriver.main.nhanhang.model.TotalItems;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.suco.SuCoActivity;
import com.tandm.abadeliverydriver.main.utilities.FragmentScan;
import com.tandm.abadeliverydriver.main.utilities.ScanCameraPortrait;
import com.tandm.abadeliverydriver.main.utilities.SpinCusPrefCustomer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.jai.genericdialog2.GenericDialog;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private static final int PERMISSION_CODE = 10001;
    //
//    // location updates interval - 10sec
//    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
//
//    // fastest updates interval - 5 sec
//    // location updates will be received if another app is requesting the locations
//    // than your app can handle
//    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
//
    private static final int REQUEST_CHECK_SETTINGS = 100;
    //
//    // bunch of location related apis
//    private FusedLocationProviderClient mFusedLocationClient;
//    private SettingsClient mSettingsClient;
//    private LocationRequest mLocationRequest;
//    private LocationSettingsRequest mLocationSettingsRequest;
//    private LocationCallback mLocationCallback;
//    private Location mCurrentLocation;
//
    View view;
    Unbinder unbinder;
    String strDate, strDate2, strToken, strUsername, strCustomerCode, strCustomerName, strStoreCode, strCustomerNameFromAPI, strATMShipmentID, strATMOrderReleaseID, strTotalWeight, strLocationName, strLocationGID, strAddressLine, strPackagegItemId;
    int strTotal;
    boolean bToggle = false;
    ProgressDialog progressDialog;
    StoreATM storeATM;
    List<StoreATM> storeATM2;
    ZXingScannerView zXingScannerView;
    @BindView(R.id.spinBikerCustomer)
    Spinner spinBikerCustomer;
    @BindView(R.id.lnHomeBiker)
    LinearLayout lnHomeBiker;
    @BindView(R.id.edtStoreID)
    TextInputEditText edtStoreID;
    @BindView(R.id.tvPreStoreCode)
    EditText tvPreStoreCode;
    @BindView(R.id.edtTotalCarton)
    TextInputEditText edtTotalCarton;
    @BindView(R.id.btnQRCode)
    ImageButton btnQRCode;
    @BindView(R.id.btnFind)
    ImageButton btnFind;
    @BindView(R.id.cardHomeBiker)
    CardView cardHomeBiker;
    @BindView(R.id.tvThoiGianNhanHang)
    TextView tvThoiGianNhanHang;
    @BindView(R.id.tvMaCuaHangNhanHang)
    TextView tvMaCuaHangNhanHang;
    @BindView(R.id.tvCuaHangNhanHang)
    TextView tvCuaHangNhanHang;
    @BindView(R.id.tvDiaChiNhanHang)
    TextView tvDiaChiNhanHang;
    @BindView(R.id.tvThongBaoBiker)
    TextView tvThongBaoBiker;
    @BindView(R.id.relaAlert1)
    RelativeLayout relaAlert1;
    @BindView(R.id.relaAlert2)
    RelativeLayout relaAlert2;
    @BindView(R.id.relaAlert3)
    RelativeLayout relaAlert3;
    @BindView(R.id.toggleBiker)
    CustomToggleButton toggleBiker;
    @BindView(R.id.btnSuCoHomeBiker)
    Button btnSuCoHomeBiker;
    @BindView(R.id.btnXacNhanHomeBiker)
    Button btnXacNhanHomeBiker;
    @BindView(R.id.rvStoreBikerVinMas)
    RecyclerView rvStoreBikerVinMas;
    Activity activity;
    BikerCustomerAdapter adapter;
    BikerStoreVinMasAdapter bikerStoreVinMasAdapter;
    String strTotalCarton;
//    String strStoreCodeMain, strStoreNameMain, strStoreAddressMain, strKhachHangMain, stratM_SHIPMENT_ID, strorderrelease_id, strpackaged_Item_XID, strMaNhanVien, strTotalWeight;
//    int strTotal, TotalCarton;
//    boolean blMobileHub;
//    List<Store2> list;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDate2 = Utilities.formatDate_ddMMyyyy(Utilities.formatDateTime_ddMMyyyyFromMili(System.currentTimeMillis()));
        strToken = "Bearer " + LoginPrefer.getObject(getActivity()).access_token;
        strUsername = LoginPrefer.getObject(getActivity()).userName;
        tvThoiGianNhanHang.setText(strDate2);

        zXingScannerView = new ZXingScannerView(getContext());
        activity = getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvStoreBikerVinMas.setHasFixedSize(true);
        rvStoreBikerVinMas.setLayoutManager(layoutManager);

//        init();
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            Utilities.openSettings(getContext());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


        getBikerCustomer(strToken);


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadInfoStoreATM(edtStoreID.getText().toString());

            }
        });

        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentScan intentIntegrator = new FragmentScan(HomeFragment.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.setCaptureActivity(ScanCameraPortrait.class);
                intentIntegrator.initiateScan();
            }
        });

        btnSuCoHomeBiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getContext(), SuCoActivity.class);
                i2.putExtra("storecode", strLocationGID);
                i2.putExtra("storename", strLocationName);
                i2.putExtra("storeaddress", strAddressLine);
                i2.putExtra("date", strDate);
                i2.putExtra("khachhang", strCustomerNameFromAPI);
                i2.putExtra("orderreleasexid", strATMOrderReleaseID);
                i2.putExtra("packageditemxid", strPackagegItemId);
                startActivity(i2);
            }
        });

        btnXacNhanHomeBiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleBiker.isChecked()) {
                    if (strCustomerNameFromAPI != null) {
                        if (strCustomerNameFromAPI.equals("MASAN")) {
                            MyRetrofit.initRequest(getContext()).updateStateStop(strToken, strLocationGID, strDate, strCustomerNameFromAPI, strATMShipmentID, 0, true, 0, 0, 0, Integer.parseInt(edtTotalCarton.getText().toString()), strTotalWeight, String.valueOf(0.0), String.valueOf(0.0), strATMOrderReleaseID, LoginPrefer.getObject(getContext()).userName, Integer.parseInt(strTotalCarton),0).enqueue(new Callback<StateStop>() {
                                @Override
                                public void onResponse(Call<StateStop> call, Response<StateStop> response) {
                                    if (response.isSuccessful() && response != null) {
                                        Utilities.dismissDialog(progressDialog);
                                        new GenericDialog.Builder(getContext())
                                                .setIcon(R.drawable.completed)
                                                .setTitle("Hoàn Thành!").setTitleAppearance(R.color.colorPrimaryDark, 16)
                                                .setMessage("Chúc mừng bạn đã hoàn thành xong điểm " + strLocationName + "!")
                                                .setCancelable(true)
                                                .generate();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                relaAlert2.setVisibility(View.GONE);
                                                relaAlert1.setVisibility(View.VISIBLE);
                                                tvThongBaoBiker.setText(R.string.thongbao5);
                                                edtStoreID.setText("");
                                                tvMaCuaHangNhanHang.setText("");
                                                tvCuaHangNhanHang.setText("");
                                                tvDiaChiNhanHang.setText("");
                                            }
                                        }, 2000);
                                    } else {
                                        Utilities.dismissDialog(progressDialog);
                                        Snackbar.make(view, "Thất bại! vui lòng thử lại", Snackbar.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<StateStop> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                    Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
                                }
                            });
//                    } else if (strCustomerNameFromAPI.equals("VCMFRESH")) {
                        } else {
                            //strDate
                            MyRetrofit.initRequest(getContext()).getTotalPGH(strStoreCode, strDate, strCustomerNameFromAPI, strATMOrderReleaseID, strToken).enqueue(new Callback<TotalItems>() {
                                @Override
                                public void onResponse(Call<TotalItems> call, Response<TotalItems> response) {
                                    if (response.isSuccessful() && response != null) {
                                        strTotal = response.body().totalItems;
                                        Intent i = new Intent(getContext(), NhanHangActivity.class);
                                        i.putExtra("storecode", strLocationGID);
                                        i.putExtra("storename", strLocationName);
                                        i.putExtra("storeaddress", strAddressLine);
                                        i.putExtra("date", strDate);
                                        i.putExtra("storetotal", strTotal);
                                        i.putExtra("khachhang", strCustomerNameFromAPI);
                                        i.putExtra("atmshipmentid", strATMShipmentID);
                                        i.putExtra("orderreleasexid", strATMOrderReleaseID);
                                        i.putExtra("packageditemxid", strPackagegItemId);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onFailure(Call<TotalItems> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                }
                            });

                        }
                    }
                } else {
                    Snackbar.make(view, "Vui lòng bấm Đã Tới trước khi bấm nhận hàng", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        toggleBiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = Utilities.getProgressDialog(getContext(), "Đang lấy thông tin...");
                progressDialog.show();

                if (!WifiHelper.isConnected(getActivity())) {
                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                    Utilities.dismissDialog(progressDialog);
                    return;
                }

                MyRetrofit.initRequest(getContext()).updateArrivedTime(strToken, strATMShipmentID, strStoreCode, "0", "0", strATMOrderReleaseID).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body() == 1) {
                                toggleBiker.setChecked(true);
                                toggleBiker.setEnabled(false);
                                Snackbar.make(view, "Đã dến!", Snackbar.LENGTH_LONG).show();

                            } else if (response.body() == 0) {
                                toggleBiker.setChecked(false);
                                toggleBiker.setEnabled(true);
                                Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            toggleBiker.setChecked(false);
                            toggleBiker.setEnabled(true);
                            Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                        toggleBiker.setChecked(false);
                        toggleBiker.setEnabled(true);
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

    private void loadInfoStoreATM(String storeCode) {
        if (storeCode.contains("VCM-") || storeCode.contains("MAS-") || storeCode.contains("14111-") || storeCode.contains("MASAN-")) {
            getInfoStoreATM(strToken, strDate, strCustomerName, storeCode);
        } else {
            if (strCustomerCode != null || strCustomerCode != "") {
                strStoreCode = strCustomerCode + "-" + storeCode;
            }
            if (storeCode.length() > 0) {
                getInfoStoreATM(strToken, strDate, strCustomerName, strStoreCode);
            }
        }

    }


    private void getInfoStoreATM(String strToken, String strDate, String strCustomerName, String strStoreCode) {

        progressDialog = Utilities.getProgressDialog(getContext(), "Đang lấy thông tin...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

//        MyRetrofit.initRequest(getContext()).getInfoStoreATM(strToken, strDate, strCustomerName, strStoreCode).enqueue(new Callback<StoreATM>() {
//            @Override
//            public void onResponse(Call<StoreATM> call, Response<StoreATM> response) {
//                if (response.isSuccessful()) {
//                    Utilities.dismissDialog(progressDialog);
//                    if (response.code() == 200 && response.body() != null) {
//                        storeATM = new StoreATM();
//                        storeATM = response.body();
//                        tvPreStoreCode.setText(strStoreCode);
//                        tvMaCuaHangNhanHang.setText(storeATM.locationGID);
//                        tvCuaHangNhanHang.setText(storeATM.locationName);
//                        tvDiaChiNhanHang.setText(storeATM.addressLine);
//                        edtTotalCarton.setText(storeATM.totalCarton);
//                        strCustomerNameFromAPI = storeATM.customerCode;
//                        strTotalWeight = storeATM.totalWeight;
//                        strATMShipmentID = storeATM.atmShipmentID;
//                        strATMOrderReleaseID = storeATM.atmOrderReleaseID;
//                        strLocationName = storeATM.locationName;
//                        strLocationGID = storeATM.locationGID;
//                        strAddressLine = storeATM.addressLine;
//                        strPackagegItemId = storeATM.packagedItem;
//                        if (storeATM.arrivedByDeliverer){
//                            toggleBiker.setChecked(true);
//                            toggleBiker.setEnabled(false);
//                        }else {
//                            toggleBiker.setChecked(false);
//                            toggleBiker.setEnabled(true);
//                        }
//                        if (storeATM.isCompleted) {
//                            relaAlert2.setVisibility(View.GONE);
//                            relaAlert1.setVisibility(View.VISIBLE);
//                            tvThongBaoBiker.setText(R.string.thongbaobikeriscompleted);
//                        } else {
//                            relaAlert2.setVisibility(View.VISIBLE);
//                            relaAlert1.setVisibility(View.GONE);
//                            if (strCustomerNameFromAPI.equals("MASAN")) {
//                                edtTotalCarton.setVisibility(View.VISIBLE);
//                            } else {
//                                edtTotalCarton.setVisibility(View.GONE);
//                            }
//                        }
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setMessage("Không tìm thấy thông tin cửa hàng!");
//                        Dialog dialog = builder.create();
//                        dialog.show();
//                        relaAlert2.setVisibility(View.GONE);
//                        relaAlert1.setVisibility(View.VISIBLE);
//                        tvMaCuaHangNhanHang.setText("");
//                        tvCuaHangNhanHang.setText("");
//                        tvDiaChiNhanHang.setText("");
//                        tvPreStoreCode.setText("");
//                        tvThongBaoBiker.setText(R.string.thongbao5);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StoreATM> call, Throwable t) {
//                Utilities.dismissDialog(progressDialog);
//                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//            }
//        });

        // strDate
        MyRetrofit.initRequest(getContext()).getInfoStoreATM2(strToken, strDate, strCustomerName, strStoreCode).enqueue(new Callback<List<StoreATM>>() {
            @Override
            public void onResponse(Call<List<StoreATM>> call, Response<List<StoreATM>> response) {
                if (response.isSuccessful()) {
                    Utilities.dismissDialog(progressDialog);
                    if (response.code() == 200 && response.body() != null) {
                        storeATM2 = new ArrayList<>();
                        storeATM2 = response.body();
                        if (storeATM2.size() > 0 && storeATM2.size() < 2) {
                            rvStoreBikerVinMas.setVisibility(View.GONE);
                            tvPreStoreCode.setText(strStoreCode);
                            tvMaCuaHangNhanHang.setText(storeATM2.get(0).locationGID);
                            tvCuaHangNhanHang.setText(storeATM2.get(0).locationName);
                            tvDiaChiNhanHang.setText(storeATM2.get(0).addressLine);
                            edtTotalCarton.setText(storeATM2.get(0).totalCarton);
                            strTotalCarton = storeATM2.get(0).totalCarton;
                            strCustomerNameFromAPI = storeATM2.get(0).customerCode;
                            strTotalWeight = storeATM2.get(0).totalWeight;
                            strATMShipmentID = storeATM2.get(0).atmShipmentID;
                            strATMOrderReleaseID = storeATM2.get(0).atmOrderReleaseID;
                            strLocationName = storeATM2.get(0).locationName;
                            strLocationGID = storeATM2.get(0).locationGID;
                            strAddressLine = storeATM2.get(0).addressLine;
                            strPackagegItemId = storeATM2.get(0).packagedItem;
                            if (storeATM2.get(0).arrivedByDeliverer) {
                                toggleBiker.setChecked(true);
                                toggleBiker.setEnabled(false);
                            } else {
                                toggleBiker.setChecked(false);
                                toggleBiker.setEnabled(true);
                            }
                            if (storeATM2.get(0).isCompleted) {
                                relaAlert2.setVisibility(View.GONE);
                                relaAlert1.setVisibility(View.VISIBLE);
                                tvThongBaoBiker.setText("Cửa hàng hiện tại đã được giao bởi " + storeATM2.get(0).fullName + ". Vui lòng kiểm tra lại mã cửa hàng!");
                            } else {
                                relaAlert2.setVisibility(View.VISIBLE);
                                relaAlert1.setVisibility(View.GONE);
                                if (strCustomerNameFromAPI.equals("MASAN")) {
                                    edtTotalCarton.setVisibility(View.VISIBLE);
                                } else {
                                    edtTotalCarton.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            relaAlert2.setVisibility(View.GONE);
                            relaAlert3.setVisibility(View.VISIBLE);
                            rvStoreBikerVinMas.setVisibility(View.VISIBLE);

                            bikerStoreVinMasAdapter = new BikerStoreVinMasAdapter(storeATM2, getContext(), new RecyclerViewItemClick<StoreATM>() {
                                @Override
                                public void onClick(StoreATM item, int position, int number) {
                                    switch (number) {
                                        case 0:
                                            relaAlert3.setVisibility(View.GONE);
                                            relaAlert2.setVisibility(View.GONE);
                                            tvPreStoreCode.setText(strStoreCode);
                                            tvMaCuaHangNhanHang.setText(item.locationGID);
                                            tvCuaHangNhanHang.setText(item.locationName);
                                            tvDiaChiNhanHang.setText(item.addressLine);
                                            edtTotalCarton.setText(item.totalCarton);
                                            strCustomerNameFromAPI = item.customerCode;
                                            strTotalWeight = item.totalWeight;
                                            strATMShipmentID = item.atmShipmentID;
                                            strATMOrderReleaseID = item.atmOrderReleaseID;
                                            strLocationName = item.locationName;
                                            strLocationGID = item.locationGID;
                                            strAddressLine = item.addressLine;
                                            strPackagegItemId = item.packagedItem;
                                            if (item.arrivedByDeliverer) {
                                                toggleBiker.setChecked(true);
                                                toggleBiker.setEnabled(false);
                                            } else {
                                                toggleBiker.setChecked(false);
                                                toggleBiker.setEnabled(true);
                                            }
                                            if (item.isCompleted) {
                                                relaAlert2.setVisibility(View.GONE);
                                                relaAlert1.setVisibility(View.VISIBLE);
                                                tvThongBaoBiker.setText("Cửa hàng hiện tại đã được giao bởi " + storeATM2.get(position).fullName + ". Vui lòng kiểm tra lại mã cửa hàng!");
                                            } else {
                                                relaAlert2.setVisibility(View.VISIBLE);
                                                relaAlert1.setVisibility(View.GONE);
                                                if (strCustomerNameFromAPI.equals("MASAN")) {
                                                    edtTotalCarton.setVisibility(View.VISIBLE);
                                                } else {
                                                    edtTotalCarton.setVisibility(View.GONE);
                                                }
                                            }
                                            break;
                                    }
                                }

                                @Override
                                public void onLongClick(StoreATM item, int position, int number) {

                                }
                            });
                            rvStoreBikerVinMas.setAdapter(bikerStoreVinMasAdapter);

                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Không tìm thấy thông tin cửa hàng!");
                        Dialog dialog = builder.create();
                        dialog.show();
                        relaAlert2.setVisibility(View.GONE);
                        relaAlert1.setVisibility(View.VISIBLE);
                        tvMaCuaHangNhanHang.setText("");
                        tvCuaHangNhanHang.setText("");
                        tvDiaChiNhanHang.setText("");
                        tvPreStoreCode.setText("");
                        tvThongBaoBiker.setText(R.string.thongbao5);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StoreATM>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });

    }

    private void getBikerCustomer(String strToken) {
        MyRetrofit.initRequest(getContext()).GetBikerCustomer(strToken).enqueue(new Callback<List<BikerCustomer>>() {
            @Override
            public void onResponse(Call<List<BikerCustomer>> call, Response<List<BikerCustomer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new BikerCustomerAdapter(response.body(), getContext());
                    spinBikerCustomer.setAdapter(adapter);
                    spinBikerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SpinCusPrefCustomer.SaveIntCustomer(getContext(), position);
                            strCustomerCode = response.body().get(position).customerCode;
                            strCustomerName = response.body().get(position).customerName;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    if (SpinCusPrefCustomer.LoadIntCustomer(getContext()) >= response.body().size()) {
                        spinBikerCustomer.setSelection(0);
                    } else {
                        spinBikerCustomer.setSelection(SpinCusPrefCustomer.LoadIntCustomer(getContext()));
                    }

                } else {
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<BikerCustomer>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });
    }

    private void getTrip() {


//        MyRetrofit.initRequest(getContext()).getListTrips("Bearer " + strToken, strMaNhanVien, strDate).enqueue(new Callback<List<Store2>>() {
//            @Override
//            public void onResponse(Call<List<Store2>> call, Response<List<Store2>> response) {
//                if (response.isSuccessful() && response != null) {
//                    list = response.body();
//                    if (list.size() == 0) {
//                        swipeRefreshLayout.setVisibility(View.GONE);
////                        tvThongBaoBiker.setVisibility(View.VISIBLE);
////                        lottie_biker.setVisibility(View.VISIBLE);
//                        rlLottie.setVisibility(View.VISIBLE);
//                    } else {
//                        adapter = new TripAdapter(new RecyclerViewItemListener2() {
//                            @Override
//                            public void onClick(int position, String strStoreCode, String strStoreName, String strStoreAddress, String strKhachHang, String atM_SHIPMENT_ID, String orderrelease_id, String packaged_Item_XID, int totalCarton, String totalWeight, int key) {
//                                switch (key) {
//                                    case 1:
//                                        strStoreCodeMain = strStoreCode;
//                                        strStoreNameMain = strStoreName;
//                                        strStoreAddressMain = strStoreAddress;
//                                        strKhachHangMain = strKhachHang;
//                                        stratM_SHIPMENT_ID = atM_SHIPMENT_ID;
//                                        strorderrelease_id = orderrelease_id;
//                                        strpackaged_Item_XID = packaged_Item_XID;
//                                        TotalCarton = totalCarton;
//                                        strTotalWeight = totalWeight;
//                                        getTotalBill(strStoreCode, strDate, strToken);
//                                        break;
//
//                                    case 2:
//                                        Intent i2 = new Intent(getContext(), SuCoActivity.class);
//                                        i2.putExtra("storecode", strStoreCode);
//                                        i2.putExtra("storename", strStoreName);
//                                        i2.putExtra("storeaddress", strStoreAddress);
//                                        i2.putExtra("khachhang", strKhachHang);
//                                        startActivity(i2);
//                                        break;
//
//
//                                    case 3:
//                                        if (checkPermissions()) {
//                                            final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//                                            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                                                Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
//                                                init();
//                                            } else {
//                                                startLocationUpdates();
//                                                if (mCurrentLocation == null) {
//                                                    Snackbar.make(view, "Chưa định vị đc vui lòng thử lại", Snackbar.LENGTH_LONG).show();
////                                                    getStopShipmentCurrent(atM_shipment_id);
//                                                } else {
//                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                                    builder.setTitle("Thông báo!")
//                                                            .setMessage("Bạn có chắc là đủ hàng? Nếu đủ hãy bấm đồng ý!")
//                                                            .setCancelable(false)
//                                                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                                    dialogInterface.dismiss();
//                                                                }
//                                                            }).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                                            progressDialog = Utilities.getProgressDialog(getContext(), "Vui lòng chờ...");
//                                                            progressDialog.show();
//
//                                                            if (!WifiHelper.isConnected(getContext())) {
//                                                                Utilities.dismissDialog(progressDialog);
//                                                                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                                                                return;
//                                                            }
//
//                                                            MyRetrofit.initRequest(getContext()).updateStateStop("Bearer " + strToken, strStoreCode, strDate, strKhachHang, atM_SHIPMENT_ID, 0, true, 0, 0, 0, totalCarton, totalWeight, String.valueOf(mCurrentLocation.getLatitude()), String.valueOf(mCurrentLocation.getLongitude()), orderrelease_id).enqueue(new Callback<StateStop>() {
//                                                                @Override
//                                                                public void onResponse(Call<StateStop> call, Response<StateStop> response) {
//                                                                    if (response.isSuccessful() && response != null) {
//                                                                        Utilities.dismissDialog(progressDialog);
//                                                                        new GenericDialog.Builder(getContext())
//                                                                                .setIcon(R.drawable.completed)
//                                                                                .setTitle("Hoàn Thành!").setTitleAppearance(R.color.colorPrimaryDark, 16)
//                                                                                .setMessage("Chúc mừng bạn đã hoàn thành xong điểm " + strStoreName + "!")
//                                                                                .setCancelable(true)
//                                                                                .generate();
//
//                                                                        new Handler().postDelayed(new Runnable() {
//                                                                            @Override
//                                                                            public void run() {
//                                                                                getTrip();
//                                                                            }
//                                                                        }, 2000);
//                                                                    } else {
//                                                                        Utilities.dismissDialog(progressDialog);
//                                                                        Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
//                                                                    }
//
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<StateStop> call, Throwable t) {
//                                                                    Utilities.dismissDialog(progressDialog);
//                                                                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                                                                    Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
//                                                                }
//                                                            });
//                                                        }
//                                                    });
//
//                                                    Dialog dialog = builder.create();
//                                                    dialog.show();
//                                                }
//                                            }
//                                        }
//
//                                        break;
//                                }
//                            }
//
//                            @Override
//                            public void onLongClick(int position) {
//
//                            }
//                        }, list);
//                        adapter.replace(list);
//                        try {
//                        } catch (Exception e) {
//                            Log.e("err", e.toString());
//                        }
//                    }
//                } else {
//                    if (LoginPrefer.getObject(getContext()).MaNhanVien == null || LoginPrefer.getObject(getContext()).MaNhanVien.length() == 0) {
//                        Toast.makeText(getContext(), getString(R.string.thieumanhanvien), Toast.LENGTH_SHORT).show();
////                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Store2>> call, Throwable t) {
//                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//            }
//        });
//                }

    }

//    public int getTotalBill(String strCode, String strDate, String strToken) {
//        if (!WifiHelper.isConnected(getContext())) {
//            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//            Utilities.dismissDialog(progressDialog);
//        }
//        if (strKhachHangMain.equals("VCMFRESH")) {
//            MyRetrofit.initRequest(getContext()).getTotalPGH(strCode, strDate, strKhachHangMain, "1", "Bearer " + strToken).enqueue(new Callback<TotalItems>() {
//                @Override
//                public void onResponse(Call<TotalItems> call, Response<TotalItems> response) {
//                    if (response.isSuccessful() && response != null) {
//                        strTotal = response.body().totalItems;
//                        Intent i = new Intent(getContext(), NhanHangActivity.class);
//                        i.putExtra("storecode", strStoreCodeMain);
//                        i.putExtra("storename", strStoreNameMain);
//                        i.putExtra("storeaddress", strStoreAddressMain);
//                        i.putExtra("storetotal", strTotal);
//                        i.putExtra("khachhang", strKhachHangMain);
//                        i.putExtra("atmshipmentid", stratM_SHIPMENT_ID);
//                        i.putExtra("orderreleasexid", strorderrelease_id);
//                        i.putExtra("packageditemxid", strpackaged_Item_XID);
//                        startActivity(i);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TotalItems> call, Throwable t) {
//                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                }
//            });
//
//        } else if (strKhachHangMain.equals("BHX")) {
//            MyRetrofit.initRequest(getContext()).getTotalPGH2(strCode, strDate, strKhachHangMain, "1", "Bearer " + strToken).enqueue(new Callback<TotalItems>() {
//                @Override
//                public void onResponse(Call<TotalItems> call, Response<TotalItems> response) {
//                    if (response.isSuccessful() && response != null) {
//                        strTotal = response.body().totalItems;
//                        Intent i = new Intent(getContext(), NhanHangActivity.class);
//                        i.putExtra("storecode", strStoreCodeMain);
//                        i.putExtra("storename", strStoreNameMain);
//                        i.putExtra("storeaddress", strStoreAddressMain);
//                        i.putExtra("storetotal", strTotal);
//                        i.putExtra("khachhang", strKhachHangMain);
//                        i.putExtra("atmshipmentid", stratM_SHIPMENT_ID);
//                        i.putExtra("orderreleasexid", strorderrelease_id);
//                        i.putExtra("packageditemxid", strpackaged_Item_XID);
//                        startActivity(i);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TotalItems> call, Throwable t) {
//                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                }
//            });
//        } else if (strKhachHangMain.equals("MASAN")) {
//            Intent i = new Intent(getContext(), ChupHinhGiaoHangActivity.class);
//            i.putExtra("storecode", strStoreCodeMain);
//            i.putExtra("storename", strStoreNameMain);
//            i.putExtra("storeaddress", strStoreAddressMain);
//            i.putExtra("date", strDate);
//            i.putExtra("carton", TotalCarton);
//            i.putExtra("weight", strTotalWeight);
//            i.putExtra("khachhang", strKhachHangMain);
//            i.putExtra("shipment", stratM_SHIPMENT_ID);
//            i.putExtra("orderreleasexid", strorderrelease_id);
//            i.putExtra("packageditemxid", strpackaged_Item_XID);
//            startActivity(i);
//        }
//
//
//        return strTotal;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

//    private void init() {
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
//        mSettingsClient = LocationServices.getSettingsClient(getActivity());
//
//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                // location is received
//                mCurrentLocation = locationResult.getLastLocation();
//            }
//        };
//
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        mLocationSettingsRequest = builder.build();
//    }


//    private void startLocationUpdates() {
//        mSettingsClient
//                .checkLocationSettings(mLocationSettingsRequest)
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
//                    @SuppressLint("MissingPermission")
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                                mLocationCallback, Looper.myLooper());
//                    }
//                })
//                .addOnFailureListener(getActivity(), new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        int statusCode = ((ApiException) e).getStatusCode();
//                        switch (statusCode) {
//                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
////                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
////                                        "location settings ");
//                                try {
//                                    // Show the dialog by calling startResolutionForResult(), and check the
//                                    // result in onActivityResult().
//                                    ResolvableApiException rae = (ResolvableApiException) e;
//                                    rae.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
//                                } catch (IntentSender.SendIntentException sie) {
////                                    Log.i(TAG, "PendingIntent unable to execute request.");
//                                }
//                                break;
//                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                String errorMessage = "Location settings are inadequate, and cannot be " +
//                                        "fixed here. Fix in Settings.";
//                                Log.e(TAG, errorMessage);
//
//                                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        switch (requestCode) {
//            // Check for the integer request code originally supplied to startResolutionForResult().
//            case REQUEST_CHECK_SETTINGS:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        Log.e(TAG, "User agreed to make required location settings changes.");
//                        // Nothing to do. startLocationupdates() gets called in onResume again.
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Log.e(TAG, "User chose not to make required location settings changes.");
//                        break;
//                }
//                break;
//        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Bạn đã hủy", Toast.LENGTH_SHORT).show();
            } else {
                if (edtStoreID != null) {
                    edtStoreID.setText("");
                    edtStoreID.requestFocus();
                }
                if (tvPreStoreCode != null) {
                    tvPreStoreCode.setText(result.getContents());
                }
                loadInfoStoreATM(result.getContents().trim());

//                edtStoreID.setText(result.getContents());
//                edtStoreID.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        String strPreStoreCode = "";
//                        String contents = s.toString();
//                        if (contents.contains("\n")) {
//                            strPreStoreCode = contents.replaceAll("\n", "").trim();
//                        }
//
//                    }
//                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


}
