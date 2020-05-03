package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Observation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationDAO extends JpaRepository<Observation, Integer> {

    @Query("SELECT ob FROM Observation ob WHERE ob.patient.ppsn = ?1 ORDER BY ob.dateTaken DESC")
    List<Observation> getPatientObservationHistory(String ppsn);

    @Query("SELECT ob FROM Observation ob WHERE ob.patient.ppsn = ?1 ORDER BY ob.dateTaken DESC")
    List<Observation> findRecentObservationsOrderedByDate(final String ppsn, final Pageable pageable);
}
