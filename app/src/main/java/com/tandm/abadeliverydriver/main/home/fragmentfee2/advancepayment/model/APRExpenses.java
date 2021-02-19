package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

import com.google.gson.annotations.SerializedName;

public class APRExpenses {

    @SerializedName("id")
    public int id;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("advancePaymentType")
    public String advancePaymentType;

    public APRExpenses(int id, String advancePaymentType) {
        this.id = id;
        this.advancePaymentType = advancePaymentType;
    }
}
