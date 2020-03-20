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
-- Table structure for table `patient_observation`
--

DROP TABLE IF EXISTS `patient_observation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_observation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(200) NOT NULL,
  `description` longtext NOT NULL,
  `date_taken` date NOT NULL,
  `encounter_id` int(11) NOT NULL,
  `result_value` varchar(45) NOT NULL,
  `result_unit` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_observation`
--

LOCK TABLES `patient_observation` WRITE;
/*!40000 ALTER TABLE `patient_observation` DISABLE KEYS */;
INSERT INTO `patient_observation` VALUES (1,'ECG','checking heart rate due to concerns','2020-01-12',4,'160','BPM'),(2,'ECG','checking heart rate due to concerns','2020-01-12',5,'160','BPM'),(3,'ECG','checking heart rate due to concerns','2020-01-12',6,'160','BPM'),(4,'ECG','checking heart rate due to concerns','2020-01-14',8,'160','BPM'),(5,'ECG','checking heart rate due to concerns','2020-01-14',11,'160','BPM'),(6,'ECG','checking heart rate due to concerns','2020-01-14',12,'160','BPM'),(7,'ECG','checking heart rate due to concerns','2020-01-14',13,'160','BPM'),(10,'ECG','checking heart rate due to concerns','2020-01-14',16,'160','BPM'),(11,'ECG','checking heart rate due to concerns','2020-01-14',18,'160','BPM'),(12,'ECG','checking heart rate due to concerns','2020-01-14',19,'160','BPM'),(13,'ECG','checking heart rate due to concerns','2020-01-14',20,'160','BPM'),(14,'ECG','checking heart rate due to concerns','2020-01-14',21,'160','BPM'),(15,'ECG','checking heart rate due to concerns','2020-01-14',22,'160','BPM'),(16,'ECG','checking heart rate due to concerns','2020-01-14',23,'160','BPM'),(17,'ECG','checking heart rate due to concerns','2020-01-14',24,'160','BPM'),(18,'ECG','checking heart rate due to concerns','2020-01-14',25,'160','BPM'),(19,'ECG','checking heart rate due to concerns','2020-01-14',26,'160','BPM'),(20,'ECG','checking heart rate due to concerns','2020-01-14',27,'160','BPM'),(21,'ECG','checking heart rate due to concerns','2020-01-14',28,'160','BPM');
/*!40000 ALTER TABLE `patient_observation` ENABLE KEYS */;
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
