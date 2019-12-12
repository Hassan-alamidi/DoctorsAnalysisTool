package DAO;

import Objects.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureDAO extends JpaRepository<Procedure, Integer> {
}
