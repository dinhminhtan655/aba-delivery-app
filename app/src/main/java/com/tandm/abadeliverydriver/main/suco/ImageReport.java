package com.tandm.abadeliverydriver.main.suco;

import com.google.gson.annotations.SerializedName;

public class ImageReport {

    @SerializedName("id")
    public int id;
    @SerializedName("reportId")
    public int reportId;
    @SerializedName("attachedFileKey")
    public String attachedFileKey;
    @SerializedName("fileName")
    public String fileName;
    @SerializedName("physicalFileName")
    public String physicalFileName;
    @SerializedName("created")
    public String created;
    @SerializedName("createdBy")
    public String createdBy;
    @SerializedName("modified")
    public String modified;
    @SerializedName("modifiedBy")
    public String modifiedBy;
    
}
