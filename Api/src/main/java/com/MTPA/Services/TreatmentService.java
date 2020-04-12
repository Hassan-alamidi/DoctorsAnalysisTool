package com.MTPA.Services;

import com.MTPA.DAO.PatientDAO;
import com.MTPA.DAO.TreatmentPlanDAO;
import com.MTPA.Objects.Reports.TreatmentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    private final TreatmentPlanDAO treatmentPlanDAO;
    private final PatientDAO patientDAO;

    @Autowired
    public TreatmentService(final TreatmentPlanDAO treatmentPlanDAO, final PatientDAO patientDAO){
        this.treatmentPlanDAO = treatmentPlanDAO;
        this.patientDAO = patientDAO;
    }

    public ResponseEntity<?> createTreatmentPlan(final TreatmentPlan treatmentPlan){
        if(treatmentPlan.getPatient() != null && patientDAO.exists(treatmentPlan.getPatient().getPpsn())) {
            return new ResponseEntity<>(treatmentPlanDAO.save(treatmentPlan), HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateTreatmentPlan(final TreatmentPlan treatmentPlan){
        Optional<TreatmentPlan> originalTreatmentOpt = treatmentPlanDAO.findById(treatmentPlan.getId());
        if(originalTreatmentOpt.isPresent()) {
            //the treatment date and description are the only values the doctor can change otherwise the doctor must close this treatment and create a new one
            Optional<TreatmentPlan> planWithRestrictions = originalTreatmentOpt.map(t -> {
                t.setEndDate(treatmentPlan.getEndDate());
                t.setDescription(treatmentPlan.getDescription());
                return t;
            });
            return new ResponseEntity<>(treatmentPlanDAO.save(planWithRestrictions.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Plan does not exist", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<TreatmentPlan>> getAllTreatments(final String ppsn){
        List<TreatmentPlan> treatmentPlans = treatmentPlanDAO.getAllTreatments(ppsn);
        return new ResponseEntity<>(treatmentPlans, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllOnGoingTreatments(final String ppsn){
        List<TreatmentPlan> treatmentPlans = treatmentPlanDAO.getAllOnGoingTreatments(ppsn);
        return new ResponseEntity<>(treatmentPlans, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCompletedTreatments(final String ppsn){
        List<TreatmentPlan> treatmentPlans = treatmentPlanDAO.getAllCompletedTreatments(ppsn);
        return new ResponseEntity<>(treatmentPlans, HttpStatus.OK);
    }
}
