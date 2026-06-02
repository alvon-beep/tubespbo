package com.ecotukar.user;

/**
 * Role user dalam sistem EcoTukar.
 * Dipakai sebagai discriminator column di tabel users (SINGLE_TABLE inheritance).
 */
public enum UserRole {
    CUSTOMER,
    COURIER,
    ADMIN
}
