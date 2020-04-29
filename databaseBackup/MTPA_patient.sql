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
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `dob` date NOT NULL,
  `ppsn` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `dod` date DEFAULT NULL,
  `gender` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PPSN_UNIQUE` (`ppsn`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'Amparo58','2001-12-14','29191fad-b355-472d-86df-4bc85a61eda6','493 Ferry Tunnel Suite 72','Doyle959',NULL,'F'),(2,'Fredrick998','2005-04-08','daecdb66-65b0-4737-89fa-ec6827715c5d','1030 Hodkiewicz Estate Suite 24','Hodkiewicz467',NULL,'M'),(3,'Amberly552','1997-09-05','321cb15c-5b27-4adb-abda-445069a1057e','602 Kirlin Bridge Apt 94','Larson43',NULL,'F'),(4,'Greg949','1976-03-14','3ffdc287-c0a4-449f-bba3-df3ab393297c','409 Haag Way Suite 13','Schneider199',NULL,'M'),(5,'Crista774','1970-04-02','0bfe91e3-2f81-46d8-8526-545c7ecd1c9b','789 Stokes Route Unit 60','Hand679',NULL,'F'),(6,'Ramon749','1959-11-07','fe7ff4f8-6953-4ade-b5c7-75c99bc7465a','685 Larson Green Apt 35','Haley279',NULL,'M'),(7,'Otto672','1945-03-14','c982e9e8-e89e-4c3a-b9a0-8d48cb690c97','665 Howell Crossing','Hoppe518',NULL,'M'),(8,'Danyel','1948-08-04','145452b5-8ede-44f4-9d68-8a60ef0771d6','446 Shields Route','Howell',NULL,'F'),(9,'Genny123','1954-05-28','e9ffc8c6-b80f-437b-929d-42423d17f33d','408 Mertz Club','Hodkiewicz467',NULL,'F'),(10,'Makeda237','1964-04-03','699bffd1-979d-48bb-82ca-c30bea013f04','701 Graham Fort','Bailey598',NULL,'F');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-29 17:19:35
