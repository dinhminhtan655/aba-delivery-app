package com.tandm.abadeliverydriver.main.nhanhang.bhxice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
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
import com.tandm.abadeliverydriver.main.nhanhang.model.Item;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBooking;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment2;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BHXIceActivity extends AppCompatActivity {

    public static final String TAG = "BHXIceActivity";


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
    private ArrayList<ItemChild> itemChildren = new ArrayList<>();
    @BindView(R.id.tbBHXICE)
    Toolbar tbBHXICE;
    @BindView(R.id.tvToolbarBHXICE)
    TextView tvToolbarBHXICE;
    @BindView(R.id.tvThoiGianNhanHangBHXICE)
    TextView tvThoiGianNhanHangBHXICE;
    @BindView(R.id.tvMaCuaHangNhanHangBHXICE)
    TextView tvMaCuaHangNhanHangBHXICE;
    @BindView(R.id.tvCuaHangNhanHangBHXICE)
    TextView tvCuaHangNhanHangBHXICE;
    @BindView(R.id.tvDiaChiNhanHangBHXICE)
    TextView tvDiaChiNhanHangBHXICE;
    @BindView(R.id.edtIceTotal)
    TextInputEditText edtIceTotal;
    @BindView(R.id.edtIceGiao)
    TextInputEditText edtIceGiao;
    View view;

    String strStoreCode, strToken, strDate, strDate2, strName, strCustomerCode, strShipment = "", strOrdeR_RELEASE_XID = "", strPackaged_Item_XID = "", ID = "",strLat = "0.0",strLng = "0.0", strPhoneNumber = "";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhxice);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        setSupportActionBar(tbBHXICE);
        Utilities.showBackIcon(getSupportActionBar());
        strToken = LoginPrefer.getObject(this).access_token;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        strDate2 = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        Bundle b = getIntent().getExtras();
        if (b == null) {
            tvThoiGianNhanHangBHXICE.setText("");
            tvMaCuaHangNhanHangBHXICE.setText("");
            tvCuaHangNhanHangBHXICE.setText("");
            tvDiaChiNhanHangBHXICE.setText("");
        } else {
            strStoreCode = b.getString("storecode");
            strName = b.getString("storeaddress");
            strCustomerCode = b.getString("khachhang");
            strShipment = b.getString("atmshipmentid");
            strOrdeR_RELEASE_XID = b.getString("orderreleasexid");
            strPackaged_Item_XID = b.getString("packageditemxid");
            strDate = b.getString("date");
            tvThoiGianNhanHangBHXICE.setText(Utilities.formatDate_ddMMyyyy(strDate));
            tvMaCuaHangNhanHangBHXICE.setText(strStoreCode);
            tvCuaHangNhanHangBHXICE.setText(b.getString("storename"));
            tvDiaChiNhanHangBHXICE.setText(strName);
            strPhoneNumber = b.getString("phonenumber");
        }
//        init();

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
                            Utilities.openSettings(BHXIceActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


        MyRetrofit.initRequest(BHXIceActivity.this).getItemsPGHICE(strStoreCode, Utilities.formatDate_ddMMyyyy(strDate), strCustomerCode, strOrdeR_RELEASE_XID, "Bearer " + strToken).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response != null) {
                    Item item = response.body();
                    if (item != null && item.getItems() != null) {
                        itemChildren = (ArrayList<ItemChild>) item.getItems();
                        if (itemChildren.size() > 0){
                            edtIceTotal.setText(String.valueOf(itemChildren.get(0).soBich));
                            edtIceGiao.setText(String.valueOf(itemChildren.get(0).soBich));

                            ID = itemChildren.get(0).xdocK_Doc_Entry;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(BHXIceActivity.this, new NoInternet(), TAG, view);
            }
        });
    }


    @OnClick(R.id.btnSendBHXICE)
    public void sendBHXICE(View view) {
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


                    AlertDialog.Builder b = new AlertDialog.Builder(BHXIceActivity.this);
                    b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bấm OK để gửi thông tin")
                            .setCancelable(false)
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i2) {
                                    List<ItemBooking> item2List = new ArrayList<>();
                                    if (edtIceGiao.getText().toString().equals("")) {

                                    } else {
//                                        item2List.add(new Item2("",
//                                                "",
//                                                "",
//                                                strStoreCode,
//                                                strDate,
//                                                "ice",
//                                                "Đá",
//                                                "0",
//                                                "",
//                                                edtIceGiao.getText().toString(),
//                                                "",
//                                                "",
//                                                "",
//                                                "",
//                                                strCustomerCode,
//                                                strLat,
//                                                strLng,
//                                                "",
//                                                "",
//                                                strShipment,
//                                                strOrdeR_RELEASE_XID,
//                                                strPackaged_Item_XID,
//                                                "0",
//                                                "0",
//                                                "0"));
                                        item2List.add(new ItemBooking(ID,
                                                Integer.parseInt(edtIceGiao.getText().toString()),
                                                0.0,
                                                "",
                                                LoginPrefer.getObject(BHXIceActivity.this).userName,
                                                strLat,
                                                strLng,
                                                "",
                                                "",
                                                0,
                                                0,
                                                0,
                                                strShipment));

                                        progressDialog = Utilities.getProgressDialog(BHXIceActivity.this, "Đang gửi..");
                                        progressDialog.show();

                                        MyRetrofit.initRequest(BHXIceActivity.this).updateBooking("Bearer " + strToken, item2List).enqueue(new Callback<ItemBill>() {
                                            @Override
                                            public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                                if (response.isSuccessful() && response != null) {

                                                    MyRetrofit.initRequest(BHXIceActivity.this).UpdateStateTripDriver("Bearer " + strToken, strStoreCode, strDate2, strCustomerCode, LoginPrefer.getObject(BHXIceActivity.this).userName,
                                                            strLat,
                                                            strLng,
                                                            "0", "0","0","0", strShipment, strOrdeR_RELEASE_XID, LoginPrefer.getObject(BHXIceActivity.this).userName).enqueue(new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body() == 1) {
                                                                    Snackbar.make(view, "Đã gửi!", Snackbar.LENGTH_LONG).show();
                                                                    TemplateDataParent2 templateDataParentRating = new TemplateDataParent2("84906700164", "202474",new TemplateData("","","","",""), "3");


//                                                                    MyRetrofit2.initRequest2().SendZaloRating(templateDataParentRating).enqueue(new Callback<Zalo>() {
//                                                                        @Override
//                                                                        public void onResponse(Call<Zalo> call, Response<Zalo> response) {
//                                                                            if (response.isSuccessful() && response.body() != null) {
//                                                                                if (response.body().message.equals("Success")) {
//                                                                                    Toast.makeText(BHXIceActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
//                                                                                }
//                                                                            }
//                                                                        }
//
//                                                                        @Override
//                                                                        public void onFailure(Call<Zalo> call, Throwable t) {
//                                                                            Toast.makeText(BHXIceActivity.this, "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
//                                                                        }
//                                                                    });

                                                                    Intent intent = new Intent(BHXIceActivity.this, MainActivity.class);
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
                                                            RetrofitError.errorAction(BHXIceActivity.this, new NoInternet(), TAG, view);
                                                        }
                                                    });

                                                } else if (response.code() == 500) {
                                                    MyRetrofit.initRequest(BHXIceActivity.this).UpdateStateTripDriver("Bearer " + strToken, strStoreCode, strDate2, strCustomerCode, LoginPrefer.getObject(BHXIceActivity.this).userName,
                                                            strLat,
                                                            strLng,
                                                            "0", "0","0","0", strShipment, strOrdeR_RELEASE_XID, LoginPrefer.getObject(BHXIceActivity.this).userName).enqueue(new Callback<Integer>() {
                                                        @Override
                                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body() == 1) {
                                                                    Snackbar.make(view, "Đã gửi!", Snackbar.LENGTH_LONG).show();
                                                                    sendStarZalo(strPhoneNumber);
                                                                    Intent intent = new Intent(BHXIceActivity.this, MainActivity.class);
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
                                                            RetrofitError.errorAction(BHXIceActivity.this, new NoInternet(), TAG, view);
                                                        }
                                                    });
                                                }
                                                Utilities.dismissDialog(progressDialog);
                                            }

                                            @Override
                                            public void onFailure(Call<ItemBill> call, Throwable t) {
                                                Utilities.dismissDialog(progressDialog);
                                                RetrofitError.errorAction(BHXIceActivity.this, new NoInternet(), TAG, view);
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


//                }
            }

        } else {
            Dexter.withActivity(BHXIceActivity.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
//                            startLocationUpdates();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                Utilities.openSettings(BHXIceActivity.this);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
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

//
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
//
    private void startLocationUpdates() {
        mSettingsClient = LocationServices.getSettingsClient(this);
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
//                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(BHXIceActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
//                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(BHXIceActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

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
//            Toast.makeText(BHXIceActivity.this, data, Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(BHXIceActivity.this, data+"/aaa2", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BHXIceActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zalo> call, Throwable t) {
                Toast.makeText(BHXIceActivity.this, "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
