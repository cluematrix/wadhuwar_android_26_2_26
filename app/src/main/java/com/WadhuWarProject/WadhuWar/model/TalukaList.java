package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TalukaList {


    @SerializedName("TalukaList")
    @Expose
    public ArrayList<TalukaListData> TalukaList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<TalukaListData> getTalukaList() {
        return TalukaList;
    }

    public void setTalukaList(ArrayList<TalukaListData> talukaList) {
        TalukaList = talukaList;
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

    public static class TalukaListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("district_id")
        @Expose
        String district_id;



        public TalukaListData(String id, String name, String district_id) {
            this.id = id;
            this.name = name;
            this.district_id = district_id;

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

        public String getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(String district_id) {
            this.district_id = district_id;
        }
    }

}
