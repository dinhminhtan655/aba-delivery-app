package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

import com.google.gson.annotations.SerializedName;

public class ResultImage {

    @SerializedName("rowPointer")
    public String rowPointer;
    @SerializedName("id")
    public String id;
    @SerializedName("atmBuyshipment")
    public String atmBuyshipment;
    @SerializedName("documentType")
    public String documentType;
    @SerializedName("attachName")
    public String attachName;
    @SerializedName("attachment")
    public String attachment;

    public ResultImage(String id, String atmBuyshipment, String documentType) {
        this.id = id;
        this.atmBuyshipment = atmBuyshipment;
        this.documentType = documentType;
    }
}
