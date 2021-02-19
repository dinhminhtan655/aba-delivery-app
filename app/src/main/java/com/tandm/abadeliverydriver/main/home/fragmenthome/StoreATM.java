package com.tandm.abadeliverydriver.main.home.fragmenthome;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreATM implements Serializable {

    @SerializedName("customerCode")
    public String customerCode;
    @SerializedName("arrivedByDeliverer")
    public boolean arrivedByDeliverer;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("atmOrderReleaseID")
    public String atmOrderReleaseID;
    @SerializedName("locationGID")
    public String locationGID;
    @SerializedName("locationName")
    public String locationName;
    @SerializedName("addressLine")
    public String addressLine;
    @SerializedName("packagedItem")
    public String packagedItem;
    @SerializedName("isCompleted")
    public boolean isCompleted;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lon")
    public String lon;
    @SerializedName("totalCarton")
    public String totalCarton;
    @SerializedName("totalWeight")
    public String totalWeight;
    @SerializedName("vehicle")
    public String vehicle;
    @SerializedName("deliveredBy")
    public String deliveredBy;
    @SerializedName("fullName")
    public String fullName;

}
