package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter.AmountFeeAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.DateItem;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ExpensesAmount;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.GeneralItem;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ListItem;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.MonthYearPickerDialog;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvancePaymentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "AdvancePaymentActivity";

    @BindView(R.id.floatingGotoForm)
    FloatingActionButton floatingGotoForm;
    @BindView(R.id.rvExpenses)
    RecyclerView rvExpenses;
    @BindView(R.id.btnFromDate)
    Button btnFromDate;
    View view;
    String[] strMonthYear;
    private List<ExpensesAmount> expensesAmountList = new ArrayList<>();
    List<ListItem> consolidatedList = new ArrayList<>();

    private AmountFeeAdapter amountFeeAdapter;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private Calendar calendar, calendar2;
    private String reportDate, reportDate2, reportDate3, reportDate4, MaNhanVien, token, strMonth, strYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_payment);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        rvExpenses.setHasFixedSize(true);
        MaNhanVien = LoginPrefer.getObject(AdvancePaymentActivity.this).MaNhanVien;
        token = "Bearer " + LoginPrefer.getObject(AdvancePaymentActivity.this).access_token;
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar2.getTimeInMillis());
        reportDate3 = Utilities.formatDate_yyyyMMdd(reportDate);
        reportDate4 = Utilities.formatDate_yyyyMMdd(reportDate2);
        btnFromDate.setText(Utilities.formatDate_MMyyyy(reportDate));
        strMonthYear = reportDate3.split("/");
//        getAmountFee(token, strMonthYear[0], strMonthYear[1], MaNhanVien);


        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                if (strMonth != null && strMonth != "" && strYear != null && strYear != ""){
                    getAmountFee(token, strYear, strMonth, MaNhanVien);
                }else {
                    getAmountFee(token, strMonthYear[0], strMonthYear[1], MaNhanVien);
                }
            }
        });

//        Log.d("region", LoginPrefer.getObject(AdvancePaymentActivity.this).Region);

    }

    private HashMap<String, List<ExpensesAmount>> groupDataIntoHashMap(List<ExpensesAmount> expensesAmountList) throws ParseException {
        HashMap<String, List<ExpensesAmount>> groupedHashMap = new HashMap<>();

        for (ExpensesAmount expensesAmount : expensesAmountList) {
            String hashMapKey = expensesAmount.getDateExpenses();
            if (groupedHashMap.containsKey(hashMapKey)) {
                groupedHashMap.get(hashMapKey).add(expensesAmount);
            } else {
                List<ExpensesAmount> list = new ArrayList<>();
                list.add(expensesAmount);
                groupedHashMap.put(hashMapKey, list);
            }
        }
        return groupedHashMap;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
        Intent intent = new Intent(AdvancePaymentActivity.this, MainActivity.class);
        intent.putExtra("hihi", 1);
        startActivity(intent);
    }

    private void getAmountFee(String token, String year, String month, String maNhanVien) {

        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(AdvancePaymentActivity.this)) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(AdvancePaymentActivity.this, new NoInternet(), TAG, view);
            return;
        }
        
        MyRetrofit.initRequest(AdvancePaymentActivity.this).GetExpensesAmount(token, month, year, maNhanVien).enqueue(new Callback<List<ExpensesAmount>>() {
            @Override
            public void onResponse(Call<List<ExpensesAmount>> call, Response<List<ExpensesAmount>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    if (response.body().size() > 0) {
                    consolidatedList.clear();
                    expensesAmountList = response.body();

                    HashMap<String, List<ExpensesAmount>> groupedHashMap = null;
                    List<String> list= null;
                    try {
                        groupedHashMap = groupDataIntoHashMap(expensesAmountList);
                        list = new ArrayList<>(groupedHashMap.keySet());
                        Collections.sort(list, Collections.reverseOrder());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for (String date : list) {
                        DateItem dateItem = new DateItem();
                        dateItem.setDate(date);

                        consolidatedList.add(dateItem);

                        for (ExpensesAmount expensesAmount : groupedHashMap.get(date)) {
                            GeneralItem generalItem = new GeneralItem();
                            generalItem.setExpensesAmount(expensesAmount);
                            consolidatedList.add(generalItem);
                        }
                    }

                    amountFeeAdapter = new AmountFeeAdapter(AdvancePaymentActivity.this, consolidatedList, new RecyclerViewItemClick<GeneralItem>() {
                        @Override
                        public void onClick(GeneralItem item, int position, int number) {
                            Intent intent = new Intent(AdvancePaymentActivity.this, DetailExpensesAmountActivity.class);
                            intent.putExtra("expenses", item);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(GeneralItem item, int position, int number) {

                        }
                    });

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdvancePaymentActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvExpenses.setLayoutManager(linearLayoutManager);
                    rvExpenses.setAdapter(amountFeeAdapter);
//                    }
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<ExpensesAmount>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(view, "Lỗi truy cập! Vui lòng thử lại", Snackbar.LENGTH_LONG);
            }
        });
    }


    @OnClick(R.id.floatingGotoForm)
    public void GotoFormAdvancePayment(View view) {
        startActivity(new Intent(AdvancePaymentActivity.this, FormAdvancePaymentActivity.class));
    }


    @OnClick(R.id.btnFromDate)
    public void FromDate(View view) {
        MonthYearPickerDialog monthYearPickerDialog = new MonthYearPickerDialog();
        monthYearPickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFromDate.setText(month + "/" + year);
                strMonth = String.valueOf(month);
                strYear = String.valueOf(year);
                getAmountFee(token, String.valueOf(year), String.valueOf(month), MaNhanVien);
            }
        });
        monthYearPickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        if (strMonth != null && strMonth != "" && strYear != null && strYear != ""){
            getAmountFee(token, strYear, strMonth, MaNhanVien);
        }else {
            getAmountFee(token, strMonthYear[0], strMonthYear[1], MaNhanVien);
        }
    }
}
