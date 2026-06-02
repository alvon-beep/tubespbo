package com.ecotukar.pickup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * AssignCourierRequest — DTO untuk admin assign kurir ke pickup.
 * Diterima dari body JSON: PUT /api/pickups/{id}/assign
 */
@Data
public class AssignCourierRequest {

    @NotBlank(message = "Courier ID tidak boleh kosong")
    private String courierId;
}
