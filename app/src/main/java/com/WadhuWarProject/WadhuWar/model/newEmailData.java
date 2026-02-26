package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

public class newEmailData {

    @SerializedName("resid")
    int resid;

    @SerializedName("resMsg")
    String resMsg;

    @SerializedName("OTP")
    int OTP;

    @SerializedName("user_id")
    int userId;


    public void setResid(int resid) {
        this.resid = resid;
    }
    public int getResid() {
        return resid;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
    public String getResMsg() {
        return resMsg;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
    }
    public int getOTP() {
        return OTP;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

}