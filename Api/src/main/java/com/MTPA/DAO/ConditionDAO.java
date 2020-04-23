package com.MTPA.DAO;

import com.MTPA.Objects.Information.Condition;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.PatientCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConditionDAO extends JpaRepository<PatientCondition, Integer> {

    @Query("SELECT c FROM PatientCondition c WHERE c.patient.ppsn = ?1 ORDER BY c.discovered DESC")
    List<PatientCondition> findAllPatientConditions(String ppsn);

    @Query("SELECT c FROM PatientCondition c WHERE c.id = ?1")
    Optional<PatientCondition> findSpecificPatientCondition(int id);

    @Query("SELECT c FROM PatientCondition c WHERE c.curedOn IS NULL AND c.patient.ppsn = ?1 ORDER BY c.discovered DESC")
    List<PatientCondition> findPatientsOnGoingConditions(String ppsn);
}
