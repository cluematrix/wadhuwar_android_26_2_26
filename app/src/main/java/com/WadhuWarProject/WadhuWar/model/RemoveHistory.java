package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.SerializedName;

public class RemoveHistory {

        @SerializedName("resid")
        int resid;

        @SerializedName("resMsg")
        String resMsg;


        public void setResid(int resid) {
            this.resid = resid;
        }
        public int getResid() {
            return resid;
        }

        public void setResMsg(String resMsg) {
            this.resMsg = resMsg;
        }
        public String getResMsg() {
            return resMsg;
        }

    }

