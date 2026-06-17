package com.ecotukar.controller;

import com.ecotukar.model.User;
import com.ecotukar.model.PickupRequest;
import com.ecotukar.model.WalletTransaction;
import com.ecotukar.model.WalletTransaction;
import com.ecotukar.model.CourierUser;
import com.ecotukar.repository.UserRepository;
import com.ecotukar.service.EcoTukarService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {
    private final EcoTukarService service;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiController(EcoTukarService service, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get current customer profile
    @GetMapping("/profile")
    public User getProfile(java.security.Principal principal) {
        String username = (principal != null) ? principal.getName() : "sarah";
        User user = service.getUserByUsername(username);
        if (user == null) {
            // Safe fallback
            return new com.ecotukar.model.CustomerUser(username, username, username + "@mail.com", "👤", "Alamat Belum Set", "2026", 0, 0);
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
    public PickupRequest createPickup(@RequestBody Map<String, Object> body, java.security.Principal principal) {
        String wasteType = (String) body.getOrDefault("wasteType", "Plastik");
        double weight = Double.parseDouble(body.getOrDefault("weight", "1.0").toString());
        String date = (String) body.getOrDefault("date", "Hari Ini");
        String note = (String) body.getOrDefault("note", "");
        String customAddress = (String) body.get("address");
        String username = (principal != null) ? principal.getName() : (String) body.getOrDefault("username", "sarah");
        
        if (weight < 3.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estimasi berat minimal 3 kg!");
        }

        User customer = service.getUserByUsername(username);
        String name = (customer != null) ? customer.getName() : "Pengguna Baru";
        String addr = (customAddress != null && !customAddress.trim().isEmpty()) ? customAddress : ((customer != null) ? customer.getAddress() : "Alamat default");

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

    // Verify pickup request physical condition
    @PostMapping("/pickups/{id}/verify")
    public Map<String, Object> verifyPickup(@PathVariable String id, @RequestBody Map<String, Object> body) {
        boolean dataValid = Boolean.parseBoolean(body.getOrDefault("dataValid", "false").toString());
        String physicalCondition = (String) body.getOrDefault("physicalCondition", "Sesuai");

        com.ecotukar.model.PickupVerification verification = service.verifyPickup(id, dataValid, physicalCondition);
        if (verification != null) {
            return Map.of(
                "message", "Penjemputan berhasil diverifikasi",
                "status", "success",
                "verificationId", verification.getVerificationId()
            );
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gagal memverifikasi penjemputan. Tiket tidak ditemukan.");
        }
    }

    // Convert waste to coin points
    @PostMapping("/pickups/{id}/convert")
    public Map<String, String> convertCoins(@PathVariable String id, @RequestBody Map<String, Object> body) {
        double actualWeight = Double.parseDouble(body.get("actualWeight").toString());
        int ratePerKg = Integer.parseInt(body.get("ratePerKg").toString());
        
        if (actualWeight < 3.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Berat riil sampah minimal 3 kg!");
        }

        service.convertCoins(id, actualWeight, ratePerKg);
        return Map.of("message", "Coins successfully credited to wallet", "status", "success");
    }

    // Convert coins to ewallet balance
    @PostMapping("/wallet/convert")
    public Map<String, Object> convertCoinsToEWallet(@RequestBody Map<String, Object> body, java.security.Principal principal) {
        String username = (principal != null) ? principal.getName() : (String) body.getOrDefault("username", "sarah");
        int coins = Integer.parseInt(body.get("coins").toString());

        if (coins < 600) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimal konversi adalah 600 koin (Rp30.000)");
        }

        boolean success = service.convertCoinsToEWallet(username, coins);
        if (!success) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Koin tidak mencukupi atau pengguna tidak ditemukan");
        }

        return Map.of("message", "Konversi koin ke saldo E-Wallet berhasil!", "status", "success");
    }

    // Get point wallet history transactions for logged-in user
    @GetMapping("/transactions")
    public List<WalletTransaction> getTransactions(java.security.Principal principal) {
        String username = (principal != null) ? principal.getName() : "sarah";
        User user = service.getUserByUsername(username);
        if (user != null) {
            return service.getTransactionsByUser(user);
        }
        return service.getTransactions(); // Fallback for admin or if not found
    }

    // Get list of all couriers from database
    @GetMapping("/couriers")
    public List<Map<String, String>> getCouriers() {
        return userRepository.findAll().stream()
            .filter(u -> u instanceof CourierUser)
            .map(u -> Map.of("username", u.getUsername(), "name", u.getName()))
            .collect(java.util.stream.Collectors.toList());
    }

    // Admin register new courier
    @PostMapping("/admin/couriers")
    public Map<String, String> registerCourier(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String name = body.get("name");
        String password = body.get("password");

        if (username == null || name == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data tidak lengkap");
        }

        if (userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username sudah dipakai");
        }

        CourierUser courier = new CourierUser();
        courier.setUsername(username);
        courier.setName(name);
        courier.setPassword(passwordEncoder.encode(password));
        courier.setEmail(username + "@ecotukar.com");
        courier.setAvatar("👷");
        courier.setJoined(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy")));
        courier.setPoints(0);
        courier.setEwalletBalance(0);

        userRepository.save(courier);
        
        return Map.of("message", "Kurir berhasil didaftarkan", "status", "success");
    }
}
