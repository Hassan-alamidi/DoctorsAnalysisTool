package DAO;

import Objects.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterDAO extends JpaRepository<Encounter, Integer> {
}
