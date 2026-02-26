package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HeightList {


    @SerializedName("HeightList")
    @Expose
    public ArrayList<HeightListData> HeightList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<HeightListData> getHeightList() {
        return HeightList;
    }

    public void setHeightList(ArrayList<HeightListData> heightList) {
        HeightList = heightList;
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

    public static class HeightListData {


        @SerializedName("name")
        @Expose
        String name;

        public HeightListData( String name) {
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
