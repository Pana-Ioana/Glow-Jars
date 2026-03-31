package com.glowjar.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {
    @NotBlank(message = "Emailul este obligatoriu.")
    @Email(message = "Email invalid.")
    private String email;

    @NotBlank(message = "Codul este obligatoriu.")
    private String code;

    @NotBlank(message = "Parola noua este obligatorie.")
    @Size(min = 8, message = "Parola noua trebuie sa aiba minim 8 caractere.")
    private String newPassword;

    public ResetPasswordRequest() {}

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}