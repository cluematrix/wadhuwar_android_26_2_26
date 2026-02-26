package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BloodGroupList {


    @SerializedName("BloodGroupList")
    @Expose
    public ArrayList<BloodGroupListData> BloodGroupList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<BloodGroupListData> getBloodGroupList() {
        return BloodGroupList;
    }

    public void setBloodGroupList(ArrayList<BloodGroupListData> bloodGroupList) {
        BloodGroupList = bloodGroupList;
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


    public static class BloodGroupListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;





        public BloodGroupListData(String id, String name) {
            this.id = id;
            this.name = name;

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


    }

}
