---
--- Create Patient Data
---

INSERT INTO patient (first_name, last_name, dob, ppsn, address, mother_ppsn, father_ppsn) VALUES
('Test', 'Subject', TO_DATE('1990-10-19', 'yyyy-MM-dd'), '87937M', 'Our Lab', 'UNKNOWN', 'UNKNOWN');

---
--- Create Organization Data
---

INSERT INTO organization (id, name,address,phone_number) VALUES
(1, 'testHospt','pain in the ass',9774);


---
--- Create Doctor Data
---

INSERT INTO doctor (medical_licence_number, first_name, last_name, dob, ppsn, address, privilege_level, password, phone_number, organization_id) VALUES
('num1','testDoc','testie','1991-04-13','jsaof234j','123 easy street','Admin','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',819283,1),
('num31','testDoc','testie','1991-04-13','jsaofsfee234j','123 easy street','User','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',81923483,1),
('num331','testDoc','testie','1991-04-13','jsaofsfsfee234j','123 easy street','User','$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u',34323483,1),
('num4','Default','Pass','1991-04-13','StopForGettingToChangeUnique','123 easy street','User','$2a$10$N2mxboOvsAn56DA8Q.OeJ.ibE7UlRmQnLgbVvqZSj6YY8AqV.kdDq',0871736482,1);

---
--- Create Encounter Data
---

INSERT INTO `encounter` (type, date_visited, date_left, patient_ppsn, organization_id) VALUES
('checkup','2020-01-12',NULL,'87937M',1);

---
--- Create Patient Observations
---

INSERT INTO `patient_observation` (type, description, date_taken, encounter_id, result_value, result_unit) VALUES
('ECG','checking heart rate due to concerns','2020-01-12',1,'160','BPM');