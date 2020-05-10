package com.MTPA.Services;

import com.MTPA.DAO.ConditionDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Reports.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConditionServices {

    private final ConditionDAO conditionDAO;
    private final PatientDAO patientDAO;

    @Autowired
    public ConditionServices(final ConditionDAO conditionDAO, final PatientDAO patientDAO){
        this.conditionDAO = conditionDAO;
        this.patientDAO = patientDAO;
    }

    public ResponseEntity<List<Condition>> getAllPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findAllPatientConditions(ppsn), HttpStatus.OK);
    }

    public ResponseEntity<Condition> getPatientCondition(int id){
        return conditionDAO.findSpecificPatientCondition(id)
                .map(condition -> new ResponseEntity<>(condition, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Condition>> getOnGoingPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findPatientsOnGoingConditions(ppsn), HttpStatus.OK);
    }

    public ResponseEntity<?> addPatientCondition(Condition condition){
        if(condition.getPatient() != null && patientDAO.exists(condition.getPatient().getPpsn())){
            return new ResponseEntity<>(conditionDAO.save(condition), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deletePatientCondition(final int id){
        try {
            conditionDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException | EmptyResultDataAccessException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> markAsCured(final int id){
        Optional<Condition> optCondition = conditionDAO.findSpecificPatientCondition(id);
        if(optCondition.isPresent()){
            Condition condition = optCondition.get();
            condition.setCuredOn(LocalDate.now());
            conditionDAO.save(condition);
            return new ResponseEntity<>(condition, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updatePatientCondition(Condition newCondition){
        //get the original condition and ensure only certain details can be modified
        Optional<Condition> originalCondition = conditionDAO.findSpecificPatientCondition(newCondition.getId());
        if(originalCondition.isPresent()){
            Condition condition = originalCondition.get();
            condition.setSymptoms(newCondition.getSymptoms());
            condition.setDetails(newCondition.getDetails());
            condition.setCuredOn(newCondition.getCuredOn());
            return new ResponseEntity<>(conditionDAO.save(condition), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
