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
        
        if (weight < 5.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estimasi berat minimal 5 kg!");
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
        
        if (actualWeight < 5.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Berat riil sampah minimal 5 kg!");
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

    // Get list of all customers from database
    @GetMapping("/customers")
    public List<Map<String, String>> getCustomers() {
        return userRepository.findAll().stream()
            .filter(u -> u instanceof com.ecotukar.model.CustomerUser)
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

    // Get ALL transactions for admin report page
    @GetMapping("/admin/transactions")
    public List<WalletTransaction> getAllTransactionsForAdmin() {
        return service.getTransactions();
    }

    // ─── Admin: User Management ───────────────────────────────────────────────

    // Get ALL users for admin user management page
    @GetMapping("/admin/users")
    public List<Map<String, Object>> getAllUsers() {
        return userRepository.findAll().stream().map(u -> {
            Map<String, Object> m = new java.util.LinkedHashMap<>();
            m.put("username",       u.getUsername());
            m.put("name",           u.getName());
            m.put("email",          u.getEmail());
            m.put("address",        u.getAddress());
            m.put("role",           u.getRole());
            m.put("avatar",         u.getAvatar());
            m.put("joined",         u.getJoined());
            m.put("points",         u.getPoints());
            m.put("ewalletBalance", u.getEwalletBalance());
            return m;
        }).collect(java.util.stream.Collectors.toList());
    }

    // Update a user's data (admin)
    @PutMapping("/admin/users/{username}")
    public Map<String, String> updateUser(@PathVariable String username, @RequestBody Map<String, Object> body) {
        com.ecotukar.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan");
        }

        if (body.containsKey("name"))           user.setName((String) body.get("name"));
        if (body.containsKey("email"))          user.setEmail((String) body.get("email"));
        if (body.containsKey("address"))        user.setAddress((String) body.get("address"));
        if (body.containsKey("points"))         user.setPoints(Integer.parseInt(body.get("points").toString()));
        if (body.containsKey("ewalletBalance")) user.setEwalletBalance(Integer.parseInt(body.get("ewalletBalance").toString()));
        if (body.containsKey("password")) {
            String newPw = (String) body.get("password");
            if (newPw != null && !newPw.isBlank()) {
                user.setPassword(passwordEncoder.encode(newPw));
            }
        }

        userRepository.save(user);
        return Map.of("message", "User berhasil diperbarui", "status", "success");
    }

    // Delete a user (admin)
    @DeleteMapping("/admin/users/{username}")
    public Map<String, String> deleteUser(@PathVariable String username) {
        com.ecotukar.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan");
        }
        if ("ADMIN".equals(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Akun admin tidak boleh dihapus");
        }
        userRepository.delete(user);
        return Map.of("message", "User berhasil dihapus", "status", "success");
    }

    // Add new customer (admin)
    @PostMapping("/admin/customers")
    public Map<String, String> registerCustomer(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String name     = body.get("name");
        String password = body.get("password");
        String email    = body.get("email");
        String address  = body.getOrDefault("address", "");

        if (username == null || name == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data tidak lengkap");
        }
        if (userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username sudah dipakai");
        }

        com.ecotukar.model.CustomerUser customer = new com.ecotukar.model.CustomerUser();
        customer.setUsername(username);
        customer.setName(name);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setEmail(email != null ? email : username + "@ecotukar.com");
        customer.setAvatar("👤");
        customer.setAddress(address);
        customer.setJoined(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy")));
        customer.setPoints(0);
        customer.setEwalletBalance(0);

        userRepository.save(customer);
        return Map.of("message", "Customer berhasil didaftarkan", "status", "success");
    }
}
