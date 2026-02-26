package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EbookList {
    @SerializedName("EbookList")
    @Expose
    public ArrayList<EbookListData> EbookList;



    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<EbookListData> getEbookList() {
        return EbookList;
    }

    public void setEbookList(ArrayList<EbookListData> ebookList) {
        EbookList = ebookList;
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

    public static class EbookListData {

        @SerializedName("status")
        @Expose
        String status;


        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("amount")
        @Expose
        String amount;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }


}
