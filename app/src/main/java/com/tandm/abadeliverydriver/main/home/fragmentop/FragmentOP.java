package com.tandm.abadeliverydriver.main.home.fragmentop;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ExpensesAmount;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.Message;
import com.tandm.abadeliverydriver.main.home.fragmentop.adapter.ExpensesOPDuyetAdapter;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.receiver.CheckFeeOPBroadcastReceiver;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOP extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "FragmentOP";
    public static final String DUYET = "DUYỆT";
    public static final String KO_DUYET = "KHÔNG DUYỆT";
    private View view;
    Unbinder unbinder;
//    @BindView(R.id.lnCalendar)
//    LinearLayout lnCalendar;
//    @BindView(R.id.btnDate)
//    Button btnDate;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.rvExpensesOPDuyet)
    RecyclerView rvExpensesOPDuyet;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvTitleState)
    TextView tvTitleState;
    ProgressDialog progressDialog;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Calendar c;

    private Calendar calendar;
    int i = 1;
    private String reportDate, reportDate2, strToken, driverID, strDate, strRegion, strDepartment, strState;
    private ExpensesOPDuyetAdapter adapter;
    private ArrayList<ExpensesAmount> list = new ArrayList<>();
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_o_p, container, false);
        unbinder = ButterKnife.bind(this, view);

        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        driverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        strDate = Utilities.formatDate_yyyyMMdd(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));
        strRegion = LoginPrefer.getObject(getContext()).Region;
        strState = "CHỜ XỬ LÝ";
        tvTitleState.setText(strState);
        if (strRegion.equals("MN")) {
            strDepartment = "2001-TRUCKING";
        } else {
            strDepartment = "2002-TRUCKING";
        }

        c = Calendar.getInstance();
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(getContext(), CheckFeeOPBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);


        calendar = Calendar.getInstance();
        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
        reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
//        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
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
                GetOp2(strState, 1);
            }
        });

        return view;
    }

    private void startAlarm(Calendar c) {
        if (!LoginPrefer.getSetAlarm(getContext())) {
            LoginPrefer.saveSetAlarm(true, getContext());
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),20000, pendingIntent);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Utilities.triggerAtMillis, Utilities.intervalMillis, pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Utilities.triggerAtMillis, Utilities.intervalMillis, pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
            } else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Utilities.triggerAtMillis, Utilities.intervalMillis, pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
            }

        }
    }

    private boolean menuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_choxuly:
                strState = "CHỜ XỬ LÝ";
                tvTitleState.setText(strState);
                GetOp2(strState, 1);
                break;
            case R.id.menuItem_duyet:
                strState = "DUYỆT";
                tvTitleState.setText(strState);
                GetOp2(strState, 1);
                break;
            case R.id.menuItem_khongduyet:
                strState = "KHÔNG DUYỆT";
                tvTitleState.setText(strState);
                GetOp2(strState, 1);
                break;
        }
        return true;
    }

    private void GetOp2(String state, int pageNumber) {

        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }
        if (LoginPrefer.getObject(getContext()).Position.equals("OP")) {
            MyRetrofit.initRequest(getContext()).GetOp2(strToken, state, strDepartment, reportDate).enqueue(new Callback<List<ExpensesAmount>>() {
                @Override
                public void onResponse(Call<List<ExpensesAmount>> call, Response<List<ExpensesAmount>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        list = new ArrayList<>();
                        list = (ArrayList<ExpensesAmount>) response.body();
                        adapter = new ExpensesOPDuyetAdapter(new RecyclerViewItemClick<ExpensesAmount>() {
                            @Override
                            public void onClick(ExpensesAmount item, int position, int number) {
                                switch (number) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), DetailExpensesOPDuyet.class);
                                        intent.putExtra("expenses", item);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                                        b.setTitle("Thông báo");
                                        b.setMessage("Bạn đồng ý duyệt phiếu này?");
                                        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                progressDialog.show();
                                                if (!WifiHelper.isConnected(getActivity())) {
                                                    Utilities.dismissDialog(progressDialog);
                                                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                    return;
                                                }
                                                MyRetrofit.initRequest(getContext()).UpdateStateOP(strToken, item.id, DUYET, "", LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                list.remove(position);
                                                                adapter.notifyDataSetChanged();
                                                                MyRetrofit.initRequest(getContext()).TriggerStateFromOPToUser(strToken, item.employeeID, item.employeeName, LoginPrefer.getObject(getContext()).fullName, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                                    @Override
                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                        if (response.isSuccessful() && response.body() != null) {
                                                                            if (response.body()) {
                                                                                Toast.makeText(getContext(), "Đẫ gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                                    }
                                                                });
                                                            } else {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                GetOp2(strState, 0);
                                                            }
                                                        }
                                                        Utilities.dismissDialog(progressDialog);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                    }
                                                });
                                            }
                                        });
                                        Dialog d = b.create();
                                        d.show();


                                        break;
                                    case 2:

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
                                                if (editText.length() == 0) {
                                                    Toast.makeText(getContext(), "không được để trống", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                    progressDialog.show();
                                                    if (!WifiHelper.isConnected(getActivity())) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                        return;
                                                    }
                                                    MyRetrofit.initRequest(getContext()).UpdateStateOP(strToken, item.id, KO_DUYET, editText.getText().toString(), LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                        @Override
                                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    list.remove(position);
                                                                    adapter.notifyDataSetChanged();
                                                                    dialog.dismiss();
                                                                    MyRetrofit.initRequest(getContext()).TriggerStateFromOPToUser(strToken, item.employeeID, item.employeeName, LoginPrefer.getObject(getContext()).fullName, KO_DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                                        @Override
                                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                            if (response.isSuccessful() && response.body() != null) {
                                                                                if (response.body()) {
                                                                                    Toast.makeText(getContext(), "Đẫ gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);

                                                                        }
                                                                    });
                                                                } else {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    GetOp2(strState, 0);
                                                                }
                                                            }
                                                            Utilities.dismissDialog(progressDialog);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Message> call, Throwable t) {
                                                            Utilities.dismissDialog(progressDialog);
                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
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

                            @Override
                            public void onLongClick(ExpensesAmount item, int position, int number) {

                            }
                        });
                        adapter.replace(list);
                        rvExpensesOPDuyet.setAdapter(adapter);
                    }

                    try {
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<ExpensesAmount>> call, Throwable t) {
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        } else if (LoginPrefer.getObject(getContext()).Position.equals("OP2")) {
            MyRetrofit.initRequest(getContext()).GetOp4(strToken, state, strDepartment, reportDate).enqueue(new Callback<List<ExpensesAmount>>() {
                @Override
                public void onResponse(Call<List<ExpensesAmount>> call, Response<List<ExpensesAmount>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        list = new ArrayList<>();
                        list = (ArrayList<ExpensesAmount>) response.body();
                        adapter = new ExpensesOPDuyetAdapter(new RecyclerViewItemClick<ExpensesAmount>() {
                            @Override
                            public void onClick(ExpensesAmount item, int position, int number) {
                                switch (number) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), DetailExpensesOPDuyet.class);
                                        intent.putExtra("expenses", item);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                                        b.setTitle("Thông báo");
                                        b.setMessage("Bạn đồng ý xác nhận phiếu này?");
                                        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                progressDialog.show();
                                                if (!WifiHelper.isConnected(getActivity())) {
                                                    Utilities.dismissDialog(progressDialog);
                                                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                    return;
                                                }
                                                MyRetrofit.initRequest(getContext()).UpdateStateOP2(strToken, item.id, DUYET, "", LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                list.remove(position);
                                                                adapter.notifyDataSetChanged();
//                                                                MyRetrofit.initRequest(getContext()).TriggerStateFromOPToManager(strToken, item.employeeName, LoginPrefer.getObject(getContext()).fullName, LoginPrefer.getObject(getContext()).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                                                        if (response.isSuccessful() && response.body() != null) {
//                                                                            if (response.body()) {
//                                                                                Toast.makeText(getContext(), "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
//                                                                            }
//                                                                        }
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
//                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                                                                    }
//                                                                });
                                                            } else {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                GetOp2(strState, 0);
                                                            }
                                                        }
                                                        Utilities.dismissDialog(progressDialog);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                    }
                                                });
                                            }
                                        });
                                        Dialog d = b.create();
                                        d.show();


                                        break;
                                    case 2:

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
                                                if (editText.length() == 0) {
                                                    Toast.makeText(getContext(), "không được để trống", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                    progressDialog.show();
                                                    if (!WifiHelper.isConnected(getActivity())) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                        return;
                                                    }
                                                    MyRetrofit.initRequest(getContext()).UpdateStateOP2(strToken, item.id, KO_DUYET, editText.getText().toString(), LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                        @Override
                                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    MyRetrofit.initRequest(getContext()).TriggerStateFromOPToUser(strToken, item.employeeID, item.employeeName, LoginPrefer.getObject(getContext()).fullName, KO_DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                                        @Override
                                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                            if (response.isSuccessful() && response.body() != null) {
                                                                                if (response.body()) {
                                                                                    Toast.makeText(getContext(), "Đẫ gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);

                                                                        }
                                                                    });
                                                                    list.remove(position);
                                                                    adapter.notifyDataSetChanged();
                                                                    dialog.dismiss();
                                                                } else {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    GetOp2(strState, 0);
                                                                }
                                                            }
                                                            Utilities.dismissDialog(progressDialog);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Message> call, Throwable t) {
                                                            Utilities.dismissDialog(progressDialog);
                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
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

                            @Override
                            public void onLongClick(ExpensesAmount item, int position, int number) {

                            }
                        });
                        adapter.replace(list);
                        rvExpensesOPDuyet.setAdapter(adapter);
                    }

                    try {
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<ExpensesAmount>> call, Throwable t) {
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        } else if (LoginPrefer.getObject(getContext()).Position.equals("KT")) {
            MyRetrofit.initRequest(getContext()).GetKT(strToken, state, strDepartment, reportDate).enqueue(new Callback<List<ExpensesAmount>>() {
                @Override
                public void onResponse(Call<List<ExpensesAmount>> call, Response<List<ExpensesAmount>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        list = new ArrayList<>();
                        list = (ArrayList<ExpensesAmount>) response.body();
                        adapter = new ExpensesOPDuyetAdapter(new RecyclerViewItemClick<ExpensesAmount>() {
                            @Override
                            public void onClick(ExpensesAmount item, int position, int number) {
                                switch (number) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), DetailExpensesOPDuyet.class);
                                        intent.putExtra("expenses", item);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                                        b.setTitle("Thông báo");
                                        b.setMessage("Bạn đồng ý xác nhận phiếu này?");
                                        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                progressDialog.show();
                                                if (!WifiHelper.isConnected(getActivity())) {
                                                    Utilities.dismissDialog(progressDialog);
                                                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                    return;
                                                }
                                                MyRetrofit.initRequest(getContext()).UpdateStateOP2(strToken, item.id, DUYET, "", LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                MyRetrofit.initRequest(getContext()).TriggerStateFromKTToKTMa(strToken, item.employeeName, LoginPrefer.getObject(getContext()).fullName, LoginPrefer.getObject(getContext()).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                                    @Override
                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                        if (response.isSuccessful() && response.body() != null) {
                                                                            if (response.body()) {
                                                                                Toast.makeText(getContext(), "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);

                                                                    }
                                                                });

                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                list.remove(position);
                                                                adapter.notifyDataSetChanged();
//                                                                MyRetrofit.initRequest(getContext()).TriggerStateFromOPToManager(strToken,  item.employeeName,LoginPrefer.getObject(getContext()).fullName,LoginPrefer.getObject(getContext()).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                                                        if (response.isSuccessful() && response.body() != null) {
//                                                                            if (response.body()) {
//                                                                                Toast.makeText(getContext(), "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
//                                                                            }
//                                                                        }
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
//                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                                                                    }
//                                                                });
                                                            } else {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                GetOp2(strState, 0);
                                                            }
                                                        }
                                                        Utilities.dismissDialog(progressDialog);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                    }
                                                });
                                            }
                                        });
                                        Dialog d = b.create();
                                        d.show();


                                        break;
                                    case 2:

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
                                                if (editText.length() == 0) {
                                                    Toast.makeText(getContext(), "không được để trống", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                    progressDialog.show();
                                                    if (!WifiHelper.isConnected(getActivity())) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                        return;
                                                    }
                                                    MyRetrofit.initRequest(getContext()).UpdateStateOP2(strToken, item.id, KO_DUYET, editText.getText().toString(), LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                        @Override
                                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    MyRetrofit.initRequest(getContext()).TriggerStateFromOPToUser(strToken, item.employeeID, item.employeeName, LoginPrefer.getObject(getContext()).fullName, KO_DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                                        @Override
                                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                            if (response.isSuccessful() && response.body() != null) {
                                                                                if (response.body()) {
                                                                                    Toast.makeText(getContext(), "Đã gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);

                                                                        }
                                                                    });
                                                                    list.remove(position);
                                                                    adapter.notifyDataSetChanged();
                                                                    dialog.dismiss();
                                                                } else {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    GetOp2(strState, 0);
                                                                }
                                                            }
                                                            Utilities.dismissDialog(progressDialog);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Message> call, Throwable t) {
                                                            Utilities.dismissDialog(progressDialog);
                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
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

                            @Override
                            public void onLongClick(ExpensesAmount item, int position, int number) {

                            }
                        });
                        adapter.replace(list);
                        rvExpensesOPDuyet.setAdapter(adapter);
                    }

                    try {
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<ExpensesAmount>> call, Throwable t) {
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        } else if (LoginPrefer.getObject(getContext()).Position.equals("KT2")) {
            MyRetrofit.initRequest(getContext()).GetKTManager(strToken, state, strDepartment, reportDate).enqueue(new Callback<List<ExpensesAmount>>() {
                @Override
                public void onResponse(Call<List<ExpensesAmount>> call, Response<List<ExpensesAmount>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        list = new ArrayList<>();
                        list = (ArrayList<ExpensesAmount>) response.body();
                        adapter = new ExpensesOPDuyetAdapter(new RecyclerViewItemClick<ExpensesAmount>() {
                            @Override
                            public void onClick(ExpensesAmount item, int position, int number) {
                                switch (number) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), DetailExpensesOPDuyet.class);
                                        intent.putExtra("expenses", item);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                                        b.setTitle("Thông báo");
                                        b.setMessage("Bạn đồng ý xác nhận phiếu này?");
                                        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                progressDialog.show();
                                                if (!WifiHelper.isConnected(getActivity())) {
                                                    Utilities.dismissDialog(progressDialog);
                                                    RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                    return;
                                                }
                                                MyRetrofit.initRequest(getContext()).UpdateStateOP(strToken, item.id, DUYET, "", LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                list.remove(position);
                                                                adapter.notifyDataSetChanged();
//                                                                MyRetrofit.initRequest(getContext()).TriggerStateFromOPToManager(strToken,  item.employeeName,LoginPrefer.getObject(getContext()).fullName,LoginPrefer.getObject(getContext()).Region, DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
//                                                                    @Override
//                                                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                                                                        if (response.isSuccessful() && response.body() != null) {
//                                                                            if (response.body()) {
//                                                                                Toast.makeText(getContext(), "Đẫ gửi tới Manager", Toast.LENGTH_SHORT).show();
//                                                                            }
//                                                                        }
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onFailure(Call<Boolean> call, Throwable t) {
//                                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//                                                                    }
//                                                                });
                                                            } else {
                                                                Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                GetOp2(strState, 0);
                                                            }
                                                        }
                                                        Utilities.dismissDialog(progressDialog);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                                                    }
                                                });
                                            }
                                        });
                                        Dialog d = b.create();
                                        d.show();


                                        break;
                                    case 2:

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
                                                if (editText.length() == 0) {
                                                    Toast.makeText(getContext(), "không được để trống", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
                                                    progressDialog.show();
                                                    if (!WifiHelper.isConnected(getActivity())) {
                                                        Utilities.dismissDialog(progressDialog);
                                                        RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
                                                        return;
                                                    }
                                                    MyRetrofit.initRequest(getContext()).UpdateStateOP(strToken, item.id, KO_DUYET, editText.getText().toString(), LoginPrefer.getObject(getContext()).fullName).enqueue(new Callback<Message>() {
                                                        @Override
                                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                if (response.body().state.equals("Cập Nhật Thành Công!")) {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    MyRetrofit.initRequest(getContext()).TriggerStateFromOPToUser(strToken, item.employeeID, item.employeeName, LoginPrefer.getObject(getContext()).fullName, KO_DUYET, item.advancePaymentType).enqueue(new Callback<Boolean>() {
                                                                        @Override
                                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                            if (response.isSuccessful() && response.body() != null) {
                                                                                if (response.body()) {
                                                                                    Toast.makeText(getContext(), "Đẫ gửi tới NVGN", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);

                                                                        }
                                                                    });
                                                                    list.remove(position);
                                                                    adapter.notifyDataSetChanged();
                                                                    dialog.dismiss();
                                                                } else {
                                                                    Utilities.thongBaoDialog(getContext(), response.body().state);
                                                                    GetOp2(strState, 0);
                                                                }
                                                            }
                                                            Utilities.dismissDialog(progressDialog);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Message> call, Throwable t) {
                                                            Utilities.dismissDialog(progressDialog);
                                                            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
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

                            @Override
                            public void onLongClick(ExpensesAmount item, int position, int number) {

                            }
                        });
                        adapter.replace(list);
                        rvExpensesOPDuyet.setAdapter(adapter);
                    }

                    try {
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<ExpensesAmount>> call, Throwable t) {
                    try {
                        swipeRefreshLayout.setRefreshing(false);
                        RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        }
    }


//    @OnClick(R.id.btnDate)
//    public void chooseDate(View view) {
//        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                calendar.set(year, monthOfYear, dayOfMonth);
//                reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
//                reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
//                btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//                GetOp2(strState, 1);
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//    }
//
//    @OnClick(R.id.ivArrowRight)
//    public void nextDay(View view) {
//        calendar.add(Calendar.DATE, 1);
//        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
//        reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
//        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//        GetOp2(strState, 1);
//    }
//
//    @OnClick(R.id.ivArrowLeft)
//    public void preDay(View view) {
//        calendar.add(Calendar.DATE, -1);
//        reportDate = Utilities.formatDateTime_yyyyMMddHHmmssFromMili(calendar.getTimeInMillis());
//        reportDate2 = Utilities.formatDateTime_ddMMMyyyyFromMili(calendar.getTimeInMillis());
//        btnDate.setText(Utilities.formatDate_ddMMyyyy(reportDate));
//        GetOp2(strState, 1);
//    }

    @Override
    public void onRefresh() {
        totalAll();
        GetOp2(strState, 1);
    }

    public void totalAll() {
        loading = true;
        i = 1;
        list.clear();
        adapter.notifyDataSetChanged();
    }
}