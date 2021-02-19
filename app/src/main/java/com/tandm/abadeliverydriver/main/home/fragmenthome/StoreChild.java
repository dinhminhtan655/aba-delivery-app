package com.tandm.abadeliverydriver.main.home.fragmenthome;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreChild{

    @SerializedName("store_Code")
    @Expose
    public String store_Code;
    @SerializedName("store_Name")
    @Expose
    public String store_Name;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lng")
    @Expose
    public String lng;

    public String currentDate = Utilities.formatDate_ddMMyyyy(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()));

}
