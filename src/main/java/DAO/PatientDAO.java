package DAO;

import Objects.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDAO extends JpaRepository<Patient, Integer> {
}
