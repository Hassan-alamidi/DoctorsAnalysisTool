package com.MTPA.DAO;

import com.MTPA.Objects.Reports.PatientCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionDAO extends JpaRepository<PatientCondition, Integer> {
}
