package com.ecotukar.coin;

import com.ecotukar.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CoinRepository — interface untuk akses data ke tabel "coin_transactions".
 */
@Repository
public interface CoinRepository extends JpaRepository<CoinTransactionEntity, String> {

    // Semua transaksi koin milik user tertentu, diurutkan terbaru dulu
    List<CoinTransactionEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

    // Hitung total koin user (sum dari semua amount, EARN positif + REDEEM negatif)
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM CoinTransactionEntity t WHERE t.user = :user")
    int getTotalCoinsByUser(@Param("user") UserEntity user);
}
