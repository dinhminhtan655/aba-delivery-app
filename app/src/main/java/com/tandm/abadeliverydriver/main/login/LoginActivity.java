package com.tandm.abadeliverydriver.main.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.preference.ConnectStringPrefer;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.preference.SaveUAPPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.edtUsername)
    TextInputEditText edtUsername;
    @BindView(R.id.edtPass)
    TextInputEditText edtPass;
    @BindView(R.id.cbPass)
    CheckBox cb;
    @BindView(R.id.tvQuenPass)
    TextView tvQuenPass;
    @BindView(R.id.tvChuoiKetNoi)
    TextView tvChuoiKetNoi;
    ProgressDialog progressDialog;
    String strUsername;
    String strPass;
    String strGrandType, deviceName;
    float versionCode;

    String strHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        deviceName = Build.BRAND + "/" + Utilities.getDeviceName();
        versionCode = Float.parseFloat(String.valueOf(Build.VERSION.SDK_INT));
        if (LoginPrefer.getCheckBox(LoginActivity.this)) {
            cb.setChecked(true);
            edtUsername.setText(LoginPrefer.getUsername(LoginActivity.this));
            edtPass.setText(LoginPrefer.getPass(LoginActivity.this));
        } else {
            cb.setChecked(false);
            edtUsername.setText("");
            edtPass.setText("");
        }
        tvQuenPass.setPaintFlags(tvQuenPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvChuoiKetNoi.setPaintFlags(tvQuenPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.btnDangNhap)
    public void dangNhap(View view) {
        Log.d("bug", ConnectStringPrefer.getSSLOrNoSSL(LoginActivity.this));
        Log.d("bug", ConnectStringPrefer.getDomain(LoginActivity.this));
        strUsername = edtUsername.getText().toString();
        strPass = edtPass.getText().toString();
        strGrandType = "password";

        progressDialog = Utilities.getProgressDialog(LoginActivity.this, "Đang đăng nhập");
        progressDialog.show();

        if (!WifiHelper.isConnected(LoginActivity.this)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(LoginActivity.this, new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(LoginActivity.this).login(strUsername, strPass, strGrandType).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response != null) {
                    LoginPrefer.saveObject(response.body(), LoginActivity.this);
                    SaveUAPPrefer.saveUsername1(strUsername, LoginActivity.this);
                    SaveUAPPrefer.savePass1(strPass, LoginActivity.this);
                    if (cb.isChecked()) {
                        LoginPrefer.saveCheckBox(true, LoginActivity.this);
                        LoginPrefer.saveUsername(strUsername, LoginActivity.this);
                        LoginPrefer.savePass(strPass, LoginActivity.this);
                    } else {
                        LoginPrefer.deleteCheckBox(LoginActivity.this);
                    }
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String newToken = instanceIdResult.getToken();
                            Log.e("newToken", newToken);
                            MyRetrofit.initRequest(LoginActivity.this).updateNotiTokenUser2("Bearer " + LoginPrefer.getObject(LoginActivity.this).access_token, strUsername, newToken, deviceName, versionCode).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful() && response != null) {
                                        Snackbar.make(view, "Đăng nhập thành công", Snackbar.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });

                        }
                    });

                    Utilities.dismissDialog(progressDialog);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else if (response.code() == 400) {
                    Utilities.dismissDialog(progressDialog);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        String error = jsonObject.getString("error");
                        String error_description = jsonObject.getString("error_description");
                        Snackbar.make(view, error_description, Snackbar.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(LoginActivity.this, new NoInternet(), TAG, view);
            }
        });
    }


    @OnClick(R.id.tvQuenPass)
    public void quenPass() {
        Toast.makeText(this, "quên mật khẩu rồi", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.tvChuoiKetNoi)
    public void chuoiKetNoi() {

        Dialog dialog = Utilities.createNewDialog(LoginActivity.this, R.layout.dialog_connect_string);

        RadioButton radiHttp = dialog.findViewById(R.id.radiHttp);
        RadioButton radiHttps = dialog.findViewById(R.id.radiHttps);
        TextInputEditText edtDomain = dialog.findViewById(R.id.edtDomain);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);

        if (ConnectStringPrefer.getSSLOrNoSSL(LoginActivity.this).equals("https")) {
            radiHttps.setChecked(true);
            radiHttp.setChecked(false);
            strHttp = ConnectStringPrefer.getSSLOrNoSSL(LoginActivity.this);
        } else {
            radiHttp.setChecked(true);
            radiHttps.setChecked(false);
            strHttp = ConnectStringPrefer.getSSLOrNoSSL(LoginActivity.this);
        }

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strHttp = buttonView.getText().toString();
                }
            }
        };

        radiHttp.setOnCheckedChangeListener(onCheckedChangeListener);
        radiHttps.setOnCheckedChangeListener(onCheckedChangeListener);

        edtDomain.setText(ConnectStringPrefer.getDomain(LoginActivity.this));

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectStringPrefer.saveSSLOrNoSSL(strHttp, LoginActivity.this);
                ConnectStringPrefer.saveDomain(edtDomain.getText().toString(), LoginActivity.this);
                dialog.dismiss();
            }
        });


        dialog.show();
    }


}
