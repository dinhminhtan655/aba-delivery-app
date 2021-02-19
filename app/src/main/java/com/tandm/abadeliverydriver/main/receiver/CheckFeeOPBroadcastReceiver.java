package com.tandm.abadeliverydriver.main.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.MainActivity;
import com.tandm.abadeliverydriver.main.home.fragmentop.model.NumberExpensesWaiting;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.service.CheckFeeOPService;
import com.tandm.abadeliverydriver.main.utilities.NotificationHelper;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckFeeOPBroadcastReceiver extends BroadcastReceiver {
    String strRegion = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent serviceIntent = new Intent(context, CheckFeeOPService.class);
            context.startService(serviceIntent);
        } else {
//            notificationHelper.sendHighPriorityNotification("Test", "Test Thôi",MainActivity.class);
                if (LoginPrefer.getObject(context).Region.equals("MN")){
                    strRegion = "2001-TRUCKING";
                }else {
                    strRegion = "2002-TRUCKING";
                }
                TriggerNotifiOPExpenses(strRegion, notificationHelper, context);
        }
    }

    private void TriggerNotifiOPExpenses(String strRegion, NotificationHelper notificationHelper, Context context) {
        MyRetrofit.initRequest(context).CheckFeeOPWaiting("Bearer " + LoginPrefer.getObject(context).access_token, strRegion, LoginPrefer.getObject(context).Position).enqueue(new Callback<NumberExpensesWaiting>() {
            @Override
            public void onResponse(Call<NumberExpensesWaiting> call, Response<NumberExpensesWaiting> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().number > 0) {
                        notificationHelper.sendHighPriorityNotification("Expenses", "Xin Chào "
                                + LoginPrefer.getObject(context).fullName + ". Từ " + Utilities.formatDate_ddMMyyyy(response.body().minDate) + " đến "
                                + Utilities.formatDate_ddMMyyyy(response.body().maxDate) + " bạn còn " + response.body().number + " phí chưa duyệt. Vui lòng kiểm tra", MainActivity.class);
                    }
                }
            }

            @Override
            public void onFailure(Call<NumberExpensesWaiting> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

}
