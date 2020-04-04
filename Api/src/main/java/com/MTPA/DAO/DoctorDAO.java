package com.MTPA.DAO;

import com.MTPA.Objects.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDAO extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d FROM Doctor d WHERE d.medicalLicenceNumber = ?1")
    Doctor findByLicenceNumber(String licence);
}
