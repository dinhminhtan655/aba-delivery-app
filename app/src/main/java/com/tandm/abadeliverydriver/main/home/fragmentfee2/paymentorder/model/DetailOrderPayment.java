package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

public class DetailOrderPayment implements Parcelable {

    @SerializedName("id")
    public int id;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("powerUnit")
    public String powerUnit;
    @SerializedName("employeeID")
    public String employeeID;
    @SerializedName("employeeName")
    public String employeeName;
    @SerializedName("department")
    public String department;
    @SerializedName("customer")
    public String customer;
    @SerializedName("invoiceDate")
    public String invoiceDate;
    @SerializedName("oPamount")
    public int oPamount;
    @SerializedName("sEamount")
    public int sEamount;
    @SerializedName("fiNamount")
    public int fiNamount;
    @SerializedName("advancePaymentType")
    public String advancePaymentType;
    @SerializedName("description")
    public String description;
    @SerializedName("amount")
    public int amount;
    @SerializedName("amountAdjustment")
    public int amountAdjustment;
    @SerializedName("uDWho")
    public String uDWho;
    @SerializedName("seApproved")
    public String seApproved;
    @SerializedName("finApproved")
    public String finApproved;
    @SerializedName("manager")
    public String manager;
//    @SerializedName("techManagerApproved")
//    public String techManagerApproved;
//    @SerializedName("technicalApproved")
//    public String technicalApproved;
    @SerializedName("opApproved")
    public String opApproved;
    @SerializedName("approveStatus")
    public String approveStatus;
    @SerializedName("remark")
    public String remark;


    public String city;
    public String startTime;

    public DetailOrderPayment(String atmShipmentID, String powerUnit, String employeeID, String employeeName, String department, String customer, String advancePaymentType, String description, int amount, String city, String startTime) {
        this.atmShipmentID = atmShipmentID;
        this.powerUnit = powerUnit;
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.department = department;
        this.customer = customer;
        this.advancePaymentType = advancePaymentType;
        this.description = description;
        this.amount = amount;
        this.city = city;
        this.startTime = startTime;
    }

    public String getDateStartTime(String starT_TIME) {
        String[] date = starT_TIME.split("T");
        return Utilities.formatDate_ddMMyyyy(date[0]);
    }

    public String formartVND(int amount) {
        return Utilities.formatNumber(String.valueOf(amount)) + " VNĐ";
    }

    public String getRemark(String remark) {
        if (remark == null) {
            return "Lý do không duyệt: Không Có";
        } else {
            return "Lý do không duyệt: " + remark;
        }
    }

    public String SEStatus(String SEApprove) {
        return "SE: " + SEApprove;
    }

    public String FINStatus(String FINApprove) {
        return "FIN: " + FINApprove;
    }

    public String OPMaStatus(String SEApprove) {
        return "Trưởng BP: " + SEApprove;
    }

    public String KTMaStatus(String FINApprove, String advancePaymentType) {
        if (advancePaymentType.equals("PHÍ VÁ VỎ, SỬA XE")){
            return "Kỹ Thuật: " + FINApprove;
        }else {
            return "OP: " + FINApprove;
        }
    }

    public String amountHaveText(){
        return "Số tiền đề nghị: " +Utilities.formatNumber(String.valueOf(amount)) + " VNĐ";
    }


    public String amountAdjustmentHaveText(){
        return "Số tiền đã duyệt: " +Utilities.formatNumber(String.valueOf(amountAdjustment)) + " VNĐ";
    }

    public String opAmountHaveText(){
        return "Số tiền OP điều chỉnh: " +Utilities.formatNumber(String.valueOf(oPamount)) + " VNĐ";
    }


    public String seAmountHaveText(){
        return "Số tiền SE điều chỉnh: " +Utilities.formatNumber(String.valueOf(sEamount)) + " VNĐ";
    }


    public String finAmountHaveText(){
        return "Số tiền FIN điều chỉnh: " +Utilities.formatNumber(String.valueOf(fiNamount)) + " VNĐ";
    }

    protected DetailOrderPayment(Parcel in) {
        id = in.readInt();
        atmShipmentID = in.readString();
        powerUnit = in.readString();
        employeeID = in.readString();
        employeeName = in.readString();
        department = in.readString();
        customer = in.readString();
        invoiceDate = in.readString();
        advancePaymentType = in.readString();
        description = in.readString();
        amount = in.readInt();
        seApproved = in.readString();
        finApproved = in.readString();
        approveStatus = in.readString();
    }

    public static final Creator<DetailOrderPayment> CREATOR = new Creator<DetailOrderPayment>() {
        @Override
        public DetailOrderPayment createFromParcel(Parcel in) {
            return new DetailOrderPayment(in);
        }

        @Override
        public DetailOrderPayment[] newArray(int size) {
            return new DetailOrderPayment[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(atmShipmentID);
        dest.writeString(powerUnit);
        dest.writeString(employeeID);
        dest.writeString(employeeName);
        dest.writeString(department);
        dest.writeString(customer);
        dest.writeString(invoiceDate);
        dest.writeString(advancePaymentType);
        dest.writeString(description);
        dest.writeInt(amount);
        dest.writeString(seApproved);
        dest.writeString(finApproved);
        dest.writeString(approveStatus);
    }

}
