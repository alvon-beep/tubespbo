package com.ecotukar.user;

import com.ecotukar.user.dto.LoginRequest;
import com.ecotukar.user.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService — business logic untuk manajemen user.
 *
 * Tanggung jawab:
 * - Register user baru (sesuai role: Customer, Courier, Admin)
 * - Login (validasi email + password)
 * - Get profil user by ID
 * - Update profil user
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Register user baru.
     * Bikin object yang tepat sesuai role (CustomerUser, CourierUser, AdminUser).
     * Ini adalah penerapan Factory-like pattern + polymorphism.
     */
    public UserEntity register(RegisterRequest req) {
        // Cek apakah email sudah terdaftar
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email " + req.getEmail() + " sudah terdaftar!");
        }

        UserEntity user;

        // Buat subclass yang tepat berdasarkan role (polymorphism)
        switch (req.getRole()) {
            case CUSTOMER -> {
                CustomerUser customer = new CustomerUser();
                customer.setAddress(req.getAddress());
                user = customer;
            }
            case COURIER -> {
                CourierUser courier = new CourierUser();
                courier.setVehicleInfo(req.getVehicleInfo());
                user = courier;
            }
            case ADMIN -> {
                AdminUser admin = new AdminUser();
                admin.setBankSampahName(req.getBankSampahName());
                user = admin;
            }
            default -> throw new RuntimeException("Role tidak valid!");
        }

        // Set field dari base class
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        // NOTE: Di production, password harus di-hash (BCrypt)!
        // Untuk sekarang disimpan plain text dulu
        user.setPassword(req.getPassword());

        return userRepository.save(user);
    }

    /**
     * Login — cari user by email, validasi password.
     * Simpel tanpa JWT untuk saat ini.
     */
    public UserEntity login(LoginRequest req) {
        UserEntity user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Email tidak ditemukan!"));

        if (!user.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Password salah!");
        }

        return user;
    }

    /**
     * Ambil semua user (untuk admin panel)
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Ambil profil user by ID
     */
    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User dengan ID " + id + " tidak ditemukan!"));
    }

    /**
     * Update profil user (username, address, dll)
     */
    public UserEntity updateUser(String id, UserEntity updatedData) {
        UserEntity existing = getUserById(id);
        existing.setUsername(updatedData.getUsername());
        // Tambah field lain yang boleh di-update sesuai kebutuhan
        return userRepository.save(existing);
    }
}
