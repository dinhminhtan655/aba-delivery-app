package com.tandm.abadeliverydriver.main.home.fragmentchuyenxe;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.HomeDriverFragment;
import com.tandm.abadeliverydriver.main.home.fragmentchuyenxe.adapter.AADriverAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.CountShipment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ResultAA;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.Shipment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewTripListener;
import com.tandm.abadeliverydriver.main.utilities.AddFragmentUtil;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

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

import static com.tandm.abadeliverydriver.main.home.MainActivity.chuyenXe;

/**
 * A simple {@link Fragment} subclass.
 */
public class AADriverFragment extends Fragment {

    private static final String TAG = "AADriverFragment";
    Unbinder unbinder;
    View view;
    ProgressDialog progressDialog;
    @BindView(R.id.rvAA)
    RecyclerView rvAA;
    @BindView(R.id.btnDate)
    Button btnDate;
    //    @BindView(R.id.fab_refresh)
//    FloatingActionButton fab_refresh;
    @BindView(R.id.btnAccepted)
    Button btnAccepted;
    @BindView(R.id.btnWaiting)
    Button btnWaiting;
    @BindView(R.id.textOne)
    TextView textOne;
    @BindView(R.id.textTwo)
    TextView textTwo;
    private Calendar calendar;
    List<Shipment> shipments;

    boolean bClick = false;

    String strToken;

//    boolean AOA = false;

    AADriverAdapter aaDriverAdapter;

    private String reportDate, reportDate2, token, driverID, strDate, fullName;

    public AADriverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aa_driver, container, false);
        unbinder = ButterKnife.bind(this, view);

        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;

        token = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        driverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        fullName = LoginPrefer.getObject(getContext()).fullName;
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));

        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
        countAcceptedShipment(driverID, reportDate2);
        countAssignedShipment(driverID, reportDate2);
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));


        getTrip("ASSIGNED");
        btnWaiting.setBackgroundColor(getResources().getColor(R.color.blue_strong));
        btnAccepted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        rvAA.setAdapter(aaDriverAdapter);


//        fab_refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getTrip("ASSIGNED");
//                btnWaiting.setBackgroundColor(getResources().getColor(R.color.blue_strong));
//                btnAccepted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            }
//        });

        return view;
    }


    private void getTrip(String state) {

        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        String strFromDate = reportDate2 + " 00:00:00.000";
        String strToDate = reportDate2 + " 23:59:59.000";

        MyRetrofit.initRequest(getContext()).getShipment(token, driverID, strFromDate, strToDate, state).enqueue(new Callback<List<Shipment>>() {
            @Override
            public void onResponse(Call<List<Shipment>> call, Response<List<Shipment>> response) {
                if (response.isSuccessful() && response != null) {
                    countAcceptedShipment(driverID, reportDate2);
                    countAssignedShipment(driverID, reportDate2);
                    rvAA.setVisibility(View.VISIBLE);
                    shipments = new ArrayList<>();
                    shipments.addAll(response.body());
                    if (response.body().size() > 0) {
                        aaDriverAdapter = new AADriverAdapter(new RecyclerViewTripListener() {
                            @Override
                            public void onClick(int position, String atm_shipment_id, String trucktype, String deliverydate, String routeno, String driver_gid, int key) {
                                supportOnClick(key, atm_shipment_id, trucktype, deliverydate, routeno, driver_gid);
                            }

                            @Override
                            public void onLongClick() {

                            }
                        }, shipments);
                        aaDriverAdapter.replace(response.body());
                        rvAA.setAdapter(aaDriverAdapter);
                    } else {
                        rvAA.setVisibility(View.GONE);
                    }
                }
                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<List<Shipment>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.btnAccepted)
    public void AcceptedTrip(View view) {
        bClick = true;
//        AOA = false;
        getTrip("ACCEPTED");
        btnAccepted.setBackgroundColor(getResources().getColor(R.color.blue_strong));
        btnWaiting.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        countAcceptedShipment(driverID, reportDate2);
//        countAssignedShipment(driverID, reportDate2);
    }

    @OnClick(R.id.btnWaiting)
    public void AssignedTrip(View view) {
//        AOA = false;
        bClick = false;
        getTrip("ASSIGNED");
        btnWaiting.setBackgroundColor(getResources().getColor(R.color.blue_strong));
        btnAccepted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        countAcceptedShipment(driverID, reportDate2);
//        countAssignedShipment(driverID, reportDate2);
    }


    @OnClick(R.id.btnDate)
    public void chooseDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
                reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
                btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//                countAcceptedShipment(driverID, reportDate2);
//                countAssignedShipment(driverID, reportDate2);
                if (bClick) {
                    getTrip("ACCEPTED");
                } else {
                    getTrip("ASSIGNED");
                }


            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @OnClick(R.id.ivArrowRight)
    public void nextDay(View view) {
        calendar.add(Calendar.DATE, 1);
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//        countAcceptedShipment(driverID, reportDate2);
//        countAssignedShipment(driverID, reportDate2);
        if (bClick) {
            getTrip("ACCEPTED");
        } else {
            getTrip("ASSIGNED");
        }


    }

    @OnClick(R.id.ivArrowLeft)
    public void preDay(View view) {
        calendar.add(Calendar.DATE, -1);
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//        countAcceptedShipment(driverID, reportDate2);
//        countAssignedShipment(driverID, reportDate2);
        if (bClick) {
            getTrip("ACCEPTED");
        } else {
            getTrip("ASSIGNED");
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void supportOnClick(int key, String atm_shipment_id, String trucktype, String deliverydate, String routeno, String driver_gid) {
        switch (key) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view2 = inflater.inflate(R.layout.dialog_denied, null);
                builder.setView(view2);

                TextInputEditText edtlydo = view2.findViewById(R.id.edtDialogDenied);

                builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải...");
                        progressDialog.show();

                        if (!WifiHelper.isConnected(getActivity())) {
                            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                            Utilities.dismissDialog(progressDialog);
                            return;
                        }

                        MyRetrofit.initRequest(getContext()).addDeniedTrip(strToken, atm_shipment_id, deliverydate, routeno, driver_gid, fullName, edtlydo.getText().toString()).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful() && response != null) {
                                    getTrip("ASSIGNED");
//                                    countAcceptedShipment(driverID, reportDate2);
//                                    countAssignedShipment(driverID, reportDate2);
                                    rvAA.setAdapter(aaDriverAdapter);
                                    Utilities.initializeCountDrawer(view, getContext(), chuyenXe, strToken, driverID, strDate);
                                }
                                Utilities.dismissDialog(progressDialog);
                                Snackbar.make(view, "Đã từ chối", Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Utilities.dismissDialog(progressDialog);
                                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                            }
                        });
                        Utilities.dismissDialog(progressDialog);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
//-------------------------Chỉ bắt đầu chuyến đầu tiên trước sau khi xếp lại thời gian---------------------
//            case 0:
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Vui lòng hoàn thành chuyến đầu tiên trước");
//                Dialog dialog = builder.create();
//                dialog.show();
//                break;
//-------------------------Chỉ bắt đầu chuyến đầu tiên trước sau khi xếp lại thời gian---------------------
            case 1:
                startShipment(atm_shipment_id, driver_gid);
                Utilities.initializeCountDrawer(view, getContext(), chuyenXe, strToken, driverID, strDate);
                break;

            case 2:
                dongYShipment(atm_shipment_id, driver_gid);
                Utilities.initializeCountDrawer(view, getContext(), chuyenXe, strToken, driverID, strDate);
                break;

            case 3:
                FragmentManager fm = getFragmentManager();
                CheckStopFragment checkStopFragment = CheckStopFragment.newInstance(atm_shipment_id, deliverydate);
                checkStopFragment.show(fm, null);
                break;
        }
    }

    private void startShipment(String atm_shipment_id, String driver_gid) {
        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(getContext()).updateStateCurrenShipment(strToken, atm_shipment_id, driver_gid).enqueue(new Callback<ResultAA>() {
            @Override
            public void onResponse(Call<ResultAA> call, Response<ResultAA> response) {
                if (response.isSuccessful() && response != null) {
                    if (response.body().result.equals("1") || response.body().result.equals("2")) {
//                        getTrip("ASSIGNED");
//                        rvAA.setAdapter(aaDriverAdapter);
                        Fragment trangChinh = new HomeDriverFragment();
                        AddFragmentUtil.loadFragment(getContext(), trangChinh, R.id.frLayout);
                        Utilities.initializeCountDrawer(view, getContext(), chuyenXe, strToken, driverID, strDate);

                    } else {
                        Snackbar.make(view, "Bạn chưa hoàn thành chuyến hiện tại nên chưa thể bắt đầu chuyến mới!", Snackbar.LENGTH_LONG).show();
                    }

                }
                Utilities.dismissDialog(progressDialog);

            }

            @Override
            public void onFailure(Call<ResultAA> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });

        Utilities.dismissDialog(progressDialog);
    }


    private void dongYShipment(String atm_shipment_id, String driver_gid) {

        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getActivity())) {
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            return;
        }

        MyRetrofit.initRequest(getContext()).updateStateShipment(strToken, atm_shipment_id, "ACCEPTED", driver_gid).enqueue(new Callback<ResultAA>() {
            @Override
            public void onResponse(Call<ResultAA> call, Response<ResultAA> response) {
                if (response.isSuccessful() && response != null) {
                    getTrip("ASSIGNED");
                    rvAA.setAdapter(aaDriverAdapter);

                }
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Đã nhận chuyến vui lòng kiểm tra bên mục ĐÃ NHẬN", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultAA> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });

        Utilities.dismissDialog(progressDialog);

    }

    private void countAssignedShipment(String driverID, String date) {

        MyRetrofit.initRequest(getContext()).countShipmentAssigned(strToken, driverID, date).enqueue(new Callback<CountShipment>() {
            @Override
            public void onResponse(Call<CountShipment> call, Response<CountShipment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    textTwo.setText(response.body().count);
                }
            }

            @Override
            public void onFailure(Call<CountShipment> call, Throwable t) {

            }
        });

    }

    private void countAcceptedShipment(String driverID, String date) {

        MyRetrofit.initRequest(getContext()).countShipmentAccepted(strToken, driverID, date).enqueue(new Callback<CountShipment>() {
            @Override
            public void onResponse(Call<CountShipment> call, Response<CountShipment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    textOne.setText(response.body().count);
                }

            }

            @Override
            public void onFailure(Call<CountShipment> call, Throwable t) {

            }
        });


    }


}
