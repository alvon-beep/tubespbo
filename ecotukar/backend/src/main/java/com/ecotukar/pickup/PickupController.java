package com.ecotukar.pickup;

import com.ecotukar.pickup.dto.AssignCourierRequest;
import com.ecotukar.pickup.dto.PickupRequest;
import com.ecotukar.pickup.dto.UpdateStatusRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PickupController — REST API endpoint untuk manajemen pickup sampah.
 *
 * Endpoints:
 * POST   /api/pickups                        → customer buat request pickup
 * GET    /api/pickups                        → admin lihat semua pickup
 * GET    /api/pickups/{id}                   → detail 1 pickup
 * GET    /api/pickups/customer/{customerId}  → riwayat pickup milik customer
 * GET    /api/pickups/courier/{courierId}    → pickup yang diassign ke kurir
 * PUT    /api/pickups/{id}/assign            → admin assign kurir
 * PUT    /api/pickups/{id}/status            → kurir update status
 */
@RestController
@RequestMapping("/api/pickups")
@CrossOrigin("*")
public class PickupController {

    @Autowired
    private PickupService pickupService;

    // POST /api/pickups — customer buat pickup request baru
    @PostMapping
    public ResponseEntity<?> createPickup(@Valid @RequestBody PickupRequest req) {
        try {
            PickupEntity pickup = pickupService.createPickup(req);
            return ResponseEntity.ok(pickup);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/pickups — semua pickup (admin)
    @GetMapping
    public ResponseEntity<List<PickupEntity>> getAllPickups(
            @RequestParam(required = false) PickupStatus status) {
        if (status != null) {
            return ResponseEntity.ok(pickupService.getPickupsByStatus(status));
        }
        return ResponseEntity.ok(pickupService.getAllPickups());
    }

    // GET /api/pickups/{id} — detail 1 pickup
    @GetMapping("/{id}")
    public ResponseEntity<?> getPickupById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(pickupService.getPickupById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/pickups/customer/{customerId} — riwayat pickup customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getPickupsByCustomer(@PathVariable String customerId) {
        try {
            return ResponseEntity.ok(pickupService.getPickupsByCustomer(customerId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/pickups/courier/{courierId} — pickup assigned ke kurir
    @GetMapping("/courier/{courierId}")
    public ResponseEntity<?> getPickupsByCourier(@PathVariable String courierId) {
        try {
            return ResponseEntity.ok(pickupService.getPickupsByCourier(courierId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/pickups/{id}/assign — admin assign kurir ke pickup
    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignCourier(
            @PathVariable String id,
            @Valid @RequestBody AssignCourierRequest req) {
        try {
            return ResponseEntity.ok(pickupService.assignCourier(id, req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/pickups/{id}/status — kurir update status pickup
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest req) {
        try {
            return ResponseEntity.ok(pickupService.updateStatus(id, req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
