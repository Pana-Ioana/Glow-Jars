package com.glowjar.backend.dto;

public class PaymentIntentResponseDto {
    private String clientSecret;

    public PaymentIntentResponseDto(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
