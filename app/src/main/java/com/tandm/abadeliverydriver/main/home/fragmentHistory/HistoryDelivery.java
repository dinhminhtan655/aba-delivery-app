package com.tandm.abadeliverydriver.main.home.fragmentHistory;

import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.google.gson.annotations.SerializedName;

public class HistoryDelivery {

    @SerializedName("store_Code")
    public String store_Code;
    @SerializedName("store_Name")
    public String store_Name;
    @SerializedName("createdBy")
    public String createdBy;
    @SerializedName("soThung")
    public int soThung;
    @SerializedName("delivery_Date")
    public String delivery_Date;
    @SerializedName("giogiao")
    public String giogiao;

    public String convertThung(){
        return String.valueOf(soThung);
    }

    public String splitDateDeli(){
        String strDateFormat = Utilities.formatDate_ddMMyyyy(delivery_Date);
        return strDateFormat;
    }



}
