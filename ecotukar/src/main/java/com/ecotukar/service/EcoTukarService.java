package com.ecotukar.service;

import com.ecotukar.model.User;
import com.ecotukar.model.PickupRequest;
import com.ecotukar.model.WalletTransaction;
import com.ecotukar.repository.UserRepository;
import com.ecotukar.repository.PickupRequestRepository;
import com.ecotukar.repository.WalletTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EcoTukarService {

    private final UserRepository userRepository;
    private final PickupRequestRepository pickupRequestRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public EcoTukarService(UserRepository userRepository, 
                           PickupRequestRepository pickupRequestRepository,
                           WalletTransactionRepository walletTransactionRepository) {
        this.userRepository = userRepository;
        this.pickupRequestRepository = pickupRequestRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<PickupRequest> getPickups() {
        return pickupRequestRepository.findAll();
    }

    public PickupRequest addPickup(PickupRequest req) {
        long count = pickupRequestRepository.count();
        String newId = "REQ-" + (1000 + count);
        req.setId(newId);
        req.setCourier("Belum");
        req.setStatus("PENDING");
        req.setTime("12:00"); // Standard default target time
        pickupRequestRepository.save(req);
        return req;
    }

    public PickupRequest addManualPickup(PickupRequest req) {
        long count = pickupRequestRepository.count();
        String newId = "REQ-" + (1000 + count);
        req.setId(newId);
        // Courier, Status, and Time are already set by the admin form
        pickupRequestRepository.save(req);
        return req;
    }

    public void assignCourier(String id, String courierName) {
        Optional<PickupRequest> optionalReq = pickupRequestRepository.findById(id);
        if (optionalReq.isPresent()) {
            PickupRequest req = optionalReq.get();
            req.setCourier(courierName);
            if (req.getStatus().equals("PENDING")) {
                req.setStatus("ASSIGNED");
            }
            pickupRequestRepository.save(req);
        }
    }

    public void assignSchedule(String id, String date, String time, String courierName) {
        Optional<PickupRequest> optionalReq = pickupRequestRepository.findById(id);
        if (optionalReq.isPresent()) {
            PickupRequest req = optionalReq.get();
            req.setDate(date);
            req.setTime(time);
            req.setCourier(courierName);
            req.setStatus("ASSIGNED");
            pickupRequestRepository.save(req);
        }
    }

    public void updateStatus(String id, String status) {
        Optional<PickupRequest> optionalReq = pickupRequestRepository.findById(id);
        if (optionalReq.isPresent()) {
            PickupRequest req = optionalReq.get();
            req.setStatus(status.toUpperCase());
            pickupRequestRepository.save(req);
        }
    }

    public void convertCoins(String id, double actualWeight, int ratePerKg) {
        Optional<PickupRequest> optionalReq = pickupRequestRepository.findById(id);
        if (optionalReq.isPresent()) {
            PickupRequest req = optionalReq.get();
            req.setActualWeight(actualWeight);
            req.setStatus("COMPLETED");
            pickupRequestRepository.save(req);

            // Calculate points
            int pointsEarned = (int) (actualWeight * ratePerKg);

            // Find customer user and reward coins
            User customer = getUserByUsername(req.getUsername());
            if (customer != null) {
                customer.addPoints(pointsEarned);
                userRepository.save(customer);
                
                // Add to wallet history
                String transTitle = "Pickup " + req.getWasteType() + " " + actualWeight + "kg";
                WalletTransaction trans = new WalletTransaction(req.getUsername(), transTitle, "Hari Ini", "+" + pointsEarned);
                walletTransactionRepository.save(trans);
            }
        }
    }

    public List<WalletTransaction> getAllTransactions() {
        return walletTransactionRepository.findAll();
    }

    public List<WalletTransaction> getTransactions(String username) {
        return walletTransactionRepository.findByUsernameOrderByIdDesc(username);
    }
}
