package DAO;

import Objects.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationDAO extends JpaRepository<Medication, Integer> {
}
