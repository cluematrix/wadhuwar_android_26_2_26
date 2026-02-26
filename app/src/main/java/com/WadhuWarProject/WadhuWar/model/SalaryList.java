package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SalaryList {

    @SerializedName("SalaryList")
    @Expose
    private ArrayList<SalaryListData> salaryList = null;
    @SerializedName("resid")
    @Expose
    private String resid;
    @SerializedName("resMsg")
    @Expose
    private String resMsg;

    public ArrayList<SalaryListData> getSalaryList() {
        return salaryList;
    }

    public void setSalaryList(ArrayList<SalaryListData> salaryList) {
        this.salaryList = salaryList;
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


    public static class SalaryListData {

        public SalaryListData(String name) {
            this.name = name;
        }

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
