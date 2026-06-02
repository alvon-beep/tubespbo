package com.ecotukar.coin.dto;

import com.ecotukar.pickup.WasteType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * ConvertCoinRequest — DTO untuk admin konversi sampah ke koin.
 * Diterima dari body JSON: POST /api/coins/convert
 *
 * Admin input berat aktual + rate, sistem hitung total koin
 * dan kredit ke e-wallet customer.
 */
@Data
public class ConvertCoinRequest {

    @NotBlank(message = "Pickup ID tidak boleh kosong")
    private String pickupId;

    @NotNull(message = "Jenis sampah tidak boleh kosong")
    private WasteType wasteType;

    @Min(value = 0, message = "Berat aktual tidak boleh negatif")
    private double actualWeight;

    @Min(value = 1, message = "Rate koin minimal 1 per kg")
    private int ratePerKg; // koin per kg
}
