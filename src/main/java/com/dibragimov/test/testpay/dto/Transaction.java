package com.dibragimov.test.testpay.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A transaction defines what the payment is for and
 */
public class Transaction {
    @JsonProperty(value = "external_id")
    private String externalId;
    private Amount amount;
    private String description;

    @JsonCreator
    public Transaction(@JsonProperty(value = "amount", required = true) Amount amount) {
        this.amount = amount;
    }

    public String getExternalId() {
        return externalId;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "externalId='" + externalId + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
