package com.ecotukar.user.dto;

import com.ecotukar.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * RegisterRequest — DTO untuk request register user baru.
 * Diterima dari body JSON: POST /api/auth/register
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotNull(message = "Role tidak boleh kosong")
    private UserRole role; // CUSTOMER, COURIER, atau ADMIN

    // Field opsional sesuai role
    private String address;       // untuk CUSTOMER
    private String vehicleInfo;   // untuk COURIER
    private String bankSampahName; // untuk ADMIN
}
