package com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model;

import com.google.gson.annotations.SerializedName;

public class GiaoBuDetail {

    @SerializedName("rowId")
    public String rowId;
    @SerializedName("item_Code")
    public String item_Code;
    @SerializedName("item_Name")
    public String item_Name;
    @SerializedName("soBich")
    public int soBich;
    @SerializedName("actual_Received")
    public int actual_Received;
    @SerializedName("giaoBu")
    public int giaoBu;


    public String getSoBich(){
        int soB = soBich;
        return String.valueOf(soB);
    }

    public String getSoActual(){
        int soA = actual_Received;
        return String.valueOf(soA);
    }

    public String getSoGiaoBu(){
        int soG = giaoBu;
        return String.valueOf(soG);
    }





}
