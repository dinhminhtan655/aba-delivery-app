package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model;

import com.google.gson.annotations.SerializedName;

public class Dock {

    @SerializedName("DockDoorID")
    public int dockDoorID;
    @SerializedName("DockNumber")
    public String dockNumber;
    @SerializedName("DockDoorRemark")
    public String dockDoorRemark;

    @Override
    public String toString() {
        return dockNumber;
    }
}
