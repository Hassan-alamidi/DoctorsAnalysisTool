package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncounterDAO extends JpaRepository<Encounter, String> {

    @Query("SELECT e FROM Encounter e WHERE e.patient.ppsn = ?1 ORDER BY e.dateVisited DESC")
    List<Encounter> findAllEncountersOrderedByDate(final String ppsn);

    @Query("SELECT e FROM Encounter e WHERE e.patient.ppsn = ?1 ORDER BY e.dateVisited DESC")
    List<Encounter> findRecentEncountersOrderedByDate(final String ppsn, final Pageable pageable);

    @Query("SELECT e FROM Encounter e WHERE e.patient.ppsn = ?1 ORDER BY e.dateVisited DESC")
    Optional<Encounter> findLastEncounter(final String ppsn);

    @Query("SELECT e FROM Encounter e WHERE e.patient.ppsn = ?1 AND e.dateLeft IS NULL")
    List<Encounter> findOpenEncounters(final String ppsn);
}
