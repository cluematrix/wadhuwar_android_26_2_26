package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Likes implements Serializable {

    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("total_pages")
    @Expose
    String total_pages;


    @SerializedName("Likes")
    @Expose
    public ArrayList<LikesData> Likes;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<LikesData> getLikes() {
        return Likes;
    }

    public void setLikes(ArrayList<LikesData> likes) {
        Likes = likes;
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

    public static class LikesData {



        @SerializedName("gender")
        @Expose
        String gender;

        @SerializedName("m_id")
        @Expose
        String m_id;

        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("image")
        @Expose
        String image;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }


}
