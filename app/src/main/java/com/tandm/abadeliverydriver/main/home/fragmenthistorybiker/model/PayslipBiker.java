package com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.Serializable;

public class PayslipBiker implements Serializable {

    @SerializedName("id")
    public String id;
    @SerializedName("luong_Thang")
    public String luong_Thang;
    @SerializedName("ten_Nhan_Vien")
    public String ten_Nhan_Vien;
    @SerializedName("ma_Nhan_Vien")
    public String ma_Nhan_Vien;
    @SerializedName("ngay_Vao_Lam")
    public String ngay_Vao_Lam;
    @SerializedName("vi_tri")
    public String vi_tri;
    @SerializedName("khay_Nho")
    public String khay_Nho;
    @SerializedName("khay_Lon")
    public String khay_Lon;
    @SerializedName("tong_Luong_Chuyen")
    public double tong_Luong_Chuyen;
    @SerializedName("gIao_Bu")
    public String gIao_Bu;
    @SerializedName("ngay_Lam_VIec_Dat")
    public String ngay_Lam_VIec_Dat;
    @SerializedName("kpI_Ngay")
    public double kpI_Ngay;
    @SerializedName("kpI_Thang")
    public double kpI_Thang;
    @SerializedName("bu_San_Luong")
    public double bu_San_Luong;
    @SerializedName("bo_Sung")
    public double bo_Sung;
    @SerializedName("thue_TNCN")
    public double thue_TNCN;
    @SerializedName("phi_Khac")
    public double phi_Khac;
    @SerializedName("coc")
    public double coc;
    @SerializedName("thuc_Lanh")
    public double thuc_Lanh;
    @SerializedName("note")
    public String note;
    @SerializedName("tong_Khoan_Tru")
    public double tong_Khoan_Tru;
    @SerializedName("tong_Tien_Khay_Lon")
    public double tong_Tien_Khay_Lon;
    @SerializedName("tong_Tien_Khay_Nho")
    public double tong_Tien_Khay_Nho;
    @SerializedName("tong_Tien_Giao_Bu")
    public double tong_Tien_Giao_Bu;
    @SerializedName("tong_Tien_Ngay_Lam_Viec_Dat")
    public double tong_Tien_Ngay_Lam_Viec_Dat;
    @SerializedName("tong_Tien_Coc_Da_Thu")
    public double tong_Tien_Coc_Da_Thu;

    public String  getLuongThangTitle(){
        return "Phiếu lương tháng " + Utilities.formatDate_MMyyyy(this.luong_Thang);
    }
    
}
