package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicationDAO extends JpaRepository<Medication, Integer> {

    @Query("SELECT m FROM Medication m WHERE m.patient.ppsn = ?1 ORDER BY m.treatmentStart DESC")
    List<Medication> getPatientMedicationHistory(String ppsn);

    @Query("SELECT m FROM Medication m WHERE (m.treatmentEnd > ?1 OR m.treatmentEnd IS NULL) AND m.patient.ppsn = ?2 AND m.type <> 'immunization' ORDER BY m.treatmentStart DESC")
    List<Medication> getCurrentPatientMedication(LocalDate currentDate, String ppsn);

    @Query("SELECT m FROM Medication m WHERE (m.treatmentEnd > ?1 OR m.treatmentEnd IS NULL) AND m.patient.ppsn = ?2 AND m.type = 'immunization' ORDER BY m.treatmentStart DESC")
    List<Medication> getPatientImmunizations(LocalDate currentDate, String ppsn);
}