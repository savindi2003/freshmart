-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: freshmart_db
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `backups`
--

DROP TABLE IF EXISTS `backups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `backups` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datetime` datetime NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `user` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backups`
--

LOCK TABLES `backups` WRITE;
/*!40000 ALTER TABLE `backups` DISABLE KEYS */;
INSERT INTO `backups` VALUES (1,'0000-00-00 00:00:00','2024-12-13','00:00:00','Savindi_CH'),(2,'0000-00-00 00:00:00','2024-12-13','03:00:24','Savindi_CH'),(3,'2024-12-13 03:05:16','2024-12-13','03:05:16','Savindi_CH'),(4,'2025-01-21 10:15:13','2025-01-21','10:15:13','11'),(5,'2025-01-21 10:18:04','2025-01-21','10:18:04','11'),(6,'2025-01-23 00:14:00','2025-01-23','00:14:00','AD_NIMASHA'),(7,'2025-01-23 14:13:02','2025-01-23','14:13:02','AD_NIMASHA');
/*!40000 ALTER TABLE `backups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'Munchee',6),(2,'Sunlight',10),(3,'Maliban',6),(4,'Anchor',2),(5,'Lifeboy',9),(6,'Vim',10),(7,'Signam',9),(8,'Ratti',2),(9,'Nestomolt',2),(10,'Maggi',6),(11,'Coca-cola',5),(12,'Colgate',9),(13,'Pepsi',5),(14,'Nescafe',6),(15,'LUX',9),(16,'Finaga',4),(17,'No Brand',15),(18,'Araliya',8),(19,'Nipuna',8),(20,'Rathna',8),(21,'teastj',NULL),(22,'d',NULL),(23,'hhkk',NULL);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Fruits & Vegetables'),(2,'Dairy Products'),(3,'Meat & Poultry'),(4,'Bakery & Bread'),(5,'Beverages'),(6,'Canned & Packaged Foods'),(7,'Snacks & Confectionery'),(8,'Grains'),(9,'Health & Beauty'),(10,'Household Items'),(11,'Baby Products'),(12,'Pet Care'),(13,'Pharmacy & Wellness'),(14,'Biscuts'),(15,'Others'),(16,'Flour'),(17,'hhkk'),(18,'qw'),(19,'qq');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `hotline` varchar(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Preema','0112211239','preema@gmail.com'),(2,'Nestle Pvt Led','0115566765','nestle@gmail.com'),(3,'Uswaththa','0112233234','uswatta123@gmail.com'),(4,'Atles ','0112233212','atlets@gmail.com'),(5,'Ten Holdings','0776655432','tenhiookk@gmail.com'),(6,'Test','0112222121','hh@gmail.com'),(7,'Unilever','0112288123','infosunlight@gmail.com');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mobile` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `points` int DEFAULT NULL,
  `registerd_date` date DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=774464147 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'0000000000','No','No',0,'2024-10-19'),(2,'0771112321','Malshi','Perera',4000,'2024-10-19'),(3,'0772233432','Nishan','Daisa',3,'2024-10-19'),(4,'077665565','Kamal','Oshithana',2,'2024-12-07'),(5,'077667761','Samlka','Perera',1,'2024-12-07'),(6,'077555555','Janaka','Pushpakumara',1,'2024-12-07'),(7,'077111121','Nisal','Sachinthana',0,'2024-12-07'),(8,'077990091','Kameshi','Chathurika',2900,'2024-12-07'),(774464144,'jj','jj','kk',200,'2025-01-17'),(774464145,'077446413','Malkanthi','Lakmini',1,'2025-01-17'),(774464146,'0773344112','Kalama','Silva',0,'2025-01-23');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `damaged`
--

DROP TABLE IF EXISTS `damaged`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damaged` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL DEFAULT '0',
  `stock_id` bigint NOT NULL DEFAULT '0',
  `reason` text NOT NULL,
  `user_id` varchar(50) NOT NULL DEFAULT '0',
  `date_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `qty` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_damaged_user` (`user_id`),
  KEY `FK_damaged_stock` (`stock_id`),
  CONSTRAINT `FK_damaged_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `FK_damaged_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `damaged`
--

LOCK TABLES `damaged` WRITE;
/*!40000 ALTER TABLE `damaged` DISABLE KEYS */;
INSERT INTO `damaged` VALUES (1,'DMP',3,'Damaged | bb','11','2024-10-11 16:02:32',2),(2,'DMP',8,'Expired','11','2024-11-20 16:02:32',2),(3,'DMP',7,'Other | n','11','2024-11-26 16:04:01',2),(4,'DMP',6,'Expired','11','2025-01-08 11:11:11',1),(6,'DMP',8,'Damaged | bb','11','2025-01-08 11:11:11',1),(7,'DMP',1735364528039,'Other','11','2025-01-09 00:22:52',2),(8,'DMP',1735362338776,'Damaged','11','2025-01-09 00:22:52',2),(9,'DMP',7,'Damaged | oo','11','2025-01-16 22:43:52',3),(10,'DMP',1735362338809,'Damaged','11','2025-01-16 22:43:52',13),(11,'DMP',7,'Other','11','2025-01-21 13:38:23',1),(12,'DMP',1,'Damaged','11','2025-01-22 17:58:09',2),(13,'DMP',1735364370193,'Other','Dilum_1','2025-01-22 18:53:37',2),(14,'DMP',1735364139418,'Expired','AD_NIMASHA','2025-01-23 00:09:02',1),(15,'DMP',1737106408776,'Damaged | Test2','AD_NIMASHA','2025-01-23 00:09:02',30),(16,'DMP',1737106408825,'Damaged | Test1','AD_NIMASHA','2025-01-23 00:09:02',3);
/*!40000 ALTER TABLE `damaged` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_permision`
--

DROP TABLE IF EXISTS `employee_permision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_permision` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `permision_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_employee_permision_permision` (`permision_id`),
  KEY `FK_employee_permision_user_type` (`employee_id`),
  CONSTRAINT `FK_employee_permision_permision` FOREIGN KEY (`permision_id`) REFERENCES `permision` (`id`),
  CONSTRAINT `FK_employee_permision_user_type` FOREIGN KEY (`employee_id`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_permision`
--

LOCK TABLES `employee_permision` WRITE;
/*!40000 ALTER TABLE `employee_permision` DISABLE KEYS */;
INSERT INTO `employee_permision` VALUES (1,1,8),(3,1,1),(5,2,8),(6,3,5),(7,4,7),(9,4,6),(12,1,2),(13,2,2),(15,1,3),(16,1,4),(17,1,5),(18,1,6),(19,1,7);
/*!40000 ALTER TABLE `employee_permision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expenses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_id` int DEFAULT '0',
  `amount` double DEFAULT NULL,
  `note` text,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__expenses_type` (`type_id`),
  CONSTRAINT `FK__expenses_type` FOREIGN KEY (`type_id`) REFERENCES `expenses_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses`
--

LOCK TABLES `expenses` WRITE;
/*!40000 ALTER TABLE `expenses` DISABLE KEYS */;
INSERT INTO `expenses` VALUES (1,1,20000,'test1','2025-01-09'),(2,3,35000,'teast2','2025-01-07'),(3,5,130000,'Test3','2025-01-08'),(4,6,29000,'Test6','2025-01-08'),(5,4,11000,'yert ','2025-01-16'),(6,2,1200,'Test2','2025-01-16'),(7,6,20000,'Test5','2025-01-16'),(8,1,20000,'Test 2','2025-01-16'),(9,1,200,'Test2','2025-01-17'),(10,1,2000,'Test2','2025-01-21'),(11,5,1100,'tEST2','2025-01-21'),(12,1,20000,'Test1','2025-01-23'),(13,5,120000,'Test5','2025-01-23'),(14,4,5000,'Test3','2025-01-23'),(15,2,3500,'Test4','2025-01-23'),(16,5,80000,'Test5','2025-01-23'),(17,1,2000,'Test1','2025-01-23');
/*!40000 ALTER TABLE `expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses_type`
--

DROP TABLE IF EXISTS `expenses_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expenses_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses_type`
--

LOCK TABLES `expenses_type` WRITE;
/*!40000 ALTER TABLE `expenses_type` DISABLE KEYS */;
INSERT INTO `expenses_type` VALUES (1,'Rent/Lease'),(2,'Utilities (Electricity, Water, Gas)'),(3,'Internet and Telephone Bills'),(4,'Cleaning Services and Supplies'),(5,'Employee-Related Expenses'),(6,'Inventory and Stock Expenses');
/*!40000 ALTER TABLE `expenses_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (1,'Male'),(2,'Female');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn`
--

DROP TABLE IF EXISTS `grn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn` (
  `grn_id` bigint NOT NULL,
  `supplier_mobile` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `user_username` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `date_time` datetime NOT NULL,
  `paid_amount` double NOT NULL,
  `balance` double NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`grn_id`) USING BTREE,
  KEY `fk_grn_supplier1_idx` (`supplier_mobile`),
  KEY `fk_grn_user1_idx` (`user_username`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_mobile`) REFERENCES `supplier` (`mobile`),
  CONSTRAINT `fk_grn_user1` FOREIGN KEY (`user_username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn`
--

LOCK TABLES `grn` WRITE;
/*!40000 ALTER TABLE `grn` DISABLE KEYS */;
INSERT INTO `grn` VALUES (1727157608097,'0771232476','11','2024-09-25 11:30:48',240,10,240),(1727157667330,'0771232476','11','2024-09-24 11:32:58',2600,400,2600),(1727157942226,'0776655665','11','2024-09-22 11:36:42',742,58,742),(1727177670718,'0786677777','11','2024-09-21 17:05:04',30,-3,33),(1731082556728,'0771232476','11','2024-11-08 21:48:54',2000,200,1800),(1731087708645,'0771232476','11','2024-11-08 23:12:40',2000,0,2000),(1733376632125,'0771232476','11','2024-12-05 11:02:05',10000,3000,7000),(1735362257908,'0776655676','11','2024-12-28 10:35:38',5000,500,4500),(1735362506441,'0776655665','11','2024-12-28 10:38:51',3000,1000,2000),(1735364009253,'0771232476','11','2024-12-28 11:05:39',10000,720,9280),(1735364321273,'0786666765','11','2024-12-28 11:09:30',20000,16667,3333),(1735364499158,'0776655665','11','2024-12-28 11:12:08',500,100,400),(1736265274081,'0776655665','11','2025-01-07 21:25:38',0,0,12000),(1737020735380,'0776655670','11','2025-01-16 15:17:51',15000,1700,13300),(1737050213821,'0775566543','11','2025-01-16 23:27:44',3000,384,2616),(1737106308955,'0776655670','11','2025-01-17 15:03:28',30000,3200,26800),(1737446309350,'0776655665','11','2025-01-21 13:28:57',500,200,300),(1737551491565,'0776655670','Dilum_1','2025-01-22 18:43:26',200,54,146),(1737621149333,'0771144543','AD_NIMASHA','2025-01-23 14:03:48',2000,500,1500);
/*!40000 ALTER TABLE `grn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn_item`
--

DROP TABLE IF EXISTS `grn_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `buying_price` double NOT NULL,
  `qty` double NOT NULL,
  `unit_name` varchar(45) DEFAULT NULL,
  `stock_id` bigint NOT NULL DEFAULT '0',
  `grn_id` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_stock_has_grn_grn1_idx` (`grn_id`),
  KEY `fk_stock_has_grn_stock1_idx` (`stock_id`),
  CONSTRAINT `FK_grn_item_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `grn_id` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`grn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn_item`
--

LOCK TABLES `grn_item` WRITE;
/*!40000 ALTER TABLE `grn_item` DISABLE KEYS */;
INSERT INTO `grn_item` VALUES (1,120,2,NULL,1,1727157608097),(2,200,13,NULL,3,1727157667330),(3,120,6,NULL,4,1727157942226),(4,11,2,NULL,5,1727157942226),(5,11,3,NULL,6,1727177670718),(6,100,18,NULL,7,1731082556728),(7,200,10,NULL,8,1731087708645),(8,100,50,NULL,1733376725916,1733376632125),(9,200,10,NULL,1733376725937,1733376632125),(10,230,10,NULL,1735362338776,1735362257908),(11,120,10,NULL,1735362338809,1735362257908),(12,100,10,NULL,1735362338826,1735362257908),(13,1000,2,NULL,1735362531830,1735362506441),(14,200,8,NULL,1735364139392,1735364009253),(15,200,4,NULL,1735364139418,1735364009253),(16,130,20,NULL,1735364139434,1735364009253),(17,139,20,NULL,1735364139448,1735364009253),(18,150,10,NULL,1735364139461,1735364009253),(19,1111,3,NULL,1735364370193,1735364321273),(20,100,4,NULL,1735364528039,1735364499158),(21,120,100,NULL,1736265338242,1736265274081),(22,120,20,NULL,1737020871651,1737020735380),(23,200,10,NULL,1737020871683,1737020735380),(24,200,30,NULL,1737020871701,1737020735380),(25,100,29,NULL,1737020871714,1737020735380),(26,120,5,NULL,1737050264901,1737050213821),(27,100,20,NULL,1737050264921,1737050213821),(28,8,2,NULL,1737050264933,1737050213821),(29,110,100,NULL,1737106408776,1737106308955),(30,220,50,NULL,1737106408811,1737106308955),(31,120,40,NULL,1737106408825,1737106308955),(32,100,3,NULL,1737446337317,1737446309350),(33,12,3,NULL,1737551606816,1737551491565),(34,55,2,NULL,1737551606837,1737551491565),(35,100,5,NULL,1737446337317,1737621149333),(36,100,10,NULL,1737621228179,1737621149333);
/*!40000 ALTER TABLE `grn_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` bigint NOT NULL DEFAULT '0',
  `date_time` datetime NOT NULL,
  `total` double NOT NULL DEFAULT '0',
  `paid_amount` double NOT NULL,
  `discount` double DEFAULT NULL,
  `user_username` varchar(45) NOT NULL,
  `customer_mobile` int DEFAULT NULL,
  `payment_method_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_user1_idx` (`user_username`),
  KEY `fk_invoice_customer1_idx` (`customer_mobile`),
  KEY `fk_invoice_payment_method1_idx` (`payment_method_id`),
  CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_mobile`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_invoice_payment_method1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`user_username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (170321907888,'2024-12-05 22:58:12',200,200,0,'CH_Nishan',3,3),(173321907880,'2024-12-05 22:57:12',2000,2000,0,'CH_dilka',2,2),(173321907888,'2024-12-05 22:49:56',1000,1000,0,'11',1,1),(173391907888,'2024-12-05 22:57:37',100,100,0,'CH_Nishan',1,1),(1733559984453,'2024-12-07 13:56:45',150,140,10,'11',8,1),(1733560642521,'2024-12-07 14:07:43',355,355,0,'11',1,1),(1733561033566,'2024-12-07 14:14:16',341,331,10,'11',5,1),(1733561123926,'2024-12-07 14:15:49',111,100,11,'11',2,1),(1733591817308,'2024-12-07 22:47:43',264,264,0,'11',8,1),(1733592045001,'2024-12-07 22:50:57',230,230,0,'11',1,2),(1733592058972,'2024-12-07 22:52:28',590,590,0,'11',1,2),(1733592562981,'2024-12-07 23:00:02',830,830,0,'CH_Hiran',1,1),(1733593392054,'2024-12-07 23:13:33',1430,1430,0,'Dilum_1',1,1),(1733594185487,'2024-12-07 23:27:21',25205,25204,1,'CH_Hiran',8,1),(1733594491927,'2024-12-07 23:31:50',230,230,0,'Dilum_1',8,1),(1733594542687,'2024-12-07 23:33:18',217440,217440,0,'CH_Nishan',1,1),(1733594988928,'2024-12-07 23:40:10',1200,1200,0,'CH_Nishan',8,1),(1733595311631,'2024-12-07 23:45:56',830,830,0,'CH_dilka',1,1),(1733677322604,'2024-12-08 22:32:36',2120,2120,0,'11',8,1),(1733677686039,'2024-12-08 22:38:51',108563,108563,0,'11',7,1),(1733766241861,'2024-12-09 23:14:57',380,380,0,'11',7,1),(1733766434067,'2024-12-09 23:18:13',804,804,0,'11',7,1),(1733771390480,'2024-12-10 00:40:48',12000,11049.5,950.5,'11',5,1),(1733774012105,'2024-12-10 01:23:57',230,0,230,'11',4,3),(1733774084975,'2024-12-10 01:25:26',125,125,0,'11',7,1),(1733774131066,'2024-12-10 01:26:18',12000,10450,1550,'11',6,4),(1733774179921,'2024-12-10 01:26:34',0,0,0,'11',1,4),(1733943609536,'2024-12-13 00:30:27',230,230,0,'CH_Nishan',8,1),(1733943932780,'2024-12-13 00:35:50',1610,1610,0,'Dilum_1',1,1),(1733943959778,'2024-12-13 00:36:18',1200,1200,0,'Dilum_1',1,1),(1733943979727,'2024-12-12 00:36:35',2921,2921,0,'11',1,1),(1735321912678,'2024-12-27 23:22:23',690,690,0,'11',6,1),(1735321945669,'2024-12-27 23:22:47',815,815,0,'11',7,1),(1735372017477,'2024-12-28 13:18:30',2305,2205,100,'11',4,1),(1735930896526,'2025-01-04 00:31:51',150,150,0,'CH_Hiran',1,1),(1735979165156,'2025-01-04 13:56:47',3110,3110,0,'CH_Hiran',1,1),(1735979208228,'2025-01-04 13:58:21',4710,4710,0,'CH_dilka',1,2),(1735979324031,'2025-01-04 13:59:10',4910,4910,0,'CH_Hiran',3,1),(1735979987123,'2025-01-16 14:10:01',125,125,0,'CH_dilka',1,1),(1735980116272,'2025-01-16 14:12:16',870,870,0,'CH_Hiran',8,1),(1736351580068,'2025-01-16 21:23:31',1740,1740,0,'CH_dilka',1,1),(1736351612108,'2025-01-16 21:24:57',6265,6265,0,'CH_Hiran',1,1),(1736361505501,'2025-01-16 00:08:39',150,150,0,'CH_Nishan',1,1),(1736361528868,'2025-01-16 00:09:31',2000,2000,0,'CH_dilka',1,1),(1737021033586,'2025-01-16 15:21:25',1045,1045,0,'CH_Hiran',5,2),(1737021106282,'2025-01-16 15:22:40',979,979,0,'CH_Nishan',5,2),(1737021172524,'2025-01-16 15:23:15',1365,1365,0,'CH_Nishan',8,2),(1737021195143,'2025-01-16 15:23:53',220,220,0,'CH_Nishan',7,2),(1737024023795,'2025-01-16 16:11:05',1080,1080,0,'11',1,2),(1737105827022,'2025-01-17 14:55:07',240,150,90,'11',1,2),(1737111161639,'2025-01-17 16:24:20',810,810,0,'CH_Nishan',774464145,1),(1737446354029,'2025-01-23 00:00:32',345,335,10,'CH_Nishan',7,1),(1737546930726,'2025-01-23 00:00:32',149,149,0,'CH_dilka',774464145,1),(1737548343005,'2025-01-23 00:00:32',122,122,0,'CH_Hiran',1,1),(1737548531854,'2025-01-23 00:00:32',240,240,0,'AD_NIMASHA',1,1),(1737548569887,'2025-01-23 00:00:32',480,480,0,'CH_Hiran',1,1),(1737548778214,'2025-01-23 00:00:32',122,122,0,'CH_dilka',1,1),(1737570512902,'2025-01-23 00:00:32',780,770,10,'AD_NIMASHA',774464145,2),(1737570591358,'2025-01-23 00:00:32',1165,1165,0,'AD_NIMASHA',774464145,1),(1737621245380,'2025-01-23 14:05:54',3542,2792,750,'AD_NIMASHA',3,4),(1737621375955,'2025-01-23 14:06:32',290,290,0,'AD_NIMASHA',1,1),(1758998762189,'2025-09-28 00:18:02',2022,2022,0,'AD_NIMASHA',4,1);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_item`
--

DROP TABLE IF EXISTS `invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `stock_id` bigint NOT NULL DEFAULT '0',
  `invoice_id` bigint NOT NULL DEFAULT '0',
  `unit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_has_invoice_invoice1_idx` (`invoice_id`),
  KEY `fk_stock_has_invoice_stock1_idx` (`stock_id`),
  CONSTRAINT `FK_invoice_item_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `FK_invoice_item_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_item`
--

LOCK TABLES `invoice_item` WRITE;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
INSERT INTO `invoice_item` VALUES (1,1,3,173321907888,'no unit'),(2,1,7,173321907888,'no unit'),(3,1,7,1733559984453,'no unit'),(4,1,3,1733560642521,'no unit'),(5,1,4,1733560642521,'no unit'),(6,1,3,1733561033566,'no unit'),(7,1,6,1733561033566,'no unit'),(8,1,6,1733561123926,'no unit'),(10,1,8,1733592045001,'g'),(11,1,1733376725916,1733592058972,'Kg'),(12,1,8,1733592058972,'no unit'),(14,1,3,1733592562981,'no unit'),(15,1,1733376725916,1733592562981,'g'),(16,5,9,1733592562981,NULL),(17,1,3,1733593392054,'no unit'),(18,10,9,1733593392054,'Kg'),(19,1,3,1733594185487,'no unit'),(20,3,4,1733594185487,'no unit'),(21,205,9,1733594185487,'Kg'),(22,1,3,1733594491927,'no unit'),(23,1,3,1733594542687,'no unit'),(24,1812,9,1733594542687,'Kg'),(25,10,9,1733594988928,'Kg'),(26,1,3,1733595311631,'no unit'),(27,5,9,1733595311631,'Kg'),(28,4,3,1733677322604,'no unit'),(29,5,1733376725937,1733677322604,'no unit'),(30,1,3,1733677686039,'no unit'),(31,3,6,1733677686039,'no unit'),(32,900,9,1733677686039,'Kg'),(33,1,3,1733766241861,'no unit'),(34,1,7,1733766241861,'no unit'),(35,3,3,1733766434067,'no unit'),(36,3,4,1733766434067,'no unit'),(37,2,9,1733766434067,'Kg'),(38,100,9,1733771390480,'Kg'),(39,1,3,1733774012105,'no unit'),(40,1,4,1733774084975,'no unit'),(41,100,9,1733774131066,'Kg'),(42,100,9,1733774179921,'Kg'),(43,1,3,1733943609536,'no unit'),(44,7,3,1733943932780,'no unit'),(45,7,3,1733943959778,'no unit'),(46,10,9,1733943959778,'Kg'),(47,7,3,1733943979727,'no unit'),(48,1,6,1733943979727,'no unit'),(49,10,9,1733943979727,'Kg'),(50,3,3,1735321912678,'no unit'),(51,3,3,1735321945669,'no unit'),(52,1,4,1735321945669,'no unit'),(53,1,1735364528039,1735372017477,'no unit'),(54,3,1735364139448,1735372017477,'no unit'),(55,1,1735364139434,1735372017477,'no unit'),(56,3,1735362338776,1735372017477,'no unit'),(57,5,9,1735372017477,'Kg'),(58,1,7,1735930896526,'no unit'),(59,3,1735364139448,1735979165156,'no unit'),(60,6,1733376725916,1735979165156,'no unit'),(61,10,1735364139434,1735979165156,'no unit'),(62,1,1733376725937,1735979165156,'no unit'),(63,3,1735364139448,1735979208228,'no unit'),(64,6,1733376725916,1735979208228,'no unit'),(65,10,1735364139434,1735979208228,'no unit'),(66,1,1733376725937,1735979208228,'no unit'),(67,8,1735362338809,1735979208228,'no unit'),(68,3,1735364139448,1735979324031,'no unit'),(69,6,1733376725916,1735979324031,'no unit'),(70,10,1735364139434,1735979324031,'no unit'),(71,1,1733376725937,1735979324031,'no unit'),(72,8,1735362338809,1735979324031,'no unit'),(73,1,1735364139461,1735979324031,'no unit'),(74,1,4,1735979987123,'no unit'),(75,3,1735364139392,1735980116272,'no unit'),(76,2,7,1736351580068,'no unit'),(77,12,9,1736351580068,'Kg'),(78,1,1735364528039,1736351612108,'no unit'),(79,1,4,1736351612108,'no unit'),(80,2,7,1736351612108,'no unit'),(81,47,9,1736351612108,'Kg'),(82,1,7,1736361505501,'no unit'),(83,3,1735364528039,1736361528868,'no unit'),(84,10,9,1736361528868,'Kg'),(85,1,1735364139461,1736361528868,'no unit'),(86,1,3,1737021033586,'no unit'),(87,1,1735364139448,1737021033586,'no unit'),(88,2,8,1737021033586,'no unit'),(89,1,1735364139434,1737021033586,'no unit'),(90,1,4,1737021106282,'no unit'),(91,1,1735362338826,1737021106282,'no unit'),(92,1,8,1737021106282,'no unit'),(93,3,1735364139461,1737021106282,'no unit'),(94,1,4,1737021172524,'no unit'),(95,8,1735364139434,1737021172524,'no unit'),(96,1,1735364139418,1737021195143,'no unit'),(97,1,4,1737021195143,'no unit'),(98,8,1735364139434,1737021195143,'no unit'),(99,1,1735364139418,1737024023795,'no unit'),(100,1,3,1737024023795,'no unit'),(101,1,1737020871701,1737024023795,'no unit'),(102,2,1735364139461,1737024023795,'no unit'),(103,1,1733376725937,1737105827022,'no unit'),(104,2,1735364139392,1737111161639,'no unit'),(105,1,3,1737111161639,'no unit'),(106,1,1735364139418,1737446354029,'no unit'),(107,1,4,1737446354029,'no unit'),(108,1,1735362338826,1737546930726,'no unit'),(109,1,1,1737548343005,'no unit'),(110,1,1733376725937,1737548531854,'no unit'),(111,2,1733376725937,1737548569887,'no unit'),(112,1,1,1737548778214,'no unit'),(113,1,1735364139392,1737570512902,'no unit'),(114,1,1735362338776,1737570512902,'no unit'),(115,2,9,1737570512902,'Kg'),(116,1,1735364139392,1737570591358,'no unit'),(117,1,3,1737570591358,'no unit'),(118,1,1735364139434,1737570591358,'no unit'),(119,1,1735362338776,1737570591358,'no unit'),(120,2,9,1737570591358,'Kg'),(121,1,1735364139418,1737621245380,'no unit'),(122,1,1737106408776,1737621245380,'no unit'),(123,3,1733376725937,1737621245380,'no unit'),(124,2,9,1737621245380,'Kg'),(125,9,1737106408811,1737621245380,'no unit'),(126,1,1735364139392,1737621375955,'no unit'),(127,1,1,1758998762189,'no unit'),(128,2,1735364139392,1758998762189,'no unit'),(129,1,1733376725937,1758998762189,'no unit'),(130,9,9,1758998762189,'Kg');
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` VALUES (1,'Cash'),(2,'Card'),(3,'Points'),(4,'Cash & Points'),(5,'Card & Points');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permision`
--

DROP TABLE IF EXISTS `permision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permision` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permision`
--

LOCK TABLES `permision` WRITE;
/*!40000 ALTER TABLE `permision` DISABLE KEYS */;
INSERT INTO `permision` VALUES (1,'Employee Mng'),(2,'Customer Mng'),(3,'Supplier Mng'),(4,'Product Mng'),(5,'GRN'),(6,'Damage Product'),(7,'Weighting & Pricing'),(8,'Invoice');
/*!40000 ALTER TABLE `permision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` varchar(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `brand_id` int NOT NULL,
  `unit_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_brand1_idx` (`brand_id`),
  KEY `unit` (`unit_id`),
  CONSTRAINT `fk_product_brand1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `unit` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('1','Munchee Nice Biscuits 100g',1,1),('12','Sunlight Lemon Detergent Soap 110g',2,1),('128','Sunlight Naturals Matic Detergent Liquid 1L',2,1),('128k','Sunlight Matic Liquid 600ml',2,1),('13','Munchee Choc Shock 30g',1,1),('1Ca','Lifeboy Lemon Fresh Soap Bar 110g',5,6),('1ko2','Manchee Hawaian Cookies 100g',1,1),('2','Munchee Milk Shortcake 200g',1,1),('26','Munchee Tikiri Marie 80gd',1,1),('3dd','Sunlight Royal Lavender Soap 110g',2,1),('99','Manchee ',1,1),('ag23','Sunlight Matic Liquid 1L',2,1),('B_01','Sunlight After Wash Floral Bliss 280ml ',2,6),('B_02','Munchee Lemon Puff 100g',1,6),('C12','Munchee Chocolate Puff 100g',1,2),('F01','Thirigu Piti',17,1),('F02','Samba Sahal',17,1),('F03','Ulundu Piti',17,1),('F04','Kurakkan Piti',17,1),('F088','Banana(Kolikuttu)',17,1),('F089','Apple',17,1),('F090','Guava',17,1),('F091','Papaya',17,1),('F092','Grapes',17,1),('G01','Mun Grains',17,1),('G02','Kawpi Grains',17,1),('G03','Dhal',17,1),('G04','Chickpeas',17,1),('HB001','Test1',5,6),('qq','Munchee Cheese Cracker 100g ',1,6),('V01','Beans',17,1),('V02','Carrots',17,1),('V03','Cabbage',17,1),('V04','Radish',17,1),('V05','Nocole',17,1),('V06','Pumpkin',17,1),('V07','Tomato',17,1),('V08','Cuicamba',17,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_status`
--

DROP TABLE IF EXISTS `product_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_status`
--

LOCK TABLES `product_status` WRITE;
/*!40000 ALTER TABLE `product_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_item`
--

DROP TABLE IF EXISTS `return_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_id` bigint NOT NULL DEFAULT '0',
  `reason` text,
  `qty` int DEFAULT NULL,
  `total` double DEFAULT NULL,
  `return_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `return_id` (`return_id`),
  KEY `FK_return_item_stock` (`stock_id`),
  CONSTRAINT `FK_return_item_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `return_id` FOREIGN KEY (`return_id`) REFERENCES `returnp` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_item`
--

LOCK TABLES `return_item` WRITE;
/*!40000 ALTER TABLE `return_item` DISABLE KEYS */;
INSERT INTO `return_item` VALUES (1,3,'Expired',2,400,1732275095612),(2,3,'Expired',2,400,1732275236537),(3,7,'Damaged',4,400,1732295690265),(4,3,'Damaged | it is damaged all',3,600,1732296097831),(5,3,'Damaged',2,400,1732296713267),(6,8,'Damaged',2,400,1732296895133),(7,1735362531830,'Damaged',2,2000,1735929179818),(8,1735364139392,'Other | kalabama jajaja',2,400,1735930250400),(9,1735364139418,'Expired',2,400,1735930250400),(10,1735362338776,'Damaged',2,460,1735930250400),(11,4,'Other',2,240,1735930390549),(12,1735362338809,'Other',2,240,1735930468291),(13,4,'Damaged',1,120,1735931117165),(14,6,'Damaged',1,11,1735931191365),(15,7,'Damaged',2,200,1735931530979),(16,6,'Expired',1,11,1735932082660),(17,1735362338826,'Expired',3,300,1735932255139),(18,1733376725916,'Expired',4,400,1735932255139),(19,7,'Other | No wanted food',3,300,1735932255139),(20,8,'Damaged',2,400,1735932255139),(21,4,'Damaged',2,240,1735932444271),(22,1733376725937,'Other',2,400,1735932444271),(23,1735362338826,'Damaged',2,200,1736265354227),(24,1735364370193,'Expired',1,1111,1736967335835),(25,6,'Other',1,11,1736967335835),(26,7,'Damaged',1,100,1736967335835),(27,1736265338242,'Damaged',2,240,1737021275988),(28,1735364139448,'Other',2,278,1737021275988),(29,1737020871651,'Expired',1,120,1737021275988),(30,1737106408776,'Expired',1,110,1737106422359),(31,8,'Expired',2,400,1737106548966),(32,7,'Damaged',2,200,1737446391513),(33,1,'Damaged',2,240,1737549002177),(34,1,'Other',2,240,1737570800408),(35,1737106408825,'Other | Test',2,240,1737621404943);
/*!40000 ALTER TABLE `return_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `returnp`
--

DROP TABLE IF EXISTS `returnp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `returnp` (
  `id` bigint NOT NULL,
  `date_time` datetime DEFAULT NULL,
  `supplier_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `paid_amount` double DEFAULT NULL,
  `balence` double DEFAULT NULL,
  `employee` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_mob` (`supplier_id`),
  KEY `employee` (`employee`),
  CONSTRAINT `employee` FOREIGN KEY (`employee`) REFERENCES `user` (`username`),
  CONSTRAINT `supplier_mob` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returnp`
--

LOCK TABLES `returnp` WRITE;
/*!40000 ALTER TABLE `returnp` DISABLE KEYS */;
INSERT INTO `returnp` VALUES (1,'2024-11-21 23:36:38','0776655665',1600,2000,400,'11'),(2,'2024-11-21 23:38:22','0776655676',622,1000,378,'11'),(1000,'2024-11-22 16:29:56','0776655676',700,1000,300,'11'),(1732273556205,'2024-11-22 16:59:56','0775566543',360,1000,640,'11'),(1732275095612,'2024-11-22 17:01:55','0111111111',400,500,100,'11'),(1732275236537,'2024-11-22 17:04:12','0775566543',400,1000,600,'11'),(1732295690265,'2024-11-22 22:45:08','0776655676',400,666,266,'11'),(1732296097831,'2024-11-22 22:52:07','0775566543',600,1000,400,'11'),(1732296713267,'2024-11-22 23:02:13','0776655665',400,1000,600,'11'),(1732296895133,'2024-11-22 23:05:15','0776655432',400,1000,600,'11'),(1735929179818,'2025-01-04 00:03:28','0111111111',2000,4555,2555,'11'),(1735930250400,'2025-01-04 00:21:42','0775566543',1260,1500,240,'11'),(1735930390549,'2025-01-04 00:23:25','0776655670',240,800,560,'11'),(1735930468291,'2025-01-04 00:24:42','0775566543',240,1000,760,'11'),(1735931117165,'2025-01-04 00:35:30','0786666765',120,120,0,'11'),(1735931191365,'2025-01-04 00:36:47','0776655676',11,10,-1,'11'),(1735931530979,'2025-01-04 00:42:38','0775566543',200,1000,800,'11'),(1735932082660,'2025-01-04 00:51:36','0786666765',11,100,89,'11'),(1735932255139,'2025-01-04 00:55:17','0111111111',1400,2000,600,'11'),(1735932444271,'2025-01-04 00:57:53','0776655670',640,700,60,'11'),(1736265354227,'2025-01-07 21:26:11','0776655676',200,200,0,'11'),(1736967335835,'2025-01-16 00:27:38','0776655432',1222,1000,-222,'11'),(1737021275988,'2025-01-16 15:25:44','0111111111',638,600,-38,'11'),(1737106422359,'2025-01-17 15:04:25','0776655670',110,90,-20,'11'),(1737106548966,'2025-01-17 15:06:11','0786666765',400,1111,711,'11'),(1737446391513,'2025-01-21 13:30:08','0775566543',200,200,0,'11'),(1737549002177,'2025-01-22 18:00:19','0776655665',240,300,60,'11'),(1737570800408,'2025-01-23 00:03:41','0786677777',240,300,60,'AD_NIMASHA'),(1737621404943,'2025-01-23 14:07:33','0772233233',240,300,60,'AD_NIMASHA');
/*!40000 ALTER TABLE `returnp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'active'),(2,'Inactive');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `id` bigint NOT NULL DEFAULT '0',
  `product_id` varchar(20) NOT NULL,
  `selling_price` double NOT NULL,
  `qty` int NOT NULL DEFAULT '0',
  `mfd` date NOT NULL,
  `exp` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'ag23',122,2,'2024-09-01','2024-09-01'),(2,'qq',135,2,'2024-09-01','2024-09-30'),(3,'qq',230,7,'2023-09-01','2025-09-30'),(4,'qq',125,1,'2024-09-01','2024-09-30'),(5,'26',12,0,'2024-09-03','2024-09-29'),(6,'2',111,0,'2024-09-01','2024-09-30'),(7,'1ko2',150,0,'2024-11-01','2024-11-30'),(8,'ag23',230,4,'2024-11-01','2024-11-30'),(9,'F02',120,35,'2024-01-24','2024-12-02'),(1733376725916,'F03',120,46,'2024-02-01','2025-05-31'),(1733376725937,'F04',240,4,'2024-12-01','2024-12-31'),(1735362338776,'12',250,4,'2024-12-01','2024-12-31'),(1735362338809,'3dd',200,-5,'2024-12-01','2024-12-31'),(1735362338826,'26',149,5,'2024-12-01','2024-12-31'),(1735362531830,'1',2000,0,'2024-12-01','2024-12-16'),(1735364139392,'12',290,1,'2024-12-01','2024-12-31'),(1735364139418,'128k',220,0,'2024-12-01','2024-12-31'),(1735364139434,'3dd',155,19,'2024-12-08','2025-03-31'),(1735364139448,'ag23',200,18,'2024-12-01','2024-12-31'),(1735364139461,'1Ca',200,10,'2024-12-01','2024-12-31'),(1735364370193,'1',1111,0,'2024-12-01','2024-12-31'),(1735364528039,'1',200,2,'2024-12-01','2024-12-31'),(1736265338242,'26',130,98,'2025-01-01','2025-01-31'),(1737020871651,'1',140,19,'2025-01-01','2025-01-31'),(1737020871683,'26',220,10,'2025-01-01','2025-01-31'),(1737020871701,'128',230,30,'2024-11-05','2025-01-31'),(1737020871714,'1ko2',150,29,'2025-01-02','2025-01-31'),(1737050264901,'1',150,5,'2025-01-01','2025-01-31'),(1737050264921,'26',120,20,'2025-01-01','2025-01-31'),(1737050264933,'1ko2',99,2,'2025-01-08','2025-01-31'),(1737106408776,'F01',112,68,'2025-01-01','2025-02-28'),(1737106408811,'F03',250,41,'2025-01-01','2025-01-31'),(1737106408825,'F04',140,35,'2025-01-01','2025-01-31'),(1737446337317,'12',120,8,'2025-01-01','2025-01-31'),(1737551606816,'12',12,3,'2025-01-01','2025-01-31'),(1737551606837,'1',77,2,'2025-01-31','2025-02-27'),(1737621228179,'128k',120,10,'2025-01-01','2025-01-31');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `mobile` varchar(10) NOT NULL,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) NOT NULL,
  `nic` varchar(45) NOT NULL,
  `company_id` int NOT NULL,
  `registerd_date` date NOT NULL,
  PRIMARY KEY (`mobile`),
  KEY `fk_supplier_company1_idx` (`company_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('0111111111','Madura','Rangitha','111122233234',2,'2024-08-28'),('0771144543','Sadun','Perera','200378800901',7,'2025-01-23'),('0771232476','Lahiruni','Kumarasiri','200376688987',1,'0000-00-00'),('0772233233','Kolitha','Samalka','200322222222',5,'2025-01-09'),('0775566543','Kamala','Silva','200387766787',3,'2024-09-20'),('0776655432','Janaka','Pushpakumara','112786987v',3,'2024-08-28'),('0776655665','Kusal','Perera','200787700989',2,'2024-09-23'),('0776655670','Kolitha','Muthukumarana','200375500953',1,'2024-09-23'),('0776655676','Sandali','Pathiraj','200322211987',3,'0000-00-00'),('0786666765','Priyani','Silva','991234322v',2,'2024-08-28'),('0786677777','Niam','Mohombas','200988899899',4,'2024-09-23');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (1,'Kg'),(2,'g'),(3,'mg'),(4,'L'),(5,'ml'),(6,'p'),(7,'b'),(8,'no unit');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `nic` varchar(20) NOT NULL,
  `date_registerd` date NOT NULL,
  `user_type_id` int NOT NULL,
  `gender_id` int NOT NULL,
  `user_status_id` int DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `fk_user_user_type_idx` (`user_type_id`),
  KEY `fk_user_gender1_idx` (`gender_id`),
  KEY `user_status_id` (`user_status_id`),
  CONSTRAINT `fk_user_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`),
  CONSTRAINT `user_status_id` FOREIGN KEY (`user_status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('11','11','Savindini','Duleesh','0774464138','200375500952','2024-08-28',1,2,1),('77','12345678','Himasha','Nimsara','0776666776','200376677877','2025-01-09',2,1,1),('AD_NIMASHA','12345678','Nimasha','Kavindini','0775566770','200387799098','2025-01-22',1,2,1),('CH_dilka','12345678','Nishan','Wicramasinghe','0776677654','123456789v','2024-08-28',1,2,1),('CH_Hiran','12348987','Himashi','Vithanage','0774464138','200376688987','2024-12-10',1,1,1),('CH_Nishan','111111','Nishan','Wicramasinhe','0776655654','981121188986','2024-11-08',2,1,1),('dd','23456789','Maneesha','Disanayaka','0775566565','200378899098','2025-01-09',4,1,1),('Dilum_1','12345678','Dilum','Randil','0775566543','200233322387','2024-09-20',3,1,1),('Hrruni','12345678','Hiruni','Perera','0771122365','200378800982','2025-01-23',2,1,1),('Omini','12345678','Omini','Perera','0774433126','200389900112','2025-01-09',4,2,1),('WH_Amalka','12345678','Amalka','Kalani','0774434332','200389999892','2025-01-16',4,2,1),('WH_Iman','12345678','Iman','Ranidu','0775565452','200376688987','2025-01-22',4,1,1),('W_Nima','12345678','Nimantha','Kawya','0776600909','200311122321','2025-01-09',4,1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'Admin'),(2,'Cashier'),(3,'Store Manager'),(4,'Weighing Station Operator');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weight`
--

DROP TABLE IF EXISTS `weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weight` (
  `id` bigint NOT NULL,
  `user_id` varchar(50) NOT NULL DEFAULT '0',
  `stock_id` bigint DEFAULT NULL,
  `weight` varchar(50) NOT NULL DEFAULT '0',
  `unit` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user_id`),
  KEY `FK_weight_stock` (`stock_id`),
  CONSTRAINT `FK_weight_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weight`
--

LOCK TABLES `weight` WRITE;
/*!40000 ALTER TABLE `weight` DISABLE KEYS */;
INSERT INTO `weight` VALUES (1,'11',NULL,'1000',NULL,NULL,'2024-12-02 22:59:12'),(1733218932216,'11',9,'2','Kg',240,'2024-12-03 15:12:12'),(1733218965893,'11',9,'200','g',24,'2024-12-03 15:12:45'),(1733219030172,'11',9,'5','Kg',600,'2024-12-03 15:13:50'),(1733219040876,'11',9,'50','Kg',6000,'2024-12-03 15:14:00'),(1733219055104,'11',9,'1202','g',144.24,'2024-12-03 15:14:15'),(1733219067482,'11',9,'22','g',2.64,'2024-12-03 15:14:27'),(1733219077772,'11',9,'19','Kg',2280,'2024-12-03 15:14:37'),(1733219085204,'11',9,'100','Kg',12000,'2024-12-03 15:14:45'),(1733221442300,'11',9,'900','Kg',108000,'2024-12-03 15:54:02'),(1733221531009,'11',9,'10','Kg',1200,'2024-12-03 15:55:31'),(1736268469649,'11',9,'2','Kg',240,'2025-01-07 22:17:49'),(1736269072538,'11',9,'0.500','Kg',60,'2025-01-07 22:27:52'),(1736269426306,'11',9,'0.590','Kg',70.8,'2025-01-07 22:33:46'),(1736269640160,'11',9,'1','Kg',120,'2025-01-07 22:37:20'),(1736269794928,'11',9,'5','Kg',600,'2025-01-07 22:39:54'),(1736269834254,'11',9,'8','Kg',960,'2025-01-07 22:40:34'),(1736270171573,'11',9,'7','Kg',840,'2025-01-07 22:46:11'),(1736270513229,'11',9,'8','Kg',960,'2025-01-07 22:51:53'),(1736272050911,'11',9,'12','Kg',1440,'2025-01-07 23:17:30'),(1736272156661,'11',9,'2','Kg',240,'2025-01-07 23:19:16'),(1736347660399,'11',9,'0.890','Kg',106.8,'2025-01-08 20:17:40'),(1736347720660,'11',9,'900','g',108,'2025-01-08 20:18:40'),(1736347847832,'11',9,'10','Kg',1200,'2025-01-08 20:20:47'),(1736347955730,'11',9,'10','Kg',1200,'2025-01-08 20:22:35'),(1736347975946,'11',9,'10','Kg',1200,'2025-01-08 20:22:55'),(1736348000971,'11',9,'10','Kg',1200,'2025-01-08 20:23:20'),(1736348112540,'11',9,'0.900','Kg',108,'2025-01-08 20:25:12'),(1736351457310,'11',9,'13','Kg',1560,'2025-01-08 21:20:57'),(1736351549364,'11',9,'12','Kg',1440,'2025-01-08 21:22:29'),(1737021421737,'11',9,'1.28','Kg',153.6,'2025-01-16 15:27:01'),(1737441621555,'11',9,'11','Kg',1320,'2025-01-21 12:10:21'),(1737552746690,'W_Nima',9,'2','Kg',240,'2025-01-22 19:02:26'),(1737552818763,'W_Nima',9,'7','Kg',840,'2025-01-22 19:03:38'),(1758999001420,'AD_NIMASHA',9,'2.303','Kg',276.36,'2025-09-28 00:20:01');
/*!40000 ALTER TABLE `weight` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-06 14:15:43
