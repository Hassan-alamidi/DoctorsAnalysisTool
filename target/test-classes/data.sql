---
--- Create Patient Data
---

INSERT INTO patient (first_name, last_name, dob, ppsn, address, mother_ppsn, father_ppsn) VALUES
('Test', 'Subject', TO_DATE('1990-10-19', 'yyyy-MM-dd'), '87937M', 'Our Lab', 'UNKNOWN', 'UNKNOWN');