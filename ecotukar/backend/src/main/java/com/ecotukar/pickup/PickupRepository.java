package com.ecotukar.pickup;

import com.ecotukar.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PickupRepository — interface untuk akses data ke tabel "pickups".
 */
@Repository
public interface PickupRepository extends JpaRepository<PickupEntity, String> {

    // Semua pickup milik customer tertentu (untuk halaman riwayat customer)
    List<PickupEntity> findByCustomerOrderByCreatedAtDesc(UserEntity customer);

    // Semua pickup yang diassign ke kurir tertentu (untuk halaman kurir)
    List<PickupEntity> findByCourierOrderByPickupDateAsc(UserEntity courier);

    // Semua pickup by status (untuk admin filter)
    List<PickupEntity> findByStatusOrderByCreatedAtDesc(PickupStatus status);
}
