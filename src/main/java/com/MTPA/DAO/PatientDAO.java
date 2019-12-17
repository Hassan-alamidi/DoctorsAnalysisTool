package com.MTPA.DAO;

import com.MTPA.Objects.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDAO extends JpaRepository<Patient, Integer> {
}
