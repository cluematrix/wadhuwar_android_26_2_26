package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryList {


    @SerializedName("CountryList")
    @Expose
    public ArrayList<CountryListData> countryList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<CountryListData> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<CountryListData> countryList) {
        this.countryList = countryList;
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


    public static class CountryListData {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("stateList")
        @Expose
        public ArrayList<StateList> stateList;


        public CountryListData(String id, String name,ArrayList<StateList> stateList) {
            this.id = id;
            this.name = name;
            this.stateList = stateList;

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

        public ArrayList<StateList> getStateList() {
            return stateList;
        }

        public void setStateList(ArrayList<StateList> stateList) {
            this.stateList = stateList;
        }
    }



}
