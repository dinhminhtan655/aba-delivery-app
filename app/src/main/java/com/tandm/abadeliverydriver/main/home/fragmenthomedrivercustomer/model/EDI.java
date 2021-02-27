package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model;

import com.google.gson.annotations.SerializedName;

public class EDI {
    @SerializedName("EDI_OrderID")
    public String eDI_OrderID;
    @SerializedName("OrderDate")
    public String orderDate;
    @SerializedName("TruckNumber")
    public String truckNumber;
    @SerializedName("CustomerReference")
    public String customerReference;
    @SerializedName("EDIOrderRemark")
    public String eDIOrderRemark;
    @SerializedName("StatusDescription")
    public String statusDescription;
    @SerializedName("TimeSlot")
    public String timeSlot;
    @SerializedName("VehicleType")
    public String vehicleType;
    @SerializedName("DockNumber")
    public String dockNumber;
    @SerializedName("TotalWeights")
    public double totalWeights;
    @SerializedName("TotalQuantity")
    public int totalQuantity;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("OrderNumber")
    public String orderNumber;
    @SerializedName("RatingValue")
    public float ratingValue;
    @SerializedName("Comment")
    public String comment;
    @SerializedName("CommentBy")
    public String commentBy;
    @SerializedName("CommentTime")
    public String commentTime;
    @SerializedName("EDI_OrderTypeDescription")
    public String eDI_OrderTypeDescription;
    @SerializedName("EDI_OrderType")
    public String eDI_OrderType;
    @SerializedName("IsArrived")
    public boolean IsArrived;

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public boolean isArrived() {
        return IsArrived;
    }

    public void setArrived(boolean arrived) {
        IsArrived = arrived;
    }
}
