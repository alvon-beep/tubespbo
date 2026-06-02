package com.ecotukar.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * CustomerUser — user tipe customer (rumah tangga).
 *
 * Extends UserEntity (inheritance).
 * Discriminator value "CUSTOMER" → kolom role di tabel users.
 *
 * Tambahan field:
 * - address : alamat rumah untuk penjemputan sampah
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerUser extends UserEntity {

    @Column
    private String address;

    @Override
    public UserRole getRole() {
        return UserRole.CUSTOMER;
    }
}
