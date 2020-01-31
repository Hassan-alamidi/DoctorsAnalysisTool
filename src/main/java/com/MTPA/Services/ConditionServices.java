package com.MTPA.Services;

import com.MTPA.DAO.ConditionDAO;
import com.MTPA.Objects.Reports.PatientCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionServices {

    ConditionDAO conditionDAO;

    @Autowired
    public ConditionServices(ConditionDAO conditionDAO){
        this.conditionDAO = conditionDAO;
    }

    public ResponseEntity<List<PatientCondition>> getAllPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findAllPatientConditions(ppsn), HttpStatus.OK);
    }

    public ResponseEntity<PatientCondition> getPatientCondition(int id){
        return new ResponseEntity<>(conditionDAO.findSpecificPatientCondition(id), HttpStatus.OK);
    }

    public ResponseEntity<List<PatientCondition>> getOnGoingPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findPatientsOnGoingConditions(ppsn), HttpStatus.OK);
    }

    public ResponseEntity<PatientCondition> addPatientCondition(PatientCondition condition){
        conditionDAO.save(condition);
        return new ResponseEntity<>(condition, HttpStatus.OK);
    }
}
