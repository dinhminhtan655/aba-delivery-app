package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.MyRetrofitWMS;
import com.tandm.abadeliverydriver.main.helper.GeofenceHelper;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.GeofencingBoss;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.LocationGeofencing;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.adapter.EDIAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.EDI;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDriverCustomerFragment extends Fragment {
    private static final int REQUEST_GET_DATA_FROM_SOME_ACTIVITY = 1;
    private static final String TAG = "HomeDriverCustomerFragm";
    private View view;
    private Unbinder unbinder;
    private float GEOFENCE_RADIUS = 150;
    @BindView(R.id.btnAddNhapXuat)
    FloatingActionButton btnAddNhapXuat;

    @BindView(R.id.rvNhapXuat)
    RecyclerView rvNhapXuat;
    @BindView(R.id.btnDate)
    Button btnDate;

    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    EDIAdapter ediAdapter;

    String strPhoneNumber,strOrderDate,reportDate, reportDate2,strDate, token;
    private Calendar calendar;
    private List<GeofencingBoss> listP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("ant1", "tao nè");
        view = inflater.inflate(R.layout.fragment_home_driver_customer, container, false);
        unbinder = ButterKnife.bind(this,view);
        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        listP = new ArrayList<>();

        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        strDate = Utilities.formatDate_yyyyMMdd2(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        geofencingClient = LocationServices.getGeofencingClient(getActivity());
        geofenceHelper = new GeofenceHelper(getActivity());
//        removeGeofence();
        strPhoneNumber = LoginPrefer.getObject(getContext()).userName;
        getEDI(strPhoneNumber, reportDate2);
        return view;
    }

    private void getEDI(String strPhoneNumber, String strOrderDate) {

        MyRetrofitWMS.initRequest().getEDI(strPhoneNumber,strOrderDate).enqueue(new Callback<List<EDI>>() {
            @Override
            public void onResponse(Call<List<EDI>> call, Response<List<EDI>> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().size() > 0){
                        ediAdapter = new EDIAdapter(response.body(),getContext(),rvNhapXuat,REQUEST_GET_DATA_FROM_SOME_ACTIVITY);
                        rvNhapXuat.setAdapter(ediAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EDI>> call, Throwable t) {
                Toast.makeText(getContext(), "Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @OnClick(R.id.btnAddNhapXuat)
    public void AddNhapXuat(View view){
        Toast.makeText(getContext(), "huhuhu", Toast.LENGTH_SHORT).show();
        FragmentManager fm = getFragmentManager();
        ThemNhapXuatFragment themNhapXuatFragment = ThemNhapXuatFragment.newInstances(strPhoneNumber);
        themNhapXuatFragment.show(fm,null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (CommentActivity.iOk == 1){
            getEDI(strPhoneNumber, reportDate2);
        }
    }

    private void addDepot() {

        MyRetrofit.initRequest(getContext()).getLocationGeofencing(token).enqueue(new Callback<List<LocationGeofencing>>() {
            @Override
            public void onResponse(Call<List<LocationGeofencing>> call, Response<List<LocationGeofencing>> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Log.e(TAG, "Thêm tc");
                    for (int i = 0; i < response.body().size(); i++) {
//                        Log.d("add123", response.body().get(i).type + "/" + response.body().get(i).locationName);
                        listP.add(new GeofencingBoss(response.body().get(i).type + "/" + response.body().get(i).locationName, response.body().get(i).lat, response.body().get(i).lng));
                    }

                    for (GeofencingBoss g : listP) {
                        LatLng latLng = new LatLng(Double.parseDouble(g.getLat()), Double.parseDouble(g.getLng()));
                        handleMapLongClick(g.getId(), latLng);
                    }
                } else {
                    Toast.makeText(geofenceHelper, "Thêm Geofencing thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocationGeofencing>> call, Throwable t) {
                Toast.makeText(geofenceHelper, "Không có mạng để thêm Geofencing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleMapLongClick(String Id, LatLng latLng) {
//        addGeofence(Id, latLng, GEOFENCE_RADIUS);
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
                getEDI(strPhoneNumber, reportDate2);

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
        getEDI(strPhoneNumber, reportDate2);
    }

    @OnClick(R.id.ivArrowLeft)
    public void preDay(View view) {
        calendar.add(Calendar.DATE, -1);
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        getEDI(strPhoneNumber, reportDate2);
    }


}