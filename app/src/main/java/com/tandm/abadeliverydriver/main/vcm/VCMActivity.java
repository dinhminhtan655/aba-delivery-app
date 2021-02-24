package com.tandm.abadeliverydriver.main.vcm;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.MyRetrofit2;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassListVCM;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment2;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VCMActivity extends AppCompatActivity {
    View view;
    private static final String TAG = "VCMActivity";
    @BindView(R.id.tvThoiGianNhanHangVCM)
    TextView tvThoiGianNhanHangVCM;
    @BindView(R.id.tvMaCuaHangNhanHangVCM)
    TextView tvMaCuaHangNhanHangVCM;
    @BindView(R.id.tvCuaHangNhanHangVCM)
    TextView tvCuaHangNhanHangVCM;
    @BindView(R.id.tvDiaChiNhanHangVCM)
    TextView tvDiaChiNhanHangVCM;
    @BindView(R.id.tbVCM)
    Toolbar tbVCM;
    @BindView(R.id.tvSoLuongCBVCM)
    TextView tvSoLuongCBVCM;
    @BindView(R.id.tvSoLuongTotalVCM)
    TextView tvSoLuongTotalVCM;
    @BindView(R.id.rvNhanHangVCM)
    RecyclerView rvNhanHangVCM;
    @BindView(R.id.btnCheckAllVCM)
    FloatingActionButton btnCheckAllVCM;
    @BindView(R.id.btnSendVCM)
    FloatingActionButton btnSendVCM;

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

    String strMaCuaHangVCM, strCuaHangVCM, strDate, strDiaChiVCM, strKhachHangVCM, strToken, strROUTENO, strThoiGian, strShipment, strOrdeR_RELEASE_XID, strPackaged_Item_XID,strLat = "0.0",strLng = "0.0",strPhoneNumber = "";
    int i = 0;
    ProgressDialog progressDialog;

    VCMAdapter adapter;
    List<Khay> list;
    List<Khay> listFinalVCM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcm);
        ButterKnife.bind(this);
        setSupportActionBar(tbVCM);
        Utilities.showBackIcon(getSupportActionBar());
        list = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        strToken = "Bearer " + LoginPrefer.getObject(VCMActivity.this).access_token;
        strThoiGian = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        Log.e("dateVCM", strThoiGian);
        if (b == null) {
            tvThoiGianNhanHangVCM.setText("");
            tvMaCuaHangNhanHangVCM.setText("");
            tvCuaHangNhanHangVCM.setText("");
            tvDiaChiNhanHangVCM.setText("");
            strROUTENO = "";
            strShipment = "";
        } else {
            strMaCuaHangVCM = b.getString("storecode");
            strCuaHangVCM = b.getString("storename");
            strDiaChiVCM = b.getString("storeaddress");
            strKhachHangVCM = b.getString("khachhang");
            strROUTENO = b.getString("routeno");
            strShipment = b.getString("atmshipmentid");
            strDate = b.getString("date");
            strOrdeR_RELEASE_XID = b.getString("orderreleasexid");
            strPackaged_Item_XID = b.getString("packageditemxid");
            tvThoiGianNhanHangVCM.setText(Utilities.formatDate_ddMMyyyy(strDate));
            tvMaCuaHangNhanHangVCM.setText(strMaCuaHangVCM);
            tvCuaHangNhanHangVCM.setText(strCuaHangVCM);
            tvDiaChiNhanHangVCM.setText(strDiaChiVCM);
            strPhoneNumber = b.getString("phonenumber");
        }
//        init();

        getKhay();

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
                            Utilities.openSettings(VCMActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void getKhay() {
        progressDialog = Utilities.getProgressDialog(VCMActivity.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(VCMActivity.this)) {
            RetrofitError.errorAction(VCMActivity.this, new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(VCMActivity.this).getKhay(strToken).enqueue(new Callback<List<Khay>>() {
            @Override
            public void onResponse(Call<List<Khay>> call, Response<List<Khay>> response) {
                if (response.isSuccessful() && response != null) {
                    Utilities.dismissDialog(progressDialog);
                    int a = 0;
                    for (Khay k : response.body()) {

                        response.body().get(a).setEdtTextSLKhayGiao(k.getEdtTextSLKhayGiao());
                        response.body().get(a).setEdtTextSLKhayLayVe(k.getEdtTextSLKhayLayVe());
                        list = response.body();
                        a++;
                        tvSoLuongTotalVCM.setText(String.valueOf(a));
                    }
                    adapter = new VCMAdapter(response.body(), new PassCheckBox() {
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

                    rvNhanHangVCM.setAdapter(adapter);

                    adapter.setList(VCMActivity.this, response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Khay>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });

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


    @OnClick(R.id.btnSendVCM)
    public void sendVCM(View view) {
        if (checkPermissions()) {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
//                init();
            } else {
//                startLocationUpdates();
//                if (mCurrentLocation == null) {
//                    Snackbar.make(view, "Chưa định vị đc vui lòng thử lại", Snackbar.LENGTH_LONG).show();
//                } else {
                    if (i < list.size()) {
                        AlertDialog.Builder b = new AlertDialog.Builder(VCMActivity.this);
                        b.setTitle("Thông báo").setIcon(R.drawable.warning).setMessage("Vui lòng xác nhận đầy đủ đơn hàng!").show();
                    } else {
                        listFinalVCM = new ArrayList<>();
                        VCMAdapter vcmAdapter = new VCMAdapter(new PassListVCM() {
                            @Override
                            public void passList(List<Khay> list) {
                                listFinalVCM = list;
                            }
                        });
                        vcmAdapter.SendVCM();
                        AlertDialog.Builder b = new AlertDialog.Builder(VCMActivity.this);
                        b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bấm OK để gửi thông tin")
                                .setCancelable(false)
                                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        List<ABA_TrayBookingHistory> item2List = new ArrayList<>();
                                        int i = 0;
                                        for (Khay k : list) {
                                                item2List.add(new ABA_TrayBookingHistory(strShipment,
                                                        strMaCuaHangVCM,
                                                        strOrdeR_RELEASE_XID,
                                                        list.get(i).getEdtTextSLKhayGiao(),
                                                        list.get(i).getEdtTextSLKhayLayVe(),
                                                        list.get(i).khay,
                                                        "1",
                                                        strLat,
                                                        strLng,
                                                        strKhachHangVCM,
                                                        strPackaged_Item_XID));
                                            i++;
                                        }

                                        progressDialog = Utilities.getProgressDialog(VCMActivity.this, "Đang gửi..");
                                        progressDialog.show();


                                        MyRetrofit.initRequest(VCMActivity.this).insertBill(strToken, item2List).enqueue(new Callback<ItemBill>() {
                                            @Override
                                            public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                                if (response.isSuccessful() && response != null) {
                                                    MyRetrofit.initRequest(VCMActivity.this).UpdateStateTripDriver(strToken, strMaCuaHangVCM, strDate, strKhachHangVCM, LoginPrefer.getObject(VCMActivity.this).userName,
                                                            strLat,
                                                            strLng,
                                                            "0", "0","0","0", strShipment, strOrdeR_RELEASE_XID, LoginPrefer.getObject(VCMActivity.this).userName).enqueue(new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body() == 1) {
                                                                    sendStarZalo(strPhoneNumber);
                                                                    Snackbar.make(view, "Đã gửi!", Snackbar.LENGTH_LONG).show();
                                                                    Intent intent = new Intent(VCMActivity.this, MainActivity.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (response.body().equals("0")) {
                                                                    Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Integer> call, Throwable t) {
                                                            RetrofitError.errorAction(VCMActivity.this, new NoInternet(), TAG, view);
                                                        }
                                                    });
                                                } else if (response.code() == 500) {
                                                    MyRetrofit.initRequest(VCMActivity.this).UpdateStateTripDriver(strToken, strMaCuaHangVCM, strDate, strKhachHangVCM, LoginPrefer.getObject(VCMActivity.this).userName,
                                                            strLat,
                                                            strLng,
                                                            "0", "0","0","0", strShipment, strOrdeR_RELEASE_XID, LoginPrefer.getObject(VCMActivity.this).userName).enqueue(new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body() == 1) {
                                                                    Snackbar.make(view, "Đã gửi!", Snackbar.LENGTH_LONG).show();
                                                                    Intent intent = new Intent(VCMActivity.this, MainActivity.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else if (response.body().equals("0")) {
                                                                    Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Integer> call, Throwable t) {
                                                            RetrofitError.errorAction(VCMActivity.this, new NoInternet(), TAG, view);
                                                        }
                                                    });
                                                }
                                                Utilities.dismissDialog(progressDialog);
                                            }

                                            @Override
                                            public void onFailure(Call<ItemBill> call, Throwable t) {
                                                Utilities.dismissDialog(progressDialog);
                                                RetrofitError.errorAction(VCMActivity.this, new NoInternet(), TAG, view);
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
//                }
            }
        } else {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
//                            startLocationUpdates();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                Utilities.openSettings(VCMActivity.this);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
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

//    private void init() {
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mSettingsClient = LocationServices.getSettingsClient(this);
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
//                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//                    @SuppressLint("MissingPermission")
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                                mLocationCallback, Looper.myLooper());
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
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
//                                    rae.startResolutionForResult(VCMActivity.this, REQUEST_CHECK_SETTINGS);
//                                } catch (IntentSender.SendIntentException sie) {
////                                    Log.i(TAG, "PendingIntent unable to execute request.");
//                                }
//                                break;
//                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                String errorMessage = "Location settings are inadequate, and cannot be " +
//                                        "fixed here. Fix in Settings.";
//                                Log.e(TAG, errorMessage);
//
//                                Toast.makeText(VCMActivity.this, errorMessage, Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
//    }

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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToFragment event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
//            Toast.makeText(VCMActivity.this, data, Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(VCMActivity.this, data+"/aaa2", Toast.LENGTH_SHORT).show();
            strLat = String.valueOf(event.getLocation().getLatitude());
            strLng = String.valueOf(event.getLocation().getLongitude());
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void sendStarZalo(String phone) {
        TemplateDataParent2 templateDataParentRating = new TemplateDataParent2(phone, "203044",
                new TemplateData(strOrdeR_RELEASE_XID), "3");

        MyRetrofit2.initRequest2().SendZaloRating(templateDataParentRating).enqueue(new Callback<Zalo>() {
            @Override
            public void onResponse(Call<Zalo> call, Response<Zalo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().message.equals("Success")) {
                        Toast.makeText(VCMActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zalo> call, Throwable t) {
                Toast.makeText(VCMActivity.this, "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
