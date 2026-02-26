package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MotherOccupationList {


    @SerializedName("MotherOccupation")
    @Expose
    public ArrayList<MotherOccupationData> MotherOccupation;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<MotherOccupationData> getMotherOccupation() {
        return MotherOccupation;
    }

    public void setMotherOccupation(ArrayList<MotherOccupationData> motherOccupation) {
        MotherOccupation = motherOccupation;
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

    public static class MotherOccupationData {



        @SerializedName("name")
        @Expose
        String name;





        public MotherOccupationData(String name) {
            this.name = name;

        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

}
