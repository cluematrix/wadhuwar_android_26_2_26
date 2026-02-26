package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.SerializedName;

public class Checksum {


    @SerializedName("mid")
    private String mid;

    @SerializedName("orderid")
    private String orderid;

    @SerializedName("custid")
    private String custid;

    @SerializedName("chanelid")
    private String chanelid;

    @SerializedName("amount")
    private String amount;

    @SerializedName("website")
    private String website;

    @SerializedName("callbackurl")
    private String callbackurl;

    @SerializedName("industrytype")
    private String industrytype;


    @SerializedName("paytmChecksum")
    private String checksumHash;


    @SerializedName("resid")
    private String resid;


    @SerializedName("resMsg")
    private String resMsg;



    @SerializedName("ORDER_ID")
    private String orderId;

    @SerializedName("payt_STATUS")
    private String paytStatus;

    public Checksum(String checksumHash, String orderId, String paytStatus) {
        this.checksumHash = checksumHash;
        this.orderId = orderId;
        this.paytStatus = paytStatus;
    }

    public String getChecksumHash() {
        return checksumHash;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaytStatus() {
        return paytStatus;
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getChanelid() {
        return chanelid;
    }

    public void setChanelid(String chanelid) {
        this.chanelid = chanelid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getIndustrytype() {
        return industrytype;
    }

    public void setIndustrytype(String industrytype) {
        this.industrytype = industrytype;
    }

    public void setChecksumHash(String checksumHash) {
        this.checksumHash = checksumHash;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setPaytStatus(String paytStatus) {
        this.paytStatus = paytStatus;
    }
}