package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EducationList {


    @SerializedName("EducationList")
    @Expose
    public ArrayList<EducationListData> EducationList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<EducationListData> getEducationList() {
        return EducationList;
    }

    public void setEducationList(ArrayList<EducationListData> educationList) {
        EducationList = educationList;
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


    public static class EducationListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;





        public EducationListData(String id, String name) {
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
