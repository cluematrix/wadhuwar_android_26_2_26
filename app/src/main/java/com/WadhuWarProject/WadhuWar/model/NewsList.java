package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsList {



    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("total_pages")
    @Expose
    String total_pages;

    @SerializedName("NewsList")
    @Expose
    public ArrayList<NewsListData> NewsList;


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

    public ArrayList<NewsListData> getNewsList() {
        return NewsList;
    }

    public void setNewsList(ArrayList<NewsListData> newsList) {
        NewsList = newsList;
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


    public static class NewsListData {

        @SerializedName("videolink")
        @Expose
        String videolink;


        @SerializedName("type")
        @Expose
        String type;


        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("img")
        @Expose
        String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getVideolink() {
            return videolink;
        }

        public void setVideolink(String videolink) {
            this.videolink = videolink;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


}
