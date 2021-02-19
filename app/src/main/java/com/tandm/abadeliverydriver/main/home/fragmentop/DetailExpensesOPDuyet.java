package com.tandm.abadeliverydriver.main.home.fragmentop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.AttachmentPagetAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.AttachExpenses;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ExpensesAmount;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.Message;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tandm.abadeliverydriver.main.home.fragmentop.FragmentOP.DUYET;
import static com.tandm.abadeliverydriver.main.home.fragmentop.FragmentOP.KO_DUYET;

public class DetailExpensesOPDuyet extends AppCompatActivity {
    private static final String TAG = "DetailExpensesOPDuyet";
    private View view;
    private String token, strMieuTa, advancedPayment, strPosition;
    private int id, amount;
    boolean a = false;
    private List<AttachExpenses> attachExpensesList;
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
    @BindView(R.id.tvCityExpenses)
    TextView tvCityExpenses;
    @BindView(R.id.tvStartTimeExpenses)
    TextView tvStartTimeExpenses;
    @BindView(R.id.btnBack)
    Button btnBack;
    AttachmentPagetAdapter attachmentPagetAdapter;
    @BindView(R.id.vpAttachmentExpenses)
    ViewPager vpAttachmentExpenses;
    ExpensesAmount item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expenses_o_p_duyet);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        token = "Bearer " + LoginPrefer.getObject(DetailExpensesOPDuyet.this).access_token;
        LinearLayout linearLayout = findViewById(R.id.bottom_sheet_expense_op_duyet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        Bundle bundle = getIntent().getExtras();
        item = (ExpensesAmount) bundle.getSerializable("expenses");
        strPosition = LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position;
        id = item.id;
        advancedPayment = item.advancePaymentType;
        amount = item.amount;
        strMieuTa = item.description;

        tvTypeFee.setText(item.advancePaymentType);
        tvNameExpenses.setText(item.employeeName);
        tvMaNhanVienExpenses.setText(item.employeeID);
        tvATMExpenses.setText(item.atmShipmentID == null ? "Không có" : item.atmShipmentID);
        tvOTMExpenses.setText(item.otmsHipmentID == null ? "Không có" : item.otmsHipmentID);
        tvTienTamUngExpenses.setText(item.getFormatCurrency());
        tvMaTamUngExpenses.setText(item.invoiceNumber);
        imgBPExpenses.setImageResource(stateImage(item.manager));
        tvMaBPExpenses.setText(item.department);
        tvMaKhachHangExpenses.setText(item.customer == null ? "Không có" : item.customer);
        tvMieuTaExpenses.setText(item.description == null ? "Không có" : item.description);
        tvLyDoKhongDuyetExpenses.setText(item.remark == null ? "Không có" : item.remark);
        tvCityExpenses.setText(item.city == null ? "Không có" : item.city);
        tvStartTimeExpenses.setText(item.startTime == null ? "Không có" : item.startTime);
        try {
            tvInvoiceDateExpenses.setText(item.getInvoiceDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position.equals("OP") || LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position.equals("KT2")) {
            if (!item.manager.equals("CHỜ XỬ LÝ"))
                btnMenuPopup.setVisibility(View.GONE);
        }

        if (LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position.equals("OP2") || LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position.equals("KT")) {
            if (!item.opApproved.equals("CHỜ XỬ LÝ"))
                btnMenuPopup.setVisibility(View.GONE);
        }

//        if (LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position.equals("KT")) {
//            if (!item.technicalApproved.equals("CHỜ XỬ LÝ"))
//                btnMenuPopup.setVisibility(View.GONE);
//        }
//
//        if (LoginPrefer.getObject(DetailExpensesOPDuyet.this).Position.equals("KT2") ) {
//            if (!item.techManagerApproved.equals("CHỜ XỬ LÝ"))
//                btnMenuPopup.setVisibility(View.GONE);
//        }


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
                    Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
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
                PopupMenu popupMenu = new PopupMenu(DetailExpensesOPDuyet.this, btnMenuPopup);
                popupMenu.inflate(R.menu.popup_expenses_op_detail);

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
            case R.id.menuItem_duyet_detail:
                AlertDialog.Builder b = new AlertDialog.Builder(DetailExpensesOPDuyet.this);
                b.setTitle("Thông báo");
                b.setMessage("Bạn đồng ý duyệt phiếu này?");
                b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = Utilities.getProgressDialog(DetailExpensesOPDuyet.this, "Đang xử lý...");
                        progressDialog.show();
                        if (!WifiHelper.isConnected(DetailExpensesOPDuyet.this)) {
                            Utilities.dismissDialog(progressDialog);
                            RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                            return;
                        }
                        if (strPosition.equals("OP")) {
                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP(token, item.id, DUYET, "", LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).TriggerStateFromOPToUser(token, item.employeeID, item.employeeName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if (response.isSuccessful() && response.body() != null) {
                                                        if (response.body()) {
                                                            Toast.makeText(DetailExpensesOPDuyet.this, "Đẫ gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);

                                                }
                                            });
                                            Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                            a = true;
                                        }
                                    }
                                    Utilities.dismissDialog(progressDialog);
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                }
                            });
                        } else if (strPosition.equals("OP2")) {
                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP2(token, item.id, DUYET, "", LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).TriggerStateFromOPToManager(token, item.employeeName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if (response.isSuccessful() && response.body() != null) {
                                                        if (response.body()) {
                                                            Toast.makeText(DetailExpensesOPDuyet.this, "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);

                                                }
                                            });
                                            Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                            a = true;
                                        }
                                    }
                                    Utilities.dismissDialog(progressDialog);
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                }
                            });
                        } else if (strPosition.equals("KT")) {
                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP2(token, item.id, DUYET, "",LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).TriggerStateFromKTToKTMa(token, item.employeeName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if (response.isSuccessful() && response.body() != null) {
                                                        if (response.body()) {
                                                            Toast.makeText(DetailExpensesOPDuyet.this, "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {
                                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);

                                                }
                                            });
                                            Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                            a = true;
                                        }
                                    }
                                    Utilities.dismissDialog(progressDialog);
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                }
                            });
                        }else if (strPosition.equals("KT2")) {
                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP(token, item.id, DUYET, "",LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        if (response.body().state.equals("Cập Nhật Thành Công!")) {
//                                            MyRetrofit.initRequest(DetailExpensesOPDuyet.this).TriggerStateFromOPToManager(token, item.employeeName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
//                                                @Override
//                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                                    if (response.isSuccessful() && response.body() != null) {
//                                                        if (response.body()) {
//                                                            Toast.makeText(DetailExpensesOPDuyet.this, "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<Boolean> call, Throwable t) {
//                                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
//
//                                                }
//                                            });
                                            Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                            a = true;
                                        }
                                    }
                                    Utilities.dismissDialog(progressDialog);
                                }

                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    Utilities.dismissDialog(progressDialog);
                                    RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                }
                            });
                        }
                    }
                });
                Dialog d = b.create();
                d.show();
                break;
            case R.id.menuItem_khongduyet_detail:
                final Dialog dialog = new Dialog(DetailExpensesOPDuyet.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_comment_salary);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                EditText editText = dialog.findViewById(R.id.edtDiaComment);
                Button btnHuy = dialog.findViewById(R.id.btnDiaHuyComment);
                Button btnDongY = dialog.findViewById(R.id.btnDiaDongYComment);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.length() == 0) {
                            Toast.makeText(DetailExpensesOPDuyet.this, "không được để trống", Toast.LENGTH_SHORT).show();

                        } else {
                            progressDialog = Utilities.getProgressDialog(DetailExpensesOPDuyet.this, "Đang xử lý...");
                            progressDialog.show();
                            if (!WifiHelper.isConnected(DetailExpensesOPDuyet.this)) {
                                Utilities.dismissDialog(progressDialog);
                                RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                return;
                            }

                            if (strPosition.equals("OP")) {
                                MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP(token, item.id, KO_DUYET, editText.getText().toString(), LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                MyRetrofit.initRequest(DetailExpensesOPDuyet.this).TriggerStateFromOPToUser(token, item.employeeID, item.employeeName, LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName, KO_DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                    @Override
                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            if (response.body()) {
                                                                Toast.makeText(DetailExpensesOPDuyet.this, "Đẫ gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                                        RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);

                                                    }
                                                });
                                                Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                                a = true;
                                            }
                                        }
                                        Utilities.dismissDialog(progressDialog);
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                    }
                                });

                            } else if (strPosition.equals("OP2")) {
                                MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP2(token, item.id, KO_DUYET, editText.getText().toString(), LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                                a = true;
                                            }
                                        }
                                        Utilities.dismissDialog(progressDialog);
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                    }
                                });

                            } else if (strPosition.equals("KT")) {
                                MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP2(token, item.id, KO_DUYET, editText.getText().toString(),LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                                a = true;
                                            }
                                        }
                                        Utilities.dismissDialog(progressDialog);
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                    }
                                });

                            } else if (strPosition.equals("KT2")) {
                                MyRetrofit.initRequest(DetailExpensesOPDuyet.this).UpdateStateOP(token, item.id, KO_DUYET, editText.getText().toString(),LoginPrefer.getObject(DetailExpensesOPDuyet.this).fullName).enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                Intent intent = new Intent(DetailExpensesOPDuyet.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Utilities.thongBaoDialog(DetailExpensesOPDuyet.this, response.body().state);
                                                a = true;
                                            }
                                        }
                                        Utilities.dismissDialog(progressDialog);
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        Utilities.dismissDialog(progressDialog);
                                        RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
                                    }
                                });

                            }

                        }
                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);
                break;
            default:
                Toast.makeText(this, item2.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void getImageExpenses(String token, int id) {

        progressDialog = Utilities.getProgressDialog(DetailExpensesOPDuyet.this, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(DetailExpensesOPDuyet.this)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(DetailExpensesOPDuyet.this, new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(DetailExpensesOPDuyet.this).GetExpensesAttachment(token, id).enqueue(new Callback<List<AttachExpenses>>() {
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

}