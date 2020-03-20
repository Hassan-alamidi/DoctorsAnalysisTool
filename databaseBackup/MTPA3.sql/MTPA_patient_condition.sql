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
-- Table structure for table `patient_condition`
--

DROP TABLE IF EXISTS `patient_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `symptoms` varchar(200) NOT NULL,
  `condition_code` int(11) NOT NULL,
  `details` longtext,
  `discovered` date NOT NULL,
  `cured_on` date DEFAULT NULL,
  `patient_ppsn` varchar(45) NOT NULL,
  `encounter_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_condition`
--

LOCK TABLES `patient_condition` WRITE;
/*!40000 ALTER TABLE `patient_condition` DISABLE KEYS */;
INSERT INTO `patient_condition` VALUES (1,'flu','runny noes and dizzy',552,'patient has been moaning bout feeling dizzy','2020-01-03',NULL,'jsaofj',16),(2,'randomName','cough',1,'sjfoij','2020-06-12',NULL,'jsaofj',22),(3,'randomName','cough',1,'sjfoij','2020-06-12',NULL,'jsaofj',23),(4,'randomName','cough',1,'sjfoij','2020-06-12',NULL,'jsaofj',24),(5,'randomName','cough',1,'sjfoij','2020-06-12',NULL,'jsaofj',25),(6,'flu','runny noes and dizzy',552,'patient has been moaning about feeling dizzy','2020-01-03',NULL,'jsaofj',26),(7,'flu','runny noes and dizzy',552,'patient has been moaning about feeling dizzy','2020-01-03',NULL,'jsaofj',27),(8,'flu','runny noes and dizzy',552,'patient has been moaning about feeling dizzy','2020-01-30',NULL,'jsaofj',28);
/*!40000 ALTER TABLE `patient_condition` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-02  9:14:45
