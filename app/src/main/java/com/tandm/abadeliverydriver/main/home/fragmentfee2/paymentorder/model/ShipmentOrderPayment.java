package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.io.Serializable;

public class ShipmentOrderPayment implements Serializable {

    @SerializedName("customerCode")
    public String customerCode;
    @SerializedName("powerUnitGID")
    public String powerUnit;
    @SerializedName("atmShipmentID")
    public String atmShipmentID;
    @SerializedName("packagedItem")
    public String packagedItem;
    @SerializedName("startTime")
    public String startTime;
    @SerializedName("amount")
    public int amount;
    @SerializedName("amountTotal")
    public int amountTotal;
    @SerializedName("city")
    public String city;
    @SerializedName("customerName")
    public String customerName;


    public String getCustomerCodeSwap(String customerCode, String packagedItem){

        if (customerCode == null){
            if (packagedItem.contains(",") && packagedItem.contains("ABA.CHILLED_FOOD_0-5")
                    || packagedItem.contains(",") && packagedItem.contains("ABA.FRESH_MEAT_0-4")
                    || packagedItem.contains(",") && packagedItem.contains("ABA.MEAT_0-5")){
                return "Hàng Ghép Thịt";
            }else if (packagedItem.contains("ABA.TRAY")){
                return "Hàng Ghép Thu Khay";
            }
        }else if (customerCode.equals("VCM")){
            return "Vin Rau";
        }else if (customerCode.equals("VCMFRESH")){
            return "Vin Thịt";
        }else if (customerCode.equals("BHX") && packagedItem.contains("ABA.ICE_0,ABA.MEAT_0-5") ||
                customerCode.equals("BHX") && packagedItem.contains("ABA.MEAT_0-5,ABA.ICE_0")){
            return "BHX Thịt và Đá";
        }
        else if (customerCode.equals("BHX") && packagedItem.contains("ABA.ICE_0")){
            return "BHX Đá";
        }else if (customerCode.equals("BHX") && packagedItem.contains("ABA.TRAY")){
            return "BHX Thu khay";
        }else if (customerCode.equals("BHX") && packagedItem.contains("ABA.MEAT_0-5")){
            return "BHX Thịt";
        }

        return customerCode;
    }


    public String getDateStartTime(String starT_TIME){
        String[] date = starT_TIME.split("T");
        return Utilities.formatDate_ddMMyyyy(date[0]);
    }

    public String formartVND(int amount){
        return Utilities.formatNumber(String.valueOf(amount))+" VNĐ";
    }

    public String totalRemaining(int amountTotal, int amount){
        int total = amountTotal - amount;
        if (total > 0){
            return Utilities.formatNumber(String.valueOf(total))+" VNĐ";
        }else {
            return Utilities.formatNumber("0")+" VNĐ";
        }

    }
}
