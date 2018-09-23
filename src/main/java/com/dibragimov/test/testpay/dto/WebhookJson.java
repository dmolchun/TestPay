package com.dibragimov.test.testpay.dto;

import com.dibragimov.test.testpay.webhook.db.WebhookHolder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookJson {
    @JsonProperty
    private String currency;
    @JsonProperty
    private String amount;
    @JsonProperty
    private String id;
    @JsonProperty(value = "external_id")
    private String externalId;
    @JsonProperty
    private State status;
    @JsonProperty
    private String sha2sig;

    private WebhookJson(String currency, String amount, String id, String externalId, State status, String sha2sig) {
        this.currency = currency;
        this.amount = amount;
        this.id = id;
        this.externalId = externalId;
        this.status = status;
        this.sha2sig = sha2sig;
    }

    public static WebhookJson from(WebhookHolder holder) {
        return new WebhookJson(
                holder.getCurrency(),
                holder.getAmount(),
                holder.getId(),
                holder.getExternalId(),
                holder.getStatus(),
                holder.getSha2sig()
        );
    }
}
