package com.tandm.abadeliverydriver.main.home.fragmentfee;

import com.google.gson.annotations.SerializedName;

public class ImageFee {

    @SerializedName("id")
    public int id;
    @SerializedName("driver_User")
    public String driver_User;
    @SerializedName("delivery_Date")
    public String delivery_Date;
    @SerializedName("iD_LoaiPhi")
    public String iD_LoaiPhi;
    @SerializedName("soTien")
    public double soTien;
    @SerializedName("ghiChu")
    public String ghiChu;
    @SerializedName("time_Create")
    public String time_Create;
    @SerializedName("physicalFileName")
    public String physicalFileName;
}
