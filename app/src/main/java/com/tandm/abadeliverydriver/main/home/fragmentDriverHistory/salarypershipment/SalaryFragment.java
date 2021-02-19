package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.adapter.SalaryAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.Items;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.ResultUpdateComment;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.SalaryList;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.MonthYearPickerDialog;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaryFragment extends Fragment {

    private static final String TAG = "SalaryFragment";
    View view;
    Unbinder unbinder;
    @BindView(R.id.btnFromDate)
    Button btnFromDate;
    @BindView(R.id.rvSalary)
    RecyclerView rvSalary;
    @BindView(R.id.tvTotalSalary)
    TextView tvTotalSalary;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    SalaryAdapter adapter;
    String[] strMonthYear;

    private Calendar calendar, calendar2;
    private String reportDate, reportDate2, reportDate3, reportDate4, MaNhanVien,token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_salary, container, false);
        unbinder = ButterKnife.bind(this,view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        MaNhanVien = LoginPrefer.getObject(getContext()).MaNhanVien;
        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar2.getTimeInMillis());
        reportDate3 = Utilities.formatDate_yyyyMMdd(reportDate);
        reportDate4 = Utilities.formatDate_yyyyMMdd(reportDate2);
        btnFromDate.setText(Utilities.formatDate_MMyyyy(reportDate));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSalary.getContext(), linearLayoutManager.getOrientation());
        rvSalary.addItemDecoration(dividerItemDecoration);

        strMonthYear = reportDate3.split("/");
        getSalaryDriver(token,strMonthYear[0], strMonthYear[1], MaNhanVien);




        return view;
    }

    private void getSalaryDriver(String token,String reportDate3, String reportDate4, String maNhanVien) {

        progressDialog = ProgressDialog.show(getContext(),"", "Đang tải...", false, true);

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(getContext()).getSalary(token,reportDate3, reportDate4, maNhanVien).enqueue(new Callback<SalaryList>() {
            @Override
            public void onResponse(Call<SalaryList> call, Response<SalaryList> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter = new SalaryAdapter(new RecyclerViewItemClick<Items>() {
                        @Override
                        public void onClick(Items item, int position, int number) {
                            switch (number){
                                case 0:
                                    Intent i = new Intent(getContext(), SalaryDetailActivity.class);
                                    i.putExtra("items", item);
                                    startActivity(i);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(Items item, int position, int number) {

                            switch (number){
                                case 1:

                            final Dialog dialog = new Dialog(getContext());
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
                                    if (editText.length() == 0){
                                        Toast.makeText(getContext(), "không được để trống", Toast.LENGTH_SHORT).show();

                                    }else {
                                        MyRetrofit.initRequest(getContext()).updateComment(token,editText.getText().toString(),item.shipmenT_ID).enqueue(new Callback<ResultUpdateComment>() {
                                            @Override
                                            public void onResponse(Call<ResultUpdateComment> call, Response<ResultUpdateComment> response) {
                                                if (response.isSuccessful() && response.body() != null){
                                                    Toast.makeText(getContext(), response.body().items+"", Toast.LENGTH_SHORT).show();
                                                    getSalaryDriver(token,strMonthYear[0], strMonthYear[1], MaNhanVien);
                                                    dialog.dismiss();

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResultUpdateComment> call, Throwable t) {
                                                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            });

                            dialog.show();
                            dialog.getWindow().setAttributes(lp);
                                    break;
                            }


                        }
                    });
                    adapter.replace(response.body().items);
                    if (rvSalary != null){
                        rvSalary.setAdapter(adapter);
                    }

                    int salaryTotal = 0;
                    for (Items s : response.body().items){
                            salaryTotal += (int)s.salary;
                    }

                    tvTotalSalary.setText("Tạm tính: " + Utilities.formatNumber(String.valueOf(salaryTotal))+" vnđ");

                }
                Utilities.dismissDialog(progressDialog);
            }
            @Override
            public void onFailure(Call<SalaryList> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    @OnClick(R.id.btnFromDate)
    public void FromDate(View view) {
        MonthYearPickerDialog monthYearPickerDialog = new MonthYearPickerDialog();
        monthYearPickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnFromDate.setText(month+"/"+year);
                getSalaryDriver(token,String.valueOf(year), String.valueOf(month), MaNhanVien);
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
    public void onResume() {
        super.onResume();
        Utilities.dismissDialog(progressDialog);
    }
}
