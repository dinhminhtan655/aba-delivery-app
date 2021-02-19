package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.Serializable;

public class Items implements Serializable {

    @SerializedName("shipmenT_ID")
    public String shipmenT_ID;
    @SerializedName("tranS_DATE")
    public String tranS_DATE;
    @SerializedName("driveR_ID")
    public String driveR_ID;
    @SerializedName("driveR_NAME")
    public String driveR_NAME;
    @SerializedName("salary")
    public double salary;
    @SerializedName("customeR_NAME")
    public String customeR_NAME;
    @SerializedName("status")
    public String status;
    @SerializedName("description")
    public String description;
    @SerializedName("comment")
    public String comment;


    public String getSalary(){
        return Utilities.formatNumber(String.valueOf((int) salary)) + " vnđ";
//        return salary + " vnđ";
    }

//    public String getSalaryAmount(){
//        return Utilities.formatNumber(String.valueOf(salarY_AMOUNT)) + " vnđ";
//    }


    public String getDate(){
        String splitDate;
        splitDate = Utilities.formatDate_ddMMyyyy(tranS_DATE);
        return splitDate;
    }

}
