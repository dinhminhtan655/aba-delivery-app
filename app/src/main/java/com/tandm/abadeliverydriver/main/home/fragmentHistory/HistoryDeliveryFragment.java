package com.tandm.abadeliverydriver.main.home.fragmentHistory;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
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
public class HistoryDeliveryFragment extends Fragment {
    private static final String TAG = "HistoryDeliveryFragment";
    View view;
    Unbinder unbinder;
    @BindView(R.id.spinCustomer)
    Spinner spinCustomer;
    @BindView(R.id.tvTongSoThung)
    TextView tvTongSoThung;
    @BindView(R.id.rvHistory)
    RecyclerView rvHis;
    @BindView(R.id.btnFromDate)
    Button btnFromDate;
    @BindView(R.id.btnToDate)
    Button btnToDate;
    @BindView(R.id.cardHis)
    CardView cardView;
    private Calendar calendar, calendar2;
    private String reportDate, reportDate2, reportDate3, reportDate4, strSpinner;
    HistoryDeliveryAdapter historyDeliveryAdapter;
    SpinnerCustomerAdapter adapter;

    public HistoryDeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_delivery, container, false);
        unbinder = ButterKnife.bind(this, view);
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar2.getTimeInMillis());
        reportDate3 = Utilities.formatDate_yyyyMMdd(reportDate);
        reportDate4 = Utilities.formatDate_yyyyMMdd(reportDate2);
        btnFromDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        btnToDate.setText(Utilities.formatDate_ddMMyyyy(reportDate2));

        getDataSpinner();
        spinCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = (Customer) adapter.getItem(i);
                strSpinner = customer.nameCustomer;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void getDataSpinner() {
        MyRetrofit.initRequest(getContext()).getCustomer("Bearer " + LoginPrefer.getObject(getContext()).access_token).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful() && response != null) {
                    adapter = new SpinnerCustomerAdapter(response.body(), getContext());
                    try {
                        spinCustomer.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }

                } else {
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });

    }

    public void getHistoryDelivery(String strFromDate, String strToDate, String UserName, String KhachHang) {

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).getHistoryDelivery("Bearer " + LoginPrefer.getObject(getContext()).access_token, strFromDate, strToDate, UserName, KhachHang).enqueue(new Callback<List<HistoryDelivery>>() {
            @Override
            public void onResponse(Call<List<HistoryDelivery>> call, Response<List<HistoryDelivery>> response) {
                if (response.isSuccessful() && response != null) {
                    if (response.body().size() == 0) {
                        cardView.setVisibility(View.GONE);
                    } else if (response.body().size() > 0) {
                        cardView.setVisibility(View.VISIBLE);
                        historyDeliveryAdapter = new HistoryDeliveryAdapter();
                        int i = 0;
                        historyDeliveryAdapter.replace(response.body());
                        rvHis.setAdapter(historyDeliveryAdapter);
                        for (HistoryDelivery his : response.body()) {
                            i += his.soThung;
                        }
                        tvTongSoThung.setText(String.valueOf(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HistoryDelivery>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });

    }


    @OnClick(R.id.btnCheckHistory)
    public void CheckHis(View view) {
        getHistoryDelivery(reportDate3, reportDate4, LoginPrefer.getObject(getContext()).userName, strSpinner);

    }


    @OnClick(R.id.btnFromDate)
    public void FromDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
                reportDate3 = Utilities.formatDate_yyyyMMdd(reportDate);
                btnFromDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @OnClick(R.id.btnToDate)
    public void ToDate(View view) {
        DatePickerDialog dialog2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar2.set(year, monthOfYear, dayOfMonth);
                reportDate2 = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar2.getTimeInMillis());
                reportDate4 = Utilities.formatDate_yyyyMMdd(reportDate2);
                btnToDate.setText(Utilities.formatDate_ddMMyyyy(reportDate2));
            }
        }, calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH));
        dialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
