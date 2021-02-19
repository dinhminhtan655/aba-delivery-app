package com.tandm.abadeliverydriver.main.home.fragmentvouchers.model;

import com.google.gson.annotations.SerializedName;

public class LocationATMID {
    @SerializedName("atM_SHIPMENT_ID")
    public String atM_SHIPMENT_ID;
    @SerializedName("customerCode")
    public String customerCode;
    @SerializedName("startTime")
    public String startTime;
    @SerializedName("packagedItem")
    public String packagedItem;


    public String getCustomerCodeSwap(String packagedItem){

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
}
