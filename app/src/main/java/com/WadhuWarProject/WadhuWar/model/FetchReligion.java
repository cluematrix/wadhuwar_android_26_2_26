package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchReligion {

    @SerializedName("resid")
    @Expose
    String resid;


    @SerializedName("resMsg")
    @Expose
    String resMsg;

    @SerializedName("religion_id")
    @Expose
    String religion_id;

    @SerializedName("religion_name")
    @Expose
    String religion_name;

    @SerializedName("caste_id")
    @Expose
    String caste_id;

    @SerializedName("caste_name")
    @Expose
    String caste_name;

    @SerializedName("subcaste_id")
    @Expose
    String subcaste_id;

    @SerializedName("subcaste_name")
    @Expose
    String subcaste_name;

    @SerializedName("mothertounge_id")
    @Expose
    String mothertounge_id;

    @SerializedName("mothertounge_name")
    @Expose
    String mothertounge_name;

    @SerializedName("gothra")
    @Expose
    String gothra;

    @SerializedName("sampraday_name")
    @Expose
    String sampraday_name;

    @SerializedName("sampraday_id")
    @Expose
    String sampraday_id;

    @SerializedName("rashi")
    @Expose
    String rashi;



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

    public String getReligion_id() {
        return religion_id;
    }

    public void setReligion_id(String religion_id) {
        this.religion_id = religion_id;
    }

    public String getReligion_name() {
        return religion_name;
    }

    public void setReligion_name(String religion_name) {
        this.religion_name = religion_name;
    }

    public String getCaste_id() {
        return caste_id;
    }

    public void setCaste_id(String caste_id) {
        this.caste_id = caste_id;
    }

    public String getCaste_name() {
        return caste_name;
    }

    public void setCaste_name(String caste_name) {
        this.caste_name = caste_name;
    }

    public String getSubcaste_id() {
        return subcaste_id;
    }

    public void setSubcaste_id(String subcaste_id) {
        this.subcaste_id = subcaste_id;
    }

    public String getSubcaste_name() {
        return subcaste_name;
    }

    public void setSubcaste_name(String subcaste_name) {
        this.subcaste_name = subcaste_name;
    }

    public String getMothertounge_id() {
        return mothertounge_id;
    }

    public void setMothertounge_id(String mothertounge_id) {
        this.mothertounge_id = mothertounge_id;
    }

    public String getMothertounge_name() {
        return mothertounge_name;
    }

    public void setMothertounge_name(String mothertounge_name) {
        this.mothertounge_name = mothertounge_name;
    }

    public String getGothra() {
        return gothra;
    }

    public void setGothra(String gothra) {
        this.gothra = gothra;
    }

    public String getSampraday_name() {
        return sampraday_name;
    }

    public void setSampraday_name(String sampraday_name) {
        this.sampraday_name = sampraday_name;
    }

    public String getSampraday_id() {
        return sampraday_id;
    }

    public void setSampraday_id(String sampraday_id) {
        this.sampraday_id = sampraday_id;
    }

    public String getRashi() {
        return rashi;
    }

    public void setRashi(String rashi) {
        this.rashi = rashi;
    }
}
