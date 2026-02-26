package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeightList {


    @SerializedName("WeightList")
    @Expose
    public ArrayList<WeightListData> WeightList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<WeightListData> getWeightList() {
        return WeightList;
    }

    public void setWeightList(ArrayList<WeightListData> weightList) {
        WeightList = weightList;
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


    public static class WeightListData {


        @SerializedName("name")
        @Expose
        String name;

        public WeightListData(String name) {
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
