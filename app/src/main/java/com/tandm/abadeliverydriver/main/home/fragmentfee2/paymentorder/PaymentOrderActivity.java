package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter.AmountOPFeeAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.OrderPayment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.MonthYearPickerDialog;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentOrderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PaymentOrderActivity";
    @BindView(R.id.btnFromDateOP)
    Button btnFromDateOP;
    @BindView(R.id.rvExpensesOP)
    RecyclerView rvExpensesOP;
    @BindView(R.id.floatingGotoFormOP)
    FloatingActionButton floatingGotoFormOP;
    @BindView(R.id.tvTongConLai)
    TextView tvTongConLai;
    @BindView(R.id.tvTongChuyen)
    TextView tvTongChuyen;
    View view;
    String[] strMonthYear;
    AmountOPFeeAdapter amountOPFeeAdapter;
    private Calendar calendar, calendar2;
    private String reportDate, reportDate2, reportDate3, reportDate4, MaNhanVien, token, strMonth, strYear;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_order);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        rvExpensesOP.setHasFixedSize(true);
        MaNhanVien = LoginPrefer.getObject(PaymentOrderActivity.this).MaNhanVien;
        token = "Bearer " + LoginPrefer.getObject(PaymentOrderActivity.this).access_token;
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar2.getTimeInMillis());
        reportDate3 = Utilities.formatDate_yyyyMMdd(reportDate);
        reportDate4 = Utilities.formatDate_yyyyMMdd(reportDate2);
        btnFromDateOP.setText(Utilities.formatDate_MMyyyy(reportDate));
        strMonthYear = reportDate3.split("/");
//        getAmountFeeOP(token, Integer.parseInt(strMonthYear[0]), Integer.parseInt(strMonthYear[1]), MaNhanVien);


        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                if (strMonth != null && strMonth != "" && strYear != null && strYear != "") {
//                    getAmountFee(token, strYear, strMonth, MaNhanVien);
                    getAmountFeeOP(token, Integer.parseInt(strYear), Integer.parseInt(strMonth), MaNhanVien);
                } else {
                    getAmountFeeOP(token, Integer.parseInt(strMonthYear[0]), Integer.parseInt(strMonthYear[1]), MaNhanVien);
                }
            }
        });
    }


    @OnClick(R.id.floatingGotoFormOP)
    public void GotoFormAdvancePayment(View view) {
        startActivity(new Intent(PaymentOrderActivity.this, FormPaymentOrderActivity.class));
    }


    public void getAmountFeeOP(String token, int year, int month, String maNhanVien) {
        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(PaymentOrderActivity.this)) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(PaymentOrderActivity.this, new NoInternet(), TAG, view);
            return;
        }
        MyRetrofit.initRequest(PaymentOrderActivity.this).GetShipmentOrderPayment(token, month, year, maNhanVien).enqueue(new Callback<List<OrderPayment>>() {
            @Override
            public void onResponse(Call<List<OrderPayment>> call, Response<List<OrderPayment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvTongChuyen.setText("Tổng Chuyến: " + response.body().size());
                    int totalConLai = 0;
                    for (int i = 0; i < response.body().size(); i++){
                        totalConLai += (response.body().get(i).amountTotal - response.body().get(i).amount);
//                        if (totalConLai > 0){
                            tvTongConLai.setText("Tổng Còn lại: "+ Utilities.formatNumber(String.valueOf(totalConLai))+" VNĐ");
//                            return ;
//                        }else {
//                            tvTongConLai.setText("Tổng Còn lại: "+ Utilities.formatNumber("0")+" VNĐ");
//                        }


                    }
                    amountOPFeeAdapter = new AmountOPFeeAdapter(new RecyclerViewItemClick<OrderPayment>() {
                        @Override
                        public void onClick(OrderPayment item, int position, int number) {
                            switch (number) {
                                case 0:
                                    Intent intent = new Intent(PaymentOrderActivity.this, DetailOPAmountActivity.class);
                                    intent.putExtra("orderpayment", item);
                                    startActivity(intent);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(OrderPayment item, int position, int number) {

                        }
                    });
                    amountOPFeeAdapter.replace(response.body());
                    rvExpensesOP.setAdapter(amountOPFeeAdapter);
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<OrderPayment>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(view, "Lỗi hệ thống: " + t.getMessage(), Snackbar.LENGTH_LONG);
            }
        });
    }


    @OnClick(R.id.btnFromDateOP)
    public void FromDate(View view) {
        MonthYearPickerDialog monthYearPickerDialog = new MonthYearPickerDialog();
        monthYearPickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFromDateOP.setText(month + "/" + year);
                strMonth = String.valueOf(month);
                strYear = String.valueOf(year);
                getAmountFeeOP(token, year, month, MaNhanVien);
//                getAmountFee(token, String.valueOf(year), String.valueOf(month), MaNhanVien);
            }
        });
        monthYearPickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PaymentOrderActivity.this, MainActivity.class);
        intent.putExtra("hihi", 1);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (strMonth != null && strMonth != "" && strYear != null && strYear != "") {
            getAmountFeeOP(token, Integer.parseInt(strYear), Integer.parseInt(strMonth), MaNhanVien);
        } else {
            getAmountFeeOP(token, Integer.parseInt(strMonthYear[0]), Integer.parseInt(strMonthYear[1]), MaNhanVien);
        }
    }
}
