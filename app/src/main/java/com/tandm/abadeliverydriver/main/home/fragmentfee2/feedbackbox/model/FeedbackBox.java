package com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.model;

import com.google.gson.annotations.SerializedName;

public class FeedbackBox {

    @SerializedName("id")
    public int id;
    @SerializedName("driverName")
    public String driverName;
    @SerializedName("driverID")
    public String driverID;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("type")
    public String type;
    @SerializedName("title")
    public String title;
    @SerializedName("content")
    public String content;
    @SerializedName("createDate")
    public String createDate;

    public FeedbackBox(String driverName, String driverID, String atmShipmentID, String type, String title, String content) {
        this.driverName = driverName;
        this.driverID = driverID;
        this.atmShipmentID = atmShipmentID;
        this.type = type;
        this.title = title;
        this.content = content;
    }
}
