package com.MTPA.DAO;

import com.MTPA.Objects.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDAO extends JpaRepository<Patient, Integer> {

    @Query("SELECT p FROM Patient p WHERE p.ppsn = ?1")
    Patient findByPPSN(String ppsn);

    @Query("SELECT CASE WHEN count(p) > 0 THEN true else false END FROM Patient p WHERE p.ppsn = ?1")
    boolean exists(String ppsn);
}
