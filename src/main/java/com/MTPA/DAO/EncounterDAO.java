package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface EncounterDAO extends JpaRepository<Encounter, Integer> {

    @Query("SELECT e FROM Encounter e JOIN e.patient p WHERE p.ppsn = ?1 ORDER BY e.dateVisited DESC")
    List<Encounter> findAllEncountersOrderedByDate(final String ppsn);


    @Query("SELECT e FROM Encounter e JOIN e.patient p WHERE p.ppsn = ?1 ORDER BY e.dateVisited DESC")
    List<Encounter> findRecentEncountersOrderedByDate(final String ppsn, final Pageable pageable);

    @Query("SELECT e FROM Encounter e JOIN e.patient p WHERE p.ppsn = ?1")
    List<Encounter> findAllPatientEncounter(final String ppsn);
}
