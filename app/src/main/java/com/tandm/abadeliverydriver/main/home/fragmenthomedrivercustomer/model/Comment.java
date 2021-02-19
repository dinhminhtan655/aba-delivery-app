package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("CommentID")
    public int commentID;
    @SerializedName("OrderNumber")
    public String orderNumber;
    @SerializedName("CommentBy")
    public String commentBy;
    @SerializedName("CommentTime")
    public String commentTime;
    @SerializedName("Comment")
    public String comment;

}
