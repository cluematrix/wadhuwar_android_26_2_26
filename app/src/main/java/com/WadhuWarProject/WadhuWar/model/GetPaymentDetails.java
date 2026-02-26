package com.WadhuWarProject.WadhuWar.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPaymentDetails {

    @SerializedName("id")
    String id;

    @SerializedName("entity")
    String entity;

    @SerializedName("amount")
    int amount;

    @SerializedName("amount_captured")
    String amountCaptured;

    @SerializedName("currency")
    String currency;

    @SerializedName("status")
    String status;

    @SerializedName("order_id")
    String orderId;

    @SerializedName("invoice_id")
    String invoiceId;

    @SerializedName("international")
    boolean international;

    @SerializedName("method")
    String method;

    @SerializedName("amount_refunded")
    int amountRefunded;

    @SerializedName("refund_status")
    String refundStatus;

    @SerializedName("captured")
    boolean captured;

    @SerializedName("description")
    String description;

    @SerializedName("card_id")
    String cardId;

    @SerializedName("bank")
    String bank;

    @SerializedName("wallet")
    String wallet;

    @SerializedName("vpa")
    String vpa;

    @SerializedName("email")
    String email;

    @SerializedName("contact")
    String contact;

    @SerializedName("notes")
    List<String> notes;

    @SerializedName("fee")
    int fee;

    @SerializedName("tax")
    int tax;

    @SerializedName("error_code")
    String errorCode;

    @SerializedName("error_description")
    String errorDescription;

    @SerializedName("error_source")
    String errorSource;

    @SerializedName("error_step")
    String errorStep;

    @SerializedName("error_reason")
    String errorReason;

    @SerializedName("acquirer_data")
    AcquirerData acquirerData;

    @SerializedName("created_at")
    int createdAt;

    @SerializedName("provider")
    String provider;

    @SerializedName("upi")
    Upi upi;

    @SerializedName("reward")
    String reward;


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
    public String getEntity() {
        return entity;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmountCaptured(String amountCaptured) {
        this.amountCaptured = amountCaptured;
    }
    public String getAmountCaptured() {
        return amountCaptured;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getCurrency() {
        return currency;
    }

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

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }
    public boolean getInternational() {
        return international;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getMethod() {
        return method;
    }

    public void setAmountRefunded(int amountRefunded) {
        this.amountRefunded = amountRefunded;
    }
    public int getAmountRefunded() {
        return amountRefunded;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }
    public String getRefundStatus() {
        return refundStatus;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }
    public boolean getCaptured() {
        return captured;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getCardId() {
        return cardId;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getBank() {
        return bank;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }
    public String getWallet() {
        return wallet;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }
    public String getVpa() {
        return vpa;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContact() {
        return contact;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
    public List<String> getNotes() {
        return notes;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
    public int getFee() {
        return fee;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }
    public int getTax() {
        return tax;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }
    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorStep(String errorStep) {
        this.errorStep = errorStep;
    }
    public String getErrorStep() {
        return errorStep;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
    public String getErrorReason() {
        return errorReason;
    }

    public void setAcquirerData(AcquirerData acquirerData) {
        this.acquirerData = acquirerData;
    }
    public AcquirerData getAcquirerData() {
        return acquirerData;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }
    public int getCreatedAt() {
        return createdAt;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    public String getProvider() {
        return provider;
    }

    public void setUpi(Upi upi) {
        this.upi = upi;
    }
    public Upi getUpi() {
        return upi;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
    public String getReward() {
        return reward;
    }


    public class AcquirerData {

        @SerializedName("rrn")
        String rrn;


        public void setRrn(String rrn) {
            this.rrn = rrn;
        }
        public String getRrn() {
            return rrn;
        }

    }


    public class Upi {

        @SerializedName("payer_account_type")
        String payerAccountType;

        @SerializedName("vpa")
        String vpa;


        public void setPayerAccountType(String payerAccountType) {
            this.payerAccountType = payerAccountType;
        }
        public String getPayerAccountType() {
            return payerAccountType;
        }

        public void setVpa(String vpa) {
            this.vpa = vpa;
        }
        public String getVpa() {
            return vpa;
        }

    }

}