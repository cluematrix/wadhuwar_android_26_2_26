package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class EducationType {

    @SerializedName("EducationList")
    @Expose
    private ArrayList<EducationTypeList> educationList = null;
    @SerializedName("resid")
    @Expose
    private String resid;
    @SerializedName("resMsg")
    @Expose
    private String resMsg;

    public ArrayList<EducationTypeList> getEducationTypeList() {
        return educationList;
    }

    public void setEducationList(ArrayList<EducationTypeList> educationList) {
        this.educationList = educationList;
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

    public static class EducationTypeList {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        public EducationTypeList(String id, String name) {
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


