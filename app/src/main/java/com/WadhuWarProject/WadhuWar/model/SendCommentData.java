package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;



public class SendCommentData  implements Serializable {
    @SerializedName("resid")
    @Expose
    String resid;


    @SerializedName("gender")
    @Expose
    String gender;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    @SerializedName("cmt_id")
    @Expose
    String cmt_id;

    @SerializedName("member_id")
    @Expose
    String member_id;

    @SerializedName("image")
    @Expose
    String image;


    @SerializedName("name")
    @Expose
    String name;



    @SerializedName("comment")
    @Expose
    String comment;

    @SerializedName("date")
    @Expose
    String date;

    public String getCmt_id() {
        return cmt_id;
    }

    public void setCmt_id(String cmt_id) {
        this.cmt_id = cmt_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}



