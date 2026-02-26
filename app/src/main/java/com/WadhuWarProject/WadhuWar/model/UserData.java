package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    @SerializedName("name")
    @Expose
    String name;


    @SerializedName("user_id")
    @Expose
    int user_id;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("image")
    @Expose
    String image;


    public UserData(int user_id,String name,  String mobile, String image, String resid,String resMsg){
        this.name = name;
        this.user_id = user_id;
        this.mobile = mobile;
        this.image = image;
        this.resid = resid;
        this.resMsg = resMsg;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
