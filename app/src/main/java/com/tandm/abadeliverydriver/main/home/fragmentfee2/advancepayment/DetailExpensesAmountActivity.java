package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.AttachmentPagetAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.AttachExpenses;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.GeneralItem;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.Message;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ResultImage;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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

public class DetailExpensesAmountActivity extends AppCompatActivity {
    private static final String TAG = "DetailExpensesAmountAct";
    private View view;
    private String token, strMieuTa, advancedPayment;
    private int id, amount, documentType;
    private Uri img_uri;
    private List<AttachExpenses> attachExpensesList;
    boolean a = false;
    private ProgressDialog progressDialog;
    private BottomSheetBehavior bottomSheetBehavior;
    @BindView(R.id.imgNoImg)
    RelativeLayout imgNoImg;
    @BindView(R.id.upBottomSheet)
    TextView upBottomSheet;
    @BindView(R.id.btnMenuPopup)
    ImageButton btnMenuPopup;
    @BindView(R.id.tvTypeFee)
    TextView tvTypeFee;
    @BindView(R.id.tvNameExpenses)
    TextView tvNameExpenses;
    @BindView(R.id.tvMaNhanVienExpenses)
    TextView tvMaNhanVienExpenses;
    @BindView(R.id.tvATMExpenses)
    TextView tvATMExpenses;
    @BindView(R.id.tvOTMExpenses)
    TextView tvOTMExpenses;
    @BindView(R.id.tvTienTamUngExpenses)
    TextView tvTienTamUngExpenses;
    @BindView(R.id.tvMaTamUngExpenses)
    TextView tvMaTamUngExpenses;
    @BindView(R.id.imgBPExpenses)
    ImageView imgBPExpenses;
    @BindView(R.id.imgKeToanExpenses)
    ImageView imgKeToanExpenses;
    @BindView(R.id.tvMaBPExpenses)
    TextView tvMaBPExpenses;
    @BindView(R.id.tvMaKhachHangExpenses)
    TextView tvMaKhachHangExpenses;
    @BindView(R.id.tvMieuTaExpenses)
    TextView tvMieuTaExpenses;
    @BindView(R.id.tvLyDoKhongDuyetExpenses)
    TextView tvLyDoKhongDuyetExpenses;
    @BindView(R.id.tvInvoiceDateExpenses)
    TextView tvInvoiceDateExpenses;
    @BindView(R.id.btnBack)
    Button btnBack;
    AttachmentPagetAdapter attachmentPagetAdapter;
    @BindView(R.id.vpAttachmentExpenses)
    ViewPager vpAttachmentExpenses;
    GeneralItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expenses_amount);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        token = "Bearer " + LoginPrefer.getObject(DetailExpensesAmountActivity.this).access_token;
        LinearLayout linearLayout = findViewById(R.id.bottom_sheet_expense);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        Bundle bundle = getIntent().getExtras();
        item = (GeneralItem) bundle.getSerializable("expenses");
        id = item.getExpensesAmount().id;
        advancedPayment = item.getExpensesAmount().advancePaymentType;
        amount = item.getExpensesAmount().amount;
        strMieuTa = item.getExpensesAmount().description;

        tvTypeFee.setText(item.getExpensesAmount().advancePaymentType);
        tvNameExpenses.setText(item.getExpensesAmount().employeeName);
        tvMaNhanVienExpenses.setText(item.getExpensesAmount().employeeID);
        tvATMExpenses.setText(item.getExpensesAmount().atmShipmentID == null ? "Không có" : item.getExpensesAmount().atmShipmentID);
        tvOTMExpenses.setText(item.getExpensesAmount().otmsHipmentID == null ? "Không có" : item.getExpensesAmount().otmsHipmentID);
        tvTienTamUngExpenses.setText(item.getExpensesAmount().getFormatCurrency());
        tvMaTamUngExpenses.setText(item.getExpensesAmount().invoiceNumber);
        imgBPExpenses.setImageResource(stateImage(item.getExpensesAmount().manager));
        imgKeToanExpenses.setImageResource(stateImage(item.getExpensesAmount().finApproved));
        tvMaBPExpenses.setText(item.getExpensesAmount().department);
        tvMaKhachHangExpenses.setText(item.getExpensesAmount().customer == null ? "Không có" : item.getExpensesAmount().customer);
        tvMieuTaExpenses.setText(item.getExpensesAmount().description == null ? "Không có" : item.getExpensesAmount().description);
        tvLyDoKhongDuyetExpenses.setText(item.getExpensesAmount().remark == null ? "Không có" : item.getExpensesAmount().remark);
        try {
            tvInvoiceDateExpenses.setText(item.getExpensesAmount().getInvoiceDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getImageExpenses(token, id);

        upBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a) {
                    Intent intent = new Intent(DetailExpensesAmountActivity.this, AdvancePaymentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }

            }
        });


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        upBottomSheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            }
                        });
                        upBottomSheet.setText("Kéo Xuống");

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        upBottomSheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                            }
                        });
                        upBottomSheet.setText("Kéo Lên");

                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        btnMenuPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailExpensesAmountActivity.this, btnMenuPopup);
                popupMenu.inflate(R.menu.popup_expense);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });

                popupMenu.show();
            }
        });

    }

    private boolean menuItemClicked(MenuItem item2) {
        switch (item2.getItemId()) {
            case R.id.menuItem_remove:

                AlertDialog.Builder b = new AlertDialog.Builder(DetailExpensesAmountActivity.this);
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
                        MyRetrofit.initRequest(DetailExpensesAmountActivity.this).RemoveExpenses(token, id, advancedPayment).enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                if (response.body().state.equals("Xóa Thành Công!")) {
                                    Toast.makeText(DetailExpensesAmountActivity.this, response.body().state, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DetailExpensesAmountActivity.this, AdvancePaymentActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailExpensesAmountActivity.this);
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
            case R.id.menuItem_edit:
                final Dialog dialog = new Dialog(DetailExpensesAmountActivity.this);
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

                edtDiaAmount.setText(String.valueOf(amount));
                edtDiaNote.setText(String.valueOf(strMieuTa));

                btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtDiaAmount.length() > 0 && Integer.parseInt(edtDiaAmount.getText().toString()) >= 1000000 && Integer.parseInt(edtDiaAmount.getText().toString()) <= 100000000) {
                            MyRetrofit.initRequest(DetailExpensesAmountActivity.this).EditExpenses(token, id, Integer.parseInt(edtDiaAmount.getText().toString()), edtDiaNote.getText().toString(), advancedPayment).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                        tvTienTamUngExpenses.setText(Utilities.formatNumber(edtDiaAmount.getText().toString()));
                                        tvMieuTaExpenses.setText(edtDiaNote.getText().toString());
                                        Snackbar.make(view, "Cập nhật thành công! ", Snackbar.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        a = true;
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailExpensesAmountActivity.this);
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
                        } else {
                            Snackbar.make(view, "Số tiền tạm ứng không được để trống và ít nhất là 1,000,000", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);

                break;
            case R.id.menuItem_delete_image:
                AlertDialog.Builder b2 = new AlertDialog.Builder(DetailExpensesAmountActivity.this);
                b2.setTitle("Thông báo").setMessage("Bạn có chắc muốn xóa phí này");
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
                            progressDialog = Utilities.getProgressDialog(DetailExpensesAmountActivity.this, "Đang xóa...");
                            progressDialog.show();


                            Utilities.dismissDialog(progressDialog);
                            RetrofitError.errorAction(DetailExpensesAmountActivity.this, new NoInternet(), TAG, view);
                            Snackbar.make(view, "Thất bại", Snackbar.LENGTH_LONG).show();

                            MyRetrofit.initRequest(DetailExpensesAmountActivity.this).DeleteAttachment(token, id, attachExpensesList.get(vpAttachmentExpenses.getCurrentItem()).attachName,advancedPayment).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().state.equals("Xóa Thành Công!")) {
                                            Snackbar.make(view, "Xóa thành công", Snackbar.LENGTH_LONG).show();
                                            attachmentPagetAdapter.deletePage(vpAttachmentExpenses.getCurrentItem());
                                            if (vpAttachmentExpenses.getAdapter().getCount() == 0) {
                                                vpAttachmentExpenses.setVisibility(View.GONE);
                                                imgNoImg.setVisibility(View.VISIBLE);
                                            }
//                                vpAttachmentExpenses.getAdapter().notifyDataSetChanged();
                                        } else {
                                            Snackbar.make(view, response.body().state, Snackbar.LENGTH_LONG).show();
                                        }

                                        Utilities.dismissDialog(progressDialog);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    if (!WifiHelper.isConnected(DetailExpensesAmountActivity.this)) {
                                        RetrofitError.errorAction(DetailExpensesAmountActivity.this, new NoInternet(), TAG, view);
                                        Utilities.dismissDialog(progressDialog);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(DetailExpensesAmountActivity.this, "Không có hình để xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Dialog d2 = b2.create();
                d2.show();


                break;

            case R.id.menuItem_add_image:
                if (item.getExpensesAmount().manager.equals("CHỜ XỬ LÝ")){
//                    openCamera(4567);
                    selectImage();
                }else {
                    Snackbar.make(view, "Không thể thêm ảnh phí này", Snackbar.LENGTH_LONG).show();
                }

                break;
            default:
                Toast.makeText(this, item2.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void selectImage() {
        final CharSequence[] options = {"Chụp hình", "Chọn ảnh từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailExpensesAmountActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chụp hình")) {
                    openCamera(4567);
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


    public int stateImage(String stateFee) {
        switch (stateFee) {
            case "CHỜ XỬ LÝ":
                return R.drawable.hourglass;
            case "DUYỆT":
                return R.drawable.tick;
            case "KHÔNG DUYỆT":
                return R.drawable.close;
        }
        return R.drawable.hourglass;
    }

    private void getImageExpenses(String token, int id) {

        progressDialog = Utilities.getProgressDialog(DetailExpensesAmountActivity.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(DetailExpensesAmountActivity.this)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(DetailExpensesAmountActivity.this, new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(DetailExpensesAmountActivity.this).GetExpensesAttachment(token, id).enqueue(new Callback<List<AttachExpenses>>() {
            @Override
            public void onResponse(Call<List<AttachExpenses>> call, Response<List<AttachExpenses>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        vpAttachmentExpenses.setVisibility(View.VISIBLE);
                        imgNoImg.setVisibility(View.GONE);
                        attachExpensesList = new ArrayList<>();
                        attachExpensesList = response.body();
                        attachmentPagetAdapter = new AttachmentPagetAdapter(getSupportFragmentManager(), attachExpensesList);
                        vpAttachmentExpenses.setAdapter(attachmentPagetAdapter);

                    } else {
                        vpAttachmentExpenses.setVisibility(View.GONE);
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
            if (requestCode == 4567) {
                Bitmap bmScale = null;
                try {
//                    bmScale = Utilities.getThumbnail(img_uri, DetailExpensesAmountActivity.this);
                    RotateBitmap rotateBitmap = new RotateBitmap();
                    bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(),img_uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri uri = Utilities.getImageUri(DetailExpensesAmountActivity.this, Utilities.addDateText(bmScale));

                String path = uri.getPath();

                File originalfile = FileUtils.getFile(DetailExpensesAmountActivity.this, uri);

                Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                MyRetrofit.initRequest(DetailExpensesAmountActivity.this).addImageFee(token, id, tvATMExpenses.getText().toString(), advancedPayment, file).enqueue(new Callback<List<ResultImage>>() {
                    @Override
                    public void onResponse(Call<List<ResultImage>> call, Response<List<ResultImage>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Snackbar.make(view, "Thành công!", Snackbar.LENGTH_LONG).show();
                            getImageExpenses(token,id);
                        }else {
                            Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResultImage>> call, Throwable t) {
                        Snackbar.make(view, "Thất bại!", Snackbar.LENGTH_LONG).show();

                    }
                });
            }else if (requestCode == 2){
                Uri selectedImage = data.getData();
                Bitmap bmScale = null;
                try {
//                    bmScale = Utilities.getThumbnail(img_uri, DetailOPAmountActivity.this);
                    RotateBitmap rotateBitmap = new RotateBitmap();
                    bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(),selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri uri = Utilities.getImageUri(DetailExpensesAmountActivity.this, Utilities.addDateText(bmScale));

                String path = uri.getPath();

                File originalfile = FileUtils.getFile(DetailExpensesAmountActivity.this, uri);

                Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                MyRetrofit.initRequest(DetailExpensesAmountActivity.this).addImageFee(token, id, tvATMExpenses.getText().toString(), advancedPayment, file).enqueue(new Callback<List<ResultImage>>() {
                    @Override
                    public void onResponse(Call<List<ResultImage>> call, Response<List<ResultImage>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Snackbar.make(view, "Thành công!", Snackbar.LENGTH_LONG).show();
                            getImageExpenses(token,id);
                        }else {
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