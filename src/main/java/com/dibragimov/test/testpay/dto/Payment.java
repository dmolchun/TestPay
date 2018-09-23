package com.dibragimov.test.testpay.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {
    private Intent intent;
    private String notificationUrl;
    private Payer payer;
    private Transaction transaction;

    @JsonCreator
    public Payment(
            @JsonProperty(value = "intent", required = true) Intent intent,
            @JsonProperty(value = "notification_url", required = true) String notificationUrl,
            @JsonProperty(value = "payer", required = true) Payer payer,
            @JsonProperty(value = "transaction", required = true) Transaction transaction) {
        this.intent = intent;
        this.notificationUrl = notificationUrl;
        this.payer = payer;
        this.transaction = transaction;
    }

    public Intent getIntent() {
        return intent;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public Payer getPayer() {
        return payer;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "intent=" + intent +
                ", notificationUrl='" + notificationUrl + '\'' +
                ", payer=" + payer +
                ", transaction=" + transaction +
                '}';
    }
}
