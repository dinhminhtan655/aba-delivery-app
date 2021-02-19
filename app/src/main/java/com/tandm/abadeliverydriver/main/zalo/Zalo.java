package com.tandm.abadeliverydriver.main.zalo;

import com.google.gson.annotations.SerializedName;

public class Zalo {

    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public Data data;
    @SerializedName("error")
    public int error;


}
