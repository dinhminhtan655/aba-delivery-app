package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofitWMS;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.Dock;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.OrderType;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.TimeSlot;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.VehicleType;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemNhapXuatFragment extends DialogFragment {

    private static final String TAG = "ThemNhapXuatFragment";
    View view;
    private Unbinder unbinder;
    @BindView(R.id.spinOrderType)
    SearchableSpinner spinOrderType;
    @BindView(R.id.spinTimeSlot)
    SearchableSpinner spinTimeSlot;
    @BindView(R.id.spinVehicleType)
    SearchableSpinner spinVehicleType;
    @BindView(R.id.spinDockList)
    SearchableSpinner spinDockList;
    @BindView(R.id.edtOrderDate)
    EditText edtOrderDate;
    @BindView(R.id.edtSoXe)
    EditText edtSoXe;
    @BindView(R.id.edtKhachHang)
    EditText edtKhachHang;
    @BindView(R.id.edtTotalQuantity)
    EditText edtTotalQuantity;
    @BindView(R.id.edtTotalWeights)
    EditText edtTotalWeights;
    @BindView(R.id.btnHuy)
    Button btnHuy;
    @BindView(R.id.btnDangKy)
    Button btnDangKy;

    String strPhoneNumber, strVehicleType,strAndroidID;

    List<Dock> docks = new ArrayList<>();
    List<TimeSlot> timeSlots = new ArrayList<>();
    List<VehicleType> vehicleTypes = new ArrayList<>();
    List<OrderType> orderTypes = new ArrayList<>();

    int iOrderType,iDockID, iTimeSlotID, iYear, iMonth, iDay;
    TimeSlot timeSlotSelected;
    VehicleType vehicleTypeSelected;
    Dock dockSelected;
    OrderType orderTypeSelected;

    ArrayAdapter<TimeSlot> timeSlotArrayAdapter;
    ArrayAdapter<VehicleType> vehicleTypeArrayAdapter;
    ArrayAdapter<Dock> dockArrayAdapter;
    ArrayAdapter<OrderType> orderTypeArrayAdapter;

    Pattern pattern;

    public static ThemNhapXuatFragment newInstances(String strPhoneNumber) {
        ThemNhapXuatFragment themNhapXuatFragment = new ThemNhapXuatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone", strPhoneNumber);
        themNhapXuatFragment.setArguments(bundle);
        return themNhapXuatFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_them_nhap_xuat, container, false);
        unbinder = ButterKnife.bind(this, view);
        pattern = Pattern.compile("\\d{2}[A-Z]{1}[0-9]{4,5}");
        strAndroidID = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        strPhoneNumber = getArguments().getString("phone");

        getOrderType();
        getTimeSlot();
        getVehicleType();
        getDockList(strPhoneNumber);

        return view;
    }

    private void getOrderType(){
        MyRetrofitWMS.initRequest().getOrderType().enqueue(new Callback<List<OrderType>>() {
            @Override
            public void onResponse(Call<List<OrderType>> call, Response<List<OrderType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        orderTypes = response.body();
                        orderTypeArrayAdapter = new ArrayAdapter<OrderType>(getActivity(), android.R.layout.simple_spinner_dropdown_item, orderTypes);
                        spinDockList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                orderTypeSelected = response.body().get(position);
                                iOrderType = orderTypeSelected.eDI_OrderTypeID;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        spinOrderType.setAdapter(orderTypeArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OrderType>> call, Throwable t) {
                Toast.makeText(getContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDockList(String strPhoneNumber) {

        MyRetrofitWMS.initRequest().getDockList(strPhoneNumber).enqueue(new Callback<List<Dock>>() {
            @Override
            public void onResponse(Call<List<Dock>> call, Response<List<Dock>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        docks = response.body();
                        dockArrayAdapter = new ArrayAdapter<Dock>(getActivity(), android.R.layout.simple_spinner_dropdown_item, docks);
                        spinDockList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                dockSelected = response.body().get(position);
                                iDockID = dockSelected.dockDoorID;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        spinDockList.setAdapter(dockArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Dock>> call, Throwable t) {
                Toast.makeText(getContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getVehicleType() {

        MyRetrofitWMS.initRequest().getVehicleType().enqueue(new Callback<List<VehicleType>>() {
            @Override
            public void onResponse(Call<List<VehicleType>> call, Response<List<VehicleType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        vehicleTypes = response.body();
                        vehicleTypeArrayAdapter = new ArrayAdapter<VehicleType>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vehicleTypes);
                        spinVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                vehicleTypeSelected = response.body().get(position);
                                strVehicleType = vehicleTypeSelected.vehicleType;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        spinVehicleType.setAdapter(vehicleTypeArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VehicleType>> call, Throwable t) {
                Toast.makeText(getContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTimeSlot() {

        MyRetrofitWMS.initRequest().getTimeSlotList().enqueue(new Callback<List<TimeSlot>>() {
            @Override
            public void onResponse(Call<List<TimeSlot>> call, Response<List<TimeSlot>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        timeSlots = response.body();
                        timeSlotArrayAdapter = new ArrayAdapter<TimeSlot>(getActivity(), android.R.layout.simple_spinner_dropdown_item, timeSlots);
                        spinTimeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                timeSlotSelected = response.body().get(position);
                                iTimeSlotID = timeSlotSelected.timeSlotID;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        spinTimeSlot.setAdapter(timeSlotArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TimeSlot>> call, Throwable t) {
                Toast.makeText(getContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.btnHuy)
    public void huyDialog(View view) {
        getDialog().dismiss();
    }

    @OnClick(R.id.btnDangKy)
    public void dangKyDialog(View view){
        if (edtOrderDate.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Ngày không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }else if(edtSoXe.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Số xe không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }else if(edtTotalWeights.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Khối lượng không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }else if(edtTotalQuantity.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Số lượng hàng không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }else if (!pattern.matcher(edtSoXe.getText().toString()).matches()){
            Toast.makeText(getContext(), "Số xe không đúng định dạng", Toast.LENGTH_SHORT).show();
            return;
        }

        String strFormatDate = Utilities.formatDate_yyyyMMdd4(edtOrderDate.getText().toString());

        MyRetrofitWMS.initRequest().insertNhapXuat(strPhoneNumber,strPhoneNumber,strAndroidID,strFormatDate,iTimeSlotID,edtSoXe.getText().toString(),edtKhachHang.getText().toString(),edtTotalQuantity.getText().toString(),edtTotalWeights.getText().toString(),strVehicleType,iDockID,iOrderType).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.code() == 200){
                        if (response.body().length() > 0){
                            Utilities.thongBaoDialog(getContext(),"Đăng ký thành công");
                            getDialog().dismiss();
                        }else {
                            Utilities.thongBaoDialog(getContext(),"Đăng ký thất bại vui lòng thử lại");
                        }
                    }else {
                        Utilities.thongBaoDialog(getContext(),"Lỗi hệ thống");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @OnClick(R.id.edtOrderDate)
    public void pickOrderDate(View view) {
        final Calendar c = Calendar.getInstance();
        iYear = c.get(Calendar.YEAR);
        iMonth = c.get(Calendar.MONTH);
        iDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtOrderDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, iYear, iMonth, iDay);
        datePickerDialog.show();
    }




    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}