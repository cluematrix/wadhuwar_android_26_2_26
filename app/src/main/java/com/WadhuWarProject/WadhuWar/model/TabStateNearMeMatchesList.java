package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TabStateNearMeMatchesList {

    @SerializedName("count")
    int count;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("MatchesTabNearList")
    List<MatchesTabNearList> MatchesTabNearList;

    @SerializedName("resid")
    int resid;

    @SerializedName("resMsg")
    String resMsg;


    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public int getTotalPages() {
        return totalPages;
    }

    public void setMatchesTabNearList(List<MatchesTabNearList> MatchesTabNearList) {
        this.MatchesTabNearList = MatchesTabNearList;
    }
    public List<MatchesTabNearList> getMatchesTabNearList() {
        return MatchesTabNearList;
    }

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


    public class MatchesTabNearList {

        @SerializedName("id")
        String id;

        @SerializedName("name")
        String name;

        @SerializedName("gender")
        String gender;

        @SerializedName("img_count")
        int imgCount;

        @SerializedName("image")
        String image;

        @SerializedName("age")
        String age;

        @SerializedName("is_delete")
        String isDelete;

        @SerializedName("height")
        String height;

        @SerializedName("main_occupation")
        String mainOccupation;

        @SerializedName("occupation")
        String occupation;

        @SerializedName("state")
        String state;

        @SerializedName("district")
        String district;

        @SerializedName("mothertounge")
        String mothertounge;

        @SerializedName("caste")
        String caste;

        @SerializedName("subcaste")
        String subcaste;

        @SerializedName("chkonline")
        String chkonline;

        @SerializedName("just_joined")
        String justJoined;

        @SerializedName("account_verify")
        String accountVerify;

        @SerializedName("premium")
        String premium;

        @SerializedName("acc_verify")
        String accVerify;

        @SerializedName("is_connected")
        String isConnected;


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

        public void setImgCount(int imgCount) {
            this.imgCount = imgCount;
        }
        public int getImgCount() {
            return imgCount;
        }

        public void setImage(String image) {
            this.image = image;
        }
        public String getImage() {
            return image;
        }

        public void setAge(String age) {
            this.age = age;
        }
        public String getAge() {
            return age;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }
        public String getIsDelete() {
            return isDelete;
        }

        public void setHeight(String height) {
            this.height = height;
        }
        public String getHeight() {
            return height;
        }

        public void setMainOccupation(String mainOccupation) {
            this.mainOccupation = mainOccupation;
        }
        public String getMainOccupation() {
            return mainOccupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }
        public String getOccupation() {
            return occupation;
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

        public void setMothertounge(String mothertounge) {
            this.mothertounge = mothertounge;
        }
        public String getMothertounge() {
            return mothertounge;
        }

        public void setCaste(String caste) {
            this.caste = caste;
        }
        public String getCaste() {
            return caste;
        }

        public void setSubcaste(String subcaste) {
            this.subcaste = subcaste;
        }
        public String getSubcaste() {
            return subcaste;
        }

        public void setChkonline(String chkonline) {
            this.chkonline = chkonline;
        }
        public String getChkonline() {
            return chkonline;
        }

        public void setJustJoined(String justJoined) {
            this.justJoined = justJoined;
        }
        public String getJustJoined() {
            return justJoined;
        }

        public void setAccountVerify(String accountVerify) {
            this.accountVerify = accountVerify;
        }
        public String getAccountVerify() {
            return accountVerify;
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

        public void setIsConnected(String isConnected) {
            this.isConnected = isConnected;
        }
        public String getIsConnected() {
            return isConnected;
        }

    }

}