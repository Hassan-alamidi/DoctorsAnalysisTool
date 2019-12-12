package DAO;

import Objects.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationDAO extends JpaRepository<Observation, Integer> {
}
