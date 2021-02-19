package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.adapter.HistoryDriverDeliveryAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.model.HistoryDriverDelivery;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.HistoryStopDriverVCMDeliveryActivity;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewHisDriverListener;
import com.tandm.abadeliverydriver.main.utilities.MonthYearPickerDialog;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryDriverDeliveryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HistoryDriverDeliveryFragment";
    private View view;
    private Unbinder unbinder;
    @BindView(R.id.rvHistoryDriver)
    RecyclerView rvHistoryDriver;
    @BindView(R.id.btnFromDateOP)
    Button btnFromDateOP;
    @BindView(R.id.tvTotalShipment)
    TextView tvTotalShipment;

    String[] strMonthYear;
    private Calendar calendar, calendar2;
    private String reportDate, reportDate2, reportDate3, reportDate4,  strMonth, strYear,MaNhanVien;
    HistoryDriverDeliveryAdapter historyDriverDeliveryAdapter;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    public HistoryDriverDeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_driver_delivery, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvHistoryDriver.setHasFixedSize(true);
        MaNhanVien = LoginPrefer.getObject(getContext()).MaNhanVien;
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar2.getTimeInMillis());
        reportDate3 = Utilities.formatDate_yyyyMMdd(reportDate);
        reportDate4 = Utilities.formatDate_yyyyMMdd(reportDate2);
        btnFromDateOP.setText(Utilities.formatDate_MMyyyy(reportDate));
        strMonthYear = reportDate3.split("/");


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
                    getHistoryDriverDelivery(Integer.parseInt(strMonth), Integer.parseInt(strYear), MaNhanVien);
                } else {
                    getHistoryDriverDelivery(Integer.parseInt(strMonthYear[1]), Integer.parseInt(strMonthYear[0]), MaNhanVien);
                }
            }
        });


        return view;
    }


    private void getHistoryDriverDelivery(int strMonth, int strYear, String MaNhanVien) {

        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).getHistoryDriverDeliveryNewVer("Bearer " + LoginPrefer.getObject(getContext()).access_token, strMonth, strYear, MaNhanVien).enqueue(new Callback<List<HistoryDriverDelivery>>() {

            @Override
            public void onResponse(Call<List<HistoryDriverDelivery>> call, Response<List<HistoryDriverDelivery>> response) {
                if (response.isSuccessful() && response != null) {
                    if (response.body().size() == 0) {
                        rvHistoryDriver.setVisibility(View.GONE);
                        tvTotalShipment.setText("Tổng: " + 0);
                    } else if (response.body().size() > 0) {
                        rvHistoryDriver.setVisibility(View.VISIBLE);
                        historyDriverDeliveryAdapter = new HistoryDriverDeliveryAdapter(new RecyclerViewHisDriverListener() {
                            @Override
                            public void onClick(int position, String strATM_SHIPMENT_ID, String timeCompleteted, String timeStart) {
                                Toast.makeText(getContext(), "hahaah", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), HistoryStopDriverVCMDeliveryActivity.class);
                                i.putExtra("atmshipmentid", strATM_SHIPMENT_ID);
                                i.putExtra("time", timeCompleteted);
                                i.putExtra("time2", timeStart);
                                startActivity(i);
                            }

                            @Override
                            public void onLongClick() {

                            }
                        });
                        int i = 0;
                        historyDriverDeliveryAdapter.replace(response.body());
                        rvHistoryDriver.setAdapter(historyDriverDeliveryAdapter);
                        for (HistoryDriverDelivery his : response.body()) {
                            i++;
                            tvTotalShipment.setText("Tổng: " + i);
                        }

                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<HistoryDriverDelivery>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
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
                getHistoryDriverDelivery(Integer.parseInt(strMonth), Integer.parseInt(strYear), MaNhanVien);
//                getAmountFeeOP(token, year, month, MaNhanVien);
//                getAmountFee(token, String.valueOf(year), String.valueOf(month), MaNhanVien);
            }
        });
        monthYearPickerDialog.show(getFragmentManager(), "MonthYearPickerDialog");
    }





    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        if (strMonth != null && strMonth != "" && strYear != null && strYear != "") {
//                    getAmountFee(token, strYear, strMonth, MaNhanVien);
            getHistoryDriverDelivery(Integer.parseInt(strMonth), Integer.parseInt(strYear), MaNhanVien);
        } else {
            getHistoryDriverDelivery(Integer.parseInt(strMonthYear[1]), Integer.parseInt(strMonthYear[0]), MaNhanVien);
        }
    }
}
