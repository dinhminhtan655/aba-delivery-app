package com.tandm.abadeliverydriver.main.ViewModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.nhanhang.model.Item;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemsRepository {

    private static final String TAG = "ItemsRepository";
    private ArrayList<ItemChild> itemChildren = new ArrayList<>();
    private List<ItemChild> itemChildren2 = new ArrayList<>();
    private MutableLiveData<List<ItemChild>> mutableLiveDataItems = new MutableLiveData<>();
    View view;
    ProgressDialog progressDialog;


    public ItemsRepository() {
    }


    public MutableLiveData<List<ItemChild>> getMutableLiveDataItems(String maCH, String date, String khachHang, String orderrelease_id, Context context) {

        progressDialog = Utilities.getProgressDialog(context, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(context)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(context, new NoInternet(), TAG, view);
        }

        MyRetrofit.initRequest(context).getItemsPGH(maCH, date, khachHang, orderrelease_id, "Bearer " + LoginPrefer.getObject(context).access_token).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response != null) {
                    Item item = response.body();
                    if (item != null && item.getItems() != null) {
                        itemChildren = (ArrayList<ItemChild>) item.getItems();
                        mutableLiveDataItems.setValue(itemChildren);
                    }
                }
                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(context, new NoInternet(), TAG, view);
            }
        });

        return mutableLiveDataItems;
    }


    public MutableLiveData<List<ItemChild>> getMutableLiveDataItems2(String maCH, String date, String khachHang, String orderrelease_id, Context context) {

        progressDialog = Utilities.getProgressDialog(context, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(context)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(context, new NoInternet(), TAG, view);
        }

        MyRetrofit.initRequest(context).getItemsPGH2(maCH, date, khachHang, orderrelease_id, "Bearer " + LoginPrefer.getObject(context).access_token).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response != null) {
                    Item item = response.body();
                    if (item != null && item.getItems() != null) {
                        itemChildren = (ArrayList<ItemChild>) item.getItems();
                        mutableLiveDataItems.setValue(itemChildren);
                    }
                }

                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(context, new NoInternet(), TAG, view);
            }
        });

        return mutableLiveDataItems;
    }


    public MutableLiveData<List<ItemChild>> getMutableLiveDataItems3(String Delivery_Date, String Store_Code, String ATM_SHIPMENT_ID, String orderrelease_id,String customerCode, Context context) {
        progressDialog = Utilities.getProgressDialog(context, "Đang tải...");
        progressDialog.show();

        if (!WifiHelper.isConnected(context)) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(context, new NoInternet(), TAG, view);
        }

        MyRetrofit.initRequest(context).getHistoryDeliveryNote("Bearer " + LoginPrefer.getObject(context).access_token, Delivery_Date, Store_Code, ATM_SHIPMENT_ID, orderrelease_id,customerCode).enqueue(new Callback<List<ItemChild>>() {
            @Override
            public void onResponse(Call<List<ItemChild>> call, Response<List<ItemChild>> response) {
                if (response.isSuccessful() && response != null) {
                    itemChildren2 = response.body();
                    mutableLiveDataItems.setValue(itemChildren2);
                }

                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<List<ItemChild>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(context, new NoInternet(), TAG, view);
            }
        });

        return mutableLiveDataItems;
    }


}
