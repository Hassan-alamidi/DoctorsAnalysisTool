package DAO;

import Objects.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionDAO extends JpaRepository<Condition, Integer> {
}
