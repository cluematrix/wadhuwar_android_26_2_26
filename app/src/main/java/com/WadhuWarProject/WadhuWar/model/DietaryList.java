package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DietaryList {


    @SerializedName("DietaryList")
    @Expose
    public ArrayList<DietaryListData> DietaryList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<DietaryListData> getDietaryList() {
        return DietaryList;
    }

    public void setDietaryList(ArrayList<DietaryListData> dietaryList) {
        DietaryList = dietaryList;
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

    public static class DietaryListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;





        public DietaryListData(String id, String name) {
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
