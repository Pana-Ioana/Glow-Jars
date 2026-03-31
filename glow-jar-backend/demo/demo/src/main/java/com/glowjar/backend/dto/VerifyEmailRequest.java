package com.glowjar.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VerifyEmailRequest {
    @NotBlank(message = "Emailul este obligatoriu.")
    @Email(message = "Email invalid.")
    private String email;

    @NotBlank(message = "Codul este obligatoriu.")
    private String code;

    public VerifyEmailRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}