package com.MTPA.DAO;

import com.MTPA.Objects.Reports.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentPlanDAO extends JpaRepository<TreatmentPlan, Integer> {
}
