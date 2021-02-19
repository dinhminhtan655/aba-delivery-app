package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model;

import com.google.gson.annotations.SerializedName;

public class ADCADShipment {

    @SerializedName("id")
    public int id;
    @SerializedName("atmShipmentId")
    public String atmShipmentId;
    @SerializedName("startTime")
    public String startTime;
    @SerializedName("driverName")
    public String driverName;
    @SerializedName("driverID")
    public String driverID;
    @SerializedName("createdDate")
    public String createdDate;

}
