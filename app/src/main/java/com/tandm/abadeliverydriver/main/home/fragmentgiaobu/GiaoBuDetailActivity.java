package com.tandm.abadeliverydriver.main.home.fragmentgiaobu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.adapter.GiaoBuDetailAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model.GiaoBuDetail;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewUpdateGiaoBuListener;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiaoBuDetailActivity extends AppCompatActivity {
    private static final String TAG = "GiaoBuDetailActivity";
    View view;

    @BindView(R.id.tbNhanHang)
    Toolbar tbNhanHang;
    @BindView(R.id.tvThoiGianGiaoBu)
    TextView tvThoiGianGiaoBu;
    @BindView(R.id.tvMaCuaHangGiaoBu)
    TextView tvMaCuaHangGiaoBu;
    @BindView(R.id.tvCuaHangGiaoBu)
    TextView tvCuaHangGiaoBu;
    @BindView(R.id.tvDiaChiGiaoBu)
    TextView tvDiaChiGiaoBu;
    @BindView(R.id.rvItemGiaoBu)
    RecyclerView rvItemGiaoBu;
    GiaoBuDetailAdapter adapter;

    private Calendar calendar;

    ProgressDialog progressDialog;

    List<GiaoBuDetail> list;

    private String strStoreCode, strStoreName, strKhachHang, strDiaChi, reportDate, reportDate2, token, driverID, strTime, strDate, strShipmentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_bu_detail);
        ButterKnife.bind(this);
        setSupportActionBar(tbNhanHang);
        Utilities.showBackIcon(getSupportActionBar());
        view = getWindow().getDecorView().getRootView();
        token = "Bearer " + LoginPrefer.getObject(GiaoBuDetailActivity.this).access_token;
        driverID = LoginPrefer.getObject(GiaoBuDetailActivity.this).MaNhanVien;
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_ddMMMyyyyFromMili(System.currentTimeMillis()));

        Bundle b = getIntent().getExtras();
        if (b == null) {
            tvThoiGianGiaoBu.setText("");
            tvMaCuaHangGiaoBu.setText("");
            tvCuaHangGiaoBu.setText("");
            tvDiaChiGiaoBu.setText("");
        } else {
            strStoreCode = b.getString("storecode");
            strStoreName = b.getString("storename");
            strKhachHang = b.getString("khachang");
            strDiaChi = b.getString("address");
            strShipmentID = b.getString("atmshipmentid");
            strTime = b.getString("time");
            tvThoiGianGiaoBu.setText(Utilities.formatDate_ddMMyyyy(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis())));
            tvMaCuaHangGiaoBu.setText(strStoreCode);
            tvCuaHangGiaoBu.setText(strStoreName);
            tvDiaChiGiaoBu.setText(strDiaChi);
        }

        getItemStoreGiaoBu();
    }


    public void getItemStoreGiaoBu() {

        if (!WifiHelper.isConnected(GiaoBuDetailActivity.this)) {
            RetrofitError.errorAction(GiaoBuDetailActivity.this, new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(GiaoBuDetailActivity.this).getItemStoreGiaoBu(token, strTime, strStoreCode, strKhachHang,strShipmentID).enqueue(new Callback<List<GiaoBuDetail>>() {
            @Override
            public void onResponse(Call<List<GiaoBuDetail>> call, Response<List<GiaoBuDetail>> response) {
                if (response.isSuccessful() && response != null) {
                    list = new ArrayList<GiaoBuDetail>();
                    list = response.body();
                    if (list.size() == 0) {
                        finish();
                    }
                    adapter = new GiaoBuDetailAdapter(new RecyclerViewUpdateGiaoBuListener() {
                        @Override
                        public void onClick(int position, String strRowID, String strItemCode, String strItemName, String strSoBich, String strActual, String strGiaoBu, int i) {
                            switch (i) {
                                case 0:
                                    final Dialog dialog = new Dialog(GiaoBuDetailActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.dialog_giao_bu);

                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.copyFrom(dialog.getWindow().getAttributes());
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                    TextView text = dialog.findViewById(R.id.tvDiaItemName);
                                    Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                    Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);
                                    TextInputEditText edtDiaSoBich = dialog.findViewById(R.id.edtDiaSoBich);
                                    TextInputEditText edtDiaSoActual = dialog.findViewById(R.id.edtDiaSoActual);
                                    TextInputEditText edtDiaGiaoBu = dialog.findViewById(R.id.edtDiaGiaoBu);
                                    text.setText(strItemName);

                                    edtDiaSoBich.setText(strSoBich);
                                    edtDiaSoActual.setText(strActual);
                                    edtDiaGiaoBu.setText(strGiaoBu);

                                    btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (edtDiaGiaoBu.length() == 0) {
                                                Toast.makeText(GiaoBuDetailActivity.this, "Số Giao Bù không được để trống", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (Integer.parseInt(strSoBich) >= Integer.parseInt(strActual) + Integer.parseInt(edtDiaGiaoBu.getText().toString()) && (Integer.parseInt(strActual) + Integer.parseInt(edtDiaGiaoBu.getText().toString()) != 0)) {
                                                    progressDialog = Utilities.getProgressDialog(GiaoBuDetailActivity.this, "Đang gửi..");
                                                    progressDialog.show();

                                                    MyRetrofit.initRequest(GiaoBuDetailActivity.this).updateItemStoreGiaoBu(token, strRowID, Integer.parseInt(edtDiaGiaoBu.getText().toString()), LoginPrefer.getObject(GiaoBuDetailActivity.this).userName).enqueue(new Callback<String>() {
                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response) {
                                                            if (response.isSuccessful() && response != null) {
                                                                if (response.message().equals("OK")) {
                                                                    Toast.makeText(GiaoBuDetailActivity.this, "Giao Bù Thành Công", Toast.LENGTH_SHORT).show();
                                                                    getItemStoreGiaoBu();
                                                                    dialog.dismiss();

                                                                } else {
                                                                    Toast.makeText(GiaoBuDetailActivity.this, "Giao Bù Thất Bại", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            Utilities.dismissDialog(progressDialog);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t) {
                                                            Utilities.dismissDialog(progressDialog);
                                                            RetrofitError.errorAction(GiaoBuDetailActivity.this, new NoInternet(), TAG, view);
                                                        }
                                                    });
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(GiaoBuDetailActivity.this);
                                                    builder.setTitle("Thông báo");
                                                    builder.setMessage("Số giao bù + số đã giao không thể lớn hơn số phải giao");
                                                    builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });

                                                    Dialog dialog1 = builder.create();
                                                    dialog1.show();
                                                }
                                            }


                                        }
                                    });

                                    dialog.show();
                                    dialog.getWindow().setAttributes(lp);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(int position) {

                        }
                    });
                    adapter.replace(response.body());
                    rvItemGiaoBu.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<GiaoBuDetail>> call, Throwable t) {
                RetrofitError.errorAction(GiaoBuDetailActivity.this, new NoInternet(), TAG, view);
            }
        });

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
}
