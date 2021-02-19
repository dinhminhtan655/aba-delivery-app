package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Customer;

public class StoreDriver {

    @SerializedName("shipmenT_GID")
    public String shipmenT_GID;
    @SerializedName("stoP_TYPE")
    public String stoP_TYPE;
    @SerializedName("store_Code_ABA")
    public String store_Code_ABA;
    @SerializedName("locatioN_GID")
    public String locatioN_GID;
    @SerializedName("store_Code")
    public String store_Code;
    @SerializedName("store_Name")
    public String store_Name;
    @SerializedName("store_Name_ABA")
    public String store_Name_ABA;
    @SerializedName("addresS_LINE")
    public String addresS_LINE;
    @SerializedName("atM_SHIPMENT_ID")
    public String atM_SHIPMENT_ID;
    @SerializedName("planneD_ARRIVAL")
    public String planneD_ARRIVAL;
    @SerializedName("mobileHub")
    public boolean mobileHub;
    @SerializedName("isCompleted")
    public boolean isCompleted;
    @SerializedName("khachHang")
    public String khachHang;
    @SerializedName("delivery_Date")
    public String delivery_Date;
    @SerializedName("totalCarton")
    public int totalCarton;
    @SerializedName("daToi")
    public boolean daToi;
    @SerializedName("latToi")
    public String latToi;
    @SerializedName("lngToi")
    public String lngToi;
    @SerializedName("gioToi")
    public String gioToi;
    @SerializedName("customerClientPhone")
    public String customerClientPhone;
    @SerializedName("totalWeight")
    public String totalWeight;
    @SerializedName("orderreleasE_ID")
    public String orderreleasE_ID;
    @SerializedName("packaged_Item_XID")
    public String packaged_Item_XID;
    @SerializedName("lat_Store")
    public String lat_Store;
    @SerializedName("lon_Store")
    public String lon_Store;
    @SerializedName("locationNoABA")
    public String locationNoABA;
    @SerializedName("vehicle")
    public String vehicle;

    private boolean bDaToi;

    private int tvSLThungGiaoCHValue;

    private boolean bEdit;

    public boolean isbDaToi() {
        return bDaToi;
    }

    public void setbDaToi(boolean bDaToi) {
        this.bDaToi = bDaToi;
    }

    public int getTvSLThungGiaoCHValue() {
        return tvSLThungGiaoCHValue;
    }

    public void setTvSLThungGiaoCHValue(int tvSLThungGiaoCHValue) {
        this.tvSLThungGiaoCHValue = tvSLThungGiaoCHValue;
    }

    public boolean isbEdit() {
        return bEdit;
    }

    public void setbEdit(boolean bEdit) {
        this.bEdit = bEdit;
    }

    public String getStoreCode(String stopType) {
        if (stopType.trim().equals("P")) {
            return "Kho";
        } else {
            if (khachHang.trim().equals(Customer.MASAN) || khachHang.trim().equals(Customer.THREEF)) {
                return store_Name + "\n(" + khachHang + ")";
            } else {
                return store_Code;
            }
        }
    }


    public String getStoreCodeABA(String stopType) {
        if (stopType.trim().equals("P")) {
            return "Kho";
        } else {
            if (khachHang.trim().equals(Customer.MASAN) || khachHang.trim().equals(Customer.THREEF)) {
                return store_Name_ABA + "\n(" + khachHang + ")";
            } else {
                return store_Code_ABA;
            }
        }
    }


    public String getTotalCarton(int totalCarton) {
        return String.valueOf(totalCarton);
    }


    public String getCustomerClientPhone(String customerClientPhone) {
        if (customerClientPhone == null || customerClientPhone.equals("")) {
            return "Chưa xác định";
        } else {
            return customerClientPhone;
        }
    }

}
