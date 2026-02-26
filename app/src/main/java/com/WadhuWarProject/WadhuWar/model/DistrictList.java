package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DistrictList {


    @SerializedName("DistrictList")
    @Expose
    public ArrayList<DistrictListData> DistrictList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<DistrictListData> getDistrictList() {
        return DistrictList;
    }

    public void setDistrictList(ArrayList<DistrictListData> districtList) {
        DistrictList = districtList;
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
    public static class DistrictListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("state_id")
        @Expose
        String state_id;



        public DistrictListData(String id, String name, String state_id) {
            this.id = id;
            this.name = name;
            this.state_id = state_id;

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

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }
    }


}
