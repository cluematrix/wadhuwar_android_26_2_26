package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BlogDetail {


    @SerializedName("blogList")
    @Expose
    public ArrayList<BlogDetailData> blogList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<BlogDetailData> getBlogList() {
        return blogList;
    }

    public void setBlogList(ArrayList<BlogDetailData> blogList) {
        this.blogList = blogList;
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



    public class BlogDetailData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("img")
        @Expose
        String img;

        @SerializedName("desc")
        @Expose
        String desc;

        @SerializedName("videolink")
        @Expose
        String videolink;


        @SerializedName("type")
        @Expose
        String type;


        @SerializedName("images")
        @Expose
        public ArrayList<SliderListImage> images;



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

        public ArrayList<SliderListImage> getImages() {
            return images;
        }

        public void setImages(ArrayList<SliderListImage> images) {
            this.images = images;
        }
    }


}
