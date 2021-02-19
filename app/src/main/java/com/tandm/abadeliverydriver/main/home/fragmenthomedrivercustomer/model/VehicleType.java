package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model;

import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @SerializedName("VehicleType")
    public String vehicleType;
    @SerializedName("Description")
    public String description;
    @SerializedName("Category")
    public String category;
    @SerializedName("VehicleCapacity")
    public double vehicleCapacity;

    @Override
    public String toString() {
        return description;
    }
}
