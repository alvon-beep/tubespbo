package com.ecotukar.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * CourierUser — user tipe kurir.
 *
 * Extends UserEntity (inheritance).
 * Discriminator value "COURIER" → kolom role di tabel users.
 *
 * Tambahan field:
 * - vehicleInfo  : info kendaraan kurir (contoh: "Motor Honda Beat, B 1234 XYZ")
 * - isAvailable  : status apakah kurir sedang tersedia untuk pickup
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("COURIER")
public class CourierUser extends UserEntity {

    @Column
    private String vehicleInfo;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @Override
    public UserRole getRole() {
        return UserRole.COURIER;
    }
}
