package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

public class ItemsDetail {

    @SerializedName("shipmenT_ID")
    public String shipmenT_ID;
    @SerializedName("salarY_ELEMENT")
    public String salarY_ELEMENT;
    @SerializedName("salarY_AMOUNT")
    public double salarY_AMOUNT;
    @SerializedName("description")
    public String description;


    public String getSalaryAmount(){
        return Utilities.formatNumber(String.valueOf((int) salarY_AMOUNT)) + " vnđ";
//        return salary + " vnđ";
    }

}
