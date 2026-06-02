package com.ecotukar.coin;

import com.ecotukar.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * CoinTransactionEntity — riwayat transaksi koin di e-wallet user.
 *
 * Setiap kali koin masuk (EARN dari pickup) atau keluar (REDEEM reward),
 * dibuat 1 record baru di tabel ini.
 *
 * Relasi:
 * - user (ManyToOne → users) : pemilik transaksi ini
 *
 * Field:
 * - amount      : jumlah koin (positif untuk EARN, negatif untuk REDEEM)
 * - type        : EARN atau REDEEM
 * - description : keterangan transaksi (contoh: "Pickup Plastik 2.4kg")
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coin_transactions")
public class CoinTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoinTransactionType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
