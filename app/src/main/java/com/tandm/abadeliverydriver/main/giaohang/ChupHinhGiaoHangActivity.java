package com.tandm.abadeliverydriver.main.giaohang;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.tandm.abadeliverydriver.main.giaohang.model.ImageGiaoHang;
import com.tandm.abadeliverydriver.main.giaohang.model.StateStop;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.jai.genericdialog2.GenericDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChupHinhGiaoHangActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 2000;
    private static final String TAG = "ChupHinhGiaoHangActivity";

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


    Uri image_uri, image_uri1, image_uri2, image_uri3, image_uri4, image_uri5, image_uri6;
    @BindView(R.id.tvThoiGianChupHinhGiaoHang)
    TextView tvThoiGianChupHinhGiaoHang;
    @BindView(R.id.tvMaCuaHangChupHinhGiaoHang)
    TextView tvMaCuaHangChupHinhGiaoHang;
    @BindView(R.id.tvCuaHangChupHinhGiaoHang)
    TextView tvCuaHangChupHinhGiaoHang;
    @BindView(R.id.tvDiaChiChupHinhGiaoHang)
    TextView tvDiaChiChupHinhGiaoHang;
    @BindView(R.id.tvToolbarChupHinhGiaoHang)
    TextView tvToolbarChupHinhGiaoHang;
    @BindView(R.id.toolbarChupHinhGiaoHang)
    Toolbar toolbarChupHinhGiaoHang;
    @BindView(R.id.imgDriver1)
    ImageView imgDriver1;
    @BindView(R.id.imgDriver2)
    ImageView imgDriver2;
    @BindView(R.id.imgDriver3)
    ImageView imgDriver3;
    @BindView(R.id.imgDriver4)
    ImageView imgDriver4;
    @BindView(R.id.imgDriver5)
    ImageView imgDriver5;
    @BindView(R.id.imgDriver6)
    ImageView imgDriver6;
    @BindView(R.id.buttonDriver1)
    ImageButton buttonDriver1;
    @BindView(R.id.buttonDriver2)
    ImageButton buttonDriver2;
    @BindView(R.id.buttonDriver3)
    ImageButton buttonDriver3;
    @BindView(R.id.buttonDriver4)
    ImageButton buttonDriver4;
    @BindView(R.id.buttonDriver5)
    ImageButton buttonDriver5;
    @BindView(R.id.buttonDriver6)
    ImageButton buttonDriver6;
    @BindView(R.id.edtNoteDriver)
    TextInputEditText edtNoteDriver;
    @BindView(R.id.btnSendHinhAnhGiaoHang)
    Button btnSendHinhAnhGiaoHang;

    @BindView(R.id.tvSLThungNhan)
    TextView tvSLThungNhan;
    @BindView(R.id.tvSLThungGiao)
    TextView tvSLThungGiao;
    @BindView(R.id.cbThieu)
    CheckBox cbThieu;
    @BindView(R.id.btnTruThieu)
    ImageButton btnTruThieu;
    @BindView(R.id.tvThieu)
    TextView tvThieu;
    @BindView(R.id.btnCongThieu)
    ImageButton btnCongThieu;

    @BindView(R.id.cbHong)
    CheckBox cbHong;
    @BindView(R.id.btnTruHong)
    ImageButton btnTruHong;
    @BindView(R.id.tvHong)
    TextView tvHong;
    @BindView(R.id.btnCongHong)
    ImageButton btnCongHong;

    @BindView(R.id.cbDu)
    CheckBox cbDu;
    @BindView(R.id.btnTruDu)
    ImageButton btnTruDu;
    @BindView(R.id.tvDu)
    TextView tvDu;
    @BindView(R.id.btnCongDu)
    ImageButton btnCongDu;

    @BindView(R.id.cbNhietDo)
    CheckBox cbNhietDo;
    @BindView(R.id.btnTruNhietDo)
    ImageButton btnTruNhietDo;
    @BindView(R.id.tvNhietDo)
    TextView tvNhietDo;
    @BindView(R.id.btnCongNhietDo)
    ImageButton btnCongNhietDo;

    ArrayList<Uri> list;

    String strStoreCode, strMaNhanVien, strToken, strDate, strName, strCustomerCode, strDeliveryDate, strNote, strShipment, strStoreName, strTotalWeight, strOrdeR_RELEASE_XID, strPackaged_Item_XID;
    int soThung, soThieu = 0, soHu = 0, soThua = 0, soNhietDo = 0, soThung2, soDu = 0;
    boolean soGiao = true;
    ProgressDialog progressDialog;

    public static final int MY_CAMERA_REQUEST_CODE = 2019;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chup_hinh_giao_hang);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarChupHinhGiaoHang);
        getSupportActionBar().setTitle("");
        Utilities.showBackIcon(getSupportActionBar());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        list = new ArrayList<>(6);
        strDate = Utilities.formatDate_MMddyyyy(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strToken = "Bearer " + LoginPrefer.getObject(ChupHinhGiaoHangActivity.this).access_token;
        strMaNhanVien = LoginPrefer.getObject(ChupHinhGiaoHangActivity.this).MaNhanVien;
        Bundle b = getIntent().getExtras();

        disableImgButton();


        if (b == null) {
            tvThoiGianChupHinhGiaoHang.setText("");
            tvMaCuaHangChupHinhGiaoHang.setText("");
            tvCuaHangChupHinhGiaoHang.setText("");
            tvDiaChiChupHinhGiaoHang.setText("");
        } else {
            strStoreCode = b.getString("storecode");
            strName = b.getString("storeaddress");
            strCustomerCode = b.getString("khachhang");
            strDeliveryDate = b.getString("date");
            strShipment = b.getString("shipment");
            soThung = b.getInt("carton");
            strStoreName = b.getString("storename");
            strTotalWeight = b.getString("weight");
            strOrdeR_RELEASE_XID= b.getString("orderreleasexid");
            strPackaged_Item_XID = b.getString("packageditemxid");
            soThung2 = soThung;
            tvThoiGianChupHinhGiaoHang.setText(Utilities.formatDate_ddMMyyyy(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis())));
            tvMaCuaHangChupHinhGiaoHang.setText(strStoreCode);
            tvCuaHangChupHinhGiaoHang.setText(strStoreName);
            tvDiaChiChupHinhGiaoHang.setText(strName);
            tvSLThungNhan.setText(String.valueOf(soThung));
            tvSLThungGiao.setText(String.valueOf(soThung2));
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
        }


        init();
        Dexter.withActivity(ChupHinhGiaoHangActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            Utilities.openSettings(ChupHinhGiaoHangActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        cbThieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnCongThieu.setEnabled(true);
                    btnTruThieu.setEnabled(true);

                    btnTruThieu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soThieu > 0) {
                                soThieu -= 1;
                                soThung2 += 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvThieu.setText(String.valueOf(soThieu));
                            }
                        }
                    });

                    btnCongThieu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soThung2 == 0) {
                                soThieu += 0;
                                soThung2 -= 0;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvThieu.setText(String.valueOf(soThieu));
                            } else if (soThung2 > 0) {
                                btnCongThieu.setEnabled(true);
                                soThieu += 1;
                                soThung2 -= 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvThieu.setText(String.valueOf(soThieu));
                            }

                        }
                    });
                } else {
                    btnTruThieu.setEnabled(false);
                    btnCongThieu.setEnabled(false);
                }
            }
        });

        cbHong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnCongHong.setEnabled(true);
                    btnTruHong.setEnabled(true);

                    btnTruHong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soHu > 0) {
                                soHu -= 1;
                                soThung2 += 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvHong.setText(String.valueOf(soHu));
                            }
                        }
                    });

                    btnCongHong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soThung2 == 0) {
                                soHu += 0;
                                soThung2 -= 0;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvHong.setText(String.valueOf(soHu));
                            } else if (soThung2 > 0) {
                                btnCongHong.setEnabled(true);
                                soHu += 1;
                                soThung2 -= 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvHong.setText(String.valueOf(soHu));
                            }

                        }
                    });
                } else {
                    btnTruHong.setEnabled(false);
                    btnCongHong.setEnabled(false);
                }
            }
        });

        cbDu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnCongDu.setEnabled(true);
                    btnTruDu.setEnabled(true);

                    btnTruDu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soDu > 0) {
                                soDu -= 1;
                                soThung2 += 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvDu.setText(String.valueOf(soDu));
                            }
                        }
                    });

                    btnCongDu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soThung2 == 0) {
                                soDu += 0;
                                soThung2 -= 0;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvDu.setText(String.valueOf(soDu));
                            } else if (soThung2 > 0) {
                                btnCongDu.setEnabled(true);
                                soDu += 1;
                                soThung2 -= 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvDu.setText(String.valueOf(soDu));
                            }

                        }
                    });
                } else {
                    btnTruDu.setEnabled(false);
                    btnCongDu.setEnabled(false);
                }
            }
        });

        cbNhietDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnCongNhietDo.setEnabled(true);
                    btnTruNhietDo.setEnabled(true);

                    btnTruNhietDo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soNhietDo > 0) {
                                soNhietDo -= 1;
                                soThung2 += 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvNhietDo.setText(String.valueOf(soNhietDo));
                            }
                        }
                    });

                    btnCongNhietDo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (soThung2 == 0) {
                                soNhietDo += 0;
                                soThung2 -= 0;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvNhietDo.setText(String.valueOf(soNhietDo));
                            } else if (soThung2 > 0) {
                                btnCongNhietDo.setEnabled(true);
                                soNhietDo += 1;
                                soThung2 -= 1;
                                tvSLThungGiao.setText(String.valueOf(soThung2));
                                tvNhietDo.setText(String.valueOf(soNhietDo));
                            }

                        }
                    });
                } else {
                    btnTruNhietDo.setEnabled(false);
                    btnCongNhietDo.setEnabled(false);
                }
            }
        });



    }

    private void disableImgButton() {
        btnCongThieu.setEnabled(false);
        btnCongDu.setEnabled(false);
        btnCongHong.setEnabled(false);
        btnCongNhietDo.setEnabled(false);
        btnTruThieu.setEnabled(false);
        btnTruDu.setEnabled(false);
        btnTruHong.setEnabled(false);
        btnTruNhietDo.setEnabled(false);

        tvThieu.setText(String.valueOf(soThieu));
        tvHong.setText(String.valueOf(soHu));
        tvDu.setText(String.valueOf(soDu));
        tvNhietDo.setText(String.valueOf(soNhietDo));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void openCamera(int i) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        if (i == 1) {
            image_uri1 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            image_uri = image_uri1;
        } else if (i == 2) {
            image_uri2 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            image_uri = image_uri2;
        } else if (i == 3) {
            image_uri3 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            image_uri = image_uri3;
        } else if (i == 4) {
            image_uri4 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            image_uri = image_uri4;
        } else if (i == 5) {
            image_uri5 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            image_uri = image_uri5;
        } else if (i == 6) {
            image_uri6 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            image_uri = image_uri6;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, i);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgDriver1)
    public void image1(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openCamera(1);
            }
        } else {
            openCamera(1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgDriver2)
    public void image2(View view) {
        if (list.size() == 0) {
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    openCamera(2);
                }
            } else {
                openCamera(2);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgDriver3)
    public void image3(View view) {
        if (list.size() == 0) {
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 1) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 2", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    openCamera(3);
                }
            } else {
                openCamera(3);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgDriver4)
    public void image4(View view) {
        if (list.size() == 0) {
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 1) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 2", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 2) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 3", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    openCamera(4);
                }
            } else {
                openCamera(4);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgDriver5)
    public void image5(View view) {
        if (list.size() == 0) {
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 1) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 2", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 2) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 3", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 3) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 4", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 4) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    openCamera(5);
                }
            } else {
                openCamera(5);
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.imgDriver6)
    public void image6(View view) {
        if (list.size() == 0) {
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 1) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 2", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 2) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 3", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 3) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 4", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 4) {
            Snackbar.make(view, "Vui lòng chọn ô thứ 5", Snackbar.LENGTH_LONG).show();
        } else if (list.size() == 5) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    openCamera(6);
                }
            } else {
                openCamera(6);
            }
        }

    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String path1 = image_uri1.toString();
                Utilities.xulyAnh(ChupHinhGiaoHangActivity.this, path1, imgDriver1, image_uri1);
                buttonDriver1.setVisibility(View.VISIBLE);
                list.add(image_uri1);
            } else if (requestCode == 2) {
                String path2 = image_uri2.toString();
                Utilities.xulyAnh(ChupHinhGiaoHangActivity.this, path2, imgDriver2, image_uri2);
                buttonDriver2.setVisibility(View.VISIBLE);
                list.add(image_uri2);
            } else if (requestCode == 3) {
                String path3 = image_uri3.toString();
                Utilities.xulyAnh(ChupHinhGiaoHangActivity.this, path3, imgDriver3, image_uri3);
                buttonDriver3.setVisibility(View.VISIBLE);
                list.add(image_uri3);
            } else if (requestCode == 4) {
                String path4 = image_uri4.toString();
                Utilities.xulyAnh(ChupHinhGiaoHangActivity.this, path4, imgDriver4, image_uri4);
                buttonDriver4.setVisibility(View.VISIBLE);
                list.add(image_uri4);
            } else if (requestCode == 5) {
                String path5 = image_uri5.toString();
                Utilities.xulyAnh(ChupHinhGiaoHangActivity.this, path5, imgDriver5, image_uri5);
                buttonDriver5.setVisibility(View.VISIBLE);
                list.add(image_uri5);
            } else if (requestCode == 6) {
                String path6 = image_uri6.toString();
                Utilities.xulyAnh(ChupHinhGiaoHangActivity.this, path6, imgDriver6, image_uri6);
                buttonDriver6.setVisibility(View.VISIBLE);
                list.add(image_uri6);
            }else if (requestCode == REQUEST_CHECK_SETTINGS){
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        break;
                }
            }
        }

    }


    @OnClick(R.id.buttonDriver1)
    public void closeImage1(View view) {
        buttonDriver1.setVisibility(View.INVISIBLE);
        imgDriver1.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1) {
            list.clear();
        } else if (list.size() == 2 || list.size() == 3 || list.size() == 4 || list.size() == 5 || list.size() == 6) {
            list.remove(0);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{image_uri1.toString()});
    }

    @OnClick(R.id.buttonDriver2)
    public void closeImage2(View view) {
        buttonDriver2.setVisibility(View.INVISIBLE);
        imgDriver2.setImageResource(R.drawable.classiccamerapng);
        list.remove(1);
        if (list.size() == 1) {
            list.clear();
        } else {
            list.remove(1);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{image_uri2.toString()});
    }

    @OnClick(R.id.buttonDriver3)
    public void closeImage3(View view) {
        buttonDriver3.setVisibility(View.INVISIBLE);
        imgDriver3.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1) {
            list.clear();
        } else if (list.size() == 2) {
            list.remove(1);
        } else if (list.size() == 3) {
            list.remove(2);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{image_uri3.toString()});
    }

    @OnClick(R.id.buttonDriver4)
    public void closeImage34(View view) {
        buttonDriver4.setVisibility(View.INVISIBLE);
        imgDriver4.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1) {
            list.clear();
        } else if (list.size() == 2) {
            list.remove(1);
        } else if (list.size() == 3) {
            list.remove(2);
        } else if (list.size() == 4) {
            list.remove(3);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{image_uri4.toString()});
    }


    @OnClick(R.id.buttonDriver5)
    public void closeImage5(View view) {
        buttonDriver5.setVisibility(View.INVISIBLE);
        imgDriver5.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1) {
            list.clear();
        } else if (list.size() == 2) {
            list.remove(1);
        } else if (list.size() == 3) {
            list.remove(2);
        } else if (list.size() == 4) {
            list.remove(3);
        } else if (list.size() == 5) {
            list.remove(4);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{image_uri5.toString()});
    }


    @OnClick(R.id.buttonDriver6)
    public void closeImage6(View view) {
        buttonDriver6.setVisibility(View.INVISIBLE);
        imgDriver6.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1) {
            list.clear();
        } else if (list.size() == 2) {
            list.remove(1);
        } else if (list.size() == 3) {
            list.remove(2);
        } else if (list.size() == 4) {
            list.remove(3);
        } else if (list.size() == 5) {
            list.remove(4);
        } else if (list.size() == 6) {
            list.remove(5);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{image_uri6.toString()});
    }

    @OnClick(R.id.btnSendHinhAnhGiaoHang)
    public void sendHinhAnhGiaoHang(View view) {
        if (list.size() == 0) {
            Snackbar.make(view, "Vui lòng chọn ít nhất một hình", Snackbar.LENGTH_SHORT).show();
        } else {
            progressDialog = Utilities.getProgressDialog(ChupHinhGiaoHangActivity.this, "Vui lòng chờ...");
            progressDialog.show();

            if (!WifiHelper.isConnected(ChupHinhGiaoHangActivity.this)) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(ChupHinhGiaoHangActivity.this, new NoInternet(), TAG, view);
                return;
            }

            strNote = edtNoteDriver.getText().toString();

            if (soThung2 == soThung){
                soGiao = true;
            }else {
                soGiao = false;
            }

            if (checkPermissions()) {
                final LocationManager manager = (LocationManager) ChupHinhGiaoHangActivity.this.getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Snackbar.make(view, "Vui lòng mở GPS(Định vị) để gửi!", Snackbar.LENGTH_LONG).show();
                    init();
                }else {
                    MyRetrofit.initRequest(ChupHinhGiaoHangActivity.this).updateStateStop(strToken, strStoreCode, strDeliveryDate, strCustomerCode, strShipment, Integer.parseInt(tvThieu.getText().toString()), soGiao, Integer.parseInt(tvHong.getText().toString()), Integer.parseInt(tvDu.getText().toString()), Integer.parseInt(tvNhietDo.getText().toString()), Integer.parseInt(tvSLThungGiao.getText().toString()),strTotalWeight,String.valueOf(mCurrentLocation.getLatitude()), String.valueOf(mCurrentLocation.getLongitude()),strOrdeR_RELEASE_XID, LoginPrefer.getObject(ChupHinhGiaoHangActivity.this).userName, soThung, 0).enqueue(new Callback<StateStop>() {
                        @Override
                        public void onResponse(Call<StateStop> call, Response<StateStop> response) {
                            if (response.isSuccessful() && response != null) {
                                int i = 0;
                                for (Uri u : list) {
                                    Bitmap bmScale = null;
                                    try {
//                                        bmScale = Utilities.getThumbnail(list.get(i), ChupHinhGiaoHangActivity.this);
                                        RotateBitmap rotateBitmap = new RotateBitmap();
                                        bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(),list.get(i));
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    Uri uri = Utilities.getImageUri(ChupHinhGiaoHangActivity.this, Utilities.addDateText(bmScale));

                                    uploadImageChupHinh(uri, view);
                                    i++;
                                }

                                list.clear();
                                imgDriver1.setImageResource(R.drawable.classiccamerapng);
                                imgDriver2.setImageResource(R.drawable.classiccamerapng);
                                imgDriver3.setImageResource(R.drawable.classiccamerapng);
                                imgDriver4.setImageResource(R.drawable.classiccamerapng);
                                imgDriver5.setImageResource(R.drawable.classiccamerapng);
                                imgDriver6.setImageResource(R.drawable.classiccamerapng);
                            }
                        }

                        @Override
                        public void onFailure(Call<StateStop> call, Throwable t) {
                            Utilities.dismissDialog(progressDialog);
                            RetrofitError.errorAction(ChupHinhGiaoHangActivity.this, new NoInternet(), TAG, view);
                        }
                    });
                }
            }



        }
    }


    public void uploadImageChupHinh(Uri uri, View view) {

        if (!WifiHelper.isConnected(ChupHinhGiaoHangActivity.this)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(ChupHinhGiaoHangActivity.this, new NoInternet(), TAG, view);
            return;
        }

        String path = uri.getPath();

        File originalfile = FileUtils.getFile(this, uri);

        Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

        RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

        MyRetrofit.initRequest(ChupHinhGiaoHangActivity.this).uploadImageGiaoHang(strStoreCode, strDeliveryDate, strMaNhanVien, strCustomerCode, strNote,"SC", file, strToken).enqueue(new Callback<List<ImageGiaoHang>>() {
            @Override
            public void onResponse(Call<List<ImageGiaoHang>> call, Response<List<ImageGiaoHang>> response) {
                if (response.isSuccessful() && response != null) {
                    Utilities.dismissDialog(progressDialog);

                    new GenericDialog.Builder(ChupHinhGiaoHangActivity.this)
                            .setIcon(R.drawable.completed)
                            .setTitle("Hoàn Thành!").setTitleAppearance(R.color.colorPrimaryDark, 16)
                            .setMessage("Chúc mừng bạn đã hoàn thành xong điểm " + strStoreName + "!")
                            .setCancelable(true)
                            .generate();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(ChupHinhGiaoHangActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    },2000);


                }
            }

            @Override
            public void onFailure(Call<List<ImageGiaoHang>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(ChupHinhGiaoHangActivity.this, new NoInternet(), TAG, view);
                Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Utilities.openSettings(ChupHinhGiaoHangActivity.this);
                }
            }
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


    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(ChupHinhGiaoHangActivity.this);
        mSettingsClient = LocationServices.getSettingsClient(ChupHinhGiaoHangActivity.this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
            }
        };

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(ChupHinhGiaoHangActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(ChupHinhGiaoHangActivity.this, new OnFailureListener() {
                    @SuppressLint("LongLogTag")
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
                                    rae.startResolutionForResult(ChupHinhGiaoHangActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
//                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(ChupHinhGiaoHangActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }




    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(ChupHinhGiaoHangActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
}
