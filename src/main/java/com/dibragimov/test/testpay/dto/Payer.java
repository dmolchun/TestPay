package com.dibragimov.test.testpay.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The source of funds for payment
 * Email is required because is used as secret for sha2 encoding
 */
public class Payer {

    @JsonCreator
    public Payer(@JsonProperty(value = "email", required = true) String email) {
        this.email = email;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Payer{" +
                "email='" + email + '\'' +
                '}';
    }
}
