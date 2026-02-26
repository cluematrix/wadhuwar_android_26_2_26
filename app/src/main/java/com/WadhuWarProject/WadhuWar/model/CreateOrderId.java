package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

public class CreateOrderId {

    @SerializedName("status")
    String status;

    @SerializedName("order_id")
    String orderId;

    @SerializedName("message")
    String message;


    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}