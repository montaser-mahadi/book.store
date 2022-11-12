-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 12, 2022 at 10:52 AM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `id` int NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `available_quantity` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `classification_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ehpdfjpu1jm3hijhj4mm0hx9h` (`isbn`),
  UNIQUE KEY `UK_wugryet8mf6oi28n00x2eoc4` (`name`),
  KEY `FK8yrwqg928ud7f8u4cfpgww96b` (`classification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `author`, `available_quantity`, `description`, `isbn`, `name`, `price`, `classification_id`) VALUES
(1, 'Mickel Obama', 200, 'Learning .Net in advance', 'EGDD96s2S', 'Visual Basic Fandamental', 150, 1),
(3, 'Mickel Obama', 185, 'Learning Java  in advance', 'EV2D96s2S', 'Java Fandamental', 150, 2),
(5, 'Mon', 100, 'DFDASDF', 'ER5FSDF', 'Database', 1500, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `classification`
--

DROP TABLE IF EXISTS `classification`;
CREATE TABLE IF NOT EXISTS `classification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `classification_name` varchar(255) DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sx426fxotue1ths9m7glvg30x` (`classification_name`),
  KEY `FKolcra4pjg97o0ng8pxyct8riu` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `classification`
--

INSERT INTO `classification` (`id`, `classification_name`, `book_id`) VALUES
(1, 'comic books', NULL),
(2, 'financial books', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` int NOT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  `discount` int NOT NULL,
  `classification_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpsijspb6n1duwxucapmp7ivpb` (`classification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `coupon`
--

INSERT INTO `coupon` (`id`, `coupon_code`, `discount`, `classification_id`) VALUES
(2, 'K4NQUOM', 10, 2),
(4, 'ZIZCUA0', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `email`, `name`) VALUES
(1, 'nasor_1590@hotmail.com', 'Montaser Mahadi');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(6);

-- --------------------------------------------------------

--
-- Table structure for table `myorder`
--

DROP TABLE IF EXISTS `myorder`;
CREATE TABLE IF NOT EXISTS `myorder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `coupon_code` varchar(255) DEFAULT NULL,
  `order_description` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdq85rdthgfddja72myuxx3dub` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `myorder`
--

INSERT INTO `myorder` (`id`, `coupon_code`, `order_description`, `total_price`, `customer_id`) VALUES
(1, 'K4NQUOM', 'To collect some books from store', 1425, 1),
(2, 'K4NQUOM', 'Describe any things to test', 675, NULL),
(3, 'K4NQUOM', 'Describe any things to test', 675, NULL),
(4, '', 'Describe any things to test', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE IF NOT EXISTS `shopping_cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `book_id` int DEFAULT NULL,
  `book_name` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKti4qvivs47w39g3byxnhil8cn` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shopping_cart`
--

INSERT INTO `shopping_cart` (`id`, `amount`, `book_id`, `book_name`, `quantity`, `order_id`) VALUES
(1, 750, 1, 'Visual Basic Fandamental', 5, 1),
(2, 750, 3, 'Java Fandamental', 5, 1),
(3, 750, 3, 'Java Fandamental', 5, 2),
(4, 750, 3, 'Java Fandamental', 5, 3),
(5, 150, 3, 'Learning Java  in advance', 5, 4);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `FK8yrwqg928ud7f8u4cfpgww96b` FOREIGN KEY (`classification_id`) REFERENCES `classification` (`id`);

--
-- Constraints for table `classification`
--
ALTER TABLE `classification`
  ADD CONSTRAINT `FKolcra4pjg97o0ng8pxyct8riu` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`);

--
-- Constraints for table `coupon`
--
ALTER TABLE `coupon`
  ADD CONSTRAINT `FKpsijspb6n1duwxucapmp7ivpb` FOREIGN KEY (`classification_id`) REFERENCES `classification` (`id`);

--
-- Constraints for table `myorder`
--
ALTER TABLE `myorder`
  ADD CONSTRAINT `FKdq85rdthgfddja72myuxx3dub` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);

--
-- Constraints for table `shopping_cart`
--
ALTER TABLE `shopping_cart`
  ADD CONSTRAINT `FKti4qvivs47w39g3byxnhil8cn` FOREIGN KEY (`order_id`) REFERENCES `myorder` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
