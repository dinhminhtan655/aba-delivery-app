package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentfee.Fee;
import com.tandm.abadeliverydriver.main.home.fragmentfee.FeeSpinnerAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.ShowFullImageActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.FeeImageAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ImageFee;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ResultImage;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter.ShipmentOPAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.DetailOrderPayment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.ShipmentOrderPayment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.RealPathUtil;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
import com.tandm.abadeliverydriver.main.utilities.SpinOPFeeType;
import com.tandm.abadeliverydriver.main.utilities.SpinShipmentOP;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPaymentOrderActivity extends AppCompatActivity {

    private static final String TAG = "FormPaymentOrderActivit";
    private View view;
    private static final int MY_CAMERA_REQUEST_CODE = 4;
    @BindView(R.id.relaHaveImageOP)
    RelativeLayout relaHaveImageOP;
    @BindView(R.id.relaNoImageOP)
    RelativeLayout relaNoImageOP;
    @BindView(R.id.rvImageOP)
    RecyclerView rvImageOP;
    @BindView(R.id.fabCameraOP)
    FloatingActionButton fabCameraOP;
    @BindView(R.id.spinShipmentFeeOP)
    AppCompatSpinner spinShipmentFeeOP;
    @BindView(R.id.spinFeeTypeOP)
    AppCompatSpinner spinFeeTypeOP;
    @BindView(R.id.edtAmountFeeOP)
    TextInputEditText edtAmountFeeOP;
    @BindView(R.id.txtInAmountFeeOP)
    TextInputLayout txtInAmountFeeOP;
    @BindView(R.id.edtNoteFeeOP)
    TextInputEditText edtNoteFeeOP;
    @BindView(R.id.btnOP)
    Button btnOP;
    @BindView(R.id.tvTienTamUng)
    TextView tvTienTamUng;
    @BindView(R.id.tvTienDaChi)
    TextView tvTienDaChi;
    @BindView(R.id.tvTienNewTotal)
    TextView tvTienNewTotal;
    //    @BindView(R.id.incdec)
//    IncDecCircular incdec;
    @BindView(R.id.btnDown)
    Button btnDown;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.btnUp)
    Button btnUp;
    private Uri img_uri;
    FeeSpinnerAdapter adapter;
    FeeImageAdapter feeImageAdapter;
    ShipmentOPAdapter shipmentOPAdapter;
    String token, maNhanVien, strSpinnerFee, strSpinnerFee2, strSpinOPAmountTamUng, strSpinOPAmount, strAmount, strRegion, strDepartment, strSpinATMShipmentID, strSpinPowerUnit, fullName, strSpinCustomer, strCity, strStartTime;
    List<ImageFee> list;
    boolean a = false;
    boolean b = false;
    int bUp = 1;
    int total;
    List<MultipartBody.Part> partList;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_payment_order);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        token = "Bearer " + LoginPrefer.getObject(FormPaymentOrderActivity.this).access_token;
        strRegion = LoginPrefer.getObject(FormPaymentOrderActivity.this).Region;
        maNhanVien = LoginPrefer.getObject(FormPaymentOrderActivity.this).MaNhanVien;
        fullName = LoginPrefer.getObject(FormPaymentOrderActivity.this).fullName;
        if (ActivityCompat.checkSelfPermission(FormPaymentOrderActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(FormPaymentOrderActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(FormPaymentOrderActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            }
        }


        if (strRegion != null) {
            strDepartment = strRegion.equals("MN") ? "2001-TRUCKING" : "2002-TRUCKING";
        }
        list = new ArrayList<>();
        getShipmentOP();
        getDataSpinnerFee();


//        edtAmountFeeOP.addTextChangedListener(new NumberTextWatcher(edtAmountFeeOP));

        edtAmountFeeOP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (strSpinOPAmount != null) {
                    if (s.length() > 0) {
                        total = Integer.parseInt(strSpinOPAmount) + (Integer.parseInt(edtAmountFeeOP.getText().toString()) * bUp);
                        tvTienDaChi.setText("Tiền đã đề nghị (b): " + Utilities.formatNumber(total + "") + " VNĐ");
                        String text1 = tvTienDaChi.getText().toString().replace(",", "");
                        String text2 = text1.replace(" VNĐ", "");
                        String text3 = text2.replace("Tiền đã đề nghị (b): ", "");
                        int total2 = Integer.parseInt(strSpinOPAmountTamUng) - (Integer.parseInt(text3));
                        tvTienNewTotal.setText("Còn lại (a)-(b): " + Utilities.formatNumber(total2 + "") + " VNĐ");
                    } else {
//                    edtAmountFeeOP.setText("");
                        total = Integer.parseInt(strSpinOPAmount) + (Integer.parseInt(!edtAmountFeeOP.getText().toString().equals("") ? edtAmountFeeOP.getText().toString() : "0") * bUp);
                        tvTienDaChi.setText("Tiền đã đề nghị (b): " + Utilities.formatNumber(String.valueOf(total)) + " VNĐ");
                        String text1 = tvTienDaChi.getText().toString().replace(",", "");
                        String text2 = text1.replace(" VNĐ", "");
                        String text3 = text2.replace("Tiền đã đề nghị (b): ", "");
                        int total2 = Integer.parseInt(strSpinOPAmountTamUng) - (Integer.parseInt(text3));
                        tvTienNewTotal.setText("Còn lại (a)-(b): " + Utilities.formatNumber(total2 + "") + " VNĐ");
                    }
                }

            }
        });

        fabCameraOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        btnOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                partList = new ArrayList<>();

                if (strSpinOPAmount != null) {
                    strAmount = edtAmountFeeOP.getText().toString();

                    if (list.size() == 0 && strSpinnerFee.equals("FUEL") || list.size() == 0 && strSpinnerFee.equals("TOLL")
                            || list.size() == 0 && strSpinnerFee.equals("INSPECTION") || list.size() == 0 && strSpinnerFee.equals("TIRE REPAIR FEE")) {
                        Snackbar.make(view, "Vui lòng chụp ít nhất một hình đối với PHÍ NHIÊN LIỆU, PHÍ CẦU ĐƯỜNG,PHÍ KIỂM TRA,PHÍ VÁ VỎ, SỬA XE", Snackbar.LENGTH_LONG).show();
                    } else if (strAmount.length() == 0 || Integer.parseInt(strAmount) < 500) {
                        Snackbar.make(view, "Số tiền tạm ứng không được để trống hoặc nhỏ hơn 500", Snackbar.LENGTH_LONG).show();
                    } else if (edtNoteFeeOP.getText().length() == 0 && strSpinnerFee.equals("TRAFFIC FEE") || edtNoteFeeOP.getText().length() == 0 && strSpinnerFee.equals("OTHERS") || edtNoteFeeOP.getText().length() == 0 && strSpinnerFee.equals("PARKING")) {
                        Snackbar.make(view, "Vui lòng nhập ghi chú đối với phí Khác, phí Ngoài và phí gửi xe", Snackbar.LENGTH_LONG).show();
                    } else {
                        progressDialog = Utilities.getProgressDialog(FormPaymentOrderActivity.this, "Đang gửi hình nên hơi lâu. Vui lòng đợi");
                        progressDialog.show();

                        if (!WifiHelper.isConnected(FormPaymentOrderActivity.this)) {
                            Utilities.dismissDialog(progressDialog);
                            RetrofitError.errorAction(FormPaymentOrderActivity.this, new NoInternet(), TAG, view);
                            return;
                        }
                        List<DetailOrderPayment> l = new ArrayList<>();

                        for (int i = 0; i < bUp; i++) {
                            l.add(new DetailOrderPayment(strSpinATMShipmentID, strSpinPowerUnit, maNhanVien, fullName, strDepartment, strSpinCustomer, strSpinnerFee, edtNoteFeeOP.getText().toString(), Integer.parseInt(edtAmountFeeOP.getText().toString()), strCity, strStartTime));
                        }

                        MyRetrofit.initRequest(FormPaymentOrderActivity.this).SendThanhToan(token, l).enqueue(new Callback<List<DetailOrderPayment>>() {
                            @Override
                            public void onResponse(Call<List<DetailOrderPayment>> call, Response<List<DetailOrderPayment>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    if (response.body().size() > 0) {
                                        if (response.body().get(0).id > 0) {
                                            Snackbar.make(view, "Đề Nghị Thanh Toán Thành Công!", Snackbar.LENGTH_LONG).show();
                                            edtAmountFeeOP.setText("");
                                            edtNoteFeeOP.setText("");
//                                            getShipmentOP();
                                            a = true;

                                            if (list.size() > 0) {

                                                for (ImageFee l : list) {

                                                    Bitmap bmScale = null;
                                                    try {
                                                        RotateBitmap rotateBitmap = new RotateBitmap();
                                                        bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(), l.getUri());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Uri uri = Utilities.getImageUri(FormPaymentOrderActivity.this, Utilities.addDateText(bmScale));

                                                    String path = RealPathUtil.getRealPath(FormPaymentOrderActivity.this, uri);

                                                    File originalfile;

                                                    originalfile = FileUtils.getFile(FormPaymentOrderActivity.this, uri);


                                                    Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                                                    RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                                                    MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                                                    partList.add(file);

                                                }


                                                for (int i = 0; i < response.body().size(); i++) {
                                                    int finalI = i;

                                                    MyRetrofit.initRequest(FormPaymentOrderActivity.this).uploadImageFee3(token, response.body().get(i).id, response.body().get(i).atmShipmentID, response.body().get(i).advancePaymentType, partList).enqueue(new Callback<List<ResultImage>>() {
                                                        @Override
                                                        public void onResponse(Call<List<ResultImage>> call, Response<List<ResultImage>> response) {
                                                            if (response.isSuccessful() && response.body() != null) {

                                                                if (finalI == response.body().size() - 1) {
                                                                    Utilities.thongBaoDialog(FormPaymentOrderActivity.this, "Gửi Phí và ảnh thành công");
                                                                    list.clear();
                                                                    feeImageAdapter.notifyDataSetChanged();
                                                                }

                                                                if (list.size() == 0) {
                                                                    relaNoImageOP.setVisibility(View.VISIBLE);
                                                                }

                                                                Utilities.dismissDialog(progressDialog);

                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<ResultImage>> call, Throwable t) {
                                                            Utilities.dismissDialog(progressDialog);
                                                            Snackbar.make(view, "Gửi ảnh thất bại!", Snackbar.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    try {
                                                        Thread.sleep(3000);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }


                                            } else {
                                                Utilities.dismissDialog(progressDialog);
                                            }


                                        } else {
                                            Utilities.dismissDialog(progressDialog);
                                            Snackbar.make(view, "Đề Nghị Thanh Toán Thất Bại!", Snackbar.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Utilities.dismissDialog(progressDialog);
                                        Snackbar.make(view, "Đề Nghị Thanh Toán Thất Bại!", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Utilities.dismissDialog(progressDialog);
                                    Snackbar.make(view, "Đề Nghị Thanh Toán Thất Bại!", Snackbar.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<DetailOrderPayment>> call, Throwable t) {
                                Utilities.dismissDialog(progressDialog);
                                Snackbar.make(view, "Đề Nghị Thanh Toán Thất bại!", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(FormPaymentOrderActivity.this, "Không có mã chuyến! Không hợp lệ", Toast.LENGTH_SHORT).show();
                }

            }
        });


        edtNumber.setText("1");

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bUp = Integer.parseInt(edtNumber.getText().toString());

                if (bUp > 1 || bUp < 4) {
                    btnDown.setEnabled(true);
                }

                if (bUp == 4) {
                    btnUp.setEnabled(false);
                    btnDown.setEnabled(true);
                } else {

                    bUp++;
                    total = Integer.parseInt(strSpinOPAmount) + (Integer.parseInt(!edtAmountFeeOP.getText().toString().equals("") ? edtAmountFeeOP.getText().toString() : "0") * bUp);
                    tvTienDaChi.setText("Tiền đã đề nghị (b): " + Utilities.formatNumber(String.valueOf(total)) + " VNĐ");
                    String text1 = tvTienDaChi.getText().toString().replace(",", "");
                    String text2 = text1.replace(" VNĐ", "");
                    String text3 = text2.replace("Tiền đã đề nghị (b): ", "");
                    int total2 = Integer.parseInt(strSpinOPAmountTamUng) - (Integer.parseInt(text3));
                    tvTienNewTotal.setText("Còn lại (a)-(b): " + Utilities.formatNumber(total2 + "") + " VNĐ");
                    edtNumber.setText(String.valueOf(bUp));
                    btnUp.setEnabled(true);
                }
            }
        });


        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bUp = Integer.parseInt(edtNumber.getText().toString());
                if (bUp == 1) {
                    btnDown.setEnabled(false);
                    btnUp.setEnabled(true);
                } else if (bUp > 1 || bUp < 4) {
                    btnDown.setEnabled(true);
                    btnUp.setEnabled(true);
                    bUp--;
                    total = Integer.parseInt(strSpinOPAmount) + (Integer.parseInt(!edtAmountFeeOP.getText().toString().equals("") ? edtAmountFeeOP.getText().toString() : "0") * bUp);
                    tvTienDaChi.setText("Tiền đã đề nghị (b): " + Utilities.formatNumber(String.valueOf(total)) + " VNĐ");
                    String text1 = tvTienDaChi.getText().toString().replace(",", "");
                    String text2 = text1.replace(" VNĐ", "");
                    String text3 = text2.replace("Tiền đã đề nghị (b): ", "");
                    int total2 = Integer.parseInt(strSpinOPAmountTamUng) - (Integer.parseInt(text3));
                    tvTienNewTotal.setText("Còn lại (a)-(b): " + Utilities.formatNumber(total2 + "") + " VNĐ");
//                    edtAmountFeeOP.setText(String.valueOf(Integer.parseInt(!edtAmountFeeOP.getText().toString().equals("") ? edtAmountFeeOP.getText().toString() : "0") / bUp));
                    edtNumber.setText(String.valueOf(bUp));
                }
            }
        });

        edtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Integer.parseInt(edtNumber.getText().toString()) == 1) {
                    btnDown.setEnabled(false);
                    btnUp.setEnabled(true);
                }
                if (Integer.parseInt(edtNumber.getText().toString()) == 4) {
                    btnDown.setEnabled(true);
                    btnUp.setEnabled(false);
                }
            }
        });


    }

    private void selectImage() {
        final CharSequence[] options = {"Chụp hình", "Chọn ảnh từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormPaymentOrderActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chụp hình")) {
                    openCamera(5678);
                } else if (options[item].equals("Chọn ảnh từ thư viện")) {
                    if (strSpinnerFee2.equals("PHÍ CẦU ĐƯỜNG")) {
                        Utilities.thongBaoDialog(FormPaymentOrderActivity.this, "Phí Cầu Đường không được chọn ảnh từ thư viện");
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                } else if (options[item].equals("Hủy")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void getDataSpinnerFee() {
        MyRetrofit.initRequest(FormPaymentOrderActivity.this).getFee(token).enqueue(new Callback<List<Fee>>() {
            @Override
            public void onResponse(Call<List<Fee>> call, Response<List<Fee>> response) {
                if (response.isSuccessful() && response != null) {
                    adapter = new FeeSpinnerAdapter(response.body(), FormPaymentOrderActivity.this);
                    spinFeeTypeOP.setAdapter(adapter);

                    spinFeeTypeOP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            SpinOPFeeType.SaveIntFeeType(FormPaymentOrderActivity.this, i);
                            Fee fee = (Fee) adapter.getItem(i);
                            strSpinnerFee = fee.iD_LoaiPhi;
                            strSpinnerFee2 = fee.tenLoaiPhi;

                            if (strSpinnerFee2.equals("PHÍ CẦU ĐƯỜNG")) {
                                btnUp.setEnabled(true);
                                btnDown.setEnabled(true);
                                btnUp.setVisibility(View.VISIBLE);
                                btnDown.setVisibility(View.VISIBLE);
                                edtNumber.setVisibility(View.VISIBLE);
                                txtInAmountFeeOP.setHint("Mệnh Giá");
                            } else {
                                edtNumber.setText("1");
                                bUp = 1;
                                btnUp.setVisibility(View.GONE);
                                btnDown.setVisibility(View.GONE);
                                edtNumber.setVisibility(View.GONE);
                                edtAmountFeeOP.setText("0");
                                txtInAmountFeeOP.setHint("Số Tiền Cần Thanh Toán");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    if (SpinOPFeeType.LoadIntFeeType(FormPaymentOrderActivity.this) >= response.body().size()) {
                        spinFeeTypeOP.setSelection(0);
                    } else {
                        spinFeeTypeOP.setSelection(SpinOPFeeType.LoadIntFeeType(FormPaymentOrderActivity.this));
                    }

                } else {
                    RetrofitError.errorAction(FormPaymentOrderActivity.this, new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<Fee>> call, Throwable t) {
                RetrofitError.errorAction(FormPaymentOrderActivity.this, new NoInternet(), TAG, view);
            }
        });
    }

    private void getShipmentOP() {
        MyRetrofit.initRequest(FormPaymentOrderActivity.this).GetShipmentIDOP(token, maNhanVien).enqueue(new Callback<List<ShipmentOrderPayment>>() {
            @Override
            public void onResponse(Call<List<ShipmentOrderPayment>> call, Response<List<ShipmentOrderPayment>> response) {
                if (response.isSuccessful() && response != null) {
                    shipmentOPAdapter = new ShipmentOPAdapter(response.body(), FormPaymentOrderActivity.this);
                    spinShipmentFeeOP.setAdapter(shipmentOPAdapter);

                    spinShipmentFeeOP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SpinShipmentOP.SaveIntShipmentOP(FormPaymentOrderActivity.this, position);
                            ShipmentOrderPayment shipmentOrderPayment = (ShipmentOrderPayment) shipmentOPAdapter.getItem(position);
                            strSpinATMShipmentID = shipmentOrderPayment.atmShipmentID;
                            strSpinCustomer = shipmentOrderPayment.customerCode;
                            strSpinPowerUnit = shipmentOrderPayment.powerUnit;
                            strSpinOPAmountTamUng = String.valueOf(shipmentOrderPayment.amount);
                            strSpinOPAmount = String.valueOf(shipmentOrderPayment.amountTotal);
                            strCity = shipmentOrderPayment.city;
                            strStartTime = shipmentOrderPayment.startTime;
                            tvTienTamUng.setText("Tiền đã tạm ứng (a): " + Utilities.formatNumber(strSpinOPAmountTamUng) + " VNĐ");
                            tvTienDaChi.setText("Tiền đã đề nghị (b): " + Utilities.formatNumber(strSpinOPAmount) + " VNĐ");
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    if (SpinShipmentOP.LoadIntShipmentOP(FormPaymentOrderActivity.this) >= response.body().size()) {
                        spinShipmentFeeOP.setSelection(0);
                    } else {
                        spinShipmentFeeOP.setSelection(SpinShipmentOP.LoadIntShipmentOP(FormPaymentOrderActivity.this));
                    }

                } else {
                    RetrofitError.errorAction(FormPaymentOrderActivity.this, new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentOrderPayment>> call, Throwable t) {
                RetrofitError.errorAction(FormPaymentOrderActivity.this, new NoInternet(), TAG, view);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (a) {
            Intent intent = new Intent(FormPaymentOrderActivity.this, PaymentOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        finish();
    }

    private void openCamera(int i) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        img_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
        startActivityForResult(cameraIntent, i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 5678) {
//                Uri u = Uri.parse(img_uri.toString());
//                File f = new File("" + u);

//                String path = img_uri.toString();
                String path = RealPathUtil.getRealPath(FormPaymentOrderActivity.this, img_uri);
                list.add(new ImageFee(path, img_uri));
                if (list.size() > 0) {
                    relaHaveImageOP.setVisibility(View.VISIBLE);
                    relaNoImageOP.setVisibility(View.GONE);
                } else {
                    relaHaveImageOP.setVisibility(View.GONE);
                    relaNoImageOP.setVisibility(View.VISIBLE);
                }
                if (feeImageAdapter != null) {
                    feeImageAdapter.notifyDataSetChanged();
                } else {
                    feeImageAdapter = new FeeImageAdapter(list, FormPaymentOrderActivity.this, new RecyclerViewItemClick<ImageFee>() {
                        @Override
                        public void onClick(ImageFee item, int position, int number) {
                            switch (number) {
                                case 0:
                                    list.remove(position);
                                    feeImageAdapter.notifyDataSetChanged();
                                    if (list.size() == 0) {
                                        relaNoImageOP.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case 1:
                                    Intent intent = new Intent(FormPaymentOrderActivity.this, ShowFullImageActivity.class);
                                    intent.putExtra("imagefee", item.getUri().toString());
                                    startActivity(intent);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(ImageFee item, int position, int number) {

                        }
                    });


                }

                rvImageOP.setHasFixedSize(true);
                rvImageOP.setLayoutManager(new LinearLayoutManager(FormPaymentOrderActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvImageOP.setAdapter(feeImageAdapter);
                rvImageOP.setNestedScrollingEnabled(false);


            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String pathPick = selectedImage.toString();
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                list.add(new ImageFee(pathPick, selectedImage));
                if (list.size() > 0) {
                    relaHaveImageOP.setVisibility(View.VISIBLE);
                    relaNoImageOP.setVisibility(View.GONE);
                } else {
                    relaHaveImageOP.setVisibility(View.GONE);
                    relaNoImageOP.setVisibility(View.VISIBLE);
                }

                if (feeImageAdapter != null) {
                    feeImageAdapter.notifyDataSetChanged();
                } else {
                    feeImageAdapter = new FeeImageAdapter(list, FormPaymentOrderActivity.this, new RecyclerViewItemClick<ImageFee>() {
                        @Override
                        public void onClick(ImageFee item, int position, int number) {
                            switch (number) {
                                case 0:
                                    list.remove(position);
                                    feeImageAdapter.notifyDataSetChanged();
                                    if (list.size() == 0) {
                                        relaNoImageOP.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case 1:
                                    Intent intent = new Intent(FormPaymentOrderActivity.this, ShowFullImageActivity.class);
                                    intent.putExtra("imagefee", item.getUri().toString());
                                    startActivity(intent);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(ImageFee item, int position, int number) {

                        }
                    });
                }
                rvImageOP.setHasFixedSize(true);
                rvImageOP.setLayoutManager(new LinearLayoutManager(FormPaymentOrderActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvImageOP.setAdapter(feeImageAdapter);
                rvImageOP.setNestedScrollingEnabled(false);

            }
        }
    }
}