package com.tandm.abadeliverydriver.main.home.fragmentgiaobu;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentHistory.SpinnerCustomerAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.adapter.GiaoBuAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model.GiaoBu;
import com.tandm.abadeliverydriver.main.home.fragmenthome.BikerCustomer;
import com.tandm.abadeliverydriver.main.home.fragmenthome.BikerCustomerAdapter;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewGiaoBuListener;
import com.tandm.abadeliverydriver.main.utilities.SpinCusPrefCustomer;
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
public class GiaoBuFragment extends Fragment {
    private static final String TAG = "GiaoBuFragment";
    View view;
    Unbinder unbinder;
//    @BindView(R.id.spinGiaoBu)
//    Spinner spinGiaoBu;
    @BindView(R.id.rvGiaBu)
    RecyclerView rvGiaoBu;
    @BindView(R.id.btnDate)
    Button btnDate;
    @BindView(R.id.spinIDGiaoBu)
    Spinner spinIDGiaoBu;
    private Calendar calendar;
    SpinnerCustomerAdapter adapter;
    GiaoBuAdapter giaoBuAdapter;

    BikerCustomerAdapter adapter2;
//    LocationATMIDSpinnerAdapter atmidSpinnerAdapter;
    private String reportDate, reportDate2, token, driverID, strDate, strSpinner, strCustomerCode, strShipmentID;
    private String strCustomerCode2, strCustomerName2;

    public GiaoBuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_giao_bu, container, false);
        unbinder = ButterKnife.bind(this, view);

        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        driverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));

        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);

        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//        getDataSpinner();
//        getShipmentSpinner();

        getBikerCustomer(token);

//        spinIDGiaoBu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                LocationATMID locationATMID = (LocationATMID) atmidSpinnerAdapter.getItem(i);
//                strCustomerCode = locationATMID.customerCode;
//                strShipmentID = locationATMID.atM_SHIPMENT_ID;
//                getStoreGiaoBu();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


//        spinGiaoBu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Customer customer = (Customer) adapter.getItem(i);
//                strSpinner = customer.nameCustomer;
//
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        return view;
    }


//    private void getDataSpinner() {
//        MyRetrofit.initRequest(getContext()).getCustomer(token).enqueue(new Callback<List<Customer>>() {
//            @Override
//            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
//                if (response.isSuccessful() && response != null) {
//                    adapter = new SpinnerCustomerAdapter(response.body(), getContext());
//                    try {
//                        spinGiaoBu.setAdapter(adapter);
//                    } catch (Exception e) {
//                        Log.e(TAG, e.toString());
//                    }
//
//                } else {
//                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Customer>> call, Throwable t) {
//                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//            }
//        });
//
//    }

//    private void getShipmentSpinner(){
//        MyRetrofit.initRequest(getContext()).GetAtmShipmentIDGiaoBu("Bearer " + LoginPrefer.getObject(getContext()).access_token, reportDate2).enqueue(new Callback<List<LocationATMID>>() {
//            @Override
//            public void onResponse(Call<List<LocationATMID>> call, Response<List<LocationATMID>> response) {
//                if (response.isSuccessful() && response != null) {
//                    atmidSpinnerAdapter = new LocationATMIDSpinnerAdapter(response.body(), getContext());
//                    spinIDGiaoBu.setAdapter(atmidSpinnerAdapter);
//                }else {
//                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<LocationATMID>> call, Throwable t) {
//                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//            }
//        });
//    }


    private void getBikerCustomer(String strToken) {
        MyRetrofit.initRequest(getContext()).GetBikerCustomer(strToken).enqueue(new Callback<List<BikerCustomer>>() {
            @Override
            public void onResponse(Call<List<BikerCustomer>> call, Response<List<BikerCustomer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter2 = new BikerCustomerAdapter(response.body(), getContext());
                    spinIDGiaoBu.setAdapter(adapter2);

                    spinIDGiaoBu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            SpinCusPrefCustomer.SaveIntCustomer(getContext(), position);
                            strCustomerCode2 = response.body().get(position).customerCode;
                            strCustomerName2 = response.body().get(position).customerName;
                            getStoreGiaoBu();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    if (SpinCusPrefCustomer.LoadIntCustomer(getContext()) >= response.body().size()) {
                        spinIDGiaoBu.setSelection(0);
                    } else {
                        spinIDGiaoBu.setSelection(SpinCusPrefCustomer.LoadIntCustomer(getContext()));
                    }

                } else {
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<BikerCustomer>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });
    }


    public void getStoreGiaoBu() {

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).getStoreGiaoBu(token, reportDate2, strCustomerName2).enqueue(new Callback<List<GiaoBu>>() {
            @Override
            public void onResponse(Call<List<GiaoBu>> call, Response<List<GiaoBu>> response) {
                if (response.isSuccessful() && response != null) {
                    giaoBuAdapter = new GiaoBuAdapter(new RecyclerViewGiaoBuListener() {
                        @Override
                        public void onClick(int position, String store_Code, String store_Name, String khachHang, String address,String ATMShipmentID, int i) {
                            switch (i) {
                                case 0:
                                    Intent intent = new Intent(getContext(), GiaoBuDetailActivity.class);
                                    intent.putExtra("storecode", store_Code);
                                    intent.putExtra("storename", store_Name);
                                    intent.putExtra("atmshipmentid", ATMShipmentID);
                                    intent.putExtra("khachang", khachHang);
                                    intent.putExtra("address", address);
                                    intent.putExtra("time", reportDate2);
                                    startActivity(intent);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(int position) {

                        }
                    });
                    giaoBuAdapter.replace(response.body());
                    rvGiaoBu.setAdapter(giaoBuAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<GiaoBu>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDate)
    public void chooseDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
                reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
                btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
                getBikerCustomer(token);
                getStoreGiaoBu();

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @OnClick(R.id.ivArrowRight)
    public void nextDay(View view) {
        calendar.add(Calendar.DATE, 1);
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        getBikerCustomer(token);
        getStoreGiaoBu();

    }

    @OnClick(R.id.ivArrowLeft)
    public void preDay(View view) {
        calendar.add(Calendar.DATE, -1);
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        getBikerCustomer(token);
        getStoreGiaoBu();

    }

    @Override
    public void onResume() {
        super.onResume();
        getStoreGiaoBu();
    }
}
