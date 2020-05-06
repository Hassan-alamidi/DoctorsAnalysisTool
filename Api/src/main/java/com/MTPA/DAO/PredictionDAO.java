package com.MTPA.DAO;

import com.MTPA.Objects.Reports.Prediction;
import com.MTPA.Objects.Reports.Procedure;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public interface PredictionDAO extends JpaRepository<Prediction, Integer> {

    @Query("SELECT pre FROM Prediction pre WHERE pre.ppsn = ?1 ORDER BY pre.dateGenerated DESC")
    List<Prediction> getPatientAllPredictionReports(String ppsn);
}
