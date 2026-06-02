package com.ecotukar.service;

import com.ecotukar.model.User;
import com.ecotukar.model.PickupRequest;
import com.ecotukar.model.WalletTransaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EcoTukarService {
    private final List<User> users = new ArrayList<>();
    private final List<PickupRequest> pickups = new ArrayList<>();
    private final List<WalletTransaction> transactions = new ArrayList<>();
    private final AtomicInteger requestCounter = new AtomicInteger(1046); // Start from REQ-1046

    public EcoTukarService() {
        // Initialize Default Mock Users
        users.add(new User("sarah", "Sarah Putri", "sarah.putri@mail.com", "CUSTOMER", "👩‍🦰", "Jl. Melati No. 21, Bandung", "Maret 2025", 1240));
        users.add(new User("budi", "Budi Santoso", "budi.s@mail.com", "COURIER", "👷", "Hub Bandung", "Januari 2025", 0));
        users.add(new User("admin", "EcoTukar Admin", "admin@ecotukar.id", "ADMIN", "💼", "Kantor Pusat Bandung", "Desember 2024", 0));

        // Initialize Default Mock Pickup Tickets
        pickups.add(new PickupRequest("REQ-1041", "sarah", "Sarah Putri", "Jl. Melati No. 21", "Plastik", 3.0, "2026-05-20", "Letakkan di pagar", "Budi S.", "ASSIGNED", "09:00"));
        pickups.add(new PickupRequest("REQ-1042", "andi", "Andi Wijaya", "Jl. Mawar No. 5", "Kertas", 5.0, "2026-05-20", "Di garasi", "Belum", "PENDING", "09:30"));
        pickups.add(new PickupRequest("REQ-1043", "rina", "Rina Lestari", "Jl. Anggrek No. 12", "Kaca", 8.0, "2026-05-21", "Ketuk pintu", "Eko P.", "ON_ROUTE", "10:00"));
        
        PickupRequest req4 = new PickupRequest("REQ-1044", "doni", "Doni Pratama", "Jl. Kenanga No. 7", "Logam", 2.0, "2026-05-21", "", "Budi S.", "COMPLETED", "10:45");
        req4.setActualWeight(2.0);
        pickups.add(req4);
        
        pickups.add(new PickupRequest("REQ-1045", "maya", "Maya Anggun", "Jl. Cempaka No. 9", "Plastik", 4.0, "2026-05-22", "", "Belum", "PENDING", "11:30"));

        // Initialize Default Wallet Point History for Sarah
        transactions.add(new WalletTransaction("Pickup Plastik 2.4kg", "12 Mei 2026", "+120"));
        transactions.add(new WalletTransaction("Tukar Voucher Belanja", "08 Mei 2026", "-300"));
        transactions.add(new WalletTransaction("Pickup Kertas 5.1kg", "03 Mei 2026", "+210"));
        transactions.add(new WalletTransaction("Bonus Referral", "01 Mei 2026", "+50"));
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    public List<PickupRequest> getPickups() {
        return pickups;
    }

    public PickupRequest addPickup(PickupRequest req) {
        String newId = "REQ-" + requestCounter.getAndIncrement();
        req.setId(newId);
        req.setCourier("Belum");
        req.setStatus("PENDING");
        req.setTime("12:00"); // Standard default target time
        pickups.add(req);
        return req;
    }

    public void assignCourier(String id, String courierName) {
        for (PickupRequest req : pickups) {
            if (req.getId().equalsIgnoreCase(id)) {
                req.setCourier(courierName);
                if (req.getStatus().equalsIgnoreCase("PENDING")) {
                    req.setStatus("ASSIGNED");
                }
                break;
            }
        }
    }

    public void updateStatus(String id, String status) {
        for (PickupRequest req : pickups) {
            if (req.getId().equalsIgnoreCase(id)) {
                req.setStatus(status.toUpperCase());
                break;
            }
        }
    }

    public void convertCoins(String id, double actualWeight, int ratePerKg) {
        for (PickupRequest req : pickups) {
            if (req.getId().equalsIgnoreCase(id)) {
                req.setActualWeight(actualWeight);
                req.setStatus("COMPLETED");

                // Calculate points
                int pointsEarned = (int) (actualWeight * ratePerKg);

                // Find customer user and reward coins
                User customer = getUserByUsername(req.getUsername());
                if (customer != null) {
                    customer.addPoints(pointsEarned);
                    // Add to wallet history
                    String transTitle = "Pickup " + req.getWasteType() + " " + actualWeight + "kg";
                    transactions.add(0, new WalletTransaction(transTitle, "Hari Ini", "+" + pointsEarned));
                }
                break;
            }
        }
    }

    public List<WalletTransaction> getTransactions() {
        return transactions;
    }
}
