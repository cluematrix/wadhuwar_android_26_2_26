package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchDataMore implements Serializable {

    @SerializedName("MoreMatchesList")
    @Expose
    private ArrayList<SearchDataList> searchDataList; // Changed field name for clarity

    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("total_pages")
    @Expose
    private String totalPages; // Changed to camelCase

    @SerializedName("resid")
    @Expose
    private String resid;

    @SerializedName("resMsg")
    @Expose
    private String resMsg;

    // Getters and Setters
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<SearchDataList> getSearchDataList() { // Updated getter name
        return searchDataList;
    }

    public void setSearchDataList(ArrayList<SearchDataList> searchDataList) {
        this.searchDataList = searchDataList;
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

    // Inner class for search data items
    public static class SearchDataList implements Serializable {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("gender")
        @Expose
        private String gender;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("age")
        @Expose
        private String age;

        @SerializedName("height")
        @Expose
        private String height;

        @SerializedName("is_delete")
        @Expose
        private String isDelete;

        @SerializedName("chkonline")
        @Expose
        private String chkonline;

//        @SerializedName("post_name")
        @SerializedName("occupation")
        @Expose
        private String occupation;

        @SerializedName("state")
        @Expose
        private String state;

        @SerializedName("district")
        @Expose
        private String district;

        @SerializedName("mothertounge")
        @Expose
        private String mothertounge;

        @SerializedName("caste")
        @Expose
        private String caste;

        @SerializedName("subcaste")
        @Expose
        private String subcaste;

        @SerializedName("education")
        @Expose
        private String education;

        @SerializedName("income")
        @Expose
        private String income;

        @SerializedName("just_joined")
        @Expose
        private String justJoined;

        @SerializedName("account_verify")
        @Expose
        private String accountVerify;

        @SerializedName("premium")
        @Expose
        private String premium;

        @SerializedName("first_name")
        @Expose
        private String firstName;

         @SerializedName("post_name")
        @Expose
        private String occupationName;
//         @SerializedName("state")
//        @Expose
//        private String stateName;
//         @SerializedName("district")
//        @Expose
//        private String districtName;
//         @SerializedName("mothertounge")
//        @Expose
//        private String mothertoungeName;
//         @SerializedName("caste")
//        @Expose
//        private String casteName;





        public String getOccupationName() {
            return occupationName;
        }

        public void setOccupationName(String occupationName) {
            this.occupationName = occupationName;
        }

//        public String getStateName() {
//            return stateName;
//        }

//        public void setStateName(String stateName) {
//            this.stateName = stateName;
//        }

//        public String getDistrictName() {
//            return districtName;
//        }
//
//        public void setDistrictName(String districtName) {
//            this.districtName = districtName;
//        }

//        public String getMothertoungeName() {
//            return mothertoungeName;
//        }
//
//        public void setMothertoungeName(String mothertoungeName) {
//            this.mothertoungeName = mothertoungeName;
//        }

//        public String getCasteName() {
//            return casteName;
//        }
//
//        public void setCasteName(String casteName) {
//            this.casteName = casteName;
//        }






        // Add all getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
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

        public String getJustJoined() {
            return justJoined;
        }

        public void setJustJoined(String justJoined) {
            this.justJoined = justJoined;
        }

        public String getAccountVerify() {
            return accountVerify;
        }

        public void setAccountVerify(String accountVerify) {
            this.accountVerify = accountVerify;
        }

        public String getPremium() {
            return premium;
        }

        public void setPremium(String premium) {
            this.premium = premium;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
}