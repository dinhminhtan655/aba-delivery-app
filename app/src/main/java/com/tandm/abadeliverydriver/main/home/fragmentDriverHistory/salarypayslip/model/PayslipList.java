package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayslipList {

    @SerializedName("items")
    public List<ItemPayslip> items;

}
