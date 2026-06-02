package com.ecotukar.coin;

import com.ecotukar.coin.dto.ConvertCoinRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * CoinController — REST API endpoint untuk e-wallet koin.
 *
 * Endpoints:
 * POST /api/coins/convert          → admin konversi sampah → koin
 * GET  /api/coins/{userId}/balance → get saldo koin user
 * GET  /api/coins/{userId}/history → get riwayat transaksi koin user
 */
@RestController
@RequestMapping("/api/coins")
@CrossOrigin("*")
public class CoinController {

    @Autowired
    private CoinService coinService;

    // POST /api/coins/convert — admin konversi pickup → koin
    @PostMapping("/convert")
    public ResponseEntity<?> convertPickupToCoins(@Valid @RequestBody ConvertCoinRequest req) {
        try {
            CoinTransactionEntity transaction = coinService.convertPickupToCoins(req);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/coins/{userId}/balance — saldo koin user
    @GetMapping("/{userId}/balance")
    public ResponseEntity<?> getCoinBalance(@PathVariable String userId) {
        try {
            int balance = coinService.getCoinBalance(userId);
            return ResponseEntity.ok(Map.of("userId", userId, "balance", balance));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/coins/{userId}/history — riwayat transaksi koin user
    @GetMapping("/{userId}/history")
    public ResponseEntity<?> getCoinHistory(@PathVariable String userId) {
        try {
            List<CoinTransactionEntity> history = coinService.getCoinHistory(userId);
            return ResponseEntity.ok(history);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
