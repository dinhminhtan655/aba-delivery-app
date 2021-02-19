package com.tandm.abadeliverydriver.main.home.fragmentinfo.model;

import com.google.gson.annotations.SerializedName;

public class UserRating {

    @SerializedName("orderNumber")
    public String orderNumber;
    @SerializedName("znsRatingValue")
    public int znsRatingValue;
    @SerializedName("driverCode")
    public String driverCode;
    @SerializedName("driverName")
    public String driverName;
    @SerializedName("znsRatingComment")
    public String znsRatingComment;
    @SerializedName("createdTime")
    public String createdTime;

}
