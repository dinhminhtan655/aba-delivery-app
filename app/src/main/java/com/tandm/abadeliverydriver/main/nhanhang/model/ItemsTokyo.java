package com.tandm.abadeliverydriver.main.nhanhang.model;

import com.google.gson.annotations.SerializedName;

public class ItemsTokyo {

    @SerializedName("rowId")
    public String rowId;
    @SerializedName("customerNumber")
    public String customerNumber;
    @SerializedName("customerName")
    public String customerName;
    @SerializedName("dispatchingOrderDate")
    public String dispatchingOrderDate;
    @SerializedName("customerClientName")
    public String customerClientName;
    @SerializedName("shiP_TO_LOCATION")
    public String shiP_TO_LOCATION;
    @SerializedName("shiP_TO_ADDRESS")
    public String shiP_TO_ADDRESS;
    @SerializedName("productNumber")
    public String productNumber;
    @SerializedName("productName")
    public String productName;
    @SerializedName("qty")
    public int qty;

}
