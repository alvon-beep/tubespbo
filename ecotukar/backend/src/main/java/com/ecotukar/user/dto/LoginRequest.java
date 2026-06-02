package com.ecotukar.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * LoginRequest — DTO untuk request login.
 * Diterima dari body JSON: POST /api/auth/login
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;
}
