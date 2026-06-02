package com.ecotukar.pickup.dto;

import com.ecotukar.pickup.PickupStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * UpdateStatusRequest — DTO untuk kurir update status pickup.
 * Diterima dari body JSON: PUT /api/pickups/{id}/status
 */
@Data
public class UpdateStatusRequest {

    @NotNull(message = "Status tidak boleh kosong")
    private PickupStatus status; // ON_ROUTE, COMPLETED, CANCELLED
}
