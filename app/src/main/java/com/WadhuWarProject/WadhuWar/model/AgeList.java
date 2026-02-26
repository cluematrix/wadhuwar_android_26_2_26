package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AgeList {


    @SerializedName("AgeList")
    @Expose
    public ArrayList<AgeListData> AgeList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<AgeListData> getAgeList() {
        return AgeList;
    }

    public void setAgeList(ArrayList<AgeListData> ageList) {
        AgeList = ageList;
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

    public static class AgeListData {



        @SerializedName("name")
        @Expose
        String name;





        public AgeListData(String name) {
            this.name = name;

        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

}
