package com.tandm.abadeliverydriver.main.splash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tandm.abadeliverydriver.BuildConfig;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.DetailOPAmountActivity;
import com.tandm.abadeliverydriver.main.login.LoginActivity;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.AnimationUtil;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @BindView(R.id.imgLogo)
    ImageView img;
    View view;
    ProgressDialog progressDialog;
    String strGrandType = "password";
    String latestVersion, deviceName;
    float versionCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        view = getWindow().getDecorView().getRootView();
        img.startAnimation(AnimationUtil.anim(getApplicationContext(), R.anim.logo_anim));
        deviceName = Build.BRAND + "/" + Utilities.getDeviceName();
        versionCode = Float.parseFloat(String.valueOf(Build.VERSION.SDK_INT));
        String versionName = BuildConfig.VERSION_NAME.replace("-DEBUG", "");
        if (!WifiHelper.isConnected(SplashActivity.this)) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            Toast.makeText(this, "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
        } else {
            MyRetrofit.initRequest(SplashActivity.this).getVersionChecker().enqueue(new Callback<Float>() {
                @Override
                public void onResponse(Call<Float> call, Response<Float> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (Float.parseFloat(versionName) < response.body()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setTitle("Cập nhật phần mềm " + response.body());
                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Dialog dialog2 = builder.create();
                                    dialog2.show();
                                }
                            }).setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tandm.abadeliverydriver"));
                                    startActivity(intent);
                                }
                            });
                            Dialog dialog = builder.create();
                            dialog.show();
                        } else if (Float.parseFloat(versionName) == response.body()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!WifiHelper.isConnected(SplashActivity.this)) {
                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                        return;
                                    } else {
                                        if (LoginPrefer.getObject(SplashActivity.this) != null) {

                                            Intent intent2 = getIntent();
                                            if (intent2.getStringExtra("atmshipmentid") != null) {
                                                Intent intent = new Intent(SplashActivity.this, DetailOPAmountActivity.class);
                                                intent.putExtra("atmshipmentid", intent2.getStringExtra("atmshipmentid"));
                                                startActivity(intent);
                                            } else {
                                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                                finish();
                                            }

                                        } else {
                                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    }

                                }
                            }, 500);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Float> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!WifiHelper.isConnected(SplashActivity.this)) {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                return;
                            } else {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    finish();
                            }

                        }
                    }, 1000);
                }
            });
        }

    }
}

