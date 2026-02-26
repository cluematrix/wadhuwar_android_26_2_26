package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchAstroLifestyle {

    @SerializedName("resid")
    @Expose
    String resid;


    @SerializedName("resMsg")
    @Expose
    String resMsg;

    @SerializedName("manglik")
    @Expose
    String manglik;

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getManglik() {
        return manglik;
    }

    public void setManglik(String manglik) {
        this.manglik = manglik;
    }
}
