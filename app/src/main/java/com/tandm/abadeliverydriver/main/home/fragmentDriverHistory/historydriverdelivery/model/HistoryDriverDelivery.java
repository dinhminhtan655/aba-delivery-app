package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryDriverDelivery {
    @SerializedName("startTime")
    public String starT_TIME;
    @SerializedName("endTime")
    public String enD_TIME;
    @SerializedName("atmShipmentID")
    public String atM_SHIPMENT_ID;
    @SerializedName("deliveryTime")
    public String delivery_Time;
    @SerializedName("customerCode")
    public String customerCode;

    String[] date, date2, time, time2;
    public String getDateDeliveryTime(String delivery_Time){
        if(delivery_Time == null){
            return "Chưa xác định";
        }else {
            date = delivery_Time.split("T");
        }
        return date[0];
    }

    public String getTimeDeliveryTime(String delivery_Time){
        if(delivery_Time == null){
            return "Chưa xác định";
        }else {
            time = delivery_Time.split("T");
        }
        return time[1];
    }

    public String getDateEndTime(String enD_TIME){
        if(enD_TIME == null){
            return "Chưa xác định";
        }else {
            date2 = enD_TIME.split("T");
        }
        return date2[0];
    }

    public String getTimeEndTime(String enD_TIME){
        if(enD_TIME == null){
            return "Chưa xác định";
        }else {
            time2 = enD_TIME.split("T");
        }
        return time2[1];
    }

    public String getDateStartTime(String starT_TIME){
        if(enD_TIME == null){
            return "Chưa xác định";
        }else {
            date2 = enD_TIME.split("T");
        }
        return date2[0];
    }

    public String getTimeStartTime(String starT_TIME){
        if(enD_TIME == null){
            return "Chưa xác định";
        }else {
            time2 = enD_TIME.split("T");
        }
        return time2[1];
    }

    public String getOnTime(String enD_TIME, String delivery_Time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        if (enD_TIME == null || delivery_Time == null){
            return "Chưa xác định";
        }else {
            try {
                Date date = sdf.parse(enD_TIME);
                Date date2 = sdf.parse(delivery_Time);
                long miliEndTime = date.getTime();
                long miliDeliveryTime = date2.getTime();

                if (miliDeliveryTime <= miliEndTime){
                    return "Đúng giờ";
                }else {
                    return "Trễ";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return "";

    }
}
