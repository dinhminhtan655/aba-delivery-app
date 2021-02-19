package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShipmentBikerForDriver implements Serializable {

    @SerializedName("locationGID")
    public String locationGID;
    @SerializedName("locationName")
    public String locationName;
    @SerializedName("addressLine")
    public String addressLine;
    @SerializedName("vehicle")
    public String vehicle;
    @SerializedName("driverName")
    public String driverName;
    @SerializedName("customerCode")
    public String customerCode;


    public String getStoreCode(){
            if (customerCode.trim().equals("MASAN")){
                return locationName;
            }else {
                return locationGID;
            }
        }
    
}
