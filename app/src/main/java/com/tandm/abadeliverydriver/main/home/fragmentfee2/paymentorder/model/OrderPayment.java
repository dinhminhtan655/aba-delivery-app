package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.Serializable;

public class OrderPayment implements Serializable {

    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("powerUnit")
    public String powerUnit;
    @SerializedName("employeeID")
    public String employeeID;
    @SerializedName("employeeName")
    public String employeeName;
    @SerializedName("department")
    public String department;
    @SerializedName("customer")
    public String customer;
    @SerializedName("invoiceNumber")
    public String invoiceNumber;
    @SerializedName("advancePaymentType")
    public String advancePaymentType;
    @SerializedName("amount")
    public int amount;
    @SerializedName("amountAdjustment")
    public int amountAdjustment;
    @SerializedName("amountTotal")
    public int amountTotal;
    @SerializedName("startTime")
    public String startTime;

    public String formartVND(int amount) {
        return Utilities.formatNumber(String.valueOf(amount)) + " VNĐ";
    }

    public String totalRemaining(int amountTotal, int amount) {
        int total = amountTotal - amount;
//        if (total > 0){
        return Utilities.formatNumber(String.valueOf(total)) + " VNĐ";
//        }else {
//            return Utilities.formatNumber("0")+" VNĐ";
//        }

    }

    public String SplitStartTime(String startTime) {
        String[] strStartTime = startTime.split("T");
        return strStartTime[0];
    }


    public String totalRemaining2(int amountTotal, int amount2) {
        int total2 = amountTotal - amount2;
//        if (total2 > 0) {
        return Utilities.formatNumber(String.valueOf(total2)) + " VNĐ";
//        } else {
//            return Utilities.formatNumber("0") + " VNĐ";
//        }

    }

}
