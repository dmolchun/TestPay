package com.dibragimov.test.testpay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum State {
    @JsonProperty("created")
    CREATED,
    @JsonProperty("approved")
    APPROVED,
    @JsonProperty("failed")
    FAILED;
}
