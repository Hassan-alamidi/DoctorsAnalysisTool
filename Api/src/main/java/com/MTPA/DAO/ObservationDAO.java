package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientObservation;
import com.MTPA.Objects.Reports.PatientProcedure;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationDAO extends JpaRepository<PatientObservation, Integer> {

    @Query("SELECT ob FROM PatientObservation ob WHERE ob.patient.ppsn = ?1 ORDER BY ob.dateTaken DESC")
    List<PatientObservation> getPatientObservationHistory(String ppsn);

    @Query("SELECT ob FROM PatientObservation ob WHERE ob.patient.ppsn = ?1 ORDER BY ob.dateTaken DESC")
    List<PatientObservation> findRecentObservationsOrderedByDate(final String ppsn, final Pageable pageable);
}
