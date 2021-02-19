package com.tandm.abadeliverydriver.main.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel {

    ItemsRepository itemsRepository;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
        itemsRepository = new ItemsRepository();
    }

    public LiveData<List<ItemChild>> getAllItems(String maCh, String date, String khachHang, String orderrelease_id, Context context) {

        return itemsRepository.getMutableLiveDataItems(maCh, date, khachHang, orderrelease_id, context);
    }

    public LiveData<List<ItemChild>> getAllItems2(String maCh, String date, String khachHang, String orderrelease_id, Context context) {
        return itemsRepository.getMutableLiveDataItems2(maCh, date, khachHang, orderrelease_id, context);
    }

    public LiveData<List<ItemChild>> getAllItems3(String Delivery_Date, String Store_Code, String ATM_SHIPMENT_ID, String orderrelease_id,String customerCode, Context context) {
        return itemsRepository.getMutableLiveDataItems3(Delivery_Date, Store_Code, ATM_SHIPMENT_ID, orderrelease_id,customerCode, context);
    }
}
