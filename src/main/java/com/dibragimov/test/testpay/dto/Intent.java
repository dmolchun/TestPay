package com.dibragimov.test.testpay.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The payment intent.
 */
public enum Intent {
    @JsonProperty("order")
    ORDER
}
