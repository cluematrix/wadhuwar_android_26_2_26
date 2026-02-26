package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsDetail {


    @SerializedName("NewsList")
    @Expose
    public ArrayList<NewsDetailData> NewsList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<NewsDetailData> getNewsList() {
        return NewsList;
    }

    public void setNewsList(ArrayList<NewsDetailData> newsList) {
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


    public class NewsDetailData {

        @SerializedName("id")
        @Expose
        String id;
        @SerializedName("videolink")
        @Expose
        String videolink;


        @SerializedName("type")
        @Expose
        String type;


        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("images")
        @Expose
        public ArrayList<SliderListImage> images;


        @SerializedName("desc")
        @Expose
        String desc;


        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

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

        public ArrayList<SliderListImage> getImages() {
            return images;
        }

        public void setImages(ArrayList<SliderListImage> images) {
            this.images = images;
        }
    }

}
