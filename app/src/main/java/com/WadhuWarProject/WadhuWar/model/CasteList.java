package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CasteList {


    @SerializedName("CasteList")
    @Expose
    public ArrayList<CasteListData> CasteList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;


    public ArrayList<CasteListData> getCasteList() {
        return CasteList;
    }

    public void setCasteList(ArrayList<CasteListData> casteList) {
        CasteList = casteList;
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

    public static class CasteListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("SubCasteList")
        @Expose
        public ArrayList<SubCasteList> SubCasteList;


        public CasteListData(String id, String name, ArrayList<SubCasteList> SubCasteList) {
            this.id = id;
            this.name = name;
            this.SubCasteList = SubCasteList;

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

        public ArrayList<SubCasteList> getSubCasteList() {
            return SubCasteList;
        }

        public void setSubCasteList(ArrayList<SubCasteList> subCasteList) {
            SubCasteList = subCasteList;
        }
    }

}
