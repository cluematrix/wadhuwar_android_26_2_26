package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

public class SendProfileViewNoti {

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;


    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}