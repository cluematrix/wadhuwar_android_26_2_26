package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Chat {

    @SerializedName("last_seen")
    @Expose
    String last_seen;

    @SerializedName("count")
    @Expose
    String count;


    @SerializedName("total_pages")
    @Expose
    String total_pages;

    @SerializedName("last_id")
    @Expose
    String last_id;



    @SerializedName("ChatList")
    @Expose
    public ArrayList<ChatListData> ChatList;

    @SerializedName("resid")
    @Expose
    String res;

    @SerializedName("resMsg")
    @Expose
    String msg;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public ArrayList<ChatListData> getChat_list() {
        return ChatList;
    }

    public void setChat_list(ArrayList<ChatListData> ChatList) {
        this.ChatList = ChatList;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ChatListData  {

        @SerializedName("chat_id")
        @Expose
        String chat_id;

        @SerializedName("message")
        @Expose
        String message;

        @SerializedName("sender_id")
        @Expose
        String sender_id;

        @SerializedName("reciver_id")
        @Expose
        String reciver_id;

        @SerializedName("timestamp")
        @Expose
        String timestamp;


        public ChatListData(String chat_id, String message, String sender_id, String reciver_id, String timestamp){
            this.chat_id = chat_id;
            this.message = message;
            this.sender_id = sender_id;
            this.reciver_id = reciver_id;
            this.timestamp = timestamp;

        }

        public ChatListData() {

        }

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReceiver_id() {
            return reciver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.reciver_id = receiver_id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }


    }



}
