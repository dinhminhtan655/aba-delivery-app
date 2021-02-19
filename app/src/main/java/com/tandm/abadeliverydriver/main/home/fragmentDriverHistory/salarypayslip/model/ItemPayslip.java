package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.Serializable;

public class ItemPayslip implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("loai_xe")
    public String loai_xe;
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
    @SerializedName("ma_so_thue")
    public String ma_so_thue;
    @SerializedName("tong_TG_BHXH")
    public double tong_TG_BHXH;
    @SerializedName("tong_Chuyen")
    public int tong_Chuyen;
    @SerializedName("luong_Ngay_NP")
    public double luong_Ngay_NP;
    @SerializedName("soNgay_Hoc_Viec")
    public int soNgay_Hoc_Viec;
    @SerializedName("luong_Hoc_Viec")
    public double luong_Hoc_Viec;
    @SerializedName("luong_Chuyen_Chinh")
    public double luong_Chuyen_Chinh;
    @SerializedName("luong_Ho_Tro_Phu")
    public double luong_Ho_Tro_Phu;
    @SerializedName("thuong_Hotro_Le")
    public double thuong_Hotro_Le;
    @SerializedName("tong_Luong_Chuyen")
    public double tong_Luong_Chuyen;
    @SerializedName("luong_Bo_Sung_4B")
    public double luong_Bo_Sung_4B;
    @SerializedName("khac")
    public double khac;
    @SerializedName("tien_Dien_Thoai")
    public double tien_Dien_Thoai;
    @SerializedName("sinh_Nhat")
    public double sinh_Nhat;
    @SerializedName("phu_Cap_Doi_Truong")
    public double phu_Cap_Doi_Truong;
    @SerializedName("thuong_Dinh_Muc_Dau")
    public double thuong_Dinh_Muc_Dau;
    @SerializedName("tru_Dinh_Muc_Dau")
    public double tru_Dinh_Muc_Dau;
    @SerializedName("thanh_Tien_Thuong_Dau")
    public double thanh_Tien_Thuong_Dau;
    @SerializedName("thanh_Tien_Tru_Dau")
    public double thanh_Tien_Tru_Dau;
    @SerializedName("tong_Thu_Nhap")
    public double tong_Thu_Nhap;
    @SerializedName("thu_Nhap_Chiu_Thue")
    public double thu_Nhap_Chiu_Thue;
    @SerializedName("bhxh")
    public double bhxh;
    @SerializedName("bhyt")
    public double bhyt;
    @SerializedName("bhtn")
    public double bhtn;
    @SerializedName("trich_BHXH")
    public double trich_BHXH;
    @SerializedName("giam_Tru_BT")
    public double giam_Tru_BT;
    @SerializedName("so_Nguoi_Phu_Thuoc")
    public int so_Nguoi_Phu_Thuoc;
    @SerializedName("tong_GTGC")
    public double tong_GTGC;
    @SerializedName("tN_Tinh_Thue")
    public double tN_Tinh_Thue;
    @SerializedName("thue_Theo_Bac")
    public double thue_Theo_Bac;
    @SerializedName("thue_TNCN")
    public double thue_TNCN;
    @SerializedName("tN_Sau_Thue_BH")
    public double tN_Sau_Thue_BH;
    @SerializedName("tien_Coc_Da_Thu")
    public double tien_Coc_Da_Thu;
    @SerializedName("thu_Coc")
    public double thu_Coc;
    @SerializedName("tam_Ung_Boi_Thuong")
    public double tam_Ung_Boi_Thuong;
    @SerializedName("tam_Ung_Luong")
    public double tam_Ung_Luong;
    @SerializedName("khoan_Tru_Khac")
    public double khoan_Tru_Khac;
    @SerializedName("phi_Ngan_Hang")
    public double phi_Ngan_Hang;
    @SerializedName("truy_Thu_BHXH")
    public double truy_Thu_BHXH;
    @SerializedName("truy_Thu_Con_Lai")
    public double truy_Thu_Con_Lai;
    @SerializedName("thu_Nhap_Khac")
    public double thu_Nhap_Khac;
    @SerializedName("tong_Khau_Tru")
    public double tong_Khau_Tru;
    @SerializedName("le_Phi_Cong_Doan")
    public double le_Phi_Cong_Doan;
    @SerializedName("thuc_Lanh")
    public double thuc_Lanh;
    @SerializedName("note")
    public String note;



    public String  getLuongThangTitle(){
        return "Phiếu lương tháng " + Utilities.formatDate_MMyyyy(this.luong_Thang);
    }

}
