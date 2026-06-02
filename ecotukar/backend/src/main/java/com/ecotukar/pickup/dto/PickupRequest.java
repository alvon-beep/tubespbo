package com.ecotukar.pickup.dto;

import com.ecotukar.pickup.WasteType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * PickupRequest — DTO untuk customer buat pickup request baru.
 * Diterima dari body JSON: POST /api/pickups
 */
@Data
public class PickupRequest {

    @NotBlank(message = "Customer ID tidak boleh kosong")
    private String customerId;

    @NotNull(message = "Jenis sampah tidak boleh kosong")
    private WasteType wasteType;

    @Min(value = 1, message = "Estimasi berat minimal 1 kg")
    private double estimatedWeight;

    @NotNull(message = "Tanggal pickup tidak boleh kosong")
    private LocalDate pickupDate;

    // Catatan opsional dari customer
    private String note;
}
