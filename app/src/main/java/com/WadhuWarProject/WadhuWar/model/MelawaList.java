package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MelawaList implements Serializable {


    @SerializedName("MelawaList")
    @Expose
    public ArrayList<MelawaListData> melawaList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<MelawaListData> getMelawaList() {
        return melawaList;
    }

    public void setMelawaList(ArrayList<MelawaListData> melawaList) {
        this.melawaList = melawaList;
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

    public class MelawaListData {


        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("amount")
        @Expose
        String amount;

        @SerializedName("status")
        @Expose
        String status;

        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("event_date")
        @Expose
        String event_date;

        @SerializedName("event_time")
        @Expose
        String event_time;


        @SerializedName("location")
        @Expose
        String location;


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

        public String getEvent_date() {
            return event_date;
        }

        public void setEvent_date(String event_date) {
            this.event_date = event_date;
        }

        public String getEvent_time() {
            return event_time;
        }

        public void setEvent_time(String event_time) {
            this.event_time = event_time;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
