package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientMedication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationDAO extends JpaRepository<PatientMedication, Integer> {
}
