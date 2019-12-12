package DAO;

import Objects.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentPlanDAO extends JpaRepository<TreatmentPlan, Integer> {
}
