package com.tandm.abadeliverydriver.main.giaohang.model;

import com.google.gson.annotations.SerializedName;

public class ImageGiaoHang {

    @SerializedName("id")
    public int id;
    @SerializedName("store_Code")
    public String store_Code;
    @SerializedName("delivery_Date")
    public String delivery_Date;
    @SerializedName("hinhAnh")
    public String hinhAnh;
    @SerializedName("ngayTao")
    public String ngayTao;
    @SerializedName("nguoiTao")
    public String nguoiTao;
    @SerializedName("khachHang")
    public String khachHang;
    @SerializedName("note")
    public String note;
}
