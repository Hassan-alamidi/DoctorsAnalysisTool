
CREATE TABLE IF NOT EXISTS `encounter` (
  `id` varchar(200) NOT NULL PRIMARY KEY,
  `type` varchar(200) NOT NULL,
  `date_visited` date NOT NULL,
  `date_left` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL,
  `reason_description` varchar(200) NULL
);


--
-- Table structure for table `patient`
--

CREATE TABLE IF NOT EXISTS `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `first_name` varchar(45) NOT NULL,
  `dob` date NOT NULL,
  `dod` date NULL,
  `ppsn` varchar(45) NOT NULL,
  `address` varchar(200) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL
);

ALTER TABLE `patient` ADD CONSTRAINT patient_unique_cols UNIQUE ( ppsn );
--
-- Table structure for table `patient_condition`
--

CREATE TABLE IF NOT EXISTS `patient_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(200) NOT NULL,
  `symptoms` varchar(200) NULL,
  `code` varchar(45) NOT NULL,
  `details` varchar(200) NULL,
  `discovered` date NOT NULL,
  `cured_on` date NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `type` varchar(45) NOT NULL
);

--
-- Table structure for table `patient_medication`
--

CREATE TABLE IF NOT EXISTS `patient_medication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(200) NOT NULL,
  `treatment_start` date NOT NULL,
  `treatment_end` date DEFAULT NULL,
  `patient_ppsn` varchar(200) NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `description` longtext NOT NULL,
  `reason_code` varchar(45) NULL,
  `prescribed_amount` int(11) NULL,
  `code` varchar(45) NULL,
  `reason_description` varchar(200) NULL
);

--
-- Table structure for table `patient_observation`
--

CREATE TABLE IF NOT EXISTS `patient_observation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(200) NOT NULL,
  `date_taken` date NOT NULL,
  `encounter_id` varchar(200) NOT NULL,
  `result_value` varchar(45) NOT NULL,
  `result_unit` varchar(45) NOT NULL,
  `code` varchar(45) NULL,
  `patient_ppsn` varchar(200) NOT NULL
);

--
-- Table structure for table `patient_procedure`
--

CREATE TABLE IF NOT EXISTS `patient_procedure` (
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
--
-- Table structure for table `treatment_plan`
--

CREATE TABLE IF NOT EXISTS `treatment_plan` (
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

--
-- Table structure for table `doctor`
--

CREATE TABLE IF NOT EXISTS `doctor` (
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


ALTER TABLE `doctor` ADD CONSTRAINT doctor_unique_cols UNIQUE ( ppsn, medical_licence_number, phone_number );
