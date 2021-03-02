-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 02, 2021 at 03:46 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `receiptdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `name` varchar(15) NOT NULL,
  `password` varchar(256) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `monthly`
--

CREATE TABLE `monthly` (
  `mId` int(11) NOT NULL,
  `sId` int(11) NOT NULL,
  `sept` tinyint(1) NOT NULL,
  `oct` tinyint(1) NOT NULL,
  `nov` tinyint(1) NOT NULL,
  `dece` tinyint(1) NOT NULL,
  `jan` tinyint(1) NOT NULL,
  `feb` tinyint(1) NOT NULL,
  `mar` tinyint(1) NOT NULL,
  `apr` tinyint(1) NOT NULL,
  `may` tinyint(1) NOT NULL,
  `jun` tinyint(1) NOT NULL,
  `jul` tinyint(1) NOT NULL,
  `aug` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `serial` int(11) NOT NULL,
  `payer` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `reason` varchar(200) NOT NULL,
  `amount` float(11,2) NOT NULL,
  `disabled` tinyint(1) NOT NULL,
  `printed` tinyint(1) NOT NULL,
  `cashier` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `fn` varchar(15) NOT NULL,
  `mn` varchar(15) NOT NULL,
  `ln` varchar(15) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `grade` int(11) NOT NULL,
  `sec` varchar(1) NOT NULL,
  `remaining` float NOT NULL,
  `left_school` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `students`
--
DELIMITER $$
CREATE TRIGGER `addStudentTrigger` AFTER INSERT ON `students` FOR EACH ROW BEGIN
INSERT INTO MONTHLY(sId,sept,oct,nov,dece,jan,feb,mar,apr,may,jun,jul,aug) VALUES(new.id,false,false,false,false,false,false,false,false,false,false,false,false);
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
