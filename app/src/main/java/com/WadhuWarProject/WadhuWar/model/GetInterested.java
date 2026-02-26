package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

public class GetInterested {

    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;

    @SerializedName("insert_id")
    int insertId;


    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setInsertId(int insertId) {
        this.insertId = insertId;
    }
    public int getInsertId() {
        return insertId;
    }

}
