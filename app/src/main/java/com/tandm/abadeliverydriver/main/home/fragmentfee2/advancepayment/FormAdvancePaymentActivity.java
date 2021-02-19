package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.FeeImageAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.FeeTypeAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.ShipmentAdvancePaymentAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.APRExpensesParent;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.FeeType;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ImageFee;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ResultImage;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ShipmentAdvancePayment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.NumberTextWatcher;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAdvancePaymentActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 10003;
    private static final int MY_CAMERA_REQUEST_CODE = 3;
    @BindView(R.id.spinShipmentFee2)
    Spinner spinShipmentFee2;

    @BindView(R.id.spinFeeType)
    Spinner spinFeeType;

    @BindView(R.id.edtAmountFee2)
    TextInputEditText edtAmountFee2;

    @BindView(R.id.edtNoteFee2)
    TextInputEditText edtNoteFee2;

    @BindView(R.id.btnAdvancePayment)
    Button btnAdvancePayment;

    View view1;

//    @BindView(R.id.btnAdvancePaymentAndNew)
//    Button btnAdvancePaymentAndNew;

    @BindView(R.id.fabCamera)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.relaHaveImage)
    RelativeLayout relaHaveImage;

    @BindView(R.id.relaNoImage)
    RelativeLayout relaNoImage;

    @BindView(R.id.rvImage)
    RecyclerView rvImage;


    ShipmentAdvancePaymentAdapter advancePaymentAdapter;
    FeeTypeAdapter feeTypeAdapter;
    FeeImageAdapter feeImageAdapter;
    List<ImageFee> list;
    List<ShipmentAdvancePayment> shipmentAdvancePaymentList;
    int i = 0;
    ProgressDialog progressDialog;
    List<MultipartBody.Part> partList;

    private String strToken, strBuyShipment, strOTMBuyShipment, strDriverID, strDriverName, strAmount, strDepartment, strTypeFee, strInvoiceNumber1, strInvoiceNumber2, strInvoiceNumber3, strContent, strCustomer, strPowerUnit, strRegion, strAbbreviations, strCity, strStartTime;
    private Uri img_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_advance_payment);
        ButterKnife.bind(this);
        view1 = getWindow().getDecorView().getRootView();
        strToken = "Bearer " + LoginPrefer.getObject(FormAdvancePaymentActivity.this).access_token;
        strRegion = LoginPrefer.getObject(FormAdvancePaymentActivity.this).Region;
        strDriverID = LoginPrefer.getObject(FormAdvancePaymentActivity.this).MaNhanVien;
        strDriverName = LoginPrefer.getObject(FormAdvancePaymentActivity.this).fullName;
        if (ActivityCompat.checkSelfPermission(FormAdvancePaymentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(FormAdvancePaymentActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(FormAdvancePaymentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            }
        }
        if (strRegion != null) {
            strDepartment = strRegion.equals("MN") ? "2001-TRUCKING" : "2002-TRUCKING";
            strInvoiceNumber1 = strRegion.equals("MN") ? "TMN" : "TMB";
        }
        list = new ArrayList<>();
        getShipmentAdvancePayment();
        getFeeTypeAdvancePayment();


        spinShipmentFee2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ShipmentAdvancePayment shipmentAdvancePayment = (ShipmentAdvancePayment) advancePaymentAdapter.getItem(position);
                strBuyShipment = shipmentAdvancePayment.atmShipmentID;
                strOTMBuyShipment = shipmentAdvancePayment.shipmentGID;
                strCustomer = shipmentAdvancePayment.customerCode;
                strPowerUnit = shipmentAdvancePayment.powerUnitGID;
                strCity = shipmentAdvancePayment.city;
                strStartTime = shipmentAdvancePayment.startTime;
//                strDriverName = shipmentAdvancePayment.driverName;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinFeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FeeType feeType = (FeeType) feeTypeAdapter.getItem(position);

                if (!feeType.abbreviations.equals(" ")) {
                    strBuyShipment = "";
                    strOTMBuyShipment = "";
                    strCustomer = "";
                    strPowerUnit = "";
                    strCity = "";
                    strStartTime = "";
                    spinShipmentFee2.setEnabled(false);
                    shipmentAdvancePaymentList = new ArrayList<>();
                    advancePaymentAdapter = new ShipmentAdvancePaymentAdapter(shipmentAdvancePaymentList, FormAdvancePaymentActivity.this);
                    spinShipmentFee2.setAdapter(advancePaymentAdapter);
                    strInvoiceNumber2 = "-ADV-" + Utilities.formatDateTime_yyMMddFromMili(System.currentTimeMillis()) + "-" + feeType.abbreviations;
                } else {
                    spinShipmentFee2.setEnabled(true);
                    getShipmentAdvancePayment();
                    strInvoiceNumber2 = "-ADV-" + Utilities.formatDateTime_yyMMddFromMili(System.currentTimeMillis());
                }
//                if (feeType.abbreviations == null || feeType.abbreviations.equals(" ") || feeType.abbreviations.equals("")) {
//                    strInvoiceNumber2 = "-ADV-" + Utilities.formatDateTime_yyMMddFromMili(System.currentTimeMillis());
//                } else {
//                    strInvoiceNumber2 = "-ADV-" + Utilities.formatDateTime_yyMMddFromMili(System.currentTimeMillis()) + "-" + feeType.abbreviations;
//                }
                
                strTypeFee = feeType.feeName;
                strAbbreviations = feeType.abbreviations;
                strInvoiceNumber3 = strInvoiceNumber1 + strInvoiceNumber2;
//
//                Log.e("TypeFee1", strInvoiceNumber3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtAmountFee2.addTextChangedListener(new NumberTextWatcher(edtAmountFee2));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    private void selectImage() {
        final CharSequence[] options = {"Chụp hình", "Chọn ảnh từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormAdvancePaymentActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chụp hình")) {
                    openCamera(1234);
                } else if (options[item].equals("Chọn ảnh từ thư viện")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Hủy")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void getShipmentAdvancePayment() {
        MyRetrofit.initRequest(FormAdvancePaymentActivity.this).GetShipmentAdvancePayment(strToken, strDriverID).enqueue(new Callback<List<ShipmentAdvancePayment>>() {
            @Override
            public void onResponse(Call<List<ShipmentAdvancePayment>> call, Response<List<ShipmentAdvancePayment>> response) {
                if (response.isSuccessful() && response != null) {
                    advancePaymentAdapter = new ShipmentAdvancePaymentAdapter(response.body(), FormAdvancePaymentActivity.this);
                    spinShipmentFee2.setAdapter(advancePaymentAdapter);
                } else {
                    Toast.makeText(FormAdvancePaymentActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentAdvancePayment>> call, Throwable t) {
                Toast.makeText(FormAdvancePaymentActivity.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getFeeTypeAdvancePayment() {
        MyRetrofit.initRequest(FormAdvancePaymentActivity.this).getFeeType(strToken).enqueue(new Callback<List<FeeType>>() {
            @Override
            public void onResponse(Call<List<FeeType>> call, Response<List<FeeType>> response) {
                if (response.isSuccessful() && response != null) {
                    feeTypeAdapter = new FeeTypeAdapter(response.body(), FormAdvancePaymentActivity.this);
                    spinFeeType.setAdapter(feeTypeAdapter);
                } else {
                    Toast.makeText(FormAdvancePaymentActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FeeType>> call, Throwable t) {
                Toast.makeText(FormAdvancePaymentActivity.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.btnAdvancePayment)
    public void SendTamUng(View view) {
        sendTamUngFunction(view, 0);
    }


//    @OnClick(R.id.btnAdvancePaymentAndNew)
//    public void SendTamUngAndNew(View view) {
//        sendTamUngFunction(view, 1);
//        edtAmountFee2.setText("");
//        edtNoteFee2.setText("");
//        list.clear();
//        feeImageAdapter.notifyDataSetChanged();
//        relaNoImage.setVisibility(View.VISIBLE);


//    }


    public void sendTamUngFunction(View view, int num) {
        partList = new ArrayList<>();
        strAmount = edtAmountFee2.getText().toString();
        strAmount = strAmount.replace(",", "");
        strContent = edtNoteFee2.getText().toString();

        if (list.size() == 0 && strAbbreviations.equals("K") || list.size() == 0 && strAbbreviations.equals("BT")) {
            Snackbar.make(view, "Vui lòng chụp ít nhất một hình đối với Phí Bồi Thường và Phí Khác", Snackbar.LENGTH_LONG).show();
        } else if (strAbbreviations.equals(" ") && strBuyShipment == null || strAbbreviations.equals(" ") && strBuyShipment.equals("")) {
            Snackbar.make(view, "Thất bại! Tạm Ứng Phí Đi Đường cần có mã chuyến", Snackbar.LENGTH_LONG).show();
        } else {
            if (strAmount.length() > 0 && Integer.parseInt(strAmount) >= 500 && Integer.parseInt(strAmount) <= 100000000) {
                if (!strInvoiceNumber3.contains("null")) {

                    progressDialog = Utilities.getProgressDialog(FormAdvancePaymentActivity.this, "Vui lòng đợi...");
                    progressDialog.show();

                    MyRetrofit.initRequest(FormAdvancePaymentActivity.this).SendTamUng(strToken, strBuyShipment, strOTMBuyShipment, strPowerUnit, strDriverID, strDriverName, strDepartment, strCustomer, strInvoiceNumber3, strTypeFee, strContent, strAmount, strCity, strStartTime).enqueue(new Callback<APRExpensesParent>() {
                        @Override
                        public void onResponse(Call<APRExpensesParent> call, Response<APRExpensesParent> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().aPR_Expenses.id == 0) {
                                    Snackbar.make(view, "Thất bại! Mã chuyến này đã có tạm ứng phí đi đường trước đó", Snackbar.LENGTH_LONG).show();
                                } else if (response.body().aPR_Expenses.id > 0) {
                                    Snackbar.make(view, "Thành công!", Snackbar.LENGTH_LONG).show();

                                    TriggerAP();

                                    if (list.size() > 0) {

                                        for (ImageFee l : list) {

                                            Bitmap bmScale = null;
                                            try {
//                                                bmScale = Utilities.getThumbnail(l.getUri(), FormAdvancePaymentActivity.this);
                                                RotateBitmap rotateBitmap = new RotateBitmap();
                                                bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(), l.getUri());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            Uri uri = Utilities.getImageUri(FormAdvancePaymentActivity.this, Utilities.addDateText(bmScale));

                                            String path = uri.getPath();

                                            File originalfile = FileUtils.getFile(FormAdvancePaymentActivity.this, uri);

                                            Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                                            RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                                            MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                                            partList.add(file);
                                        }


                                        MyRetrofit.initRequest(FormAdvancePaymentActivity.this).uploadImageFee3(strToken, response.body().aPR_Expenses.id, response.body().aPR_Expenses.atmShipmentID, response.body().aPR_Expenses.advancePaymentType, partList).enqueue(new Callback<List<ResultImage>>() {
                                            @Override
                                            public void onResponse(Call<List<ResultImage>> call, Response<List<ResultImage>> response) {
                                                if (response.isSuccessful() && response.body() != null) {

                                                    confirmNewOrExit(num);

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResultImage>> call, Throwable t) {
                                                Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();

                                            }
                                        });
                                    } else {

                                        confirmNewOrExit(num);

                                    }


                                }
                                Utilities.dismissDialog(progressDialog);
                            } else {
                                Snackbar.make(view, "Tạm Ứng Phí Thất bại!", Snackbar.LENGTH_LONG).show();
                                Utilities.dismissDialog(progressDialog);
                            }
                        }


                        @Override
                        public void onFailure(Call<APRExpensesParent> call, Throwable t) {
                            Utilities.dismissDialog(progressDialog);
                            Snackbar.make(view, "Thất bại! ", Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Snackbar.make(view, "Không có mã lệnh! Thất bại", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(view, "Số tiền tạm ứng không được để trống và ít nhất là 500", Snackbar.LENGTH_LONG).show();
            }

        }

    }

    private void confirmNewOrExit(int num) {
        if (num == 0) {
            Intent intent = new Intent(FormAdvancePaymentActivity.this, AdvancePaymentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (num == 1) {
            edtAmountFee2.setText("");
            edtNoteFee2.setText("");
            list.clear();
            feeImageAdapter.notifyDataSetChanged();
            relaNoImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Utilities.openSettings(FormAdvancePaymentActivity.this);
                }
            }
        }
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1234) {
                String path = img_uri.toString();
                list.add(new ImageFee(path, img_uri));
                if (list.size() > 0) {
                    relaHaveImage.setVisibility(View.VISIBLE);
                    relaNoImage.setVisibility(View.GONE);
                } else {
                    relaHaveImage.setVisibility(View.GONE);
                    relaNoImage.setVisibility(View.VISIBLE);
                }
                if (feeImageAdapter != null) {
                    feeImageAdapter.notifyDataSetChanged();
                } else {
                    feeImageAdapter = new FeeImageAdapter(list, FormAdvancePaymentActivity.this, new RecyclerViewItemClick<ImageFee>() {
                        @Override
                        public void onClick(ImageFee item, int position, int number) {
                            switch (number) {
                                case 0:
                                    list.remove(position);
                                    feeImageAdapter.notifyDataSetChanged();
                                    if (list.size() == 0) {
                                        relaNoImage.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case 1:
                                    Intent intent = new Intent(FormAdvancePaymentActivity.this, ShowFullImageActivity.class);
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
                rvImage.setHasFixedSize(true);
                rvImage.setLayoutManager(new LinearLayoutManager(FormAdvancePaymentActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvImage.setAdapter(feeImageAdapter);
                rvImage.setNestedScrollingEnabled(false);

            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String pathPick = selectedImage.toString();
                list.add(new ImageFee(pathPick, selectedImage));
                if (list.size() > 0) {
                    relaHaveImage.setVisibility(View.VISIBLE);
                    relaNoImage.setVisibility(View.GONE);
                } else {
                    relaHaveImage.setVisibility(View.GONE);
                    relaNoImage.setVisibility(View.VISIBLE);
                }

                if (feeImageAdapter != null) {
                    feeImageAdapter.notifyDataSetChanged();
                } else {
                    feeImageAdapter = new FeeImageAdapter(list, FormAdvancePaymentActivity.this, new RecyclerViewItemClick<ImageFee>() {
                        @Override
                        public void onClick(ImageFee item, int position, int number) {
                            switch (number) {
                                case 0:
                                    list.remove(position);
                                    feeImageAdapter.notifyDataSetChanged();
                                    if (list.size() == 0) {
                                        relaNoImage.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case 1:
                                    Intent intent = new Intent(FormAdvancePaymentActivity.this, ShowFullImageActivity.class);
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
                rvImage.setHasFixedSize(true);
                rvImage.setLayoutManager(new LinearLayoutManager(FormAdvancePaymentActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvImage.setAdapter(feeImageAdapter);
                rvImage.setNestedScrollingEnabled(false);

            }
        }
    }

    private void TriggerAP() {
        MyRetrofit.initRequest(FormAdvancePaymentActivity.this).TriggerAdvancedPayment(strToken, "denghitamung", LoginPrefer.getObject(FormAdvancePaymentActivity.this).fullName).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {
                    Toast.makeText(FormAdvancePaymentActivity.this, "Đã Thông báo đến OP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Snackbar.make(view1, "Thông báo đến OP thất bại! ", Snackbar.LENGTH_LONG).show();
            }
        });
    }


}
