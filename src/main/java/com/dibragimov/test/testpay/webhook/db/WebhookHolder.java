package com.dibragimov.test.testpay.webhook.db;

import com.dibragimov.test.testpay.dto.State;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WebhookHolder {
    @Id
    private String id;
    private String currency;
    private String amount;
    private String externalId;
    private State status;
    private String notificationUrl;
    private String secret;
    private String sha2sig;
    private int retryCount;

    public WebhookHolder() {
    }

    public WebhookHolder(String id,
                         String currency,
                         String amount,
                         String externalId,
                         State status,
                         String notificationUrl,
                         String secret) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.externalId = externalId;
        this.status = status;
        this.notificationUrl = notificationUrl;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public String getExternalId() {
        return externalId;
    }

    public State getStatus() {
        return status;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public String getSecret() {
        return secret;
    }

    public String getSha2sig() {
        return sha2sig;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setSha2sig(String sha2sig) {
        this.sha2sig = sha2sig;
    }

    public void incrementCounter() {
        retryCount++;
    }

    public String asString() {
        return currency +
                amount +
                secret +
                id +
                externalId +
                status;
    }
}
