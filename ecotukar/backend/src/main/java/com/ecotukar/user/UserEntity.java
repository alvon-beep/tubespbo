package com.ecotukar.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ecotukar.pickup.PickupEntity;
import com.ecotukar.coin.CoinTransactionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * UserEntity — abstract base class untuk semua tipe user EcoTukar.
 *
 * Konsep OOP yang dipakai:
 * - Abstraction   : class ini abstract, ga bisa di-instantiate langsung
 * - Inheritance   : CustomerUser, CourierUser, AdminUser extends class ini
 * - Polymorphism  : method getRole() di-override tiap subclass
 *
 * Pakai SINGLE_TABLE inheritance: semua user disimpan di 1 tabel "users",
 * dibedakan by kolom "role" sebagai discriminator.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Saldo koin e-wallet user
    @Column(nullable = false)
    private int coinBalance = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relasi ke pickup (customer punya banyak pickup)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PickupEntity> pickups = new ArrayList<>();

    // Relasi ke riwayat transaksi koin
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CoinTransactionEntity> coinTransactions = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Method abstract — harus di-implement tiap subclass (polymorphism)
    public abstract UserRole getRole();
}
