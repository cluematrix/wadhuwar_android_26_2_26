package com.WadhuWarProject.WadhuWar.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MatchesTabList {


    @SerializedName("MatchesTabList")
    @Expose
    public ArrayList<MatchesTabListData> MatchesTabList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<MatchesTabListData> getMatchesTabList() {
        return MatchesTabList;
    }

    public void setMatchesTabList(ArrayList<MatchesTabListData> matchesTabList) {
        MatchesTabList = matchesTabList;
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

    public static class MatchesTabListData {



        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("id")
        @Expose
        String id;

        public MatchesTabListData(String name,String id ) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
