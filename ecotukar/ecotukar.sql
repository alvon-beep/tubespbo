-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2026 at 09:09 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecotukar`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `id_akun` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('CUSTOMER','ADMIN','TUKANG') NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`id_akun`, `email`, `password_hash`, `role`, `is_active`, `created_at`, `updated_at`) VALUES
(22, 'faizulafiat@gmail.com', 'halo', 'CUSTOMER', 1, '2026-05-19 09:35:50', '2026-05-19 09:35:50'),
(23, 'raynarkm@gmail.com', 'raynar', 'TUKANG', 1, '2026-05-19 09:36:36', '2026-05-19 09:36:36'),
(24, 'hehe@gmail.com', 'hehe', 'ADMIN', 1, '2026-05-19 09:37:02', '2026-05-19 09:37:02'),
(25, 'raynarkk@gmail.com', 'raynar', 'ADMIN', 1, '2026-05-20 04:31:15', '2026-05-20 04:31:15'),
(26, 'asepboi@gmail.com', 'asep', 'TUKANG', 1, '2026-05-20 09:14:30', '2026-05-20 09:14:30'),
(27, 'raynarkhalid@gmail.com', 'raynar2112', 'ADMIN', 1, '2026-05-20 09:15:04', '2026-05-20 09:15:04'),
(28, 'qwerty@gmail.com', 'qwerty', 'CUSTOMER', 1, '2026-05-20 09:18:06', '2026-05-20 09:18:06'),
(29, 'raynarkms@gmail.com', 'raynar', 'ADMIN', 1, '2026-05-21 08:58:04', '2026-05-21 08:58:04'),
(30, 'kep@gmail.com', 'kepin', 'CUSTOMER', 1, '2026-05-21 08:59:54', '2026-05-21 08:59:54'),
(31, 'customer@gmail.com', 'customer', 'CUSTOMER', 1, '2026-06-02 04:42:35', '2026-06-02 04:42:35');

-- --------------------------------------------------------

--
-- Table structure for table `permintaan_penjemputan`
--

CREATE TABLE `permintaan_penjemputan` (
  `id_verifikasi` int(11) NOT NULL,
  `id_request` int(11) NOT NULL,
  `id_tukang` int(11) NOT NULL,
  `berat_aktual` decimal(5,2) NOT NULL,
  `catatan_fisik` text DEFAULT NULL,
  `waktu_verifikasi` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permintaan_penjemputan`
--

INSERT INTO `permintaan_penjemputan` (`id_verifikasi`, `id_request`, `id_tukang`, `berat_aktual`, `catatan_fisik`, `waktu_verifikasi`) VALUES
(1, 2, 31, 0.00, '', '2026-06-02 04:58:40'),
(2, 4, 31, 0.00, '', '2026-06-02 05:44:42');

-- --------------------------------------------------------

--
-- Table structure for table `profil_admin`
--

CREATE TABLE `profil_admin` (
  `id_admin` int(11) NOT NULL,
  `id_akun` int(11) NOT NULL,
  `nama_lengkap` varchar(150) NOT NULL,
  `no_telepon` varchar(15) NOT NULL,
  `regional_bank_sampah` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profil_admin`
--

INSERT INTO `profil_admin` (`id_admin`, `id_akun`, `nama_lengkap`, `no_telepon`, `regional_bank_sampah`) VALUES
(7, 25, 'raynark', '12345678', NULL),
(9, 29, 'Raynarkm', '081322901234', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `profil_customer`
--

CREATE TABLE `profil_customer` (
  `id_customer` int(11) NOT NULL,
  `id_akun` int(11) NOT NULL,
  `nama_lengkap` varchar(150) NOT NULL,
  `no_telepon` varchar(15) NOT NULL,
  `alamat_lengkap` text NOT NULL,
  `saldo_poin` int(11) DEFAULT 0,
  `koordinat_maps` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profil_customer`
--

INSERT INTO `profil_customer` (`id_customer`, `id_akun`, `nama_lengkap`, `no_telepon`, `alamat_lengkap`, `saldo_poin`, `koordinat_maps`) VALUES
(6, 22, 'Faizul', '112312312', '', 0, NULL),
(7, 28, 'qwerty', '08132456789', '', 0, NULL),
(8, 30, 'kep', '123454321', '', 0, NULL),
(9, 31, 'customer', '123123', '', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `profil_tukang`
--

CREATE TABLE `profil_tukang` (
  `id_tukang` int(11) NOT NULL,
  `id_akun` int(11) NOT NULL,
  `nama_lengkap` varchar(150) NOT NULL,
  `no_telepon` varchar(15) NOT NULL,
  `plat_kendaraan` varchar(20) DEFAULT NULL,
  `status_ketersediaan` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profil_tukang`
--

INSERT INTO `profil_tukang` (`id_tukang`, `id_akun`, `nama_lengkap`, `no_telepon`, `plat_kendaraan`, `status_ketersediaan`) VALUES
(5, 23, 'raynar', '2131312', NULL, 0),
(6, 26, 'Asep', '081322905432', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `request_sampah`
--

CREATE TABLE `request_sampah` (
  `id_request` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `jenis_sampah` varchar(50) NOT NULL,
  `berat_estimasi` decimal(5,2) NOT NULL,
  `alamat_penjemputan` text NOT NULL,
  `status` enum('PENDING','ASSIGNED','ON ROUTE','COMPLETED','CANCELLED') DEFAULT 'PENDING',
  `tanggal_dibuat` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `request_sampah`
--

INSERT INTO `request_sampah` (`id_request`, `id_customer`, `jenis_sampah`, `berat_estimasi`, `alamat_penjemputan`, `status`, `tanggal_dibuat`) VALUES
(1, 31, 'Plastik', 3.00, 'ppp', 'PENDING', '2026-06-02 04:51:10'),
(2, 31, 'Plastik', 7.00, 'SOREANG', 'ASSIGNED', '2026-06-02 04:58:27'),
(3, 31, 'Logam', 3.00, 'Bandung', 'CANCELLED', '2026-06-02 05:03:46'),
(4, 31, 'Logam', 3.00, 'rifat', 'ASSIGNED', '2026-06-02 05:42:02');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`id_akun`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `permintaan_penjemputan`
--
ALTER TABLE `permintaan_penjemputan`
  ADD PRIMARY KEY (`id_verifikasi`),
  ADD KEY `id_request` (`id_request`),
  ADD KEY `id_tukang` (`id_tukang`);

--
-- Indexes for table `profil_admin`
--
ALTER TABLE `profil_admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD KEY `id_akun` (`id_akun`);

--
-- Indexes for table `profil_customer`
--
ALTER TABLE `profil_customer`
  ADD PRIMARY KEY (`id_customer`),
  ADD KEY `id_akun` (`id_akun`);

--
-- Indexes for table `profil_tukang`
--
ALTER TABLE `profil_tukang`
  ADD PRIMARY KEY (`id_tukang`),
  ADD KEY `id_akun` (`id_akun`);

--
-- Indexes for table `request_sampah`
--
ALTER TABLE `request_sampah`
  ADD PRIMARY KEY (`id_request`),
  ADD KEY `id_customer` (`id_customer`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `akun`
--
ALTER TABLE `akun`
  MODIFY `id_akun` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `permintaan_penjemputan`
--
ALTER TABLE `permintaan_penjemputan`
  MODIFY `id_verifikasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `profil_admin`
--
ALTER TABLE `profil_admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `profil_customer`
--
ALTER TABLE `profil_customer`
  MODIFY `id_customer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `profil_tukang`
--
ALTER TABLE `profil_tukang`
  MODIFY `id_tukang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `request_sampah`
--
ALTER TABLE `request_sampah`
  MODIFY `id_request` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `permintaan_penjemputan`
--
ALTER TABLE `permintaan_penjemputan`
  ADD CONSTRAINT `permintaan_penjemputan_ibfk_1` FOREIGN KEY (`id_request`) REFERENCES `request_sampah` (`id_request`),
  ADD CONSTRAINT `permintaan_penjemputan_ibfk_2` FOREIGN KEY (`id_tukang`) REFERENCES `akun` (`id_akun`);

--
-- Constraints for table `profil_admin`
--
ALTER TABLE `profil_admin`
  ADD CONSTRAINT `profil_admin_ibfk_1` FOREIGN KEY (`id_akun`) REFERENCES `akun` (`id_akun`) ON DELETE CASCADE;

--
-- Constraints for table `profil_customer`
--
ALTER TABLE `profil_customer`
  ADD CONSTRAINT `profil_customer_ibfk_1` FOREIGN KEY (`id_akun`) REFERENCES `akun` (`id_akun`) ON DELETE CASCADE;

--
-- Constraints for table `profil_tukang`
--
ALTER TABLE `profil_tukang`
  ADD CONSTRAINT `profil_tukang_ibfk_1` FOREIGN KEY (`id_akun`) REFERENCES `akun` (`id_akun`) ON DELETE CASCADE;

--
-- Constraints for table `request_sampah`
--
ALTER TABLE `request_sampah`
  ADD CONSTRAINT `request_sampah_ibfk_1` FOREIGN KEY (`id_customer`) REFERENCES `akun` (`id_akun`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
