package com.MTPA.DAO;

import com.MTPA.Objects.Reports.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentPlanDAO extends JpaRepository<TreatmentPlan, Integer> {

    @Query("SELECT t FROM TreatmentPlan t WHERE t.patientPPSN = ?1 ORDER BY t.startDate DESC")
    List<TreatmentPlan> getAllTreatments(final String ppsn);

    @Query("SELECT t FROM TreatmentPlan t WHERE t.patientPPSN = ?1 AND (t.endDate > CURRENT_DATE() OR t.endDate IS NULL)")
    List<TreatmentPlan> getAllOnGoingTreatments(final String ppsn);

    @Query("SELECT t FROM TreatmentPlan t WHERE t.patientPPSN = ?1 AND t.endDate < CURRENT_DATE()")
    List<TreatmentPlan> getAllCompletedTreatments(final String ppsn);
}
