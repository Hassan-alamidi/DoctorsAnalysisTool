CREATE DATABASE  IF NOT EXISTS `MTPA` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `MTPA`;
-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: MTPA
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medical_licence_number` varchar(200) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `dob` date NOT NULL,
  `ppsn` varchar(45) NOT NULL,
  `address` varchar(200) NOT NULL,
  `privilege_level` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `phone_number` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `medical_licence_number_UNIQUE` (`medical_licence_number`),
  UNIQUE KEY `ppsn_UNIQUE` (`ppsn`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'num1','testDoc','testie','1991-04-13','jsaof234j','123 easy street','Admin','$2a$10$9Zrs5s5wGDmxcSP71IG73eplyJXn0cSDdIyrvpVGxI8raHthHRSFy',819283,1),(2,'num31','testDoc','testie','1991-04-13','jsaofsfee234j','123 easy street','User','$2a$10$SaZQmxR7DSPXDokK2DXDrufdNe9AmJWrHm38XZoQ9tIIj..vsaiWC',81923483,1),(4,'num331','testDoc','testie','1991-04-13','jsaofsfsfee234j','123 easy street','User','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',34323483,1),(5,'12134e','testDoc','testie','1991-04-13','fsfee234j','123 easy street','User','$2a$10$N2mxboOvsAn56DA8Q.OeJ.ibE7UlRmQnLgbVvqZSj6YY8AqV.kdDq',343243,1);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-17 15:37:54
