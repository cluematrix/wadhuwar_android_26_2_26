package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchBasicInfo {


    @SerializedName("fname")
    @Expose
    String fname;

    @SerializedName("mname")
    @Expose
    String mname;

    @SerializedName("lname")
    @Expose
    String lname;

    @SerializedName("profilefor_id")
    @Expose
    String profilefor;

    @SerializedName("profilefor_name")
    @Expose
    String profilefor_name;

    @SerializedName("gender")
    @Expose
    String gender;


    @SerializedName("age")
    @Expose
    String age;

    @SerializedName("dob")
    @Expose
    String dob;

    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("alt_email")
    @Expose
    String alt_email;


    @SerializedName("mobile_no")
    @Expose
    String mobile_no;

    @SerializedName("wtsapp_no")
    @Expose
    String wtsapp_no;

    @SerializedName("any_disable")
    @Expose
    String any_disable;

    @SerializedName("disable_name")
    @Expose
    String disable_name;

    @SerializedName("height")
    @Expose
    String height;

    @SerializedName("weight")
    @Expose
    String weight;


    @SerializedName("resid")
    @Expose
    String resid;



    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getProfilefor() {
        return profilefor;
    }

    public void setProfilefor(String profilefor) {
        this.profilefor = profilefor;
    }

    public String getProfilefor_name() {
        return profilefor_name;
    }

    public void setProfilefor_name(String profilefor_name) {
        this.profilefor_name = profilefor_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlt_email() {
        return alt_email;
    }

    public void setAlt_email(String alt_email) {
        this.alt_email = alt_email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getWtsapp_no() {
        return wtsapp_no;
    }

    public void setWtsapp_no(String wtsapp_no) {
        this.wtsapp_no = wtsapp_no;
    }

    public String getAny_disable() {
        return any_disable;
    }

    public void setAny_disable(String any_disable) {
        this.any_disable = any_disable;
    }

    public String getDisable_name() {
        return disable_name;
    }

    public void setDisable_name(String disable_name) {
        this.disable_name = disable_name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

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
}
