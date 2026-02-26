package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatList {



    @SerializedName("ChatList")
    @Expose
    public ArrayList<ChatListData> ChatList;


    @SerializedName("total_pages")
    @Expose
    String total_pages;



    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;


    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<ChatListData> getChatList() {
        return ChatList;
    }

    public void setChatList(ArrayList<ChatListData> chatList) {
        ChatList = chatList;
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


    public static class ChatListData {

        @SerializedName("mcount")
        @Expose
        String mcount;


        @SerializedName("chkonline")
        @Expose
        String chkonline;

        @SerializedName("op_id")
        @Expose
        String op_id;

        @SerializedName("image")
        @Expose
        String image;

        @SerializedName("chat_id")
        @Expose
        String chat_id;


        @SerializedName("sender_id")
        @Expose
        String sender_id;


        @SerializedName("reciver_id")
        @Expose
        String reciver_id;


        @SerializedName("message")
        @Expose
        String message;

        @SerializedName("status")
        @Expose
        String status;

        @SerializedName("timestamp")
        @Expose
        String timestamp;

        @SerializedName("name")
        @Expose
        String name;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReciver_id() {
            return reciver_id;
        }

        public void setReciver_id(String reciver_id) {
            this.reciver_id = reciver_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOp_id() {
            return op_id;
        }

        public void setOp_id(String op_id) {
            this.op_id = op_id;
        }

        public String getChkonline() {
            return chkonline;
        }

        public void setChkonline(String chkonline) {
            this.chkonline = chkonline;
        }

        public String getMcount() {
            return mcount;
        }

        public void setMcount(String mcount) {
            this.mcount = mcount;
        }
    }


}
