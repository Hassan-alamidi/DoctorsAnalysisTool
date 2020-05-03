package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Procedure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcedureDAO extends JpaRepository<Procedure, Integer> {

    @Query("SELECT pro FROM Procedure pro WHERE pro.patient.ppsn = ?1 ORDER BY pro.carriedOutOn DESC")
    List<Procedure> getPatientProcedureHistory(String ppsn);

    @Query("SELECT pro FROM Procedure pro WHERE pro.patient.ppsn = ?1 ORDER BY pro.carriedOutOn DESC")
    List<Procedure> findRecentProcedureOrderedByDate(final String ppsn, final Pageable pageable);
}
