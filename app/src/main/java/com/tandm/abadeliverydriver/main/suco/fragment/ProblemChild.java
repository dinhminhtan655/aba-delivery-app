package com.tandm.abadeliverydriver.main.suco.fragment;

import com.google.gson.annotations.SerializedName;

public class ProblemChild {

    @SerializedName("rowId")
    public int rowId;
    @SerializedName("title")
    public String title;
    @SerializedName("created")
    public String created;
    @SerializedName("createdBy")
    public String createdBy;
    @SerializedName("modified")
    public String modified;
    @SerializedName("modifiedBy")
    public String modifiedBy;


    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
