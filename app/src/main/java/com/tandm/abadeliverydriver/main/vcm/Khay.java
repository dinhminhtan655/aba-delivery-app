package com.tandm.abadeliverydriver.main.vcm;

import com.google.gson.annotations.SerializedName;

public class Khay {

    @SerializedName("id")
    public String id;
    @SerializedName("maKhay")
    public String maKhay;
    @SerializedName("khay")
    public String khay;
    @SerializedName("khachHang")
    public String khachHang;

    private int edtTextSLKhayGiao;

    private int edtTextSLKhayLayVe;

    private boolean isChecked;


    public int getEdtTextSLKhayGiao() {
        return edtTextSLKhayGiao;
    }

    public void setEdtTextSLKhayGiao(int edtTextSLKhayGiao) {
        this.edtTextSLKhayGiao = edtTextSLKhayGiao;
    }

    public int getEdtTextSLKhayLayVe() {
        return edtTextSLKhayLayVe;
    }

    public void setEdtTextSLKhayLayVe(int edtTextSLKhayLayVe) {
        this.edtTextSLKhayLayVe = edtTextSLKhayLayVe;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
