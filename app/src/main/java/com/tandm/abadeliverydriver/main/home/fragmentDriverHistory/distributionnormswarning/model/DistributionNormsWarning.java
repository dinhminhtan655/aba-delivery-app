package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.model;

import com.google.gson.annotations.SerializedName;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.text.ParseException;

public class DistributionNormsWarning {
    @SerializedName("lit")
    public String lit;
    @SerializedName("km")
    public String km;
    @SerializedName("kilometer")
    public String kilometer;
    @SerializedName("kilometerLatest")
    public String kilometerLatest;
    @SerializedName("actualNorm")
    public String actualNorm;
    @SerializedName("normsWarning")
    public String normsWarning;
    @SerializedName("fillTime")
    public String fillTime;
    @SerializedName("gasStationName")
    public String gasStationName;
    @SerializedName("documentNumber")
    public String documentNumber;
    @SerializedName("invoiceNumber")
    public String invoiceNumber;



    public String mainLit(){
        return "Số lí đổ: " + lit;
    }

    public String mainKM(){
        return "KM đã đi: " + km;
    }

    public String mainKilometer(){
        return "Số KM sau khi đi: " + kilometer;
    }

    public String mainKilometerLatest(){
        return "Số KM trước khi đi: " + kilometerLatest;
    }


    public String mainActualNorm(){
        return "Tiêu thụ: " + actualNorm;
    }

    public String mainNormsWarning(){
        return "Định mức: " + normsWarning;
    }

    public String mainFillTime() {
        try {
            return "Giờ đổ: " + Utilities.formatDateTimeddMMyyyyHHmmss_ToMilisecond(fillTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String mainGasStationName(){
        return "Cây dầu: " + gasStationName;
    }

    public String mainDocumentNumber(){
        return "Số phiếu: " + documentNumber;
    }

    public String mainInvoiceNumber(){
        return "Số hóa đơn: " + invoiceNumber;
    }



}
