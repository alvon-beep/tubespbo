package com.ecotukar.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository — interface untuk akses data ke tabel "users".
 * Spring Data JPA otomatis generate implementasinya, kita tinggal definisikan method-nya.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    // Cari user berdasarkan email (untuk login)
    Optional<UserEntity> findByEmail(String email);

    // Cek apakah email sudah terdaftar (untuk register)
    boolean existsByEmail(String email);
}
