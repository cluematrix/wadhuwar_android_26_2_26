package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MemberList {


    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("total_pages")
    @Expose
    String total_pages;

    @SerializedName("MemberList")
    @Expose
    public ArrayList<MemberListData> memberList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;



    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<MemberListData> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<MemberListData> memberList) {
        this.memberList = memberList;
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

    public static class MemberListData {
        @SerializedName("education_name")
        @Expose
        String education_name;


        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("gender")
        @Expose
        String gender;

        @SerializedName("whatsapp")
        @Expose
        String whatsapp;


        @SerializedName("mobile_no")
        @Expose
        String mobile_no;



        @SerializedName("date")
        @Expose
        String date;


        @SerializedName("is_connected")
        @Expose
        String is_connected;

        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("image")
        @Expose
        String image;

        @SerializedName("age")
        @Expose
        String age;


        @SerializedName("height")
        @Expose
        String height;


        @SerializedName("chkonline")
        @Expose
        String chkonline;


        @SerializedName("occupation")
        @Expose
        String occupation;


        @SerializedName("state")
        @Expose
        String state;


        @SerializedName("district")
        @Expose
        String district;



        @SerializedName("mothertounge")
        @Expose
        String mothertounge;


        @SerializedName("caste")
        @Expose
        String caste;

        @SerializedName("just_joined")
        @Expose
        String just_joined;

        @SerializedName("premium")
        @Expose
        String premium;


        @SerializedName("img_count")
        @Expose
        String img_count;

        public String getImg_count() {
            return img_count;
        }

        public void setImg_count(String img_count) {
            this.img_count = img_count;
        }

        public String getJust_joined() {
            return just_joined;
        }

        public void setJust_joined(String just_joined) {
            this.just_joined = just_joined;
        }

        public String getPremium() {
            return premium;
        }

        public void setPremium(String premium) {
            this.premium = premium;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getChkonline() {
            return chkonline;
        }

        public void setChkonline(String chkonline) {
            this.chkonline = chkonline;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getMothertounge() {
            return mothertounge;
        }

        public void setMothertounge(String mothertounge) {
            this.mothertounge = mothertounge;
        }

        public String getCaste() {
            return caste;
        }

        public void setCaste(String caste) {
            this.caste = caste;
        }

        public String getIs_connected() {
            return is_connected;
        }

        public void setIs_connected(String is_connected) {
            this.is_connected = is_connected;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWhatsapp() {
            return whatsapp;
        }

        public void setWhatsapp(String whatsapp) {
            this.whatsapp = whatsapp;
        }


        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEducation_name() {
            return education_name;
        }

        public void setEducation_name(String education_name) {
            this.education_name = education_name;
        }
    }


}
