package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConditionDAO extends JpaRepository<Condition, Integer> {

    @Query("SELECT c FROM Condition c WHERE c.patient.ppsn = ?1 ORDER BY c.discovered DESC")
    List<Condition> findAllPatientConditions(String ppsn);

    @Query("SELECT c FROM Condition c WHERE c.id = ?1")
    Optional<Condition> findSpecificPatientCondition(int id);

    @Query("SELECT c FROM Condition c WHERE c.curedOn IS NULL AND c.patient.ppsn = ?1 ORDER BY c.discovered DESC")
    List<Condition> findPatientsOnGoingConditions(String ppsn);
}
