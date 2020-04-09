package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientProcedure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureDAO extends JpaRepository<PatientProcedure, Integer> {

    @Query("SELECT pro FROM PatientProcedure pro  WHERE pro.patient.PPSN = ?1 ORDER BY pro.carriedOutOn DESC")
    List<PatientProcedure> getPatientProcedureHistory(String ppsn);

    @Query("SELECT pro FROM PatientProcedure pro WHERE pro.patient.PPSN = ?1 ORDER BY pro.carriedOutOn DESC")
    List<PatientProcedure> findRecentProcedureOrderedByDate(final String ppsn, final Pageable pageable);
}
