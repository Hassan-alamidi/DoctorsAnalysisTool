package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MedicationDAO extends JpaRepository<PatientMedication, Integer> {

    @Query("SELECT m FROM PatientMedication m WHERE m.patient.ppsn = ?1 ORDER BY m.treatmentStart DESC")
    List<PatientMedication> getPatientMedicationHistory(String ppsn);

    @Query("SELECT m FROM PatientMedication m WHERE (m.treatmentEnd > ?1 OR m.treatmentEnd IS NULL) AND m.patient.ppsn = ?2 AND m.type <> 'immunization' ORDER BY m.treatmentStart DESC")
    List<PatientMedication> getCurrentPatientMedication(LocalDate currentDate, String ppsn);

    @Query("SELECT m FROM PatientMedication m WHERE (m.treatmentEnd > ?1 OR m.treatmentEnd IS NULL) AND m.patient.ppsn = ?2 AND m.type = 'immunization' ORDER BY m.treatmentStart DESC")
    List<PatientMedication> getPatientImmunizations(LocalDate currentDate, String ppsn);
}