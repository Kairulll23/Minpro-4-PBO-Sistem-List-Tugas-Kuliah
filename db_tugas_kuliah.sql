-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 13, 2026 at 02:34 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tugas_kuliah`
--

-- --------------------------------------------------------

--
-- Table structure for table `tugas`
--

CREATE TABLE `tugas` (
  `kode_tugas` varchar(10) NOT NULL,
  `tipe_tugas` enum('Individu','Kelompok') NOT NULL,
  `nama_matkul` varchar(100) NOT NULL,
  `detail_tugas` text NOT NULL,
  `dateline` varchar(50) NOT NULL,
  `is_selesai` tinyint(1) DEFAULT '0',
  `platform_pengumpulan` varchar(100) DEFAULT NULL,
  `jumlah_anggota` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tugas`
--

INSERT INTO `tugas` (`kode_tugas`, `tipe_tugas`, `nama_matkul`, `detail_tugas`, `dateline`, `is_selesai`, `platform_pengumpulan`, `jumlah_anggota`) VALUES
('TGS-001', 'Individu', 'Pemrograman Berbasis Objek', 'Laporan Mini Project 4 JDBC & DAO', '2026-11-01 23:59', 1, 'Classroom\r\n', NULL),
('TGS-002', 'Kelompok', 'Basis Data', 'Desain Database & Query Optimization', '2026-11-05 20:00', 1, 'Langsung sama ibu', 4),
('TGS-003', 'Kelompok', 'Jaringan Komputer', 'PA JARKOM', '2026-11-01 23.59', 0, NULL, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tugas`
--
ALTER TABLE `tugas`
  ADD PRIMARY KEY (`kode_tugas`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
