package com.ecotukar.controller;

import com.ecotukar.model.Akun;
import com.ecotukar.model.User;
import com.ecotukar.model.PickupRequest;
import com.ecotukar.model.WalletTransaction;
import com.ecotukar.service.EcoTukarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // ==================== SECURITY & AUTHENTICATION ENDPOINTS ====================

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body, HttpSession session) {
        String email = body.get("email");
        String password = body.get("password");

        Akun authenticated = service.authenticate(email, password);
        if (authenticated != null) {
            session.setAttribute("user", authenticated); // Store in HTTP session
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login berhasil!",
                "user", Map.of(
                    "id_akun", authenticated.getIdAkun(),
                    "email", authenticated.getEmail(),
                    "role", authenticated.getRole()
                )
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "message", "Email atau password salah."
            ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        String namaLengkap = body.get("nama_lengkap");
        String noTelepon = body.get("no_telepon");
        String email = body.get("email");
        String password = body.get("password");

        try {
            service.registerCustomer(namaLengkap, noTelepon, email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "Registrasi warga berhasil! Silakan login."
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Gagal melakukan registrasi."
            ));
        }
    }

    @PostMapping("/admin/register-admin")
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody Map<String, String> body) {
        String namaLengkap = body.get("nama_lengkap");
        String noTelepon = body.get("no_telepon");
        String email = body.get("email");
        String password = body.get("password");

        try {
            service.registerAdmin(namaLengkap, noTelepon, email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "Registrasi admin berhasil! Silakan login."
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Gagal mendaftarkan admin."
            ));
        }
    }

    @PostMapping("/admin/register-tukang")
    public ResponseEntity<Map<String, Object>> registerTukang(@RequestBody Map<String, String> body) {
        String namaLengkap = body.get("nama_lengkap");
        String noTelepon = body.get("no_telepon");
        String email = body.get("email");
        String password = body.get("password");

        try {
            service.registerTukang(namaLengkap, noTelepon, email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "Akun Tukang berhasil dibuat oleh Admin."
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Gagal mendaftarkan kurir."
            ));
        }
    }

    // ==================== PROFILE ENDPOINT ====================

    @GetMapping("/profile")
    public User getProfile(@RequestParam(required = false) String username, HttpSession session) {
        Akun loggedInUser = (Akun) session.getAttribute("user");
        
        // 1. If actually logged in through session, fetch their profile details
        if (loggedInUser != null) {
            return service.convertAkunToUser(loggedInUser);
        }
        
        // 2. Fallback to simulator request params if no active session
        if (username != null && !username.isEmpty()) {
            User user = service.getUserByUsername(username);
            if (user != null) return user;
        }

        // 3. Absolute safe fallback
        return new User("sarah.putri@mail.com", "Sarah Putri", "sarah.putri@mail.com", "CUSTOMER", "👩‍🦰", "Jl. Melati No. 21, Bandung", "Juni 2026", 0);
    }

    // ==================== PICKUP & TICKET WORKFLOWS ====================

    @GetMapping("/pickups")
    public List<PickupRequest> getPickups() {
        return service.getPickups();
    }

    @PostMapping("/pickups")
    public PickupRequest createPickup(@RequestBody Map<String, Object> body, HttpSession session) {
        String wasteType = (String) body.getOrDefault("wasteType", "Plastik");
        double weight = Double.parseDouble(body.getOrDefault("weight", "1.0").toString());
        String date = (String) body.getOrDefault("date", "Hari Ini");
        String note = (String) body.getOrDefault("note", "");
        
        // Resolve customer email (username) from active session or fallback body
        String email = "";
        Akun loggedInUser = (Akun) session.getAttribute("user");
        if (loggedInUser != null) {
            email = loggedInUser.getEmail();
        } else {
            email = (String) body.getOrDefault("username", "sarah");
        }
        
        User customer = service.getUserByUsername(email);
        String name = (customer != null) ? customer.getName() : "Pengguna";
        String addr = (customer != null) ? customer.getAddress() : "Alamat";

        PickupRequest req = new PickupRequest(
            null, email, name, addr, wasteType, weight, date, note, "Belum", "PENDING", "12:00"
        );
        return service.addPickup(req);
    }

    @PostMapping("/pickups/{id}/assign")
    public Map<String, String> assignCourier(@PathVariable String id, @RequestBody Map<String, String> body) {
        String courier = body.get("courier");
        service.assignCourier(id, courier);
        return Map.of("message", "Courier assigned successfully", "status", "success");
    }

    @PostMapping("/pickups/{id}/status")
    public Map<String, String> updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        service.updateStatus(id, status);
        return Map.of("message", "Status updated successfully", "status", "success");
    }

    @PostMapping("/pickups/{id}/convert")
    public Map<String, String> convertCoins(@PathVariable String id, @RequestBody Map<String, Object> body) {
        double actualWeight = Double.parseDouble(body.get("actualWeight").toString());
        int ratePerKg = Integer.parseInt(body.get("ratePerKg").toString());
        
        service.convertCoins(id, actualWeight, ratePerKg);
        return Map.of("message", "Coins successfully credited to wallet", "status", "success");
    }

    @GetMapping("/transactions")
    public List<WalletTransaction> getTransactions() {
        return service.getTransactions();
    }
}
