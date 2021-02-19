package com.tandm.abadeliverydriver.main.login;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("access_token")
    public String access_token;
    @SerializedName("token_type")
    public String token_type;
    @SerializedName("expires_in")
    public int expires_in;
    @SerializedName("as:client_id")
    public String client_id;
    @SerializedName("userName")
    public String userName;
    @SerializedName("fullName")
    public String fullName;
    @SerializedName("userId")
    public String userId;
    @SerializedName("isBiker")
    public boolean isBiker;
    @SerializedName("MaNhanVien")
    public String MaNhanVien;
    @SerializedName("PasswordHash")
    public String PasswordHash;
    @SerializedName("NgaySinh")
    public String NgaySinh;
    @SerializedName("Region")
    public String Region;
    @SerializedName("Position")
    public String Position;
    @SerializedName(".issued")
    public String issued;
    @SerializedName(".expires")
    public String expires;
    @SerializedName("error")
    public String error;
    @SerializedName("error_description")
    public String error_description;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
