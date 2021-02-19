package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.Serializable;
import java.text.ParseException;

public class ExpensesAmount implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("otmsHipmentID")
    public String otmsHipmentID;
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
    @SerializedName("invoiceDate")
    public String invoiceDate2;
    @SerializedName("invoiceNumber")
    public String invoiceNumber;
    @SerializedName("advancePaymentType")
    public String advancePaymentType;
    @SerializedName("description")
    public String description;
    @SerializedName("amount")
    public int amount;
    @SerializedName("finApproved")
    public String finApproved;
    @SerializedName("seApproved")
    public String seApproved;
    @SerializedName("manager")
    public String manager;
    @SerializedName("createDate")
    public String createDate;
    @SerializedName("approveStatus")
    public String approveStatus;
    @SerializedName("remark")
    public String remark;
    @SerializedName("opApproved")
    public String opApproved;
    @SerializedName("updatedByManager")
    public String updatedByManager;
    @SerializedName("updatedByOP")
    public String updatedByOP;
    @SerializedName("city")
    public String city;
    @SerializedName("startTime")
    public String startTime;
//    @SerializedName("technicalApproved")
//    public String technicalApproved;
//    @SerializedName("techManagerApproved")
//    public String techManagerApproved;


    public String getDateExpenses(){
        return Utilities.formatDate_ddMMyyyy(invoiceDate2);
    }

    public String getFormatCurrency(){
        return Utilities.formatNumber(String.valueOf(amount));
    }

    public String getInvoiceDate() throws ParseException {
        return Utilities.formatDateTimeddMMyyyyHHmmss_ToMilisecond(invoiceDate2);
    }



}
