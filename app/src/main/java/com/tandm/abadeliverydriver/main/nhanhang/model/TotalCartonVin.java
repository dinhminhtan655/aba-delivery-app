package com.tandm.abadeliverydriver.main.nhanhang.model;

import com.google.gson.annotations.SerializedName;

public class TotalCartonVin {

    @SerializedName("atM_OrderReleaseID")
    public String atM_OrderReleaseID;
    @SerializedName("boxQuantity")
    public int boxQuantity;
    @SerializedName("shipToCode")
    public String shipToCode;

}
