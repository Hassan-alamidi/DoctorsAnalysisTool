package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationDAO extends JpaRepository<PatientObservation, Integer> {
}
