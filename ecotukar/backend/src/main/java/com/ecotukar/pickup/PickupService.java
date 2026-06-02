package com.ecotukar.pickup;

import com.ecotukar.pickup.dto.AssignCourierRequest;
import com.ecotukar.pickup.dto.PickupRequest;
import com.ecotukar.pickup.dto.UpdateStatusRequest;
import com.ecotukar.user.UserEntity;
import com.ecotukar.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PickupService — business logic untuk manajemen pickup sampah.
 *
 * Tanggung jawab:
 * - Customer buat pickup request baru
 * - Admin lihat semua pickup + assign kurir
 * - Kurir lihat pickup yang ditugaskan + update status
 * - Get detail pickup by ID
 */
@Service
public class PickupService {

    @Autowired
    private PickupRepository pickupRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Customer buat pickup request baru.
     * Status awal: PENDING
     */
    public PickupEntity createPickup(PickupRequest req) {
        UserEntity customer = userRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan!"));

        PickupEntity pickup = new PickupEntity();
        pickup.setCustomer(customer);
        pickup.setWasteType(req.getWasteType());
        pickup.setEstimatedWeight(req.getEstimatedWeight());
        pickup.setPickupDate(req.getPickupDate());
        pickup.setNote(req.getNote());
        pickup.setStatus(PickupStatus.PENDING);

        return pickupRepository.save(pickup);
    }

    /**
     * Ambil semua pickup (untuk admin)
     */
    public List<PickupEntity> getAllPickups() {
        return pickupRepository.findAll();
    }

    /**
     * Ambil pickup berdasarkan status (untuk admin filter)
     */
    public List<PickupEntity> getPickupsByStatus(PickupStatus status) {
        return pickupRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    /**
     * Ambil riwayat pickup milik customer tertentu
     */
    public List<PickupEntity> getPickupsByCustomer(String customerId) {
        UserEntity customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan!"));
        return pickupRepository.findByCustomerOrderByCreatedAtDesc(customer);
    }

    /**
     * Ambil pickup yang diassign ke kurir tertentu
     */
    public List<PickupEntity> getPickupsByCourier(String courierId) {
        UserEntity courier = userRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Kurir tidak ditemukan!"));
        return pickupRepository.findByCourierOrderByPickupDateAsc(courier);
    }

    /**
     * Admin assign kurir ke pickup.
     * Status berubah dari PENDING → ASSIGNED
     */
    public PickupEntity assignCourier(String pickupId, AssignCourierRequest req) {
        PickupEntity pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new RuntimeException("Pickup tidak ditemukan!"));

        UserEntity courier = userRepository.findById(req.getCourierId())
                .orElseThrow(() -> new RuntimeException("Kurir tidak ditemukan!"));

        pickup.setCourier(courier);
        pickup.setStatus(PickupStatus.ASSIGNED);

        return pickupRepository.save(pickup);
    }

    /**
     * Kurir update status pickup (ON_ROUTE, COMPLETED, CANCELLED)
     */
    public PickupEntity updateStatus(String pickupId, UpdateStatusRequest req) {
        PickupEntity pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new RuntimeException("Pickup tidak ditemukan!"));

        pickup.setStatus(req.getStatus());
        return pickupRepository.save(pickup);
    }

    /**
     * Get detail pickup by ID
     */
    public PickupEntity getPickupById(String id) {
        return pickupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pickup dengan ID " + id + " tidak ditemukan!"));
    }
}
