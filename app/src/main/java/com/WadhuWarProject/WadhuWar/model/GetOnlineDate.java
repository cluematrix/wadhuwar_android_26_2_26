package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOnlineDate {

    @SerializedName("resid")
    int resid;

    @SerializedName("resMsg")
    String resMsg;

    @SerializedName("LastFourProfiles")
    List<LastFourProfiles> LastFourProfiles;


    public void setResid(int resid) {
        this.resid = resid;
    }
    public int getResid() {
        return resid;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
    public String getResMsg() {
        return resMsg;
    }

    public void setLastFourProfiles(List<LastFourProfiles> LastFourProfiles) {
        this.LastFourProfiles = LastFourProfiles;
    }
    public List<LastFourProfiles> getLastFourProfiles() {
        return LastFourProfiles;
    }

    public class LastFourProfiles {

        @SerializedName("id")
        String id;

        @SerializedName("name")
        String name;

        @SerializedName("gender")
        String gender;

        @SerializedName("age")
        String age;

        @SerializedName("image")
        String image;

        @SerializedName("height")
        String height;

        @SerializedName("state")
        String state;

        @SerializedName("district")
        String district;

        @SerializedName("premium")
        String premium;

        @SerializedName("acc_verify")
        String accVerify;


        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getGender() {
            return gender;
        }

        public void setAge(String age) {
            this.age = age;
        }
        public String getAge() {
            return age;
        }

        public void setImage(String image) {
            this.image = image;
        }
        public String getImage() {
            return image;
        }

        public void setHeight(String height) {
            this.height = height;
        }
        public String getHeight() {
            return height;
        }

        public void setState(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
        public String getDistrict() {
            return district;
        }

        public void setPremium(String premium) {
            this.premium = premium;
        }
        public String getPremium() {
            return premium;
        }

        public void setAccVerify(String accVerify) {
            this.accVerify = accVerify;
        }
        public String getAccVerify() {
            return accVerify;
        }

    }

}