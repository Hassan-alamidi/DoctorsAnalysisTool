package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationDAO extends JpaRepository<PatientObservation, Integer> {
}
