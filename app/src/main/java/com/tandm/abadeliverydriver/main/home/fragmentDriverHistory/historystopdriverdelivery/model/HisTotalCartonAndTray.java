package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model;

import com.google.gson.annotations.SerializedName;

public class HisTotalCartonAndTray {

    @SerializedName("realNumDelivered")
    public int realNumDelivered;
    @SerializedName("totalCartonMasan")
    public int totalCartonMasan;
    @SerializedName("totalTray")
    public int totalTray;
    @SerializedName("productRecall")
    public int productRecall;

}
