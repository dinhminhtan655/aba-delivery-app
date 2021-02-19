package com.tandm.abadeliverydriver.main.home.fragmenthistorybiker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmenthome.BikerCustomer;
import com.tandm.abadeliverydriver.main.home.fragmenthome.BikerCustomerAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery2;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter.HistoryStopDriverDelivery2Adapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatebhxice.UpdateHistoryBHXIce;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatehistorykhay.UpdateHistoryKhay;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.UpdateHistoryWhitoutMasanActivity;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Customer;
import com.tandm.abadeliverydriver.main.utilities.SpinCusPrefCustomer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryStopBikerFragment extends Fragment {

    private static final String TAG = "HistoryStopBikerFragmen";
    View view;
    Unbinder unbinder;
    @BindView(R.id.rvGiaBu)
    RecyclerView rvGiaoBu;
    @BindView(R.id.btnDate)
    Button btnDate;
    @BindView(R.id.spinIDGiaoBu)
    Spinner spinIDGiaoBu;
    @BindView(R.id.tvTotalKho)
    TextView tvTotalKho;
    @BindView(R.id.tvTotalNVGN)
    TextView tvTotalNVGN;
    private Calendar calendar;
    BikerCustomerAdapter adapter2;
    HistoryStopDriverDelivery2Adapter adapter;
    private String reportDate, reportDate2, token, driverID, strDate, strDateTime, strDateTimeMB, driverUserName, strSpinner, strCustomerCode, strShipmentID;
    private String strCustomerCode2, strCustomerName2;
    long longTimeCompleted, longTimeCurrent;
    int real_num = 0;
    int hour = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_stop_biker, container, false);
        unbinder = ButterKnife.bind(this, view);

        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        driverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        driverUserName = LoginPrefer.getObject(getContext()).userName;

        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        strDate = Utilities.formatDate_yyyyMMdd2(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 09:00:00";
        strDateTimeMB = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 10:30:00";
        try {
            if(LoginPrefer.getObject(getContext()).Region.equals("MN")){
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
            }else {
                longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTimeMB);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        longTimeCurrent = System.currentTimeMillis();
        getBikerCustomer(token);
        return view;
    }

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
                            getHisStore(strCustomerName2);
//                            getStoreGiaoBu();
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

    public void getHisStore(String customer) {


        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).getHistoryStopBikerDelivery2(token, driverUserName, reportDate2, customer).enqueue(new Callback<List<HistoryStopDriverDelivery2>>() {
            @Override
            public void onResponse(Call<List<HistoryStopDriverDelivery2>> call, Response<List<HistoryStopDriverDelivery2>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new HistoryStopDriverDelivery2Adapter(new RecyclerViewItemClick<HistoryStopDriverDelivery2>() {
                        @Override
                        public void onClick(HistoryStopDriverDelivery2 item, int position, int number) {
                            switch (number) {
                                case 0:

                                    if (item.khachHang.equals(Customer.VCMFRESH) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.VCMFRESH) && item.packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
                                            || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAMEAT_0_5)
                                            || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.CP) && item.packaged_Item_XID.equals(Customer.ABAMEAT_0_5)
                                            || item.khachHang.equals(Customer.CP) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.NEWZEALAND) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                                            || item.khachHang.equals(Customer.NEWZEALAND) && item.packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)
                                            || item.khachHang.equals(Customer.THREEF) && item.packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
                                            || item.khachHang.equals(Customer.XX6020) && item.packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
                                            ) {
                                        Intent i = new Intent(getContext(), UpdateHistoryWhitoutMasanActivity.class);
//                                            i.putExtra("date", item.getDateDeliveryTime(strTimeCompleted));
                                        i.putExtra("date2", reportDate2);
                                        i.putExtra("storecode", item.store_Code);
                                        i.putExtra("atmshipmentid", item.atM_Shipment_ID);
                                        i.putExtra("orderreleaseid", item.orderrelease_id);
                                        i.putExtra("customer", item.khachHang);
                                        startActivity(i);
                                    } else if (item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAICE_0)) {
                                        Intent i = new Intent(getContext(), UpdateHistoryBHXIce.class);
//                                            i.putExtra("date", item.getDateDeliveryTime(strTimeCompleted));
                                        i.putExtra("date2", reportDate2);
                                        i.putExtra("storecode", item.store_Code);
                                        i.putExtra("atmshipmentid", item.atM_Shipment_ID);
                                        i.putExtra("orderreleaseid", item.orderrelease_id);
                                        i.putExtra("customer", item.khachHang);
                                        startActivity(i);
                                    } else if (item.khachHang.equals(Customer.VCM) && item.packaged_Item_XID.equals(Customer.ABALOCAL_FRUIT_VEGETABLE_9_14) || item.khachHang.equals(Customer.VCM) && item.packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)
                                            || item.khachHang.equals(Customer.VCM) && item.packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5) || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAVEGETABLE_0_5)
                                            || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABAVEGETABLE_12_17) || item.khachHang.equals(Customer.BHX) && item.packaged_Item_XID.equals(Customer.ABATRAY)
                                            || item.khachHang.equals(Customer.VCMFRESH) && item.packaged_Item_XID.equals(Customer.ABATRAY)) {
                                        Intent i = new Intent(getContext(), UpdateHistoryKhay.class);
//                                            i.putExtra("date", item.getDateDeliveryTime(strTimeCompleted));
                                        i.putExtra("date2", reportDate2);
                                        i.putExtra("storecode", item.store_Code);
                                        i.putExtra("atmshipmentid", item.atM_Shipment_ID);
                                        i.putExtra("orderreleaseid", item.orderrelease_id);
                                        i.putExtra("customer", item.khachHang);
                                        startActivity(i);
                                    } else if (item.khachHang.equals(Customer.MASAN) || item.khachHang.equals(Customer.TOKYODELI)) {

                                        final Dialog dialog = new Dialog(getContext());
                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog.setCancelable(false);
                                        dialog.setContentView(R.layout.dialog_update_his_carton2);

                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialog.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                                        TextView tvDiaMaCH = dialog.findViewById(R.id.tvDiaMaCH);
                                        TextView tvDiaTenCH = dialog.findViewById(R.id.tvDiaTenCH);
                                        TextView tvDiaSLLayTuKho = dialog.findViewById(R.id.tvDiaSLLayTuKho);
                                        TextView tvDiaSLGiaoCH = dialog.findViewById(R.id.tvDiaSLGiaoCH);
                                        TextInputEditText edtDiaSLGiaoLai = dialog.findViewById(R.id.edtDiaSLGiaoLai);
                                        Button btnDiaHuy = dialog.findViewById(R.id.btnDiaHuy);
                                        Button btnDiaDongY = dialog.findViewById(R.id.btnDiaDongY);

                                        tvDiaMaCH.setText(item.store_Code);
                                        tvDiaTenCH.setText(item.store_Name);
                                        tvDiaSLLayTuKho.setText(String.valueOf(item.totalCartonMasan));
                                        tvDiaSLGiaoCH.setText(String.valueOf(item.real_Num_Delivered));
                                        edtDiaSLGiaoLai.setText(String.valueOf(item.real_Num_Delivered));


                                        btnDiaHuy.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });


                                        btnDiaDongY.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (longTimeCurrent < longTimeCompleted) {
                                                    if (Integer.parseInt(tvDiaSLGiaoCH.getText().toString()) < 0) {
                                                        Toast.makeText(getContext(), "Số lượng giao không thể nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        boolean isEnough = real_num != Integer.parseInt(tvDiaSLLayTuKho.getText().toString()) ? false : true;
                                                        MyRetrofit.initRequest(getContext()).updateHistoryCarton(token, item.atM_Shipment_ID, item.orderrelease_id, Integer.parseInt(edtDiaSLGiaoLai.getText().toString().equals("") ? "0" : edtDiaSLGiaoLai.getText().toString()), isEnough,
                                                                0, 0,
                                                                0, 0).enqueue(new Callback<Integer>() {
                                                            @Override
                                                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                                if (response.isSuccessful() && response.body() != null) {
                                                                    if (response.body() == 1) {
                                                                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                                        dialog.dismiss();
                                                                        getHisStore(strCustomerName2);
                                                                    } else {
                                                                        Toast.makeText(getContext(), "Thất bại thử lại", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Integer> call, Throwable t) {
                                                                Toast.makeText(getContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Đã hết thời gian chỉnh sửa!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                        dialog.show();
                                        dialog.getWindow().setAttributes(lp);


                                    }

//
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(HistoryStopDriverDelivery2 item, int position, int number) {

                        }
                    });

                    int slKho = 0, slNVGN = 0;

                    for (int i = 0; i < response.body().size(); i++){
                        slKho += response.body().get(i).totalCartonMasan;
                        slNVGN += response.body().get(i).real_Num_Delivered;
                    }

                    tvTotalKho.setText("Tổng Kho: "+slKho);
                    tvTotalNVGN.setText("Tổng Giao: "+slNVGN);

                    adapter.replace(response.body());
                    rvGiaoBu.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<HistoryStopDriverDelivery2>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnDate)
    public void chooseDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
                reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
                strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 12:00:00";
                try {
                    longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
                getHisStore(strCustomerName2);

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
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 12:00:00";
        try {
            longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        getHisStore(strCustomerName2);

    }

    @OnClick(R.id.ivArrowLeft)
    public void preDay(View view) {
        calendar.add(Calendar.DATE, -1);
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDate_yyyyMMdd(reportDate);
        strDateTime = Utilities.formatDate_yyyyMMdd3(reportDate2) + " 12:00:00";
        try {
            longTimeCompleted = Utilities.formatDateTime_ToMilisecond2(strDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
        getHisStore(strCustomerName2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}