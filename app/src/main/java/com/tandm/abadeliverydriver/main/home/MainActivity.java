package com.tandm.abadeliverydriver.main.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentdieudong.DieuDongFragment;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.GiaoBuFragment;
import com.tandm.abadeliverydriver.main.home.fragmentHistory.HistoryDeliveryFragment;
import com.tandm.abadeliverydriver.main.home.fragmentchangepass.ChangePassFragment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.Fee2Fragment;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.ParentHistoryBikerFragment;
import com.tandm.abadeliverydriver.main.home.fragmenthome.HomeFragment;
import com.tandm.abadeliverydriver.main.home.fragmentchuyenxe.AADriverFragment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.HomeDriverFragment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.HomeDriverCustomerFragment;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.InforFragment;
import com.tandm.abadeliverydriver.main.home.fragmentop.FragmentOP;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.ParentSalaryFragment;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.VouchersFragment;
import com.tandm.abadeliverydriver.main.home.fragmentvouchersop.VouchersOPFragment;
import com.tandm.abadeliverydriver.main.login.LoginActivity;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.receiver.CheckFeeOPBroadcastReceiver;
import com.tandm.abadeliverydriver.main.receiver.GPSReceiver;
import com.tandm.abadeliverydriver.main.service.SendLocationToFragment2;
import com.tandm.abadeliverydriver.main.updatephanmem.CapNhatPhanMemFragment;
import com.tandm.abadeliverydriver.main.utilities.AddFragmentUtil;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {


    View view;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar tb;

    @BindView(R.id.tvToolbar)
    TextView tvToolbar;

    @BindView(R.id.navi)
    NavigationView navi;

    @BindView(R.id.lottieCake)
    LottieAnimationView lottieCake;

    boolean doubleBack = false;
    public static TextView chuyenXe;

    String strToken, strDriverID, strDate,strLat = "0.0",strLng = "0.0";
    AlarmManager alarmManager;

    LocationManager locationManager;
    GPSReceiver receiver;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(tb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        tvToolbar.setText(LoginPrefer.getObject(MainActivity.this).fullName);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Utilities.hideItemDriverDrawer(navi, MainActivity.this);

        strToken = "Bearer " + LoginPrefer.getObject(MainActivity.this).access_token;
        strDriverID = LoginPrefer.getObject(MainActivity.this).MaNhanVien;
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));

        chuyenXe = (TextView) MenuItemCompat.getActionView(navi.getMenu().findItem(R.id.trip_drawer));

        Utilities.initializeCountDrawer(view, MainActivity.this, chuyenXe, strToken, strDriverID, strDate);
        receiver = new GPSReceiver();
        MainActivity.this.registerReceiver(receiver,new IntentFilter("android.location.PROVIDERS_CHANGED"));


        if (!LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("NVGN")
                || LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("NVGN")) {
            navi.getMenu().getItem(1).setChecked(true);
        } else if (!LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("OP")
                || !LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("OP2")
                || !LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("KT")
                || !LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("KT2")) {
            navi.getMenu().getItem(2).setChecked(true);
        }else if (!LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("KH")){
            navi.getMenu().getItem(15).setChecked(true);
        }


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date(System.currentTimeMillis());
        String strDateNow = formatter.format(date);
        String strBirthDate = LoginPrefer.getObject(MainActivity.this).NgaySinh;


        if (strBirthDate.compareTo(strDateNow) == 0) {
            lottieCake.setVisibility(View.VISIBLE);
            lottieCake.setAnimation(R.raw.hpbd);

            lottieCake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_video_birthday);
                    LottieAnimationView lottieVideo = dialog.findViewById(R.id.lottiehpbd);
                    lottieVideo.setAnimation(R.raw.bdvideomp4lottie);

                    dialog.show();
                }
            });
        }


        Intent in = getIntent();
        boolean b = in.getBooleanExtra("acceptshipment", false);
        if (b) {
            tvToolbar.setText("Chuyến xe");
            AddFragmentUtil.loadFragment(MainActivity.this, new AADriverFragment(), R.id.frLayout);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Dexter.withActivity(MainActivity.this)
                    .withPermissions(Arrays.asList(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            startLocationUpdates();
                            getLocation();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    }).check();
        } else {
            Dexter.withActivity(MainActivity.this)
                    .withPermissions(Arrays.asList(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            startLocationUpdates();
                            getLocation();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    }).check();
        }


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setCheckable(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (menuItem.getItemId()) {
                            case R.id.home_drawer:
                                Fragment home = new HomeFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, home, R.id.frLayout);
                                tvToolbar.setText(LoginPrefer.getObject(MainActivity.this).fullName);
                                break;

                            case R.id.home_driver_customer_drawer:
                                Fragment homeDriverCustomer = new HomeDriverCustomerFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, homeDriverCustomer, R.id.frLayout);
                                tvToolbar.setText(LoginPrefer.getObject(MainActivity.this).fullName);
                                break;

                            case R.id.pass_drawer:
                                Fragment cb = new ChangePassFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, cb, R.id.frLayout);
                                tvToolbar.setText("Đổi mật khẩu");
                                if (!LoginPrefer.getObject(MainActivity.this).isBiker) {
                                    Utilities.initializeCountDrawer(view, MainActivity.this, chuyenXe, strToken, strDriverID, strDate);
                                }
                                break;

                            case R.id.history_drawer:
                                Fragment his = new HistoryDeliveryFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, his, R.id.frLayout);
                                tvToolbar.setText("Lịch sử giao hàng");
                                break;

                            case R.id.history_biker_drawer:
                                Fragment hisBiker = new ParentHistoryBikerFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, hisBiker, R.id.frLayout);
                                tvToolbar.setText("Lịch sử giao hàng");
                                break;

                            case R.id.fee_drawer:
                                Fragment fee = new Fee2Fragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, fee, R.id.frLayout);
                                tvToolbar.setText("Phí");
                                Utilities.initializeCountDrawer(view, MainActivity.this, chuyenXe, strToken, strDriverID, strDate);
                                break;

                            case R.id.trip_drawer:
                                Fragment trip = new AADriverFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, trip, R.id.frLayout);
                                tvToolbar.setText("Chuyến xe");
                                Utilities.initializeCountDrawer(view, MainActivity.this, chuyenXe, strToken, strDriverID, strDate);
                                break;

                            case R.id.home_driver_drawer:
                                Fragment homeDriver = new HomeDriverFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, homeDriver, R.id.frLayout);
                                tvToolbar.setText(LoginPrefer.getObject(MainActivity.this).fullName);
                                Utilities.initializeCountDrawer(view, MainActivity.this, chuyenXe, strToken, strDriverID, strDate);
                                break;

                            case R.id.history_driver_drawer:
                                Fragment hisDriver = new ParentSalaryFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, hisDriver, R.id.frLayout);
                                tvToolbar.setText("Lịch sử giao hàng");
                                break;

                            case R.id.vouchers_drawer:
                                Fragment vouchersDriver = new VouchersFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, vouchersDriver, R.id.frLayout);
                                tvToolbar.setText("Chứng từ");
                                break;

                            case R.id.update_software_drawer:
                                Fragment capNhatPhanMem = new CapNhatPhanMemFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, capNhatPhanMem, R.id.frLayout);
                                tvToolbar.setText("Cập nhật App");
                                break;

                            case R.id.dieu_dong_driver_drawer:
                                Fragment dieudongDriver = new DieuDongFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, dieudongDriver, R.id.frLayout);
                                break;

                            case R.id.giao_bu_drawer:
                                Fragment giaoBuBiker = new GiaoBuFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, giaoBuBiker, R.id.frLayout);
                                break;

                            case R.id.home_op_drawer:
                                Fragment duyetTamUngOP = new FragmentOP();
                                AddFragmentUtil.loadFragment(MainActivity.this, duyetTamUngOP, R.id.frLayout);
                                break;

                            case R.id.late_licenses_op_drawer:
                                Fragment vouchersOP = new VouchersOPFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, vouchersOP, R.id.frLayout);
                                break;

                            case R.id.information_drawer:
                                Fragment information = new InforFragment();
                                AddFragmentUtil.loadFragment(MainActivity.this, information, R.id.frLayout);
                                tvToolbar.setText(LoginPrefer.getObject(MainActivity.this).fullName);
                                break;

                        }
                    }
                }, 200);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int i = bundle.getInt("hihi");
            if (i == 1) {
                AddFragmentUtil.loadFragment(MainActivity.this, new Fee2Fragment(), R.id.frLayout);
                tvToolbar.setText("Phí");
                navi.getMenu().getItem(8).setChecked(true);
            }
        } else {
            if (!LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("NVGN")
                    || LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("NVGN")) {
                AddFragmentUtil.loadFragment(MainActivity.this, new HomeDriverFragment(), R.id.frLayout);
            } else if (!LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("OP")
                    || !LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("OP2")
                    || !LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("KT")
                    || !LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("KT2")) {
                AddFragmentUtil.loadFragment(MainActivity.this, new FragmentOP(), R.id.frLayout);
            } else if (!LoginPrefer.getObject(MainActivity.this).isBiker && LoginPrefer.getObject(MainActivity.this).Position.equals("KH")){
                AddFragmentUtil.loadFragment(MainActivity.this, new HomeDriverCustomerFragment(), R.id.frLayout);
            }
        }

    }

    @OnClick(R.id.btnExit)
    public void thoatApp() {
        MyRetrofit.initRequest(MainActivity.this).updateNotiTokenUser("Bearer " + LoginPrefer.getObject(MainActivity.this).access_token, LoginPrefer.getObject(MainActivity.this).userName, "khongco").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(view, "Vui lòng kiểm tra kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });
        Intent alarmIntent = new Intent(this, CheckFeeOPBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        LoginPrefer.deleteObject(MainActivity.this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (doubleBack) {
            super.onBackPressed();
            finish();
        }
        this.doubleBack = true;
        Toast.makeText(this, "Nhấn thêm một lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(MainActivity.this, "Vui lòng mở GPS(Định vị) để gửi!", Toast.LENGTH_SHORT).show();
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1,MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(MainActivity.this, ""+location.getLatitude()+ "/"+ location.getLongitude()+"/aaa", Toast.LENGTH_SHORT).show();
        strLat = String.valueOf(location.getLatitude());
        strLng = String.valueOf(location.getLongitude());

        EventBus.getDefault().postSticky(new SendLocationToFragment2(location));

        try {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address>addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

        }catch (Exception e){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.this.unregisterReceiver(receiver);
    }
}
