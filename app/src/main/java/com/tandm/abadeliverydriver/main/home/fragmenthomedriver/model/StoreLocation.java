package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model;

import com.google.gson.annotations.SerializedName;

public class StoreLocation {

    @SerializedName("lat_Store")
    public String lat_Store;
    @SerializedName("lon_Store")
    public String lon_Store;
    @SerializedName("atM_Shipment_ID")
    public String atM_Shipment_ID;
    @SerializedName("orderrelease_id")
    public String orderrelease_id;
    
}
