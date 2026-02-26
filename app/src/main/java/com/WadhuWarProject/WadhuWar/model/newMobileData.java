package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

public class newMobileData {

        @SerializedName("resid")
        int resid;

        @SerializedName("resMsg")
        String resMsg;

        @SerializedName("OTP")
        int OTP;

        @SerializedName("user_id")
        String userId;

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

        public void setOTP(int OTP) {
            this.OTP = OTP;
        }
        public int getOTP() {
            return OTP;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getUserId() {
            return userId;
        }
    }