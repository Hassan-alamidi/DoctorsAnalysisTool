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
('num1','testDoc','testie','1991-04-13','jsaof234j','123 easy street','Admin','$2a$10$X/1F8NfO3kEtY9jqmhI2T.DoX2ScfdLhf.B71sYuwfyf2HINl8rhe',819283,1),
('num31','testDoc','testie','1991-04-13','jsaofsfee234j','123 easy street','User','$2a$10$SaZQmxR7DSPXDokK2DXDrufdNe9AmJWrHm38XZoQ9tIIj..vsaiWC',81923483,1),
('num331','testDoc','testie','1991-04-13','jsaofsfsfee234j','123 easy street','User','$2a$10$o5F4YroB4yLQKIRZmMdrDe.XiHEI3I0wU6EdeLGXuWmHbqe.yqmyu',34323483,1);
