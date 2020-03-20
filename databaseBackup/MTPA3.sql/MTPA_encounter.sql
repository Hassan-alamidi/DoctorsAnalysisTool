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
-- Table structure for table `encounter`
--

DROP TABLE IF EXISTS `encounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encounter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(200) NOT NULL,
  `date_visited` date NOT NULL,
  `date_left` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounter`
--

LOCK TABLES `encounter` WRITE;
/*!40000 ALTER TABLE `encounter` DISABLE KEYS */;
INSERT INTO `encounter` VALUES (1,'checkup','2020-01-12',NULL,'jsaofj',1),(2,'checkup','2020-01-12',NULL,'jsaofj',1),(3,'checkup','2020-01-12',NULL,'jsaofj',1),(4,'checkup','2020-01-12',NULL,'jsaofj',1),(5,'checkup','2020-01-12',NULL,'jsaofj',1),(6,'checkup','2020-01-12',NULL,'jsaofj',1),(8,'checkup','2020-01-14',NULL,'jsaofj',1),(11,'checkup','2020-01-14',NULL,'jsaofj',1),(12,'checkup','2020-01-14',NULL,'jsaofj',1),(13,'checkup','2020-01-14',NULL,'jsaofj',1),(16,'checkup','2020-01-14',NULL,'jsaofj',1),(18,'checkup','2020-01-14',NULL,'jsaofj',1),(19,'checkup','2020-01-14',NULL,'jsaofj',1),(20,'checkup','2020-01-14',NULL,'jsaofj',1),(21,'checkup','2020-01-14',NULL,'jsaofj',1),(22,'checkup','2020-01-14',NULL,'jsaofj',1),(23,'checkup','2020-01-14',NULL,'jsaofj',1),(24,'checkup','2020-01-14',NULL,'jsaofj',1),(25,'checkup','2020-01-14',NULL,'jsaofj',1),(26,'checkup','2020-01-14',NULL,'jsaofj',1),(27,'checkup','2020-01-14',NULL,'jsaofj',1),(28,'checkup','2020-01-14',NULL,'jsaofj',1);
/*!40000 ALTER TABLE `encounter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-02  9:14:46
