package com.tandm.abadeliverydriver.main.home.fragmentvouchersop;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LateLicenses;
import com.tandm.abadeliverydriver.main.home.fragmentvouchersop.adapter.LateLicensesOpAdapter;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VouchersOPFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "VouchersOPFragment";
    public static final String DUYET = "DUYỆT";
    public static final String KO_DUYET = "KHÔNG DUYỆT";
    private View view;
    Unbinder unbinder;

    LateLicensesOpAdapter lateLicensesOpAdapter;

    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rvExpensesOPDuyet)
    RecyclerView rvExpensesOPDuyet;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvTitleState)
    TextView tvTitleState;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    private String reportDate, reportDate2, strToken, driverID, strDate, strRegion, strDepartment, strState, strFullName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vouchers_o_p, container, false);
        unbinder = ButterKnife.bind(this, view);

        strFullName = LoginPrefer.getObject(getContext()).fullName;
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        driverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        strRegion = LoginPrefer.getObject(getContext()).Region;
        strState = "CHỜ XỬ LÝ";
        tvTitleState.setText(strState);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvExpensesOPDuyet.setLayoutManager(mLayoutManager);

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), imgFilter);
                popupMenu.inflate(R.menu.popup_expenses_op);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return menuItemClicked(item);
                    }
                });

                popupMenu.show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                GetLateLicensesOP(strState);
            }
        });

        return view;
    }

    private void GetLateLicensesOP(String status) {

        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).GetLateLicensesOP(strToken, status, strRegion).enqueue(new Callback<List<LateLicenses>>() {
            @Override
            public void onResponse(Call<List<LateLicenses>> call, Response<List<LateLicenses>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        lateLicensesOpAdapter = new LateLicensesOpAdapter(new RecyclerViewItemClick<LateLicenses>() {
                            @Override
                            public void onClick(LateLicenses item, int position, int number) {
                                switch (number) {
                                    case 0:
                                        showEditLateLicensesDialog(item.id, item.atmShipmentID, item.fullNameDriver(), item.driverID, item.customer, item.ngayChay(), item.statusManager, item.dateOfFiling, item.reason, item.noteFromManage);
                                        break;
                                    default:
                                        return;
                                }
                            }

                            @Override
                            public void onLongClick(LateLicenses item, int position, int number) {

                            }
                        });

                        lateLicensesOpAdapter.replace(response.body());
                        rvExpensesOPDuyet.setAdapter(lateLicensesOpAdapter);
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

    private void showEditLateLicensesDialog(int id, String atmShipmentID, String fullName, String maNhanVien, String customer, String ngayChay, String statusManager, String dateOfFiling, String reason, String noteFromManage) {
        LayoutInflater inflater = getLayoutInflater();
        View showEditLayout = inflater.inflate(R.layout.dialog_edit_late_licenses_op, null);

        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setView(showEditLayout);
        b.setCancelable(true);
        AlertDialog dialog = b.create();

        TextView tvEditFullName = showEditLayout.findViewById(R.id.tvEditFullName);
        TextView tvEditDriverId = showEditLayout.findViewById(R.id.tvEditDriverId);
        TextView tvEditShipment = showEditLayout.findViewById(R.id.tvEditShipment);
        TextView tvEditCustomer = showEditLayout.findViewById(R.id.tvEditCustomer);
        TextView tvEditStartTime = showEditLayout.findViewById(R.id.tvEditStartTime);
        TextView tvEditState = showEditLayout.findViewById(R.id.tvEditState);
        TextInputEditText edtEditDateOfFilingVouchers = showEditLayout.findViewById(R.id.edtEditDateOfFilingVouchers);
        TextInputEditText edtEditNoteVouchersOP = showEditLayout.findViewById(R.id.edtEditNoteVouchersOP);
        TextView tvEditCommentFromUser = showEditLayout.findViewById(R.id.tvEditCommentFromUser);
        Button btnEditKhongDuyetVouchers = showEditLayout.findViewById(R.id.btnEditKhongDuyetVouchers);
        Button btnEditDuyetVouchers = showEditLayout.findViewById(R.id.btnEditDuyetVouchers);

        tvEditFullName.setText(fullName);
        tvEditDriverId.setText("Mã NV: " +maNhanVien);
        tvEditShipment.setText(atmShipmentID);
        tvEditCustomer.setText(customer);
        tvEditStartTime.setText(ngayChay);
        tvEditState.setText(statusManager);
        edtEditDateOfFilingVouchers.setText(Utilities.formatDate_ddMMyyyy(dateOfFiling));
        tvEditCommentFromUser.setText("Ghi chú của NVGN: " + reason);
        edtEditNoteVouchersOP.setText(noteFromManage);

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


        btnEditKhongDuyetVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStateLicensesByOP(id, Utilities.formatDate_yyyyMMdd4(edtEditDateOfFilingVouchers.getText().toString()), "KHÔNG DUYỆT", strFullName, edtEditNoteVouchersOP.getText().toString(), dialog, maNhanVien);
            }
        });

        btnEditDuyetVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStateLicensesByOP(id, Utilities.formatDate_yyyyMMdd4(edtEditDateOfFilingVouchers.getText().toString()), "DUYỆT", strFullName, edtEditNoteVouchersOP.getText().toString(), dialog, maNhanVien);
            }
        });

        dialog.show();
    }

    private void updateStateLicensesByOP(int id, String dateOfFiling, String state, String strFullName, String noteFromManager, Dialog dialog, String maNhanVien) {
        progressDialog = Utilities.getProgressDialog(getContext(), "Đang cập nhật...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(getContext()).updateLateLicensesFromOP(strToken, id, dateOfFiling, state, strFullName, noteFromManager).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().equals('"' + "DUYỆT Thành Công!" + '"')|| response.body().equals('"' + "KHÔNG DUYỆT Thành Công!" + '"')) {
                        Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                        Utilities.dismissDialog(progressDialog);
                        dialog.dismiss();
                        GetLateLicensesOP(strState);
                        TriggerLateLicensesFromMaToUser(maNhanVien, strFullName, state);
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

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_choxuly:
                strState = "CHỜ XỬ LÝ";
                tvTitleState.setText(strState);
                GetLateLicensesOP(strState);
                break;
            case R.id.menuItem_duyet:
                strState = "DUYỆT";
                tvTitleState.setText(strState);
                GetLateLicensesOP(strState);
                break;
            case R.id.menuItem_khongduyet:
                strState = "KHÔNG DUYỆT";
                tvTitleState.setText(strState);
                GetLateLicensesOP(strState);
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        GetLateLicensesOP(strState);
    }


    private void TriggerLateLicensesFromMaToUser(String maNhanVien, String fullName, String state) {
        MyRetrofit.initRequest(getContext()).TriggerLateLicensesFromMaToUser(strToken, maNhanVien, fullName, state).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body()) {
                        Toast.makeText(getContext(), "Đã thông báo đến NVGN!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thông báo đến NVGN thất bại!", Toast.LENGTH_SHORT).show();
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