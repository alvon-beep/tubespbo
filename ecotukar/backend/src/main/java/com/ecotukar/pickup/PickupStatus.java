package com.ecotukar.pickup;

/**
 * Status alur pickup sampah di EcoTukar.
 *
 * PENDING     → Customer baru buat request, belum ada kurir
 * ASSIGNED    → Admin udah assign kurir ke pickup ini
 * ON_ROUTE    → Kurir dalam perjalanan ke lokasi customer
 * COMPLETED   → Sampah udah diambil dan ditimbang
 * CANCELLED   → Request dibatalkan (oleh customer atau kurir)
 */
public enum PickupStatus {
    PENDING,
    ASSIGNED,
    ON_ROUTE,
    COMPLETED,
    CANCELLED
}
