package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterDAO extends JpaRepository<Encounter, Integer> {
}
