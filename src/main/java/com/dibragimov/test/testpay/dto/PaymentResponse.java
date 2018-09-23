package com.dibragimov.test.testpay.dto;

public class PaymentResponse {
    private String id;
    private String createTime;
    private State state;

    public PaymentResponse(String id, String createTime, State state) {
        this.id = id;
        this.createTime = createTime;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public State getState() {
        return state;
    }
}
