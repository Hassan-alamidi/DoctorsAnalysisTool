package com.MTPA.Services;

import com.MTPA.DAO.ConditionDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.PatientCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ConditionServices {

    private final ConditionDAO conditionDAO;
    private final PatientDAO patientDAO;

    @Autowired
    public ConditionServices(ConditionDAO conditionDAO, PatientDAO patientDAO){
        this.conditionDAO = conditionDAO;
        this.patientDAO = patientDAO;
    }

    public ResponseEntity<List<PatientCondition>> getAllPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findAllPatientConditions(ppsn), HttpStatus.OK);
    }

    public ResponseEntity<PatientCondition> getPatientCondition(int id){
        return conditionDAO.findSpecificPatientCondition(id)
                .map(condition -> new ResponseEntity<>(condition, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<PatientCondition>> getOnGoingPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findPatientsOnGoingConditions(ppsn), HttpStatus.OK);
    }

    public ResponseEntity<?> addPatientCondition(PatientCondition condition){
        if(condition.getPatient() != null && patientDAO.exists(condition.getPatient().getPpsn())){
            return new ResponseEntity<>(conditionDAO.save(condition), HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updatePatientCondition(PatientCondition newCondition){
        //get the original condition and ensure only certain details can be modified
        Optional<PatientCondition> originalCondition = conditionDAO.findSpecificPatientCondition(newCondition.getId());
        if(originalCondition.isPresent()){
            PatientCondition condition = originalCondition.get();
            condition.setSymptoms(newCondition.getSymptoms());
            condition.setDetails(newCondition.getDetails());
            condition.setCuredOn(newCondition.getCuredOn());
            return new ResponseEntity<>(conditionDAO.save(condition), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
