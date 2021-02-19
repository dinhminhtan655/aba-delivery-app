package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model;

import com.google.gson.annotations.SerializedName;

public class HistoryStopDriverDelivery2 extends HistoryStopDriverDelivery {

    @SerializedName("locatioN_GID")
    public String locatioN_GID;
    @SerializedName("daToi")
    public boolean daToi;
    @SerializedName("status")
    public String status;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lng")
    public String lng;
    @SerializedName("gioToi")
    public String gioToi;

}
