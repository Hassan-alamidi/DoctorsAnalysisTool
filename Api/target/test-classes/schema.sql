CREATE SCHEMA  IF NOT EXISTS `MTPA` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `MTPA`;
-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: MTPA
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

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
-- Table structure for table `condition`
--

DROP TABLE IF EXISTS `condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condition` (
  `condition_code` int(11) NOT NULL PRIMARY KEY,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` longtext NOT NULL,
  `common_causes` longtext NOT NULL,
  `common_symptoms` longtext NOT NULL,
  `can_progress_to` int(11) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

ALTER TABLE `condition` ADD CONSTRAINT condition_unique_cols UNIQUE ( condition_code, name );

--
-- Table structure for table `condition_medication`
--

DROP TABLE IF EXISTS `condition_medication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condition_medication` (
  `condition_id` int(11) NOT NULL,
  `medication_id` int(11) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `condition_procedure`
--

DROP TABLE IF EXISTS `condition_procedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condition_procedure` (
  `condition_id` int(11) NOT NULL,
  `procedures_id` int(11) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `encounter`
--

DROP TABLE IF EXISTS `encounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encounter` (
  `id` int(11) NOT NULL,
  `type` varchar(200) NOT NULL,
  `date_visited` date NOT NULL,
  `date_left` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `organization_id` int(11) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medication`
--

DROP TABLE IF EXISTS `medication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medication` (
  `medication_code` int(11) NOT NULL PRIMARY KEY,
  `name` varchar(200) NOT NULL,
  `type` varchar(200) NOT NULL,
  `description` longtext NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

ALTER TABLE `medication` ADD CONSTRAINT medication_unique_cols UNIQUE ( medication_code, name );

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `first_name` varchar(45) NOT NULL,
  `dob` date NOT NULL,
  `dod` date,
  `ppsn` varchar(45) NOT NULL,
  `address` varchar(200) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

ALTER TABLE `patient` ADD CONSTRAINT patient_unique_cols UNIQUE ( ppsn );
--
-- Table structure for table `patient_condition`
--

DROP TABLE IF EXISTS `patient_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `description` varchar(200) NOT NULL,
  `symptoms` varchar(200) NOT NULL,
  `code` varchar(45) NOT NULL,
  `details` varchar(200) NULL,
  `discovered` date NOT NULL,
  `cured_on` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `encounter_id` varchar(200) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_medication`
--

DROP TABLE IF EXISTS `patient_medication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_medication` (
  `id` int(11) NOT NULL,
  `type` varchar(200) NOT NULL,
  `treatment_start` date NOT NULL,
  `treatment_end` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `description` longtext NOT NULL,
  `reason_code` varchar(45) NOT NULL,
  `prescribed_amount` int(11) NOT NULL,
  `code` varchar(45) NULL,
  `reason_description` varchar(200) NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_observation`
--

DROP TABLE IF EXISTS `patient_observation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_observation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(200) NOT NULL,
  `date_taken` date NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `result_value` varchar(45) NOT NULL,
  `result_unit` varchar(45) NOT NULL,
  `code` varchar(45) NULL,
  `patient_ppsn` varchar(200) NOT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_procedure`
--

DROP TABLE IF EXISTS `patient_procedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_procedure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `description` varchar(200) NOT NULL,
  `carried_out_on` date NOT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `details` varchar(200) NULL,
  `reason_description` varchar(200) NULL,
  `reason_code` varchar(45) NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `procedure`
--

--DROP TABLE IF EXISTS `procedure`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `procedure` (
--  `procedure_code` int(11) NOT NULL,
--  `name` varchar(200) NOT NULL,
--  `type` varchar(200) NOT NULL,
--  `description` longtext NOT NULL
--);
--/*!40101 SET character_set_client = @saved_cs_client */;
--
--ALTER TABLE `procedure` ADD CONSTRAINT procedure_unique_cols UNIQUE ( procedure_code, name );

--
-- Table structure for table `treatment_plan`
--

DROP TABLE IF EXISTS `treatment_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `treatment_plan` (
  `id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL,
  `code` varchar(45) NOT NULL,
  `reason_code` varchar(45) NULL,
  `reason_description` varchar(200) NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `medical_licence_number` varchar(200) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `dob` date NOT NULL,
  `ppsn` varchar(45) NOT NULL,
  `address` varchar(200) NOT NULL,
  `privilege_level` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `phone_number` int(11) NOT NULL
);

/*!40101 SET character_set_client = @saved_cs_client */;

ALTER TABLE `doctor` ADD CONSTRAINT doctor_unique_cols UNIQUE ( ppsn, medical_licence_number, phone_number );

--
-- Table structure for table `encounter`
--

DROP TABLE IF EXISTS `encounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `encounter` (
  `id` varchar(200) NOT NULL PRIMARY KEY,
  `type` varchar(200) NOT NULL,
  `date_visited` date NOT NULL,
  `date_left` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL,
  `reason_description` varchar(200) NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;



/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;