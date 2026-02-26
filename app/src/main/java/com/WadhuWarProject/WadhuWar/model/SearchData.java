package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchData implements Serializable {


    @SerializedName("SearchData")
    @Expose
    public ArrayList<SearchDataList> SearchData;


    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("total_pages")
    @Expose
    String total_pages;

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

    public ArrayList<SearchDataList> getSearchData() {
        return SearchData;
    }

    public void setSearchData(ArrayList<SearchDataList> searchData) {
        SearchData = searchData;
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


    public static class SearchDataList implements Serializable{
        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("gender")
        @Expose
        String gender;


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

        @SerializedName("is_delete")
        @Expose
        String isDelete;


        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

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

        @SerializedName("subcaste")
        @Expose
        String subcaste;


        @SerializedName("education")
        @Expose
        String education;

        @SerializedName("income")
        @Expose
        String income;

        @SerializedName("just_joined")
        @Expose
        String just_joined;

        @SerializedName("account_verify")
        @Expose
        String account_verify;

        @SerializedName("premium")
        @Expose
        String premium;

        public String getSubcaste() {
            return subcaste;
        }

        public void setSubcaste(String subcaste) {
            this.subcaste = subcaste;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getJust_joined() {
            return just_joined;
        }

        public void setJust_joined(String just_joined) {
            this.just_joined = just_joined;
        }

        public String getAccount_verify() {
            return account_verify;
        }

        public void setAccount_verify(String account_verify) {
            this.account_verify = account_verify;
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


        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }


}
