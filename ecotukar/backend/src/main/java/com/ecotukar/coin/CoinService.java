package com.ecotukar.coin;

import com.ecotukar.coin.dto.ConvertCoinRequest;
import com.ecotukar.pickup.PickupEntity;
import com.ecotukar.pickup.PickupRepository;
import com.ecotukar.pickup.PickupStatus;
import com.ecotukar.user.UserEntity;
import com.ecotukar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CoinService — business logic untuk e-wallet koin.
 *
 * Tanggung jawab:
 * - Admin konversi berat sampah → koin (setelah pickup COMPLETED)
 * - Get saldo koin user
 * - Get riwayat transaksi koin user
 */
@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Admin konversi pickup sampah → koin untuk customer.
     *
     * Alur:
     * 1. Validasi pickup ada dan statusnya ASSIGNED/ON_ROUTE
     * 2. Hitung total koin = actualWeight * ratePerKg
     * 3. Simpan CoinTransaction (EARN) untuk customer
     * 4. Update saldo coinBalance di UserEntity customer
     * 5. Update pickup: actualWeight, coinsEarned, status → COMPLETED
     *
     * @Transactional → semua operasi di atas atomic (kalau gagal 1, semua rollback)
     */
    @Transactional
    public CoinTransactionEntity convertPickupToCoins(ConvertCoinRequest req) {
        PickupEntity pickup = pickupRepository.findById(req.getPickupId())
                .orElseThrow(() -> new RuntimeException("Pickup tidak ditemukan!"));

        if (pickup.getStatus() == PickupStatus.COMPLETED) {
            throw new RuntimeException("Pickup ini sudah dikonversi sebelumnya!");
        }
        if (pickup.getStatus() == PickupStatus.CANCELLED) {
            throw new RuntimeException("Pickup ini sudah dibatalkan, tidak bisa dikonversi!");
        }

        // Hitung total koin
        int totalCoins = (int) (req.getActualWeight() * req.getRatePerKg());

        UserEntity customer = pickup.getCustomer();

        // Buat transaksi koin baru
        CoinTransactionEntity transaction = new CoinTransactionEntity();
        transaction.setUser(customer);
        transaction.setAmount(totalCoins);
        transaction.setType(CoinTransactionType.EARN);
        transaction.setDescription("Pickup " + req.getWasteType().name() + " " + req.getActualWeight() + "kg");

        coinRepository.save(transaction);

        // Update saldo koin customer
        customer.setCoinBalance(customer.getCoinBalance() + totalCoins);
        userRepository.save(customer);

        // Update data pickup
        pickup.setActualWeight(req.getActualWeight());
        pickup.setCoinsEarned(totalCoins);
        pickup.setStatus(PickupStatus.COMPLETED);
        pickupRepository.save(pickup);

        return transaction;
    }

    /**
     * Get saldo koin user saat ini
     */
    public int getCoinBalance(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan!"));
        return user.getCoinBalance();
    }

    /**
     * Get riwayat transaksi koin user (terbaru dulu)
     */
    public List<CoinTransactionEntity> getCoinHistory(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan!"));
        return coinRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
