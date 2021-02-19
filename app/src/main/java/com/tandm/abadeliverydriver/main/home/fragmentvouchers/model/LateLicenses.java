package com.tandm.abadeliverydriver.main.home.fragmentvouchers.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.text.ParseException;

public class LateLicenses {
    @SerializedName("id")
    public int id;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("fullName")
    public String fullName;
    @SerializedName("customer")
    public String customer;
    @SerializedName("startTime")
    public String startTime;
    @SerializedName("driverID")
    public String driverID;
    @SerializedName("dateOfFiling")
    public String dateOfFiling;
    @SerializedName("reason")
    public String reason;
    @SerializedName("created")
    public String created;
    @SerializedName("statusManager")
    public String statusManager;
    @SerializedName("managerApprovedName")
    public String managerApprovedName;
    @SerializedName("timeManagerResponse")
    public String timeManagerResponse;
    @SerializedName("noteFromManage")
    public String noteFromManage;


    public String shipmentIDAndCus(){
        return atmShipmentID + " " + customer;
    }

    public String fullNameDriver(){
        return "Tên: " + fullName;
    }

    public String maDriver(){
        return "Mã NV: " + driverID;
    }

    public String ngayChay(){
        return "Chuyến ngày: " + Utilities.formatDate_ddMMyyyy(startTime);
    }

    public String ngayHen(){
        return "Ngày hẹn nộp: " + Utilities.formatDate_ddMMyyyy(dateOfFiling);
    }

    public String trangThai(){
        return "Trạng thái: " + statusManager;
    }

    public String lyDoDriver(){
        return "Lý Do: " + reason;
    }

    public String noidungManager(){
        return "Quản lý: " + reason == null ? "Không có nội dung" : reason;
    }

    public String ngayTao() {
        try {
            return "Tạo lúc: " +  Utilities.formatDateTimeddMMyyyyHHmmss_ToMilisecond(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
