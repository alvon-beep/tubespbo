package com.ecotukar.service;

import com.ecotukar.model.User;
import com.ecotukar.model.PickupRequest;
import com.ecotukar.model.PickupVerification;
import com.ecotukar.model.WalletTransaction;
import com.ecotukar.repository.UserRepository;
import com.ecotukar.repository.PickupRequestRepository;
import com.ecotukar.repository.PickupVerificationRepository;
import com.ecotukar.repository.WalletTransactionRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EcoTukarService {

    private final UserRepository userRepository;
    private final PickupRequestRepository pickupRequestRepository;
    private final PickupVerificationRepository pickupVerificationRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    private final AtomicInteger requestCounter = new AtomicInteger(1046);
    private final AtomicInteger verificationCounter = new AtomicInteger(1);

    public EcoTukarService(UserRepository userRepository, 
                           PickupRequestRepository pickupRequestRepository,
                           PickupVerificationRepository pickupVerificationRepository,
                           WalletTransactionRepository walletTransactionRepository) {
        this.userRepository = userRepository;
        this.pickupRequestRepository = pickupRequestRepository;
        this.pickupVerificationRepository = pickupVerificationRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    @PostConstruct
    public void init() {
        long reqCount = pickupRequestRepository.count();
        if (reqCount > 0) requestCounter.set(1046 + (int)reqCount);

        long verCount = pickupVerificationRepository.count();
        if (verCount > 0) verificationCounter.set(1 + (int)verCount);
        
        // Seed initial data if DB is empty
        if (reqCount == 0) {
            pickupRequestRepository.save(new PickupRequest("REQ-1041", "sarah", "Sarah Putri", "Jl. Melati No. 21", "Plastik", 3.0, "2026-05-20", "Letakkan di pagar", "Budi S.", "ASSIGNED", "09:00"));
            pickupRequestRepository.save(new PickupRequest("REQ-1042", "andi", "Andi Wijaya", "Jl. Mawar No. 5", "Kertas", 5.0, "2026-05-20", "Di garasi", "Belum", "PENDING", "09:30"));
            pickupRequestRepository.save(new PickupRequest("REQ-1043", "rina", "Rina Lestari", "Jl. Anggrek No. 12", "Kaca", 8.0, "2026-05-21", "Ketuk pintu", "Eko P.", "ON_ROUTE", "10:00"));
            
            PickupRequest req4 = new PickupRequest("REQ-1044", "doni", "Doni Pratama", "Jl. Kenanga No. 7", "Logam", 2.0, "2026-05-21", "", "Budi S.", "COMPLETED", "10:45");
            req4.setActualWeight(2.0);
            pickupRequestRepository.save(req4);
            
            pickupRequestRepository.save(new PickupRequest("REQ-1045", "maya", "Maya Anggun", "Jl. Cempaka No. 9", "Plastik", 4.0, "2026-05-22", "", "Belum", "PENDING", "11:30"));
            
            // Re-adjust counter
            requestCounter.set(1046);
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<PickupRequest> getPickups() {
        return pickupRequestRepository.findAll();
    }

    public PickupRequest addPickup(PickupRequest req) {
        String newId = "REQ-" + requestCounter.getAndIncrement();
        req.setId(newId);
        req.setCourier("Belum");
        req.setStatus("PENDING");
        req.setTime("12:00");
        return pickupRequestRepository.save(req);
    }

    public void assignCourier(String id, String courierName) {
        pickupRequestRepository.findById(id).ifPresent(req -> {
            req.setCourier(courierName);
            if (req.getStatus().equalsIgnoreCase("PENDING")) {
                req.setStatus("ASSIGNED");
            }
            pickupRequestRepository.save(req);
        });
    }

    public void updateStatus(String id, String status) {
        pickupRequestRepository.findById(id).ifPresent(req -> {
            req.setStatus(status.toUpperCase());
            pickupRequestRepository.save(req);
        });
    }

    public PickupVerification verifyPickup(String id, boolean dataValid, String physicalCondition) {
        PickupRequest req = pickupRequestRepository.findById(id).orElse(null);
        if (req != null) {
            PickupVerification verification = new PickupVerification(
                "VER-" + verificationCounter.getAndIncrement(),
                req,
                dataValid,
                physicalCondition,
                "Hari Ini"
            );
            pickupVerificationRepository.save(verification);
            
            req.setStatus("PICKED_UP");
            pickupRequestRepository.save(req);
            
            return verification;
        }
        return null;
    }

    public void convertCoins(String id, double actualWeight, int ratePerKg) {
        if (actualWeight < 3.0) {
            throw new IllegalArgumentException("Berat riil sampah minimal 3 kg!");
        }
        
        pickupRequestRepository.findById(id).ifPresent(req -> {
            req.setActualWeight(actualWeight);
            req.setStatus("COMPLETED");
            pickupRequestRepository.save(req);

            int pointsEarned = (int) (actualWeight * ratePerKg);

            User customer = getUserByUsername(req.getUsername());
            if (customer != null) {
                customer.addPoints(pointsEarned);
                userRepository.save(customer);

                String transTitle = "Pickup " + req.getWasteType() + " " + actualWeight + "kg";
                WalletTransaction trans = new WalletTransaction(customer, transTitle, "Hari Ini", "+" + pointsEarned);
                walletTransactionRepository.save(trans);
            }
        });
    }

    public boolean convertCoinsToEWallet(String username, int coinsToConvert) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }

        // Delegate business logic to EWallet class (Encapsulation)
        boolean success = user.getEWallet().convertCoinsToBalance(coinsToConvert, 50);
        if (!success) {
            return false;
        }

        userRepository.save(user);

        int rupiahEarned = coinsToConvert * 50;
        String transTitle = "Konversi Koin ke E-Wallet";
        String formattedRupiah = String.format("Rp %,d", rupiahEarned).replace(',', '.');
        WalletTransaction trans = new WalletTransaction(user, transTitle, "Hari Ini", "-" + coinsToConvert + " 🪙 (+" + formattedRupiah + ")");
        walletTransactionRepository.save(trans);
        
        return true;
    }

    public List<WalletTransaction> getTransactionsByUser(User user) {
        return walletTransactionRepository.findByUserOrderByIdDesc(user);
    }
    
    // Fallback for getting all (admin usage)
    public List<WalletTransaction> getTransactions() {
        return walletTransactionRepository.findAll();
    }
}
