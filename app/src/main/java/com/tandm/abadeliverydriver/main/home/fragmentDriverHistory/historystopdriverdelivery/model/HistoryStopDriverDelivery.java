package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model;

import com.google.gson.annotations.SerializedName;

public class HistoryStopDriverDelivery {
    @SerializedName("planneD_ARRIVAL")
    public String planneD_ARRIVAL;
    @SerializedName("delivery_Time")
    public String delivery_Time;
    @SerializedName("store_Name")
    public String store_Name;
    @SerializedName("store_Code_ABA")
    public String store_Code_ABA;
    @SerializedName("store_Code")
    public String store_Code;
    @SerializedName("atM_Shipment_ID")
    public String atM_Shipment_ID;
    @SerializedName("orderrelease_id")
    public String orderrelease_id;
    @SerializedName("packaged_Item_XID")
    public String packaged_Item_XID;
    @SerializedName("addresS_LINE")
    public String addresS_LINE;
    @SerializedName("real_Num_Delivered")
    public int real_Num_Delivered;
    @SerializedName("totalCartonMasan")
    public int totalCartonMasan;
    @SerializedName("totalTray")
    public int totalTray;
    @SerializedName("enough")
    public boolean enough;
    @SerializedName("deficient")
    public int deficient;
    @SerializedName("broken")
    public int broken;
    @SerializedName("residual")
    public int residual;
    @SerializedName("bad_Temperature")
    public int bad_Temperature;
    @SerializedName("khachHang")
    public String khachHang;
    @SerializedName("adJ_deliver_qty")
    public int adJ_deliver_qty;

    String[] date, date2, time, time2;
    public String getDatePlannedTime(String planneD_ARRIVAL){
        if(planneD_ARRIVAL == null){
            return "Chưa xác định";
        }else {
            date = planneD_ARRIVAL.split("T");
        }
        return date[0];
    }

    public String getTimePlannedTime(String planneD_ARRIVAL){
        if(planneD_ARRIVAL == null){
            return "Chưa xác định";
        }else {
            time = planneD_ARRIVAL.split("T");
        }
        return time[1];
    }

    public String getDateDeliveryTime(String delivery_Time){
        if(delivery_Time == null){
            return "Chưa xác định";
        }else {
            date2 = delivery_Time.split("T");
        }
        return date2[0];
    }

    public String getTimeDeliveryTime(String delivery_Time){
        if(delivery_Time == null){
            return "Chưa xác định";
        }else {
            time2 = delivery_Time.split("T");
        }
        return time2[1];
    }


    public int getTotalWarehouse(int real_Num_Delivered, int deficient, int broken, int residual, int bad_Temperature){
        return real_Num_Delivered + deficient + broken + residual + bad_Temperature;
    }
}
