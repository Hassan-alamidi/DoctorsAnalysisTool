---
--- Create Patient Data
---

INSERT INTO patient (first_name, last_name, dob, dod, ppsn, address, gender) VALUES
('Test', 'Subject', TO_DATE('1990-10-19', 'yyyy-MM-dd'), null, '87937M', 'Our Lab', 'Male'),
('Test', 'Subject2', TO_DATE('1990-10-19', 'yyyy-MM-dd'), null, '87937N', 'Our Lab', 'Female');


---
--- Create Doctor Data
---

INSERT INTO doctor (medical_licence_number, first_name, last_name, dob, ppsn, address, privilege_level, password, phone_number) VALUES
('num1','testDoc','testie','1991-04-13','jsaof234j','123 easy street','Admin','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',819283),
('num31','testDoc','testie','1991-04-13','jsaofsfee234j','123 easy street','User','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',81923483),
('num331','testDoc','testie','1991-04-13','jsaofsfsfee234j','123 easy street','User','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',34323483),
('num4','Default','Pass','1991-04-13','StopForGettingToChangeUnique','123 easy street','User','$2a$10$N2mxboOvsAn56DA8Q.OeJ.ibE7UlRmQnLgbVvqZSj6YY8AqV.kdDq',0871736482);

---
--- Create Encounter Data
---

INSERT INTO `encounter` (id, type, date_visited, date_left, patient_ppsn, description) VALUES
('a','checkup','2020-01-12',NULL,'87937M','any val'),
('b','checkup','2020-01-13',NULL,'87937M','any val'),
('c','checkup','2020-01-14',NULL,'87937M','any val'),
('d','checkup','2020-01-15',NULL,'87937M','any val'),
('e','checkup','2020-01-16',NULL,'87937M','any val'),
('f','checkup','2020-01-17',NULL,'87937M','any val'),
('g','checkup','2020-01-18',NULL,'87937M','any val'),
('h','checkup','2020-01-19',NULL,'87937M','any val'),
('i','checkup','2020-01-20',NULL,'87937M','any val'),
('j','checkup','2020-01-21',NULL,'87937M','any val'),
('k','checkup','2020-01-22',NULL,'87937M','any val');

---
--- Create Patient Observations
---

INSERT INTO `patient_observation` (type, date_taken, encounter_id, result_value, result_unit, patient_ppsn) VALUES
('ECG','2020-01-12','a','160','BPM','87937M');

INSERT INTO `patient_condition` (id,description,code,discovered,cured_on,patient_ppsn,encounter_id,type) VALUES
(1,'Streptococcal sore throat (disorder)','43878008','2008-06-14','2008-06-27','87937M','a','condition'),
(2,'Streptococcal sore throat (disorder)','43878008','2008-06-14','2008-06-27','87937M','a','condition'),
(3,'Streptococcal sore throat (disorder)','43878008','2008-06-14','2008-06-27','87937M','a','condition'),
(4,'Atopic dermatitis','43878008','2008-06-14',null,'87937M','b','condition'),
(5,'Atopic dermatitis','43878008','2008-06-14',null,'87937M','b','condition'),
(6,'Atopic dermatitis','43878008','2008-06-14',null,'87937M','b','condition');

INSERT INTO `patient_medication` (id,description,code,treatment_start,treatment_end,patient_ppsn,encounter_id,type) VALUES
(1,'Influenza  seasonal  injectable  preservative free','43878008','2008-06-14','2008-06-14','87937M','a','Immunization'),
(2,'Ibuprofen 100 MG Oral Tablet','43878008','2008-06-14',null,'87937M','a','Medication'),
(3,'Ibuprofen 100 MG Oral Tablet','43878008','2008-06-14',null,'87937M','a','Medication'),
(4,'Ibuprofen 100 MG Oral Tablet','43878008','2008-06-14','2008-06-14','87937M','a','Medication');