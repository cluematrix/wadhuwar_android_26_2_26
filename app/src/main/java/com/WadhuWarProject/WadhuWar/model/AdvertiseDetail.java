package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdvertiseDetail {


    @SerializedName("AdvertiseList")
    @Expose
    public ArrayList<AdvertiseDetailData> AdvertiseList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;


    public ArrayList<AdvertiseDetailData> getAdvertiseList() {
        return AdvertiseList;
    }

    public void setAdvertiseList(ArrayList<AdvertiseDetailData> advertiseList) {
        AdvertiseList = advertiseList;
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


    public class AdvertiseDetailData {

        @SerializedName("videolink")
        @Expose
        String videolink;

        @SerializedName("video")
        @Expose
        String video;


        @SerializedName("type")
        @Expose
        String type;


        @SerializedName("id")
        @Expose
        String id;


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

        public String getVideo() {
            return video;
        }

        public void setVideolink(String videolink) {
            this.videolink = videolink;
        }
        public void setVideo(String video) {
            this.video = video;
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
