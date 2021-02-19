package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery;

import com.google.gson.annotations.SerializedName;

public class HistoryTrayDeliveryNotesChild {

    @SerializedName("id")
    public int id;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("storeID")
    public String storeID;
    @SerializedName("atM_OrderreleaseID")
    public String atM_OrderreleaseID;
    @SerializedName("trayDelivering")
    public int trayDelivering;
    @SerializedName("trayReceiving")
    public int trayReceiving;
    @SerializedName("trayName")
    public String trayName;
    @SerializedName("deliveredBy")
    public String deliveredBy;
    @SerializedName("deliveredAt")
    public String deliveredAt;
    @SerializedName("latDelivered")
    public double latDelivered;
    @SerializedName("lngDelivered")
    public double lngDelivered;
    @SerializedName("updatedBy")
    public String updatedBy;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("customerID")
    public String customerID;
    @SerializedName("packageItemID")
    public String packageItemID;


    public HistoryTrayDeliveryNotesChild(int id, int trayDelivering, int trayReceiving) {
        this.id = id;
        this.trayDelivering = trayDelivering;
        this.trayReceiving = trayReceiving;
    }

    private String editTextUpdateKhayGiaoCH;

    private String editTextUpdateKhayLayVeTuCH;

    private int selected;

    private boolean isChecked;

    public String getEditTextUpdateKhayGiaoCH() {
        return editTextUpdateKhayGiaoCH;
    }

    public void setEditTextUpdateKhayGiaoCH(String editTextUpdateKhayGiaoCH) {
        this.editTextUpdateKhayGiaoCH = editTextUpdateKhayGiaoCH;
    }

    public String getEditTextUpdateKhayLayVeTuCH() {
        return editTextUpdateKhayLayVeTuCH;
    }

    public void setEditTextUpdateKhayLayVeTuCH(String editTextUpdateKhayLayVeTuCH) {
        this.editTextUpdateKhayLayVeTuCH = editTextUpdateKhayLayVeTuCH;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
