package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShowTabList {


    @SerializedName("ShowTabList")
    @Expose
    public ArrayList<ShowTabListData> ShowTabList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<ShowTabListData> getShowTabList() {
        return ShowTabList;
    }

    public void setShowTabList(ArrayList<ShowTabListData> showTabList) {
        ShowTabList = showTabList;
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

    public static class ShowTabListData {



        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("id")
        @Expose
        String id;

        public ShowTabListData(String name,String id ) {
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
