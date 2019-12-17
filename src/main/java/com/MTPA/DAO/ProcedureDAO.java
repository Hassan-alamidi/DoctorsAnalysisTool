package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureDAO extends JpaRepository<PatientProcedure, Integer> {
}
