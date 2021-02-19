package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model;

import com.google.gson.annotations.SerializedName;

public class Shipment {

    @SerializedName("customerCode")
    public String customerCode;
    @SerializedName("trucktype")
    public String trucktype;
    @SerializedName("starT_TIME")
    public String starT_TIME;
    @SerializedName("enD_TIME")
    public String enD_TIME;
    @SerializedName("driveR_GID")
    public String driveR_GID;
    @SerializedName("poweR_UNIT_GID")
    public String poweR_UNIT_GID;
    @SerializedName("atM_SHIPMENT_ID")
    public String atM_SHIPMENT_ID;
    @SerializedName("mode")
    public String mode;
    @SerializedName("routeno")
    public String routeno;
    @SerializedName("roiKho")
    public boolean roiKho;
    @SerializedName("denKho")
    public boolean denKho;
    @SerializedName("startPickup")
    public boolean startPickup;
    @SerializedName("donePickup")
    public boolean donePickup;
    @SerializedName("packaged_Item_XID")
    public String packaged_Item_XID;
    @SerializedName("status")
    public String status;

    public boolean enable;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCustomerCodeSwap() {

        if (customerCode == null) {
//            if (packaged_Item_XID.contains(",") && packaged_Item_XID.contains("ABA.CHILLED_FOOD_0-5")
//                    || packaged_Item_XID.contains(",") && packaged_Item_XID.contains("ABA.FRESH_MEAT_0-4")
//                    || packaged_Item_XID.contains(",") && packaged_Item_XID.contains("ABA.MEAT_0-5")){
//                return "Hàng Ghép Thịt";
//            }else
            if (packaged_Item_XID.contains("ABA.TRAY")) {
                return "Hàng Ghép Thu Khay";
            } else {
                return "Hàng Ghép Thịt";
            }
        } else if (customerCode.equals("VCM")) {
            return "Vin Rau";
        } else if (customerCode.equals("VCMFRESH")) {
            return "Vin Thịt";
        } else if (customerCode.equals("BHX") && packaged_Item_XID.contains("ABA.ICE_0,ABA.MEAT_0-5") ||
                customerCode.equals("BHX") && packaged_Item_XID.contains("ABA.MEAT_0-5,ABA.ICE_0")) {
            return "BHX Thịt và Đá";
        } else if (customerCode.equals("BHX") && packaged_Item_XID.contains("ABA.ICE_0")) {
            return "BHX Đá";
        } else if (customerCode.equals("BHX") && packaged_Item_XID.contains("ABA.TRAY")) {
            return "BHX Thu khay";
        } else if (customerCode.equals("BHX") && packaged_Item_XID.contains("ABA.MEAT_0-5")) {
            return "BHX Thịt";
        }

        return customerCode;
    }

    public String getDateStartTime(String starT_TIME) {
        String[] date = starT_TIME.split("T");
        return date[0];
    }

    public String getTimeStartTime(String starT_TIME) {
        String[] time = starT_TIME.split("T");
        return time[1];
    }

    public String getDateEndTime(String enD_TIME) {
        String[] date = enD_TIME.split("T");
        return date[0];
    }

    public String getTimeEndTime(String enD_TIME) {
        String[] time = enD_TIME.split("T");
        return time[1];
    }


}
