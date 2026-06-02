package com.ecotukar.pickup;

import com.ecotukar.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * PickupEntity — merepresentasikan 1 request pickup sampah.
 *
 * Relasi:
 * - customer (ManyToOne → users) : siapa yang request pickup
 * - courier  (ManyToOne → users) : kurir yang ditugaskan (bisa null kalau masih PENDING)
 *
 * Field:
 * - wasteType        : jenis sampah (enum WasteType)
 * - estimatedWeight  : estimasi berat dari customer (kg)
 * - actualWeight     : berat aktual setelah ditimbang kurir (diisi saat COMPLETED)
 * - pickupDate       : tanggal jemputan yang diminta customer
 * - note             : catatan tambahan (contoh: "taruh di depan pagar")
 * - status           : status alur pickup (enum PickupStatus)
 * - coinsEarned      : koin yang didapat customer dari pickup ini
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pickups")
public class PickupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Relasi ke customer yang buat request ini
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity customer;

    // Relasi ke kurir yang ditugaskan (nullable — masih PENDING kalau null)
    @ManyToOne
    @JoinColumn(name = "courier_id")
    private UserEntity courier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WasteType wasteType;

    @Column(nullable = false)
    private double estimatedWeight;

    // Diisi saat admin konversi koin (COMPLETED)
    @Column
    private Double actualWeight;

    @Column(nullable = false)
    private LocalDate pickupDate;

    @Column
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PickupStatus status = PickupStatus.PENDING;

    @Column(nullable = false)
    private int coinsEarned = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
