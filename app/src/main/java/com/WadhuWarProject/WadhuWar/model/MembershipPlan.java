package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MembershipPlan {


    @SerializedName("MembershipPlan")
    @Expose
    public ArrayList<MembershipPlanData> MembershipPlan;



    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<MembershipPlanData> getMembershipPlan() {
        return MembershipPlan;
    }

    public void setMembershipPlan(ArrayList<MembershipPlanData> membershipPlan) {
        MembershipPlan = membershipPlan;
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


    public class MembershipPlanData {

        @SerializedName("description")
        @Expose
        String description;


        @SerializedName("month")
        @Expose
        String month;

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("status")
        @Expose
        String status;




        @SerializedName("image")
        @Expose
        String image;



        @SerializedName("name")
        @Expose
        String name;


        @SerializedName("amount")
        @Expose
        String amount;


        @SerializedName("days")
        @Expose
        String days;

        @SerializedName("matches")
        @Expose
        String matches;


        @SerializedName("discount")
        @Expose
        String discount;


        @SerializedName("discounted_price")
        @Expose
        String discounted_price;


        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(String discounted_price) {
            this.discounted_price = discounted_price;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getMatches() {
            return matches;
        }

        public void setMatches(String matches) {
            this.matches = matches;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
