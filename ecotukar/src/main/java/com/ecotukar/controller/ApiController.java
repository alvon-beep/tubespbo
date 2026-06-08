package com.ecotukar.controller;

import com.ecotukar.model.User;
import com.ecotukar.model.PickupRequest;
import com.ecotukar.model.WalletTransaction;
import com.ecotukar.service.EcoTukarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {
    private final EcoTukarService service;

    public ApiController(EcoTukarService service) {
        this.service = service;
    }

    // Get current customer profile (defaults to "sarah")
    @GetMapping("/profile")
    public User getProfile(@RequestParam(defaultValue = "sarah") String username) {
        User user = service.getUserByUsername(username);
        if (user == null) {
            // Safe fallback
            return new User(username, "", username, username + "@mail.com", "CUSTOMER", "👤", "Alamat Belum Set", "2026", 0);
        }
        return user;
    }

    // Get list of all pickup requests
    @GetMapping("/pickups")
    public List<PickupRequest> getPickups() {
        return service.getPickups();
    }

    // Create a new pickup request
    @PostMapping("/pickups")
    public PickupRequest createPickup(@RequestBody Map<String, Object> body) {
        String wasteType = (String) body.getOrDefault("wasteType", "Plastik");
        double weight = Double.parseDouble(body.getOrDefault("weight", "1.0").toString());
        String date = (String) body.getOrDefault("date", "Hari Ini");
        String note = (String) body.getOrDefault("note", "");
        String username = (String) body.getOrDefault("username", "sarah");
        
        User customer = service.getUserByUsername(username);
        String name = (customer != null) ? customer.getName() : "Pengguna Baru";
        String addr = (customer != null) ? customer.getAddress() : "Alamat default";

        PickupRequest req = new PickupRequest(
            null, username, name, addr, wasteType, weight, date, note, "Belum", "PENDING", "12:00"
        );
        return service.addPickup(req);
    }

    // Assign courier to a ticket
    @PostMapping("/pickups/{id}/assign")
    public Map<String, String> assignCourier(@PathVariable String id, @RequestBody Map<String, String> body) {
        String courier = body.get("courier");
        service.assignCourier(id, courier);
        return Map.of("message", "Courier assigned successfully", "status", "success");
    }

    // Update status of a ticket
    @PostMapping("/pickups/{id}/status")
    public Map<String, String> updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        service.updateStatus(id, status);
        return Map.of("message", "Status updated successfully", "status", "success");
    }

    // Convert waste to coin points
    @PostMapping("/pickups/{id}/convert")
    public Map<String, String> convertCoins(@PathVariable String id, @RequestBody Map<String, Object> body) {
        double actualWeight = Double.parseDouble(body.get("actualWeight").toString());
        int ratePerKg = Integer.parseInt(body.get("ratePerKg").toString());
        
        service.convertCoins(id, actualWeight, ratePerKg);
        return Map.of("message", "Coins successfully credited to wallet", "status", "success");
    }

    // Get point wallet history transactions
    @GetMapping("/transactions")
    public List<WalletTransaction> getTransactions(@RequestParam(required = false) String username) {
        if (username == null || username.equalsIgnoreCase("all")) {
            return service.getAllTransactions();
        }
        return service.getTransactions(username);
    }
}
