package com.ecotukar.config;

import com.ecotukar.model.*;
import com.ecotukar.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataSeeder - Otomatis mengisi database dengan data awal saat aplikasi pertama
 * kali dijalankan.
 * Data hanya di-insert jika tabel masih kosong (aman dari duplikasi).
 */
@Component
public class DataSeeder implements CommandLineRunner {

        private final UserRepository userRepository;
        private final PickupRequestRepository pickupRequestRepository;
        private final PickupVerificationRepository pickupVerificationRepository;
        private final WalletTransactionRepository walletTransactionRepository;
        private final PasswordEncoder passwordEncoder;

        public DataSeeder(UserRepository userRepository,
                        PickupRequestRepository pickupRequestRepository,
                        PickupVerificationRepository pickupVerificationRepository,
                        WalletTransactionRepository walletTransactionRepository,
                        PasswordEncoder passwordEncoder) {
                this.userRepository = userRepository;
                this.pickupRequestRepository = pickupRequestRepository;
                this.pickupVerificationRepository = pickupVerificationRepository;
                this.walletTransactionRepository = walletTransactionRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) throws Exception {
                seedUsers();
                seedPickupRequests();
                seedWalletTransactions();
        }

        // =========================================================
        // SEED USERS (Admin, Customer, Courier)
        // =========================================================
        private void seedUsers() {
                if (userRepository.count() > 0) {
                        System.out.println("[Seeder] Users sudah ada, skip seeding.");
                        return;
                }

                System.out.println("[Seeder] Menanam data users...");
                String pw = passwordEncoder.encode("password"); // password semua user: password

                // --- ADMIN ---
                AdminUser admin = new AdminUser(
                                "admin",
                                pw,
                                "Admin",
                                "admin@ecotukar.id",
                                null,
                                null,
                                "June 2026",
                                0,
                                0);
                userRepository.save(admin);

                // --- CUSTOMERS ---
                CustomerUser customer1 = new CustomerUser(
                                "customer1",
                                pw,
                                "Customer Satu",
                                "customer1@ecotukar.id",
                                null,
                                "Jl. Merdeka No. 1, Bandung",
                                "June 2026",
                                250,
                                0);
                userRepository.save(customer1);

                CustomerUser customer2 = new CustomerUser(
                                "kurt",
                                pw,
                                "Kurt Seto",
                                "kurt@ecotukar.id",
                                null,
                                "Jl. Sudirman No. 45, Jakarta",
                                "June 2026",
                                500,
                                10000);
                userRepository.save(customer2);

                CustomerUser customer3 = new CustomerUser(
                                "raynar",
                                pw,
                                "Raynar Khalid Mahardika",
                                "raynarkim@gmail.com",
                                null,
                                "Jingga Residence ganteng",
                                "June 2026",
                                800,
                                20000);
                userRepository.save(customer3);

                CustomerUser customer4 = new CustomerUser(
                                "ganteng",
                                pw,
                                "Raynar Ganteng",
                                "ganteng@gmail.com",
                                null,
                                "Jingga Residence ganteng",
                                "June 2026",
                                150,
                                0);
                userRepository.save(customer4);

                CustomerUser customer5 = new CustomerUser(
                                "rifat",
                                pw,
                                "Rifat Hanaf",
                                "nflet@gmail.com",
                                null,
                                "padang",
                                "June 2026",
                                320,
                                5000);
                userRepository.save(customer5);

                CustomerUser customer6 = new CustomerUser(
                                "kevin",
                                pw,
                                "Kevin@ecotukar.id",
                                "Kevin@ecotukar.id",
                                null,
                                "Hub Bandung",
                                "June 2026",
                                600,
                                15000);
                userRepository.save(customer6);

                CustomerUser customer7 = new CustomerUser(
                                "fazul",
                                pw,
                                "Fazul Alfat",
                                "Fazul@ecotukar.com",
                                null,
                                null,
                                "June 2026",
                                100,
                                0);
                userRepository.save(customer7);

                // --- COURIERS ---
                CourierUser courier1 = new CourierUser(
                                "ner",
                                pw,
                                "Baru Bergabung",
                                "ner@ecotukar.id",
                                null,
                                "Hub Bandung",
                                "June 2026",
                                0,
                                0);
                userRepository.save(courier1);

                CourierUser courier2 = new CourierUser(
                                "begin",
                                pw,
                                "begin",
                                "begin@ecotukar.id",
                                null,
                                "Hub Bandung",
                                "June 2026",
                                0,
                                0);
                userRepository.save(courier2);

                System.out.println("[Seeder] Users berhasil ditanam: " + userRepository.count() + " user.");
        }

        // =========================================================
        // SEED PICKUP REQUESTS
        // =========================================================
        private void seedPickupRequests() {
                if (pickupRequestRepository.count() > 0) {
                        System.out.println("[Seeder] Pickup requests sudah ada, skip seeding.");
                        return;
                }

                System.out.println("[Seeder] Menanam data pickup requests...");

                PickupRequest req1 = new PickupRequest(
                                "REQ-0001", "customer1", "Customer Satu",
                                "Jl. Merdeka No. 1, Bandung", "Plastik",
                                2.5, "2026-06-01", "Tolong diambil pagi", "Belum", "PENDING", "09:00");
                pickupRequestRepository.save(req1);

                PickupRequest req2 = new PickupRequest(
                                "REQ-0002", "kurt", "Kurt Seto",
                                "Jl. Sudirman No. 45, Jakarta", "Kertas",
                                5.0, "2026-06-02", "-", "ner", "COMPLETED", "10:00");
                pickupRequestRepository.save(req2);

                PickupRequest req3 = new PickupRequest(
                                "REQ-0003", "raynar", "Raynar Khalid Mahardika",
                                "Jingga Residence ganteng", "Elektronik",
                                1.2, "2026-06-03", "Hati-hati barangnya", "ner", "ON_ROUTE", "13:00");
                pickupRequestRepository.save(req3);

                PickupRequest req4 = new PickupRequest(
                                "REQ-0004", "rifat", "Rifat Hanaf",
                                "padang", "Kaca",
                                3.0, "2026-06-04", "-", "begin", "ASSIGNED", "14:00");
                pickupRequestRepository.save(req4);

                PickupRequest req5 = new PickupRequest(
                                "REQ-0005", "kevin", "Kevin",
                                "Hub Bandung", "Logam",
                                8.0, "2026-06-05", "Berat banget", "begin", "COMPLETED", "08:00");
                pickupRequestRepository.save(req5);

                // Seed verifikasi untuk request yang sudah COMPLETED
                seedPickupVerifications(req2, req5);

                System.out.println("[Seeder] Pickup requests berhasil ditanam: " + pickupRequestRepository.count()
                                + " request.");
        }

        // =========================================================
        // SEED PICKUP VERIFICATIONS
        // =========================================================
        private void seedPickupVerifications(PickupRequest... completedRequests) {
                if (pickupVerificationRepository.count() > 0) {
                        return;
                }

                System.out.println("[Seeder] Menanam data pickup verifications...");

                int counter = 1;
                for (PickupRequest req : completedRequests) {
                        PickupVerification verification = new PickupVerification(
                                        "VER-" + String.format("%04d", counter++),
                                        req,
                                        true,
                                        "Baik",
                                        "2026-06-0" + counter + " 15:00");
                        pickupVerificationRepository.save(verification);
                }

                System.out.println("[Seeder] Verifications berhasil ditanam.");
        }

        // =========================================================
        // SEED WALLET TRANSACTIONS
        // =========================================================
        private void seedWalletTransactions() {
                if (walletTransactionRepository.count() > 0) {
                        System.out.println("[Seeder] Wallet transactions sudah ada, skip seeding.");
                        return;
                }

                System.out.println("[Seeder] Menanam data wallet transactions...");

                // Ambil user yang sudah disimpan
                userRepository.findById("kurt").ifPresent(user -> {
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Pickup Kertas - REQ-0002", "2026-06-02", "+500"));
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Konversi Poin ke Saldo", "2026-06-03", "+10000"));
                });

                userRepository.findById("raynar").ifPresent(user -> {
                        walletTransactionRepository.save(new WalletTransaction(user, "Pickup Elektronik - REQ-0003",
                                        "2026-06-03", "+800"));
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Konversi Poin ke Saldo", "2026-06-04", "+20000"));
                });

                userRepository.findById("rifat").ifPresent(user -> {
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Pickup Kaca - REQ-0004", "2026-06-04", "+320"));
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Konversi Poin ke Saldo", "2026-06-05", "+5000"));
                });

                userRepository.findById("kevin").ifPresent(user -> {
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Pickup Logam - REQ-0005", "2026-06-05", "+600"));
                        walletTransactionRepository.save(
                                        new WalletTransaction(user, "Konversi Poin ke Saldo", "2026-06-06", "+15000"));
                });

                System.out.println("[Seeder] Wallet transactions berhasil ditanam: "
                                + walletTransactionRepository.count() + " transaksi.");
        }
}
