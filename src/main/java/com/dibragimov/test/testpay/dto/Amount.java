package com.dibragimov.test.testpay.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The amount being collected
 */
public class Amount {
    private String value;
    private String currency;

    @JsonCreator
    public Amount(
            @JsonProperty(value = "value", required = true) String value,
            @JsonProperty(value = "currency", required = true) String currency) {
        this.value = value;
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value='" + value + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
