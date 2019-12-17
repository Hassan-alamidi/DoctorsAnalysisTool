package com.MTPA.DAO;

import com.MTPA.Objects.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorDAO extends JpaRepository<Doctor, Integer> {
}
