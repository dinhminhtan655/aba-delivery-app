package com.tandm.abadeliverydriver.main.nhanhang.model;

import com.google.gson.annotations.SerializedName;

public class ItemChild {

    @SerializedName("rowId")
    public String rowId;
    @SerializedName("xdocK_Doc_Entry")
    public String xdocK_Doc_Entry;
    @SerializedName("route_ID")
    public String route_ID;
    @SerializedName("store_Code")
    public String store_Code;
    @SerializedName("delivery_Date")
    public String delivery_Date;
    @SerializedName("item_Code")
    public String item_Code;
    @SerializedName("item_Name")
    public String item_Name;
    @SerializedName("soBich")
    public int soBich;
    @SerializedName("soKi")
    public double soKi;
    @SerializedName("div_Unit")
    public String div_Unit;
    @SerializedName("actual_Received")
    public String actual_Received;
    @SerializedName("actualWeightReceived")
    public double actualWeightReceived;
    @SerializedName("notes")
    public String notes;
    @SerializedName("created")
    public String created;
    @SerializedName("createdBy")
    public String createdBy;
    @SerializedName("modified")
    public String modified;
    @SerializedName("modifiedBy")
    public String modifiedBy;
    @SerializedName("item_event_id")
    public String item_event_id;
    @SerializedName("khachHang")
    public String khachHang;
    @SerializedName("atM_SHIPMENT_ID")
    public String atM_SHIPMENT_ID;
    @SerializedName("orderrelease_id")
    public String orderrelease_id;
    @SerializedName("packaged_Item_XID")
    public String packaged_Item_XID;
    @SerializedName("thieu")
    public int thieu;
    @SerializedName("thua")
    public int thua;
    @SerializedName("traVe")
    public int traVe;
    @SerializedName("trave")
    public int trave;



    private boolean expanded;

//    private int editTextValue;

    private String editTextNote;

    private String editTextUpdateKhayGiaoCH;

    private String editTextUpdateKhayLayVeTuCH;

    private int selected;

    private boolean isChecked;

    private String strSelected;

    private boolean isCheckedThieu;

    private boolean isCheckedDu;

    private boolean isCheckedTrave;

    private int tvSLGiaoCHValue;

    private int tvSLThieuValue;

    private int tvSLDuValue;

    private int tvSLTraVeValue;

    private boolean isBtnCongThieu;

    private boolean isBtnTruThieu;

    private boolean isBtnCongDu;

    private boolean isBtnTruDu;

    private boolean isBtnCongTraVe;

    private boolean isBtnTruTraVe;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getEditTextNote() {
        return editTextNote;
    }

    public void setEditTextNote(String editTextNote) {
        this.editTextNote = editTextNote;
    }

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }



    //----------------------------------Update Thiếu Dư Hỏng-----------------------


    public boolean isCheckedThieu() {
        return isCheckedThieu;
    }

    public void setCheckedThieu(boolean checkedThieu) {
        isCheckedThieu = checkedThieu;
    }

    public boolean isCheckedDu() {
        return isCheckedDu;
    }

    public void setCheckedDu(boolean checkedDu) {
        isCheckedDu = checkedDu;
    }

    public boolean isCheckedTrave() {
        return isCheckedTrave;
    }

    public void setCheckedTrave(boolean checkedTrave) {
        isCheckedTrave = checkedTrave;
    }


    public int getTvSLGiaoCHValue() {
        return tvSLGiaoCHValue;
    }

    public void setTvSLGiaoCHValue(int tvSLGiaoCHValue) {
        this.tvSLGiaoCHValue = tvSLGiaoCHValue;
    }

    public int getTvSLThieuValue() {
        return tvSLThieuValue;
    }

    public void setTvSLThieuValue(int tvSLThieuValue) {
        this.tvSLThieuValue = tvSLThieuValue;
    }

    public int getTvSLDuValue() {
        return tvSLDuValue;
    }

    public void setTvSLDuValue(int tvSLDuValue) {
        this.tvSLDuValue = tvSLDuValue;
    }

    public int getTvSLTraVeValue() {
        return tvSLTraVeValue;
    }

    public void setTvSLTraVeValue(int tvSLTraVeValue) {
        this.tvSLTraVeValue = tvSLTraVeValue;
    }
}
