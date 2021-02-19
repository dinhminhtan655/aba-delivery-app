package com.tandm.abadeliverydriver.main.home.fragmenthomedriver;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.MyRetrofit2;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.giaohang.ChupHinhGiaoHangActivity;
import com.tandm.abadeliverydriver.main.giaohang.model.StateStop;
import com.tandm.abadeliverydriver.main.helper.GeofenceHelper;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter.HomeDriverAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter.PickAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.GeofencingBoss;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.LocationGeofencing;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ResultAA;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.Shipment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreLocation;
import com.tandm.abadeliverydriver.main.login.LoginActivity;
import com.tandm.abadeliverydriver.main.nhanhang.NhanHangActivity;
import com.tandm.abadeliverydriver.main.nhanhang.bhxice.BHXIceActivity;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.nhanhang.model.TotalItems;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemListener3;
import com.tandm.abadeliverydriver.main.roomdb.GeofenceAll;
import com.tandm.abadeliverydriver.main.roomdb.GeofenceAll2;
import com.tandm.abadeliverydriver.main.roomdb.GeofenceAllViewModel;
import com.tandm.abadeliverydriver.main.service.LocationBackgroundService;
import com.tandm.abadeliverydriver.main.service.MyLocationService;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment2;
import com.tandm.abadeliverydriver.main.suco.SuCoActivity;
import com.tandm.abadeliverydriver.main.utilities.Customer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;
import com.tandm.abadeliverydriver.main.vcm.VCMActivity;
import com.tandm.abadeliverydriver.main.zalo.TemplateData;
import com.tandm.abadeliverydriver.main.zalo.TemplateData520;
import com.tandm.abadeliverydriver.main.zalo.TemplateDataParent;
import com.tandm.abadeliverydriver.main.zalo.TemplateDataParent2;
import com.tandm.abadeliverydriver.main.zalo.Zalo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dev.jai.genericdialog2.GenericDialog;
import me.rishabhkhanna.customtogglebutton.CustomToggleButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDriverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, LocationListener {
    public static final int DATEPICKER_FRAGMENT = 113; // class variable
    private float GEOFENCE_RADIUS = 150;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private static final int MY_CAMERA_REQUEST_CODE = 4;
    private final static String default_notification_channel_id = "com.tandm.abadeliverydriver.main.service.ABA";
    private static final String TAG = "HomeDriverFragment";
    View view;
    Unbinder unbinder;


    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    LocationManager locationManager;

    @BindView(R.id.btnComplete)
    Button btnComplete;
    @BindView(R.id.tvShipmentMain)
    TextView tvShipmentMain;
    @BindView(R.id.tvStartDateMain)
    TextView tvStartDateMain;
    @BindView(R.id.tvEndDateMain)
    TextView tvEndDateMain;
    @BindView(R.id.rvMainDriver)
    RecyclerView rvMainDriver;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.toggleKhoDriver)
    CustomToggleButton toggleKhoDriver;
    @BindView(R.id.toggleKhoDriverDen)
    CustomToggleButton toggleKhoDriverDen;
    @BindView(R.id.toggleKhoDriverPickUp)
    CustomToggleButton toggleKhoDriverPickUp;
    @BindView(R.id.toggleKhoDriverPickUpDone)
    CustomToggleButton toggleKhoDriverPickUpDone;
    @BindView(R.id.btnChuyen1)
    Button btnChuyen1;
    @BindView(R.id.btnChuyen2)
    Button btnChuyen2;
    @BindView(R.id.btnChuyen3)
    Button btnChuyen3;
    @BindView(R.id.lottie)
    LottieAnimationView lottie;
    @BindView(R.id.tvThongBao)
    TextView tvThongBao;
    @BindView(R.id.lnLottie)
    LinearLayout lnLottie;
    @BindView(R.id.coorHomeDriver)
    CoordinatorLayout coorHomeDriver;
    @BindView(R.id.imgArrow1)
    ImageView imgArrow1;
    @BindView(R.id.imgArrow2)
    ImageView imgArrow2;
    @BindView(R.id.imgArrow3)
    ImageView imgArrow3;


    boolean stateChuyen = false;

    DialogADCADShipmentFragment dialogADCADShipmentFragment;

    //-------------------------
    @BindView(R.id.searchDriver)
    SearchView searchDriver;


    HomeDriverAdapter adapter;
    PickAdapter pickAdapter;
    ProgressDialog progressDialog;


    List<Shipment> listShipment;

    private List<GeofencingBoss> listP;
    private List<GeofencingBoss> listD;
    private List<GeofencingBoss> totalListPD;

    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    private GeofenceAllViewModel geofenceAllViewModel;

    private static List<StoreDriver> stringList;

    private List<GeofenceAll2> geofenceAllList;

    String strToken, strDriverID, strDate, strStoreCodeMain, strStoreNameMain, strStoreAddressMain, strKhachHangMain, strShipment, strCustomerCode, strROUTENO, strOrdeR_RELEASE_XID, strPackaged_Item_XID, strUsername, strStartTime, strStartTime2;
    String strOldPass, strNewPass, strConfirmNewPass, strLat = "0.0", strLng = "0.0", strPhoneNumber;
    int strTotal, soThung, iState;
    boolean bCheckMobileHub, bAnHien = true, bDenKho, bPickUp, bPickUpDone, bRoiKho;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    LocationBackgroundService mService = null;
    boolean mBound = false;

//    Geocoder geocoder;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationBackgroundService.LocalBinder binder = (LocationBackgroundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            mService.requestLocationUpdates();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    public HomeDriverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_driver, container, false);
        unbinder = ButterKnife.bind(this, view);
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        strUsername = LoginPrefer.getObject(getContext()).userName;
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDriverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        stringList = new ArrayList<>();
        listShipment = new ArrayList<>();
        listP = new ArrayList<>();
        listD = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            }
        }


        geofencingClient = LocationServices.getGeofencingClient(getActivity());
        geofenceHelper = new GeofenceHelper(getActivity());
        removeGeofence();
        setColorButton(stateChuyen);


        getLocation();


        if (LoginPrefer.getObject(getContext()).PasswordHash.equals(Utilities.PASSWORD1) || LoginPrefer.getObject(getContext()).PasswordHash.equals(Utilities.PASSWORD2)) {
            String strGrandType = "password", strID = LoginPrefer.getObject(getContext()).userId, strUsername = LoginPrefer.getObject(getContext()).userName;
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_change_pass);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            TextInputEditText edtOldPassChange = dialog.findViewById(R.id.edtOldPassChange);
            TextInputEditText edtNewPassChange = dialog.findViewById(R.id.edtNewPassChange);
            TextInputEditText edtConfirmNewPassChange = dialog.findViewById(R.id.edtConfirmNewPassChange);
            Button btnReset = dialog.findViewById(R.id.btnReset);
            Button btnOkay = dialog.findViewById(R.id.btnOkay);
            Button btnDangXuat = dialog.findViewById(R.id.btnDangXuat);


            btnOkay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    strOldPass = edtOldPassChange.getText().toString();
                    strNewPass = edtNewPassChange.getText().toString();
                    strConfirmNewPass = edtConfirmNewPassChange.getText().toString();

                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                    progressDialog.show();

                    if (!WifiHelper.isConnected(getContext())) {
                        Utilities.dismissDialog(progressDialog);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                        return;
                    }

                    MyRetrofit.initRequest(getContext()).changePass(strUsername, strID, strGrandType, strOldPass, strNewPass, strConfirmNewPass, strToken).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.e("logo", response.body() + "");
                            if (response.isSuccessful() && response.body() != null) {
                                Snackbar.make(view, response.body(), Snackbar.LENGTH_LONG).show();
                                LoginPrefer.deleteObject(getActivity());
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            } else {
                                Snackbar.make(view, "Đổi mật khẩu thất bại vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                            }
                            Utilities.dismissDialog(progressDialog);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Utilities.dismissDialog(progressDialog);
                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                        }
                    });


                }
            });

            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtOldPassChange.setText("");
                    edtNewPassChange.setText("");
                    edtConfirmNewPassChange.setText("");
                }
            });

            btnDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyRetrofit.initRequest(getContext()).updateNotiTokenUser("Bearer " + LoginPrefer.getObject(getContext()).access_token, LoginPrefer.getObject(getContext()).userName, "khongco").enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Snackbar.make(view, "Vui lòng kiểm tra kết nối mạng", Snackbar.LENGTH_LONG).show();
                        }
                    });
                    LoginPrefer.deleteObject(getContext());
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);


        }


        addDepot();
        geofenceAllList = new ArrayList<>();
        geofenceAllViewModel = ViewModelProviders.of(getActivity()).get(GeofenceAllViewModel.class);
        geofenceAllViewModel.getAllGeofence().observe(getActivity(), new Observer<List<GeofenceAll>>() {
            @Override
            public void onChanged(List<GeofenceAll> geofenceAlls) {
                geofenceAllList.clear();
                if (geofenceAlls != null && geofenceAlls.size() > 0) {
                    for (GeofenceAll g : geofenceAlls) {
                        Log.d("sqlite", g.getAtmOrderrelease() + "/" + g.getAtmShipmentId() + "/" + g.getLatArrived() +
                                "/" + g.getLngArrived() + "/" + g.getTimeArrived() + "/" + g.getLatLeft() + "/" + g.getLngLeft() + "/" + g.getTimeLeft());
                        geofenceAllList.add(new GeofenceAll2(g.getAtmOrderrelease(), g.getAtmShipmentId(), g.getLatArrived(), g.getLngArrived(), g.getTimeArrived(), g.getLatLeft(), g.getLngLeft(), g.getTimeLeft()));
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
//        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Dexter.withActivity(getActivity())
                    .withPermissions(Arrays.asList(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            startLocationUpdates();

                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                updateLocation();
                            } else {
                                getContext().bindService(new Intent(getContext()
                                                , LocationBackgroundService.class)
                                        , mServiceConnection
                                        , Context.BIND_AUTO_CREATE);
                            }


                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    }).check();
        } else {
            Dexter.withActivity(getActivity())
                    .withPermissions(Arrays.asList(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            startLocationUpdates();

                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                updateLocation();
                            } else {
                                getContext().bindService(new Intent(getContext()
                                                , LocationBackgroundService.class)
                                        , mServiceConnection
                                        , Context.BIND_AUTO_CREATE);
                            }


                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    }).check();
        }


        getCurrentShipment();


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
//                getStopShipmentCurrent(strShipment);
            }
        });

//        GridLayoutManager manager  = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL,false);
//        rvMainDriver.setLayoutManager(manager);

        searchDriver.setOnQueryTextListener(this);

        return view;
    }

//    private void FabHien() {
//        fabFastDelivery.show();
//    }
//
//    private void FabAn() {
//        fabFastDelivery.hide();
//    }

    private void getCurrentShipment() {

        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            appBar.setVisibility(View.GONE);
            rvMainDriver.setVisibility(View.GONE);
//            fabHomeDriver.setVisibility(View.GONE);
//            fabFastDelivery.setVisibility(View.GONE);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(getContext()).getShipment(strToken, strDriverID, "", "", "CURRENT").enqueue(new Callback<List<Shipment>>() {
            @Override
            public void onResponse(Call<List<Shipment>> call, Response<List<Shipment>> response) {
                if (response.isSuccessful() && response != null) {
                    LoginPrefer.saveListP(response.body(), getContext());
                    if (response.body().size() > 0) {
                        try {
                            listShipment = response.body();
                            if (listShipment.size() == 0) {
                                btnChuyen1.setVisibility(View.GONE);
                                btnChuyen2.setVisibility(View.GONE);
                            }
                            if (listShipment.size() < 2) {
                                //Mở Nút Map ở đây
                                btnChuyen2.setVisibility(View.GONE);

                                if (LoginPrefer.getObject(getContext()).isBiker) {
                                    btnChuyen3.setVisibility(View.GONE);
                                } else {
                                    btnChuyen3.setVisibility(View.VISIBLE);
                                }
                            }
                            appBar.setVisibility(View.VISIBLE);
                            rvMainDriver.setVisibility(View.VISIBLE);
                            strShipment = response.body().get(0).atM_SHIPMENT_ID;
                            tvShipmentMain.setText(strShipment);
                            strStartTime = response.body().get(0).starT_TIME;
                            String[] strTime = strStartTime.split("T");
                            strStartTime2 = strTime[0];
                            tvStartDateMain.setText(response.body().get(0).getDateStartTime(response.body().get(0).starT_TIME));
                            tvEndDateMain.setText(response.body().get(0).getDateEndTime(response.body().get(0).enD_TIME));
//                            tvStartTimeMain.setText(response.body().get(0).getTimeStartTime(response.body().get(0).starT_TIME));
//                            tvEndTimeMain.setText(response.body().get(0).getTimeEndTime(response.body().get(0).enD_TIME));
                            strCustomerCode = response.body().get(0).customerCode;

                            bRoiKho = response.body().get(0).roiKho;
                            bDenKho = response.body().get(0).denKho;
                            bPickUp = response.body().get(0).startPickup;
                            bPickUpDone = response.body().get(0).donePickup;


                            if (LoginPrefer.getObject(getContext()).isBiker) {
                                toggleKhoDriverDen.setTextOff("Đến Hub");
                                toggleKhoDriverDen.setTextOn("Đến Hub");
                                toggleKhoDriverPickUp.setVisibility(View.GONE);
                                toggleKhoDriverPickUpDone.setVisibility(View.GONE);
                                toggleKhoDriver.setVisibility(View.GONE);
                                imgArrow1.setVisibility(View.GONE);
                                imgArrow2.setVisibility(View.GONE);
                                imgArrow3.setVisibility(View.GONE);
                            } else {
                                toggleKhoDriverPickUp.setVisibility(View.VISIBLE);
                                toggleKhoDriverPickUpDone.setVisibility(View.VISIBLE);
                                toggleKhoDriver.setVisibility(View.VISIBLE);
                                imgArrow1.setVisibility(View.VISIBLE);
                                imgArrow2.setVisibility(View.VISIBLE);
                                imgArrow3.setVisibility(View.VISIBLE);
                            }

                            if (bDenKho) {
                                toggleKhoDriverDen.setChecked(true);
                                toggleKhoDriverDen.setEnabled(false);
                            } else {
                                toggleKhoDriverDen.setChecked(false);
                                toggleKhoDriverDen.setEnabled(true);
                            }

                            if (bPickUp) {
                                toggleKhoDriverPickUp.setChecked(true);
                                toggleKhoDriverPickUp.setEnabled(false);
                            } else {
                                toggleKhoDriverPickUp.setChecked(false);
                                toggleKhoDriverPickUp.setEnabled(true);
                            }


                            if (bPickUpDone) {
                                toggleKhoDriverPickUpDone.setChecked(true);
                                toggleKhoDriverPickUpDone.setEnabled(false);
                            } else {
                                toggleKhoDriverPickUpDone.setChecked(false);
                                toggleKhoDriverPickUpDone.setEnabled(true);
                            }

                            if (bRoiKho) {
                                toggleKhoDriver.setChecked(true);
                                toggleKhoDriver.setEnabled(false);
                            } else {
                                toggleKhoDriver.setChecked(false);
                                toggleKhoDriver.setEnabled(true);
                            }

                            strROUTENO = response.body().get(0).routeno;

                        } catch (Exception e) {
                            Log.e("Error:", e.toString());
                        }


                        MyRetrofit.initRequest(getContext()).getPickShipment(strToken, response.body().get(0).atM_SHIPMENT_ID).enqueue(new Callback<List<StoreDriver>>() {
                            @Override
                            public void onResponse(Call<List<StoreDriver>> call, Response<List<StoreDriver>> response) {
                                if (response.isSuccessful() && response != null) {

                                    String strOrder;
                                    for (StoreDriver p : response.body()) {
                                        strOrder = p.orderreleasE_ID == null ? p.locatioN_GID : p.orderreleasE_ID;
                                        listP.add(new GeofencingBoss(p.atM_SHIPMENT_ID + "/" + strOrder, p.lat_Store, p.lon_Store));
                                    }

                                    for (GeofencingBoss g : listP) {
                                        LatLng latLng = new LatLng(Double.parseDouble(g.getLat()), Double.parseDouble(g.getLng()));
                                        handleMapLongClick(g.getId(), latLng);
                                    }

                                    pickAdapter = new PickAdapter(getContext(), response.body());
                                    vp.setAdapter(pickAdapter);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<StoreDriver>> call, Throwable t) {
                                Utilities.dismissDialog(progressDialog);
                                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                            }
                        });

                        MyRetrofit.initRequest(getContext()).GetLatLngStop(strToken, response.body().get(0).atM_SHIPMENT_ID).enqueue(new Callback<List<StoreLocation>>() {
                            @Override
                            public void onResponse(Call<List<StoreLocation>> call, Response<List<StoreLocation>> response) {
                                if (response.isSuccessful() && response != null) {
                                    for (StoreLocation p : response.body()) {
                                        if (listD.size() >= response.body().size()) {
                                            break;
                                        }
                                        listD.add(new GeofencingBoss(p.atM_Shipment_ID + "/" + p.orderrelease_id, p.lat_Store, p.lon_Store));
                                    }

                                    for (int i = 0; i < listD.size(); i++) {
                                        if ((listD.get(i).getLat() != null && !listD.get(i).getLat().equals("")) && (listD.get(i).getLng() != null && !listD.get(i).getLng().equals(""))) {
                                            LatLng latLng = new LatLng(Double.parseDouble(listD.get(i).getLat()), Double.parseDouble(listD.get(i).getLng()));
                                            handleMapLongClick(listD.get(i).getId(), latLng);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<StoreLocation>> call, Throwable t) {
                                Utilities.dismissDialog(progressDialog);
                                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                            }
                        });

                        getStopShipmentCurrent(strShipment);


                    } else {
                        appBar.setVisibility(View.GONE);
                        rvMainDriver.setVisibility(View.GONE);
                        lottie.setAnimation(R.raw.empty_box);
                        tvThongBao.setText(getString(R.string.thongbao3));
                        lnLottie.setVisibility(View.VISIBLE);
                        coorHomeDriver.setBackgroundColor(getResources().getColor(R.color.pink));
                    }
                    Utilities.dismissDialog(progressDialog);
                } else {
                    if (LoginPrefer.getObject(getContext()).MaNhanVien == null || LoginPrefer.getObject(getContext()).MaNhanVien.length() == 0) {
                        visibleAllMain();
                        lottie.setAnimation(R.raw.not_found);
                        coorHomeDriver.setBackgroundColor(getResources().getColor(R.color.pink));
                        tvThongBao.setText(getString(R.string.thieumanhanvien));
                    } else if (LoginPrefer.getObject(getContext()) == null) {
                        visibleAllMain();
                    }
                    Utilities.dismissDialog(progressDialog);
                }
            }

            @Override
            public void onFailure(Call<List<Shipment>> call, Throwable t) {
                for (GeofencingBoss g : listP) {
                    LatLng latLng = new LatLng(Double.parseDouble(g.getLat()), Double.parseDouble(g.getLng()));
                    handleMapLongClick(g.getId(), latLng);
                }
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void visibleAllMain() {
        Utilities.dismissDialog(progressDialog);
        appBar.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
//        fabFastDelivery.setVisibility(View.GONE);
//        fabLayout.setVisibility(View.GONE);
//        fabHomeDriver.setVisibility(View.GONE);
        btnComplete.setVisibility(View.GONE);
        lnLottie.setVisibility(View.VISIBLE);
    }


    private void getVCM(String strDateCute) {
        Intent i = new Intent(getContext(), VCMActivity.class);
        i.putExtra("storecode", strStoreCodeMain);
        i.putExtra("storename", strStoreNameMain);
        i.putExtra("storeaddress", strStoreAddressMain);
        i.putExtra("date", strDateCute);
        i.putExtra("khachhang", strKhachHangMain);
        i.putExtra("routeno", strROUTENO);
        i.putExtra("atmshipmentid", strShipment);
        i.putExtra("orderreleasexid", strOrdeR_RELEASE_XID);
        i.putExtra("packageditemxid", strPackaged_Item_XID);
        startActivity(i);
    }

    private void getBHXICE(String strDateCute) {
        Intent i = new Intent(getContext(), BHXIceActivity.class);
        i.putExtra("storecode", strStoreCodeMain);
        i.putExtra("storename", strStoreNameMain);
        i.putExtra("storeaddress", strStoreAddressMain);
        i.putExtra("date", strDateCute);
        i.putExtra("khachhang", strKhachHangMain);
        i.putExtra("routeno", strROUTENO);
        i.putExtra("atmshipmentid", strShipment);
        i.putExtra("orderreleasexid", strOrdeR_RELEASE_XID);
        i.putExtra("packageditemxid", strPackaged_Item_XID);
        startActivity(i);
    }

    private int getTotalBill(String strStoreCode, String strDate, String strToken, String strShipment) {

        if (!WifiHelper.isConnected(getContext())) {
            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
        }
        if (strKhachHangMain.equals(Customer.VCMFRESH) && strPackaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5) ||
                strKhachHangMain.equals(Customer.VCMFRESH) && strPackaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4) ||
                strKhachHangMain.equals(Customer.BHX) && strPackaged_Item_XID.equals(Customer.ABAMEAT_0_5) ||
                strKhachHangMain.equals(Customer.BHX) && strPackaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5) ||
                strKhachHangMain.equals(Customer.BHX) && strPackaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18) ||
                strKhachHangMain.equals(Customer.CP) && strPackaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5) ||
                strKhachHangMain.equals(Customer.CP) && strPackaged_Item_XID.equals(Customer.ABAMEAT_0_5) ||
                strKhachHangMain.equals(Customer.NEWZEALAND) && strPackaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18) ||
                strKhachHangMain.equals(Customer.THREEF) && strPackaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4) ||
                strKhachHangMain.equals(Customer.XX6020) && strPackaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
        ) {
            MyRetrofit.initRequest(getContext()).getTotalPGH(strStoreCode, strDate, strKhachHangMain, strOrdeR_RELEASE_XID, strToken).enqueue(new Callback<TotalItems>() {
                @Override
                public void onResponse(Call<TotalItems> call, Response<TotalItems> response) {
                    if (response.isSuccessful() && response != null) {
                        strTotal = response.body().totalItems;
                        Intent i = new Intent(getContext(), NhanHangActivity.class);
                        i.putExtra("storecode", strStoreCodeMain);
                        i.putExtra("storename", strStoreNameMain);
                        i.putExtra("storeaddress", strStoreAddressMain);
                        i.putExtra("date", strStartTime2);
                        i.putExtra("storetotal", strTotal);
                        i.putExtra("khachhang", strKhachHangMain);
                        i.putExtra("atmshipmentid", strShipment);
                        i.putExtra("orderreleasexid", strOrdeR_RELEASE_XID);
                        i.putExtra("packageditemxid", strPackaged_Item_XID);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<TotalItems> call, Throwable t) {
                    Utilities.dismissDialog(progressDialog);
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                }
            });

        } else if (strKhachHangMain.equals("BHX") && strPackaged_Item_XID.equals("ABA.TRAY") || strKhachHangMain.equals("VCMFRESH") && strPackaged_Item_XID.equals("ABA.TRAY") || strKhachHangMain.equals("BHX") && strPackaged_Item_XID.equals("ABA.VEGETABLE_12-17")) {
            getVCM(strStartTime2);
        } else if (strKhachHangMain.equals("BHX") && strPackaged_Item_XID.equals("ABA.ICE_0")) {
            getBHXICE(strStartTime2);
        }
        return strTotal;
    }

    private void getStopShipmentCurrent(String atM_shipment_id) {

        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).getStopShipment(strToken, strDriverID, atM_shipment_id, strStartTime2).enqueue(new Callback<List<StoreDriver>>() {
            @Override
            public void onResponse(Call<List<StoreDriver>> call, Response<List<StoreDriver>> response) {
                if (response.isSuccessful() && response != null) {
                    if (response.body().size() > 0) {

                        stringList = response.body();
                        adapter = new HomeDriverAdapter(new RecyclerViewItemListener3<StoreDriver>() {
                            @Override
                            public void onClick(int position, StoreDriver item, CustomToggleButton customToggleButton, TextView tvPhone, int key) {
                                switch (key) {
                                    case 0:
                                        strStoreCodeMain = item.store_Code;
                                        strStoreNameMain = item.store_Name;
                                        strStoreAddressMain = item.addresS_LINE;
                                        strKhachHangMain = item.khachHang;
                                        bCheckMobileHub = item.mobileHub;
                                        strOrdeR_RELEASE_XID = item.orderreleasE_ID;
                                        strPackaged_Item_XID = item.packaged_Item_XID;

                                        if (customToggleButton.isChecked()) {
                                            if (strKhachHangMain.equals(Customer.VCMFRESH) || strKhachHangMain.equals(Customer.BHX) ||
                                                    strKhachHangMain.equals(Customer.CP) || strKhachHangMain.equals(Customer.NEWZEALAND) ||
                                                    strKhachHangMain.equals(Customer.THREEF) || strKhachHangMain.equals(Customer.XX6020)) {
                                                getTotalBill(strStoreCodeMain, strStartTime2, strToken, strShipment);
                                            } else if (!bCheckMobileHub && strKhachHangMain.equals(Customer.VCM)) {
                                                getVCM(strStartTime2);
                                            } else if (!bCheckMobileHub && !strKhachHangMain.equals(Customer.VCMFRESH) || !bCheckMobileHub && !strKhachHangMain.equals(Customer.BHX)
                                                    || !bCheckMobileHub && !strKhachHangMain.equals(Customer.CP) || !bCheckMobileHub && !strKhachHangMain.equals(Customer.NEWZEALAND)
                                                    || !bCheckMobileHub && !strKhachHangMain.equals(Customer.THREEF) || !bCheckMobileHub && !strKhachHangMain.equals(Customer.XX6020)
                                            ) {
                                                Intent i = new Intent(getContext(), ChupHinhGiaoHangActivity.class);
                                                i.putExtra("storecode", strStoreCodeMain);
                                                i.putExtra("storename", strStoreNameMain);
                                                i.putExtra("storeaddress", strStoreAddressMain);
                                                i.putExtra("khachhang", strKhachHangMain);
                                                i.putExtra("date", strStartTime2);
                                                i.putExtra("shipment", strShipment);
                                                i.putExtra("carton", item.totalCarton);
                                                i.putExtra("weight", item.totalWeight);
                                                i.putExtra("orderreleasexid", strOrdeR_RELEASE_XID);
                                                i.putExtra("packageditemxid", strPackaged_Item_XID);
                                                startActivity(i);
                                            }
                                        } else {
                                            Utilities.thongBaoDialog(getContext(), "Vui lòng bấm đã đến");
                                        }
                                        break;
                                    case 1:
                                        Intent i2 = new Intent(getContext(), SuCoActivity.class);
                                        i2.putExtra("storecode", item.store_Code);
                                        i2.putExtra("storename", item.store_Name);
                                        i2.putExtra("storeaddress", item.addresS_LINE);
                                        i2.putExtra("date", strStartTime2);
                                        i2.putExtra("khachhang", item.khachHang);
                                        i2.putExtra("orderreleasexid", item.orderreleasE_ID);
                                        i2.putExtra("packageditemxid", item.packaged_Item_XID);
                                        startActivity(i2);
                                        break;
                                    case 2:
                                        if (checkPermissions()) {
//                                            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                                Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
//                                                init();
                                            } else {
//                                                startLocationUpdates();
//                                                if (mCurrentLocation == null) {
//                                                    Snackbar.make(view, "Chưa định vị đc vui lòng thử lại", Snackbar.LENGTH_LONG).show();
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
                                                if (customToggleButton.isChecked()) {
                                                    progressDialog = Utilities.getProgressDialog(getContext(), "Vui lòng chờ...");
                                                    progressDialog.show();
                                                    if (!WifiHelper.isConnected(getContext())) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                        return;
                                                    }
//                                                        if (item.khachHang.equals("MASAN")) {

                                                    LayoutInflater inflater = getLayoutInflater();
                                                    View showLayout = inflater.inflate(R.layout.dialog_sl_carton_masan, null);

                                                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                                                    b.setView(showLayout);
                                                    b.setCancelable(false);
                                                    AlertDialog dialog = b.create();

                                                    //------------------test start---------------------------
                                                    final Window dialogWindow = dialog.getWindow();
                                                    dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                                                    dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                                    //------------------test end---------------------------


                                                    TextInputEditText itemEdtSLThungMasan = showLayout.findViewById(R.id.itemEdtSLThungMasan);
                                                    TextInputEditText itemEdtSLKhayThuHoi = showLayout.findViewById(R.id.itemEdtSLKhayThuHoi);
                                                    Button btnCloseCarton = showLayout.findViewById(R.id.btnCloseCarton);
                                                    Button btnSendCarton = showLayout.findViewById(R.id.btnSendCarton);
                                                    itemEdtSLThungMasan.requestFocus();
                                                    itemEdtSLThungMasan.setText(item.totalCarton + "");
                                                    itemEdtSLKhayThuHoi.setText("0");

                                                    btnCloseCarton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            dialog.dismiss();
                                                            Utilities.dismissDialog(progressDialog);
                                                        }
                                                    });

                                                    btnSendCarton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (itemEdtSLThungMasan.getText().length() == 0 || itemEdtSLThungMasan.getText().equals("")) {
                                                                Toast.makeText(geofenceHelper, "Số thùng không được để trống", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                MyRetrofit.initRequest(getContext()).updateStateStop(strToken, item.store_Code, item.delivery_Date, item.khachHang, strShipment, 0, true, 0, 0, 0, Integer.parseInt(itemEdtSLThungMasan.getText().toString()), item.totalWeight, strLat, strLng, item.orderreleasE_ID, LoginPrefer.getObject(getContext()).userName, item.totalCarton, itemEdtSLKhayThuHoi.getText().toString() == null ? 0 : Integer.parseInt(itemEdtSLKhayThuHoi.getText().toString())).enqueue(new Callback<StateStop>() {
                                                                    @Override
                                                                    public void onResponse(Call<StateStop> call, Response<StateStop> response) {
                                                                        if (response.isSuccessful() && response != null) {
                                                                            Utilities.dismissDialog(progressDialog);
                                                                            dialog.dismiss();

                                                                            sendStarZalo("84903631331");

                                                                            new GenericDialog.Builder(getContext())
                                                                                    .setIcon(R.drawable.completed)
                                                                                    .setTitle("Hoàn Thành!").setTitleAppearance(R.color.colorPrimaryDark, 16)
                                                                                    .setMessage("Chúc mừng bạn đã hoàn thành xong điểm " + item.store_Name + "!")
                                                                                    .setCancelable(true)
                                                                                    .generate();

                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    removeGeofence();
                                                                                    getStopShipmentCurrent(strShipment);
                                                                                }
                                                                            }, 2000);
                                                                        } else {
                                                                            Utilities.dismissDialog(progressDialog);
                                                                            Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<StateStop> call, Throwable t) {
                                                                        dialog.dismiss();
                                                                        Utilities.dismissDialog(progressDialog);
                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                                        Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
                                                                    }
                                                                });
                                                            }

                                                        }
                                                    });

                                                    dialog.show();


                                                } else {
                                                    Utilities.thongBaoDialog(getContext(), "Vui lòng bấm đã đến!");

                                                }

                                            }
                                        }
                                        break;
                                    case 3:
                                        Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
                                                + item.addresS_LINE));
                                        startActivity(geoIntent);
                                        break;
                                    case 4:
                                        if (checkPermissions()) {
                                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                                Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                                                customToggleButton.setChecked(false);
                                                customToggleButton.setEnabled(true);
                                                stringList.get(position).setbDaToi(false);
                                            } else {
                                                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                                                b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bạn đã tới nơi? Nếu đã tới hãy bấm đồng ý.")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                progressDialog = Utilities.getProgressDialog(getContext(), "Đang lấy thông tin...");
                                                                progressDialog.show();
                                                                if (!WifiHelper.isConnected(getActivity())) {
                                                                    customToggleButton.setChecked(false);
                                                                    customToggleButton.setEnabled(true);
                                                                    stringList.get(position).setbDaToi(false);
                                                                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                                    Utilities.dismissDialog(progressDialog);
                                                                    return;
                                                                }
                                                                MyRetrofit.initRequest(getContext()).updateArrivedTime(strToken, strShipment, item.store_Code, strLat, strLng, item.orderreleasE_ID).enqueue(new Callback<Integer>() {
                                                                    @Override
                                                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                        if (response.isSuccessful() && response.body() != null) {
                                                                            Log.e("huhu", response.body() + "");
                                                                            if (response.body() == 1) {
                                                                                customToggleButton.setChecked(true);
                                                                                customToggleButton.setEnabled(false);
                                                                                stringList.get(position).setbDaToi(true);
                                                                                Snackbar.make(view, "Đã dến!", Snackbar.LENGTH_LONG).show();
//                                                                                    adapter.notifyDataSetChanged();
                                                                            } else if (response.body() == 0) {
                                                                                customToggleButton.setChecked(false);
                                                                                customToggleButton.setEnabled(true);
                                                                                stringList.get(position).setbDaToi(false);
                                                                                Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                            }
                                                                        } else {
                                                                            customToggleButton.setChecked(false);
                                                                            customToggleButton.setEnabled(true);
                                                                            stringList.get(position).setbDaToi(false);
                                                                            Snackbar.make(view, "Vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                                        }
                                                                        progressDialog.dismiss();
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Integer> call, Throwable t) {
                                                                        progressDialog.dismiss();
                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                                        customToggleButton.setChecked(false);
                                                                        customToggleButton.setEnabled(true);
                                                                        stringList.get(position).setbDaToi(false);
                                                                    }
                                                                });
                                                            }
                                                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                        customToggleButton.setChecked(false);
                                                        customToggleButton.setEnabled(true);
                                                        stringList.get(position).setbDaToi(false);
                                                    }
                                                });
                                                Dialog dialogArrTime = b.create();
                                                dialogArrTime.show();
//                                                }
                                            }
                                        }
                                        break;
                                    case 5:
                                        if (checkPermissions()) {
                                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                                Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                                                customToggleButton.setChecked(false);
                                                customToggleButton.setEnabled(true);
                                                stringList.get(position).setbDaToi(false);
                                            } else {
                                                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                                                b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bạn chắc chắn bạn giao Hub? Nếu đúng hãy bấm đồng ý.")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                if (customToggleButton.isChecked()) {
                                                                    MyRetrofit.initRequest(getContext()).UpdateStateHubDriver(strToken, item.store_Code, strStartTime2, item.khachHang, strUsername, strLat, strLng, " ", " ", strShipment, item.orderreleasE_ID).enqueue(new Callback<String>() {
                                                                        @Override
                                                                        public void onResponse(Call<String> call, Response<String> response) {
                                                                            if (response.isSuccessful() && response != null) {
                                                                                Snackbar.make(view, "Giao Hub thành công!", Snackbar.LENGTH_LONG).show();
                                                                                sendStarZalo("84903631331");
                                                                                new Handler().postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        getStopShipmentCurrent(strShipment);
                                                                                    }
                                                                                }, 2000);
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<String> call, Throwable t) {
                                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                                        }
                                                                    });
                                                                } else {
                                                                    Utilities.thongBaoDialog(getContext(), "Vui lòng bấm đã tới");
                                                                }

                                                            }
                                                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                });

                                                Dialog dialogHub = b.create();
                                                dialogHub.show();
                                            }
                                        }
                                        break;

                                    case 6:
                                        strStoreCodeMain = item.store_Code_ABA;
                                        registerForContextMenu(tvPhone);
                                        getActivity().openContextMenu(tvPhone);
                                        strPhoneNumber = tvPhone.getText().toString();
                                        break;
                                }
                            }

                            @Override
                            public void onLongClick(int position) {

                            }
                        }, stringList);
                        adapter.replace(stringList);
                        for (StoreDriver sd : response.body()) {
                            int thung = 0;
                            thung = sd.totalCarton + thung;
                            soThung = thung;
                        }

                        try {
                            btnComplete.setVisibility(View.GONE);
                            rvMainDriver.setAdapter(adapter);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            rvMainDriver.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }

                        totalListPD = new ArrayList<>(listP);
                        totalListPD.addAll(listD);

                    } else if (response.body().size() == 0) {
                        try {
                            swipeRefreshLayout.setVisibility(View.GONE);
                            btnComplete.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<List<StoreDriver>> call, Throwable t) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Liên Lạc");

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.layout_phone_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_phone:
                Toast.makeText(geofenceHelper, "Liên hệ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + strPhoneNumber));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.menu_wait5:
                sendTimeZalo("84903631331", "5 phút");
                break;
            case R.id.menu_wait10:
                sendTimeZalo("84903631331", "10 phút");
                break;
            case R.id.menu_wait15:
                sendTimeZalo("84903631331", "15 phút");
                break;
        }
        return true;
    }

    @OnClick(R.id.btnComplete)
    public void hoanThanhChuyen(View view) {

        if (!LoginPrefer.getObject(getContext()).isBiker) {
            FragmentManager fm = getFragmentManager();
            dialogADCADShipmentFragment = DialogADCADShipmentFragment.newInstances(strShipment, strStartTime, strUsername, strDriverID);
            dialogADCADShipmentFragment.setTargetFragment(HomeDriverFragment.this, DATEPICKER_FRAGMENT);
            dialogADCADShipmentFragment.setCancelable(false);
            dialogADCADShipmentFragment.show(fm, null);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc muốn hoàn thành chuyến xe(Hãy cân nhắc có thể Vận hành chưa gán các điểm giao cho bạn)");
            builder.setCancelable(false);
            builder.setPositiveButton("Hoàn thành", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MyRetrofit.initRequest(getContext()).UpdateGeofence(strToken, geofenceAllList).enqueue(new Callback<ItemBill>() {
                        @Override
                        public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Vui lòng thử lại/" + response.errorBody().toString() + "/" + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ItemBill> call, Throwable t) {
                            Utilities.dismissDialog(progressDialog);
                            Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                        }
                    });

                    MyRetrofit.initRequest(getContext()).updateStateShipment(strToken, strShipment, "ADCAD", strDriverID).enqueue(new Callback<ResultAA>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(Call<ResultAA> call, Response<ResultAA> response) {
                            if (response.isSuccessful() && response != null) {
                                if (listShipment.size() == 0) {
                                    btnChuyen1.setVisibility(View.GONE);
                                    btnChuyen2.setVisibility(View.GONE);
                                }
                                if (listShipment.size() < 2) {
                                    btnChuyen2.setVisibility(View.GONE);
                                }

                                if (btnChuyen2.getVisibility() == View.VISIBLE) {
                                    btnChuyen1.setBackgroundColor(R.color.colorPrimary);
                                }

                                getCurrentShipment();
                                btnComplete.setVisibility(View.GONE);
                                geofenceAllViewModel.deleteAllGeofenceAll();
                                removeGeofence();

                                try {
                                    LoginPrefer.deleteListP(getContext());
                                } catch (Exception e) {
                                    Log.e(TAG, e.toString());
                                }
                                Snackbar.make(view, "Đã Hoàn thành chuyến bạn có thể bắt đầu chuyến mới", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultAA> call, Throwable t) {
                            Utilities.dismissDialog(progressDialog);
                            Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                        }
                    });

                }

            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            Dialog dialog = builder.create();
            dialog.show();
        }
    }


    @OnClick(R.id.toggleKhoDriverDen)
    public void denKho(View view) {
        if (checkPermissions()) {
//            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                toggleKhoDriverDen.setChecked(false);
                toggleKhoDriverDen.setEnabled(true);

            } else {

                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bạn đã đến kho? Nếu đã đến hãy bấm đồng ý.")
                        .setCancelable(false)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                progressDialog.show();

                                if (!WifiHelper.isConnected(getContext())) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                    return;
                                }

                                MyRetrofit.initRequest(getContext()).updateGioRoiKhoVCM(strToken, strShipment, strLat, strLng, "A").enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.isSuccessful() && response != null) {
                                            if (response.body().equals('"' + "1" + '"')) {
                                                Snackbar.make(view, "Bạn đã đến kho", Snackbar.LENGTH_LONG).show();
                                                toggleKhoDriverDen.setChecked(true);
                                                toggleKhoDriverDen.setEnabled(false);
                                                Utilities.dismissDialog(progressDialog);
                                            } else {
                                                Snackbar.make(view, "Bạn đã đến kho", Snackbar.LENGTH_LONG).show();
                                                toggleKhoDriverDen.setChecked(false);
                                                toggleKhoDriverDen.setEnabled(true);
                                                Utilities.dismissDialog(progressDialog);
                                            }


                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        toggleKhoDriverDen.setChecked(false);
                                        toggleKhoDriverDen.setEnabled(true);
                                        Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        toggleKhoDriverDen.setChecked(false);
                        toggleKhoDriverDen.setEnabled(true);
                    }
                });

                Dialog dialog = b.create();
                dialog.show();

            }
        }
    }


    @OnClick(R.id.toggleKhoDriverPickUp)
    public void startPick(View view) {
        if (toggleKhoDriverDen.isChecked()) {
            if (checkPermissions()) {
//                locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                    toggleKhoDriverPickUp.setChecked(false);
                    toggleKhoDriverPickUp.setEnabled(true);
//                init();
                } else {
                    androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bạn bắt đầu nhận hàng? Nếu vậy hãy bấm đồng ý.")
                            .setCancelable(false)
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                    progressDialog.show();

                                    if (!WifiHelper.isConnected(getContext())) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                        return;
                                    }

                                    MyRetrofit.initRequest(getContext()).updateGioRoiKhoVCM(strToken, strShipment, strLat, strLng, "P").enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful() && response != null) {
                                                if (response.body().equals('"' + "1" + '"')) {
                                                    Snackbar.make(view, "Bạn đã rời kho thành công", Snackbar.LENGTH_LONG).show();
                                                    toggleKhoDriverPickUp.setChecked(true);
                                                    toggleKhoDriverPickUp.setEnabled(false);
                                                    Utilities.dismissDialog(progressDialog);
                                                } else {
                                                    Snackbar.make(view, "Bạn đã rời kho thành công", Snackbar.LENGTH_LONG).show();
                                                    toggleKhoDriverPickUp.setChecked(false);
                                                    toggleKhoDriverPickUp.setEnabled(true);
                                                    Utilities.dismissDialog(progressDialog);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Utilities.dismissDialog(progressDialog);
                                            toggleKhoDriverPickUp.setChecked(false);
                                            toggleKhoDriverPickUp.setEnabled(true);
                                            Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toggleKhoDriverPickUp.setChecked(false);
                            toggleKhoDriverPickUp.setEnabled(true);
                        }
                    });
                    Dialog dialog = b.create();
                    dialog.show();
                }
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn chưa đến kho nên chưa thể bấm nhận hàng! Nếu đã đến kho hãy nhấn đến kho");
            Dialog d = builder.create();
            d.show();
            toggleKhoDriverPickUp.setChecked(false);
            toggleKhoDriverPickUp.setEnabled(true);
        }

    }

    @OnClick(R.id.toggleKhoDriverPickUpDone)
    public void donePick(View view) {
        if (toggleKhoDriverPickUp.isChecked()) {
            if (checkPermissions()) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                    toggleKhoDriverPickUpDone.setChecked(false);
                    toggleKhoDriverPickUpDone.setEnabled(true);
                } else {
                    androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bạn đã nhận hàng xong? Nếu vậy hãy bấm đồng ý.")
                            .setCancelable(false)
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                    progressDialog.show();

                                    if (!WifiHelper.isConnected(getContext())) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                        return;
                                    }

                                    MyRetrofit.initRequest(getContext()).updateGioRoiKhoVCM(strToken, strShipment, strLat, strLng, "D").enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful() && response != null) {
                                                if (response.body().equals('"' + "1" + '"')) {
                                                    Snackbar.make(view, "Bạn đã nhận hàng xong thành công", Snackbar.LENGTH_LONG).show();
                                                    toggleKhoDriverPickUpDone.setChecked(true);
                                                    toggleKhoDriverPickUpDone.setEnabled(false);
                                                    Utilities.dismissDialog(progressDialog);
                                                } else {
                                                    Snackbar.make(view, "Bạn đã nhận hàng xong thành công", Snackbar.LENGTH_LONG).show();
                                                    toggleKhoDriverPickUpDone.setChecked(false);
                                                    toggleKhoDriverPickUpDone.setEnabled(true);
                                                    Utilities.dismissDialog(progressDialog);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Utilities.dismissDialog(progressDialog);
                                            toggleKhoDriverPickUpDone.setChecked(false);
                                            toggleKhoDriverPickUpDone.setEnabled(true);
                                            Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toggleKhoDriverPickUpDone.setChecked(false);
                            toggleKhoDriverPickUpDone.setEnabled(true);
                        }
                    });
                    Dialog dialog = b.create();
                    dialog.show();
                }
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn chưa nhận hàng kho nên chưa thể bấm lấy hàng xong! Nếu đã lấy hàng xong hãy nhấn lấy xong");
            Dialog d = builder.create();
            d.show();
            toggleKhoDriverPickUpDone.setChecked(false);
            toggleKhoDriverPickUpDone.setEnabled(true);
        }

    }


    @OnClick(R.id.toggleKhoDriver)
    public void roiKho(View view) {

        if (toggleKhoDriverPickUpDone.isChecked()) {
            if (checkPermissions()) {
//                locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                    toggleKhoDriver.setChecked(false);
                    toggleKhoDriver.setEnabled(true);
//                init();
                } else {
                    androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    b.setMessage("Gửi thông tin").setIcon(R.drawable.warning).setMessage("Bạn đã rời kho? Nếu đã rời hãy bấm đồng ý.")
                            .setCancelable(false)
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                    progressDialog.show();

                                    if (!WifiHelper.isConnected(getContext())) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                        return;
                                    }

                                    MyRetrofit.initRequest(getContext()).updateGioRoiKhoVCM(strToken, strShipment, strLat, strLng, "L").enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful() && response != null) {
                                                if (response.body().equals('"' + "1" + '"')) {
                                                    Snackbar.make(view, "Bạn đã rời kho thành công", Snackbar.LENGTH_LONG).show();
                                                    toggleKhoDriver.setChecked(true);
                                                    toggleKhoDriver.setEnabled(false);
                                                    Utilities.dismissDialog(progressDialog);
                                                } else {
                                                    Snackbar.make(view, "Thất bại vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                                                    toggleKhoDriver.setChecked(false);
                                                    toggleKhoDriver.setEnabled(true);
                                                    Utilities.dismissDialog(progressDialog);
                                                }


                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Utilities.dismissDialog(progressDialog);
                                            toggleKhoDriver.setChecked(false);
                                            toggleKhoDriver.setEnabled(true);
                                            Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toggleKhoDriver.setChecked(false);
                            toggleKhoDriver.setEnabled(true);
                        }
                    });
                    Dialog dialog = b.create();
                    dialog.show();
                }
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn chưa bấm lấy hàng xong nên chưa thể bấm rời kho!");
            Dialog d = builder.create();
            d.show();
            toggleKhoDriver.setChecked(false);
            toggleKhoDriver.setEnabled(true);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        getStopShipmentCurrent(strShipment);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
//                        Log.e(TAG, "User chose not to make required location settings changes.");
                        break;

                }
                break;

            case DATEPICKER_FRAGMENT:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Toast.makeText(geofenceHelper, data.getIntExtra("yourKey", 3) + "", Toast.LENGTH_SHORT).show();
                        Log.d("jijo", data.getIntExtra("yourKey", 3) + "");

                        if (iState == 0) {
                            MyRetrofit.initRequest(getContext()).UpdateGeofence(strToken, geofenceAllList).enqueue(new Callback<ItemBill>() {
                                @Override
                                public void onResponse(Call<ItemBill> call, Response<ItemBill> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Vui lòng thử lại/" + response.errorBody().toString() + "/" + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ItemBill> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                }
                            });

                            MyRetrofit.initRequest(getContext()).updateStateShipment(strToken, strShipment, "ADCAD", strDriverID).enqueue(new Callback<ResultAA>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onResponse(Call<ResultAA> call, Response<ResultAA> response) {
                                    if (response.isSuccessful() && response != null) {
                                        if (listShipment.size() == 0) {
                                            btnChuyen1.setVisibility(View.GONE);
                                            btnChuyen2.setVisibility(View.GONE);
                                        }
                                        if (listShipment.size() < 2) {
                                            btnChuyen2.setVisibility(View.GONE);
                                        }

                                        if (btnChuyen2.getVisibility() == View.VISIBLE) {
                                            btnChuyen1.setBackgroundColor(R.color.colorPrimary);
                                        }

                                        getCurrentShipment();
                                        btnComplete.setVisibility(View.GONE);
                                        geofenceAllViewModel.deleteAllGeofenceAll();
                                        removeGeofence();

                                        try {
                                            LoginPrefer.deleteListP(getContext());
                                        } catch (Exception e) {
                                            Log.e(TAG, e.toString());
                                        }
                                        Snackbar.make(view, "Đã Hoàn thành chuyến bạn có thể bắt đầu chuyến mới", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResultAA> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                }
                            });

                            dialogADCADShipmentFragment.dismiss();
                        } else {
                            Toast.makeText(geofenceHelper, "Gửi hình đi không thành công", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mBound) {
            getContext().unbindService(mServiceConnection);
            mBound = false;
        }
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToFragment event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
//            Log.d("bug", "" + data);
//            Toast.makeText(mService, data + "/bbb3", Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(getContext(), data + "/bbb2", Toast.LENGTH_SHORT).show();
//            Log.d("bug", "" + data);
            strLat = String.valueOf(event.getLocation().getLatitude());
            strLng = String.valueOf(event.getLocation().getLongitude());
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @OnClick(R.id.btnChuyen1)
    public void chuyen1() {
        stateChuyen = false;
        setColorButton(stateChuyen);
        getCurrentShipment();
    }

    @OnClick(R.id.btnChuyen2)
    public void chuyen2() {
        stateChuyen = true;
        setColorButton(stateChuyen);
        Bundle args = new Bundle();
        if (totalListPD != null && totalListPD.size() > 0) {
            args.putSerializable("list", (Serializable) totalListPD);
        } else {
            args.putSerializable("list", (Serializable) listP);
        }
        DialogFragment fragment = new MapFragment();
        fragment.setArguments(args);
        fragment.setCancelable(true);
        fragment.show(getActivity().getSupportFragmentManager(), "TAG2");
//        getCurrentShipment2();
    }

    @OnClick(R.id.btnChuyen3)
    public void chuyen3() {
        Bundle args = new Bundle();
        args.putString("routeno", strROUTENO);
        args.putString("customer", strCustomerCode);
        args.putString("date", strStartTime2);
        StoreBikerFragment fragment2 = new StoreBikerFragment();
        fragment2.setArguments(args);
        fragment2.setCancelable(true);
        fragment2.show(getActivity().getSupportFragmentManager(), "TAG3");
    }

    @SuppressLint("ResourceAsColor")
    private void setColorButton(boolean b) {
//        if (!b) {
//            btnChuyen1.setBackgroundColor(R.color.colorPrimary);
//            btnChuyen2.setBackgroundColor(R.color.colorPrimaryDark);
//        } else {
//            btnChuyen1.setBackgroundColor(R.color.colorPrimaryDark);
//            btnChuyen2.setBackgroundColor(R.color.colorPrimary);
//        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            String text = newText;
            adapter.filter(text);
        } catch (Exception e) {
//            Log.e("er", e + "");
        }
        return false;
    }

    private void addGeofence(String ID, LatLng latLng, float radius) {
        Geofence geofence = geofenceHelper.getGeofence(ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER
                | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "onSuccess: Geofencing Added..." + ID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errorMessage = geofenceHelper.getErrorString(e);
//                Log.d(TAG, "onFailure: " + errorMessage);
            }
        });
    }

    private void removeGeofence() {
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
        geofencingClient.removeGeofences(pendingIntent).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "onSuccess: Geofencing Removed...");
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errorMessage = geofenceHelper.getErrorString(e);
//                Log.d(TAG, "onFailure: " + errorMessage);
            }
        });
    }


    private void handleMapLongClick(String Id, LatLng latLng) {
        addGeofence(Id, latLng, GEOFENCE_RADIUS);
    }

    private void addDepot() {

        MyRetrofit.initRequest(getContext()).getLocationGeofencing(strToken).enqueue(new Callback<List<LocationGeofencing>>() {
            @Override
            public void onResponse(Call<List<LocationGeofencing>> call, Response<List<LocationGeofencing>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.e(TAG, "Thêm tc");
                    for (int i = 0; i < response.body().size(); i++) {
//                        Log.d("add123", response.body().get(i).type + "/" + response.body().get(i).locationName);
                        listP.add(new GeofencingBoss(response.body().get(i).type + "/" + response.body().get(i).locationName, response.body().get(i).lat, response.body().get(i).lng));
                    }

                    for (GeofencingBoss g : listP) {
                        LatLng latLng = new LatLng(Double.parseDouble(g.getLat()), Double.parseDouble(g.getLng()));
                        handleMapLongClick(g.getId(), latLng);
                    }
                } else {
                    Toast.makeText(geofenceHelper, "Thêm Geofencing thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocationGeofencing>> call, Throwable t) {
                Toast.makeText(geofenceHelper, "Không có mạng để thêm Geofencing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLocation() {
        buildLocationRequest();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(getContext(), MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(10f);
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    }


    //Lấy định vị gps của điện thoại
    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getContext(), "Vui lòng mở GPS(Định vị) để gửi!", Toast.LENGTH_SHORT).show();
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) getContext());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(getContext(), "" + location.getLatitude() + "/" + location.getLongitude() + "/ccc", Toast.LENGTH_SHORT).show();
        strLat = String.valueOf(location.getLatitude());
        strLng = String.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void sendStarZalo(String phone) {

        TemplateDataParent2 templateDataParentRating = new TemplateDataParent2(phone, "202474",
                new TemplateData("", "", "", "", ""), "3");

        MyRetrofit2.initRequest2().SendZaloRating(templateDataParentRating).enqueue(new Callback<Zalo>() {
            @Override
            public void onResponse(Call<Zalo> call, Response<Zalo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().message.equals("Success")) {
                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zalo> call, Throwable t) {
                Toast.makeText(getContext(), "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendTimeZalo(String phone, String time) {

        TemplateDataParent templateDataParent = new TemplateDataParent(phone, "202522", new TemplateData520(strStoreCodeMain, time), "3");

        MyRetrofit2.initRequest2().SendZalo(templateDataParent).enqueue(new Callback<Zalo>() {
            @Override
            public void onResponse(Call<Zalo> call, Response<Zalo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().message.equals("Success")) {
                        Toast.makeText(geofenceHelper, "Thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zalo> call, Throwable t) {
                Toast.makeText(geofenceHelper, "Vui lòng kiểm tra Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    @Override
//    public void sendInput(int i) {
//        iState = i;
//
////        Bundle bundle = data.getExtras();
////        iState = bundle.getInt(DialogADCADShipmentFragment.EDIT_TEXT_BUNDLE_KEY,-1);
////                        iState = data.getIntExtra(DialogADCADShipmentFragment.EDIT_TEXT_BUNDLE_KEY, -1);
//

//
//
//        }
//    }
}
