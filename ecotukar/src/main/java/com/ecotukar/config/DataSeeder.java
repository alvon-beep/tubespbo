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

                // --- CUSTOMERS (dari data pickup EcoTukarService.init()) ---
                CustomerUser sarah = new CustomerUser(
                                "sarah",
                                pw,
                                "Sarah Putri",
                                "sarah@ecotukar.id",
                                null,
                                "Jl. Melati No. 21",
                                "May 2026",
                                0,
                                0);
                userRepository.save(sarah);

                CustomerUser andi = new CustomerUser(
                                "andi",
                                pw,
                                "Andi Wijaya",
                                "andi@ecotukar.id",
                                null,
                                "Jl. Mawar No. 5",
                                "May 2026",
                                0,
                                0);
                userRepository.save(andi);

                CustomerUser rina = new CustomerUser(
                                "rina",
                                pw,
                                "Rina Lestari",
                                "rina@ecotukar.id",
                                null,
                                "Jl. Anggrek No. 12",
                                "May 2026",
                                0,
                                0);
                userRepository.save(rina);

                CustomerUser doni = new CustomerUser(
                                "doni",
                                pw,
                                "Doni Pratama",
                                "doni@ecotukar.id",
                                null,
                                "Jl. Kenanga No. 7",
                                "May 2026",
                                0,
                                0);
                userRepository.save(doni);

                CustomerUser maya = new CustomerUser(
                                "maya",
                                pw,
                                "Maya Anggun",
                                "maya@ecotukar.id",
                                null,
                                "Jl. Cempaka No. 9",
                                "May 2026",
                                0,
                                0);
                userRepository.save(maya);

                // --- COURIERS ---
                CourierUser courier1 = new CourierUser(
                                "budi",
                                pw,
                                "Budi Santoso",
                                "budi@ecotukar.id",
                                null,
                                "Hub Bandung",
                                "May 2026",
                                0,
                                0);
                userRepository.save(courier1);

                CourierUser courier2 = new CourierUser(
                                "eko",
                                pw,
                                "Eko Pratama",
                                "eko@ecotukar.id",
                                null,
                                "Hub Jakarta",
                                "May 2026",
                                0,
                                0);
                userRepository.save(courier2);

                CourierUser courier3 = new CourierUser(
                                "ner",
                                pw,
                                "Ner Kurniawan",
                                "ner@ecotukar.id",
                                null,
                                "Hub Bandung",
                                "June 2026",
                                0,
                                0);
                userRepository.save(courier3);

                CourierUser courier4 = new CourierUser(
                                "begin",
                                pw,
                                "Begin Saputra",
                                "begin@ecotukar.id",
                                null,
                                "Hub Bandung",
                                "June 2026",
                                0,
                                0);
                userRepository.save(courier4);

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
                                "REQ-0001", "sarah", "Sarah Putri",
                                "Jl. Melati No. 21", "Plastik",
                                3.0, "2026-05-20", "Letakkan di pagar", "Belum", "PENDING", "09:00",
                                -6.9141, 107.6180);
                pickupRequestRepository.save(req1);

                PickupRequest req2 = new PickupRequest(
                                "REQ-0002", "kurt", "Kurt Seto",
                                "Jl. Sudirman No. 45, Jakarta", "Kertas",
                                5.0, "2026-06-02", "-", "Ner Kurniawan", "COMPLETED", "10:00",
                                -6.9380, 107.6220);
                pickupRequestRepository.save(req2);

                PickupRequest req3 = new PickupRequest(
                                "REQ-0003", "raynar", "Raynar Khalid Mahardika",
                                "Jingga Residence ganteng", "Elektronik",
                                8.0, "2026-06-03", "Hati-hati barangnya", "Ner Kurniawan", "ON_ROUTE", "13:00",
                                -6.9450, 107.6300);
                pickupRequestRepository.save(req3);

                PickupRequest req4 = new PickupRequest(
                                "REQ-0004", "rifat", "Rifat Hanaf",
                                "padang", "Kaca",
                                6.0, "2026-06-04", "-", "Begin Saputra", "ASSIGNED", "14:00",
                                -6.9250, 107.5950);
                pickupRequestRepository.save(req4);

                PickupRequest req5 = new PickupRequest(
                                "REQ-0005", "kevin", "Kevin",
                                "Hub Bandung", "Logam",
                                8.0, "2026-06-05", "Berat banget", "Begin Saputra", "COMPLETED", "08:00",
                                -6.9175, 107.6191);
                pickupRequestRepository.save(req5);

                PickupRequest req6 = new PickupRequest(
                                "REQ-0006", "andi", "Andi Wijaya",
                                "Jl. Mawar No. 5", "Kertas",
                                5.0, "2026-05-20", "Di garasi", "Belum", "PENDING", "09:30",
                                -6.9205, 107.6250);
                pickupRequestRepository.save(req6);

                PickupRequest req7 = new PickupRequest(
                                "REQ-0007", "rina", "Rina Lestari",
                                "Jl. Anggrek No. 12", "Kaca",
                                8.0, "2026-05-21", "Ketuk pintu", "Eko Pratama", "ON_ROUTE", "10:00",
                                -6.9125, 107.6320);
                pickupRequestRepository.save(req7);

                PickupRequest req8 = new PickupRequest(
                                "REQ-0008", "doni", "Doni Pratama",
                                "Jl. Kenanga No. 7", "Logam",
                                7.0, "2026-05-21", "", "Budi Santoso", "COMPLETED", "10:45",
                                -6.9312, 107.6115);
                req8.setActualWeight(7.0);
                pickupRequestRepository.save(req8);

                PickupRequest req9 = new PickupRequest(
                                "REQ-0009", "maya", "Maya Anggun",
                                "Jl. Cempaka No. 9", "Plastik",
                                5.0, "2026-05-22", "", "Budi Santoso", "ASSIGNED", "11:30",
                                -6.9080, 107.6050);
                pickupRequestRepository.save(req9);

                // Seed verifikasi untuk request yang sudah COMPLETED
                seedPickupVerifications(req2, req5, req8);

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
