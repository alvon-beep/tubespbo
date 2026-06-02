package com.ecotukar.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * AdminUser — user tipe admin / pengelola bank sampah.
 *
 * Extends UserEntity (inheritance).
 * Discriminator value "ADMIN" → kolom role di tabel users.
 *
 * Tambahan field:
 * - bankSampahName : nama bank sampah yang dikelola admin ini
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("ADMIN")
public class AdminUser extends UserEntity {

    @Column
    private String bankSampahName;

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }
}
