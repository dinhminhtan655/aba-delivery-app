package com.tandm.abadeliverydriver.main.home.fragmentvouchers;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.adapter.LateLicensesDriverAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.adapter.LocationATMIDSpinnerAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LateLicenses;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LocationATMID;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
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
public class VouchersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "VouchersFragment";
    private static final int PERMISSION_CODE = 30003;
    private View view;
    private Unbinder unbinder;

    LocationATMIDSpinnerAdapter atmidSpinnerAdapter;
    LateLicensesDriverAdapter lateLicensesDriverAdapter;
    @BindView(R.id.rcLateLicense)
    RecyclerView rcLateLicense;
    @BindView(R.id.floatBtnAdd)
    FloatingActionButton floatBtnAdd;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    String strDriverId, strToken, strAtmShipmentId, strCustomer, strStartTime, strDateOfFiling, strReason, strRegion, strFullName;

    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;

    public VouchersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vouchers, container, false);
        unbinder = ButterKnife.bind(this, view);
        strDriverId = LoginPrefer.getObject(getContext()).MaNhanVien;
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        strFullName = LoginPrefer.getObject(getContext()).fullName;
        strRegion = LoginPrefer.getObject(getContext()).Region;
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getLateLicenses();
            }
        });


        return view;
    }

    public void getLateLicenses() {

        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).GetLateLicensesDriver(strToken, strDriverId).enqueue(new Callback<List<LateLicenses>>() {
            @Override
            public void onResponse(Call<List<LateLicenses>> call, Response<List<LateLicenses>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        lateLicensesDriverAdapter = new LateLicensesDriverAdapter(new RecyclerViewItemClick<LateLicenses>() {
                            @Override
                            public void onClick(LateLicenses item, int position, int number) {
                                switch (number) {
                                    case 0:
                                        showEditLateLicensesDialog(item.id, item.atmShipmentID, item.customer, item.ngayChay(), item.statusManager, item.dateOfFiling, item.reason, item.noteFromManage);
                                        break;
                                    default:
                                        return;
                                }
                            }

                            @Override
                            public void onLongClick(LateLicenses item, int position, int number) {

                            }
                        });

                        lateLicensesDriverAdapter.replace(response.body());
                        rcLateLicense.setAdapter(lateLicensesDriverAdapter);

                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        try {
                            swipeRefreshLayout.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.e(TAG, e + "");
                        }
                    }
                }

                try {
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<LateLicenses>> call, Throwable t) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    private void showEditLateLicensesDialog(int id, String atmShipmentID, String customer, String startTime, String status, String dateOfFiling, String reason, String noteFromManage) {

        LayoutInflater inflater = getLayoutInflater();
        View showEditLayout = inflater.inflate(R.layout.dialog_edit_late_licenses, null);

        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setView(showEditLayout);
        b.setCancelable(false);
        AlertDialog dialog = b.create();


        TextView tvEditShipment = showEditLayout.findViewById(R.id.tvEditShipment);
        TextView tvEditCustomer = showEditLayout.findViewById(R.id.tvEditCustomer);
        TextView tvEditState = showEditLayout.findViewById(R.id.tvEditState);
        TextView tvEditStartTime = showEditLayout.findViewById(R.id.tvEditStartTime);
        TextInputEditText edtEditDateOfFilingVouchers = showEditLayout.findViewById(R.id.edtEditDateOfFilingVouchers);
        TextInputEditText edtEditNoteVouchers = showEditLayout.findViewById(R.id.edtEditNoteVouchers);
        TextView tvEditCommentFromManager = showEditLayout.findViewById(R.id.tvEditCommentFromManager);
        Button btnEditCloseVouchers = showEditLayout.findViewById(R.id.btnEditCloseVouchers);
        Button btnEditDeleteVouchers = showEditLayout.findViewById(R.id.btnEditDeleteVouchers);
        Button btnEditSendVouchers = showEditLayout.findViewById(R.id.btnEditSendVouchers);

        tvEditShipment.setText(atmShipmentID);
        tvEditCustomer.setText(customer);
        tvEditState.setText(status);
        tvEditStartTime.setText(startTime);
        edtEditDateOfFilingVouchers.setText(Utilities.formatDate_ddMMyyyy(dateOfFiling));
        edtEditNoteVouchers.setText(reason);
        tvEditCommentFromManager.setText(noteFromManage == null ? "Ghi chú của quản lý: Không có" : "Ghi chú của quản lý: " + noteFromManage);


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);


        edtEditDateOfFilingVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtEditDateOfFilingVouchers.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });


        btnEditCloseVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnEditDeleteVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = Utilities.getProgressDialog(getContext(), "Đang xóa...");
                progressDialog.show();

                if (!WifiHelper.isConnected(getActivity())) {
                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                    Utilities.dismissDialog(progressDialog);
                    return;
                }

                MyRetrofit.initRequest(getContext()).removeLateLicenses(strToken, id).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().equals('"' + "Xóa Thành Công!" + '"')) {
                                Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                Utilities.dismissDialog(progressDialog);
                                dialog.dismiss();
                                getLateLicenses();
                            } else {
                                Utilities.thongBaoDialog(getContext(), response.body());
                                Utilities.dismissDialog(progressDialog);
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Utilities.dismissDialog(progressDialog);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                    }
                });
            }
        });


        btnEditSendVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = Utilities.getProgressDialog(getContext(), "Đang cập nhật...");
                progressDialog.show();

                if (!WifiHelper.isConnected(getActivity())) {
                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                    Utilities.dismissDialog(progressDialog);
                    return;
                }

                MyRetrofit.initRequest(getContext()).editLateLicenses(strToken, id, edtEditNoteVouchers.getText().toString(), Utilities.formatDate_yyyyMMdd4(edtEditDateOfFilingVouchers.getText().toString())).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().equals('"' + "Sửa Thành Công!" + '"')) {
                                Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                Utilities.dismissDialog(progressDialog);
                                dialog.dismiss();
                                getLateLicenses();
                            } else {
                                Utilities.thongBaoDialog(getContext(), response.body());
                                Utilities.dismissDialog(progressDialog);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Utilities.dismissDialog(progressDialog);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                    }
                });

            }
        });

        dialog.show();


    }


    @OnClick(R.id.floatBtnAdd)
    public void addLateLicense() {

        LayoutInflater inflater = getLayoutInflater();
        View showLayout = inflater.inflate(R.layout.add_late_licenses_dialog, null);

        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setView(showLayout);
        b.setCancelable(false);
        AlertDialog dialog = b.create();

        DatePicker datePick = showLayout.findViewById(R.id.datePick);
        AppCompatSpinner spinVouchersATMID = showLayout.findViewById(R.id.spinVouchersATMID);
        TextInputEditText edtNoteVouchers = showLayout.findViewById(R.id.edtNoteVouchers);
        Button btnSendVouchers = showLayout.findViewById(R.id.btnSendVouchers);
        Button btnCloseVouchers = showLayout.findViewById(R.id.btnCloseVouchers);
        TextView tvNoShipment = showLayout.findViewById(R.id.tvNoShipment);

        MyRetrofit.initRequest(getContext()).getLocationATMID("Bearer " + LoginPrefer.getObject(getContext()).access_token, LoginPrefer.getObject(getContext()).MaNhanVien).enqueue(new Callback<List<LocationATMID>>() {
            @Override
            public void onResponse(Call<List<LocationATMID>> call, Response<List<LocationATMID>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        atmidSpinnerAdapter = new LocationATMIDSpinnerAdapter(response.body(), getContext());
                        spinVouchersATMID.setAdapter(atmidSpinnerAdapter);

                        spinVouchersATMID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                strAtmShipmentId = response.body().get(position).atM_SHIPMENT_ID;
                                strStartTime = response.body().get(position).startTime;
                                strCustomer = response.body().get(position).customerCode == null ? "Khách hàng ghép" : response.body().get(position).customerCode;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else {
                        tvNoShipment.setVisibility(View.VISIBLE);
                        spinVouchersATMID.setVisibility(View.GONE);
                        strAtmShipmentId = "";
                        strStartTime = "";
                        strCustomer = "";
                    }

                } else {
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                }

            }

            @Override
            public void onFailure(Call<List<LocationATMID>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        strDateOfFiling = day + "/" + (month + 1) + "/" + year;
        datePick.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                strDateOfFiling = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        datePick.setMaxDate(System.currentTimeMillis() + (86400000 * 3));


        datePick.setMinDate(System.currentTimeMillis() - 1000);


        btnCloseVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSendVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtNoteVouchers.getText().length() == 0) {
                    Toast.makeText(getContext(), "Ghi chú là yêu cầu bắt buộc. Không được để trống", Toast.LENGTH_SHORT).show();
                } else if (strAtmShipmentId.equals("") || strAtmShipmentId == null) {
                    Toast.makeText(getContext(), "Không có mã chuyến không thể gửi", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang gửi...");
                    progressDialog.show();

                    if (!WifiHelper.isConnected(getActivity())) {
                        RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                        Utilities.dismissDialog(progressDialog);
                        return;
                    }

                    MyRetrofit.initRequest(getContext()).addLateLicenses(strToken, strAtmShipmentId, strCustomer, strStartTime, strDriverId, Utilities.formatDate_yyyyMMdd4(strDateOfFiling), edtNoteVouchers.getText().toString(),strFullName ,strRegion).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().equals('"' + "Thành công" + '"')) {
                                    getLateLicenses();
                                    TriggerNotiLateLicenes();
                                    Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                    Utilities.dismissDialog(progressDialog);
                                    dialog.dismiss();
                                } else {
                                    Utilities.thongBaoDialog(getContext(), response.body());
                                    Utilities.dismissDialog(progressDialog);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Utilities.dismissDialog(progressDialog);
                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                        }
                    });
                }


            }
        });


        dialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
        getLateLicenses();
    }


    private void TriggerNotiLateLicenes(){
        MyRetrofit.initRequest(getContext()).TriggerLateLicensesUserToMa(strToken, strFullName, strRegion).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (response.body()){
                        Toast.makeText(getContext(), "Đã thông báo đến quản lý!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thông báo đến quản lý thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });
    }

}
