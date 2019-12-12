package DAO;

import Objects.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorDAO extends JpaRepository<Doctor, Integer> {
}
