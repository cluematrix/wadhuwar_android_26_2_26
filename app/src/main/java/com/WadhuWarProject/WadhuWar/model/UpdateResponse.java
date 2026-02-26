package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateResponse {


    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("resid")
    @Expose
    public String resid;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }
}
