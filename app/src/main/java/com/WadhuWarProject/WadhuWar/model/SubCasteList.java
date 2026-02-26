package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCasteList {

    @SerializedName("c_id")
    @Expose
    String c_id;

    @SerializedName("sc_id")
    @Expose
    String sc_id;

    @SerializedName("sc_name")
    @Expose
    String sc_name;


    public SubCasteList(String c_id, String sc_id, String sc_name) {
        this.c_id = c_id;
        this.sc_id = sc_id;
        this.sc_name = sc_name;

    }


    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }
}
