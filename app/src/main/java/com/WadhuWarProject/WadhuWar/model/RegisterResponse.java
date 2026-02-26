package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("resid")
    int resid;

    @SerializedName("user_id")
    int userId;

    @SerializedName("resMsg")
    String resMsg;


    public void setResid(int resid) {
        this.resid = resid;
    }
    public int getResid() {
        return resid;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
    public String getResMsg() {
        return resMsg;
    }

}
