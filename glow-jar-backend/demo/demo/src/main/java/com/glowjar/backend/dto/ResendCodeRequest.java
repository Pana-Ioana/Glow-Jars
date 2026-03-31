package com.glowjar.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResendCodeRequest {
    @NotBlank(message = "Emailul este obligatoriu.")
    @Email(message = "Email invalid.")
    private String email;

    public ResendCodeRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}