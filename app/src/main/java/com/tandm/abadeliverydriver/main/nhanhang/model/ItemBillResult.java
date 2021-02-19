package com.tandm.abadeliverydriver.main.nhanhang.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemBillResult {

    @SerializedName("id")
    public int id;
    @SerializedName("isError")
    public boolean isError;
    @SerializedName("isInvalidItemsInside")
    public boolean isInvalidItemsInside;
    @SerializedName("isDuplicate")
    public boolean isDuplicate;
    @SerializedName("messages")
    public List<String> messages;
    @SerializedName("result")
    public boolean result;
    @SerializedName("list")
    public List<String> list;
}
