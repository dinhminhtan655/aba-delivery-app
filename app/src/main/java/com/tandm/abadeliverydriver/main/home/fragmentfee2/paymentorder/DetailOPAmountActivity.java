package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.AttachmentPagetAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.AttachExpenses;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.Message;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ResultImage;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter.DetailOPFeeAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.DetailOrderPayment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.OrderPayment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
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

public class DetailOPAmountActivity extends AppCompatActivity {
    private static final String TAG = "DetailOPAmountActivity";
    @BindView(R.id.rvOPAmount)
    RecyclerView rvOPAmount;
    @BindView(R.id.vpAttachmentOP)
    ViewPager vpAttachmentOP;
    @BindView(R.id.imgNoImg)
    RelativeLayout imgNoImg;
    private List<AttachExpenses> attachExpensesList;
    View view;
    DetailOPFeeAdapter adapter;
    String token, strAdvancedFeeType, strAtmShipmentId;
    OrderPayment item2;
    AttachmentPagetAdapter attachmentPagetAdapter;
    ProgressDialog progressDialog;
    int idImage = 0;
    boolean a = false;
    private Uri img_uri;

    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<DetailOrderPayment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_o_p_amount);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        token = "Bearer " + LoginPrefer.getObject(DetailOPAmountActivity.this).access_token;
        Bundle bundle = getIntent().getExtras();
        Intent intent2 = getIntent();
        item2 = (OrderPayment) bundle.getSerializable("orderpayment");
        strAtmShipmentId = intent2.getStringExtra("atmshipmentid");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvOPAmount.setLayoutManager(layoutManager);
        getDetailOP(token, strAtmShipmentId == null ? item2.atmShipmentID : strAtmShipmentId);

    }

    private void getDetailOP(String token, String atmShipmentID) {

        MyRetrofit.initRequest(DetailOPAmountActivity.this).GetDetailOP(token, atmShipmentID).enqueue(new Callback<List<DetailOrderPayment>>() {
            @Override
            public void onResponse(Call<List<DetailOrderPayment>> call, Response<List<DetailOrderPayment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new DetailOPFeeAdapter(new RecyclerViewItemClick<DetailOrderPayment>() {
                        @Override
                        public void onClick(DetailOrderPayment item, int position, int number) {
                            switch (number) {
                                case 0:
                                    getImageExpenses(token, item.id);
                                    break;
                                case 1:
                                    final Dialog dialog = new Dialog(DetailOPAmountActivity.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.dialog_edit_expenses);

                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.copyFrom(dialog.getWindow().getAttributes());
                                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                    TextInputEditText edtDiaAmount = dialog.findViewById(R.id.edtDiaAmount);
                                    TextInputEditText edtDiaNote = dialog.findViewById(R.id.edtDiaNote);
                                    Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                    Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);

                                    edtDiaAmount.setText(String.valueOf(item.amount));
                                    edtDiaNote.setText(String.valueOf(item.description));

                                    btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (edtDiaAmount.length() > 0 && Integer.parseInt(edtDiaAmount.getText().toString()) >= 500 && Integer.parseInt(edtDiaAmount.getText().toString()) <= 100000000) {
                                                MyRetrofit.initRequest(DetailOPAmountActivity.this).EditOP(token, item.id, Integer.parseInt(edtDiaAmount.getText().toString()), edtDiaNote.getText().toString(), item.advancePaymentType).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                            getDetailOP(token, strAtmShipmentId == null ? item2.atmShipmentID : strAtmShipmentId);
                                                            Snackbar.make(view, "Cập nhật thành công! ", Snackbar.LENGTH_LONG).show();
                                                            dialog.dismiss();
                                                            a = true;
                                                        } else {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailOPAmountActivity.this);
                                                            builder.setTitle("Thông báo");
                                                            builder.setMessage(response.body().state);
                                                            builder.setCancelable(true);

                                                            Dialog dialog = builder.create();
                                                            dialog.show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        Snackbar.make(view, "Thất bại! ", Snackbar.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        }
                                    });

                                    dialog.show();
                                    dialog.getWindow().setAttributes(lp);
                                    break;
                                case 2:
                                    AlertDialog.Builder b = new AlertDialog.Builder(DetailOPAmountActivity.this);
                                    b.setTitle("Thông báo").setMessage("Bạn có chắc muốn xóa phí này");
                                    b.setCancelable(false);
                                    b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MyRetrofit.initRequest(DetailOPAmountActivity.this).RemoveOP(token, item.id).enqueue(new Callback<Message>() {
                                                @Override
                                                public void onResponse(Call<Message> call, Response<Message> response) {
                                                    if (response.body().state.equals("Xóa Thành Công!")) {
                                                        Toast.makeText(DetailOPAmountActivity.this, response.body().state, Toast.LENGTH_SHORT).show();
                                                        getDetailOP(token, strAtmShipmentId == null ? item2.atmShipmentID : strAtmShipmentId);
                                                        getImageExpenses(token, 0);
                                                    } else {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailOPAmountActivity.this);
                                                        builder.setTitle("Thông báo");
                                                        builder.setMessage(response.body().state);
                                                        builder.setCancelable(true);

                                                        Dialog dialog = builder.create();
                                                        dialog.show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Message> call, Throwable t) {
                                                    Snackbar.make(view, "Thất bại! ", Snackbar.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });
                                    Dialog d = b.create();
                                    d.show();
                                    break;
                                case 3:
                                    AlertDialog.Builder b2 = new AlertDialog.Builder(DetailOPAmountActivity.this);
                                    b2.setTitle("Thông báo").setMessage("Bạn có chắc muốn xóa ảnh này");
                                    b2.setCancelable(false);
                                    b2.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (attachExpensesList != null && attachExpensesList.size() > 0) {
                                                progressDialog = Utilities.getProgressDialog(DetailOPAmountActivity.this, "Đang xóa...");
                                                progressDialog.show();

                                                Utilities.dismissDialog(progressDialog);
                                                RetrofitError.errorAction(DetailOPAmountActivity.this, new NoInternet(), TAG, view);
                                                Snackbar.make(view, "Thất bại", Snackbar.LENGTH_LONG).show();

                                                MyRetrofit.initRequest(DetailOPAmountActivity.this).DeleteAttachment(token, item.id, attachExpensesList.get(vpAttachmentOP.getCurrentItem()).attachName, item.advancePaymentType).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            if (response.body().state.equals("Xóa Thành Công!")) {
                                                                Snackbar.make(view, "Xóa thành công", Snackbar.LENGTH_LONG).show();
                                                                attachmentPagetAdapter.deletePage(vpAttachmentOP.getCurrentItem());
                                                                if (vpAttachmentOP.getAdapter().getCount() == 0) {
                                                                    vpAttachmentOP.setVisibility(View.GONE);
                                                                    imgNoImg.setVisibility(View.VISIBLE);
                                                                }
                                                            } else {
                                                                Snackbar.make(view, "Xóa thất bại", Snackbar.LENGTH_LONG).show();
                                                            }

                                                            Utilities.dismissDialog(progressDialog);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        if (!WifiHelper.isConnected(DetailOPAmountActivity.this)) {
                                                            RetrofitError.errorAction(DetailOPAmountActivity.this, new NoInternet(), TAG, view);
                                                            Utilities.dismissDialog(progressDialog);
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(DetailOPAmountActivity.this, "Không có hình để xóa", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Dialog d2 = b2.create();
                                    d2.show();
                                    break;
                                case 4:
//                                    if (item.advancePaymentType.equals("PHÍ VÁ VỎ, SỬA XE")){
//                                        if (item.technicalApproved.equals("CHỜ XỬ LÝ")){
//                                            idImage = item.id;
////                                            openCamera(4567);
//                                            selectImage();
//                                        }else {
//                                            Snackbar.make(view, "Không thể thêm ảnh phí này", Snackbar.LENGTH_LONG).show();
//                                        }
//                                    }else {
                                    strAdvancedFeeType = item.advancePaymentType;
                                    if (item.opApproved.equals("CHỜ XỬ LÝ") && !item.advancePaymentType.equals("PHÍ CẦU ĐƯỜNG")) {
                                        idImage = item.id;
//                                            openCamera(4567);
                                        selectImage();
                                    }else if (item.seApproved.equals("CHỜ XỬ LÝ") && item.advancePaymentType.equals("PHÍ CẦU ĐƯỜNG")){
                                        idImage = item.id;
//                                            openCamera(4567);
                                        selectImage();
                                    }
                                    else {
                                        Snackbar.make(view, "Không thể thêm ảnh phí này", Snackbar.LENGTH_LONG).show();
                                    }
//                                    }

                                    break;

                            }
                        }

                        @Override
                        public void onLongClick(DetailOrderPayment item, int position, int number) {

                        }
                    });

                    adapter.replace(response.body());
                    rvOPAmount.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<DetailOrderPayment>> call, Throwable t) {
                Snackbar.make(view, "Lỗi truy cập! Vui lòng thử lại", Snackbar.LENGTH_LONG);
            }
        });


    }

    private void getImageExpenses(String token, int id) {

        progressDialog = Utilities.getProgressDialog(DetailOPAmountActivity.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(DetailOPAmountActivity.this)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(DetailOPAmountActivity.this, new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(DetailOPAmountActivity.this).GetExpensesAttachment(token, id).enqueue(new Callback<List<AttachExpenses>>() {
            @Override
            public void onResponse(Call<List<AttachExpenses>> call, Response<List<AttachExpenses>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        vpAttachmentOP.setVisibility(View.VISIBLE);
                        imgNoImg.setVisibility(View.GONE);
                        attachExpensesList = new ArrayList<>();
                        attachExpensesList = response.body();
                        attachmentPagetAdapter = new AttachmentPagetAdapter(getSupportFragmentManager(), attachExpensesList);
                        vpAttachmentOP.setAdapter(attachmentPagetAdapter);

                    } else {
                        vpAttachmentOP.setVisibility(View.GONE);
                        imgNoImg.setVisibility(View.VISIBLE);
                    }

                    Utilities.dismissDialog(progressDialog);
                }
            }

            @Override
            public void onFailure(Call<List<AttachExpenses>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Tải hình ảnh thất bại! ", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (a) {
            Intent intent = new Intent(DetailOPAmountActivity.this, PaymentOrderActivity.class);
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


    private void selectImage() {
        final CharSequence[] options = {"Chụp hình", "Chọn ảnh từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailOPAmountActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chụp hình")) {
                    openCamera(4567);
                } else if (options[item].equals("Chọn ảnh từ thư viện")) {
                    if (strAdvancedFeeType.equals("PHÍ CẦU ĐƯỜNG")){
                        Utilities.thongBaoDialog(DetailOPAmountActivity.this, "Phí Cầu Đường không được chọn ảnh từ thư viện");
                    }else {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 4567) {
                Bitmap bmScale = null;
                try {
//                    bmScale = Utilities.getThumbnail(img_uri, DetailOPAmountActivity.this);
                    RotateBitmap rotateBitmap = new RotateBitmap();
                    bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(), img_uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri uri = Utilities.getImageUri(DetailOPAmountActivity.this, Utilities.addDateText(bmScale));

                String path = uri.getPath();

                File originalfile = FileUtils.getFile(DetailOPAmountActivity.this, uri);

                Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                MyRetrofit.initRequest(DetailOPAmountActivity.this).addImageOP(token, idImage, item2.atmShipmentID, file).enqueue(new Callback<List<ResultImage>>() {
                    @Override
                    public void onResponse(Call<List<ResultImage>> call, Response<List<ResultImage>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Snackbar.make(view, "Thành công!", Snackbar.LENGTH_LONG).show();
                            getImageExpenses(token, idImage);
                        } else {
                            Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResultImage>> call, Throwable t) {
                        Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();

                    }
                });
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                Bitmap bmScale = null;
                try {
//                    bmScale = Utilities.getThumbnail(img_uri, DetailOPAmountActivity.this);
                    RotateBitmap rotateBitmap = new RotateBitmap();
                    bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri uri = Utilities.getImageUri(DetailOPAmountActivity.this, Utilities.addDateText(bmScale));

                String path = uri.getPath();

                File originalfile = FileUtils.getFile(DetailOPAmountActivity.this, uri);

                Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                MyRetrofit.initRequest(DetailOPAmountActivity.this).addImageOP(token, idImage, item2.atmShipmentID, file).enqueue(new Callback<List<ResultImage>>() {
                    @Override
                    public void onResponse(Call<List<ResultImage>> call, Response<List<ResultImage>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Snackbar.make(view, "Thành công!", Snackbar.LENGTH_LONG).show();
                            getImageExpenses(token, idImage);
                        } else {
                            Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResultImage>> call, Throwable t) {
                        Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();

                    }
                });
            }
        }
    }
}