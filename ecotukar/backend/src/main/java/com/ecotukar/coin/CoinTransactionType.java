package com.ecotukar.coin;

/**
 * Tipe transaksi koin di e-wallet user.
 *
 * EARN   → Koin masuk (dari pickup yang selesai)
 * REDEEM → Koin keluar (ditukar reward)
 */
public enum CoinTransactionType {
    EARN,
    REDEEM
}
