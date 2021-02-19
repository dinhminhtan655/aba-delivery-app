package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model;

import com.google.gson.annotations.SerializedName;

public class OrderType {

    @SerializedName("EDI_OrderTypeID")
    public int eDI_OrderTypeID;
    @SerializedName("EDI_OrderTypeDescription")
    public String eDI_OrderTypeDescription;
    @SerializedName("EDI_OrderType")
    public String eDI_OrderType;

    @Override
    public String toString() {
        return eDI_OrderTypeDescription;
    }
}
