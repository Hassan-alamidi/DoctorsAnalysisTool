package com.MTPA.Services;

import com.MTPA.DAO.ConditionDAO;
import com.MTPA.DAO.PatientDAO;
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
        if(conditionDAO.existsById(id)){
            return new ResponseEntity<>(conditionDAO.findSpecificPatientCondition(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<PatientCondition>> getOnGoingPatientConditions(String ppsn){
        return new ResponseEntity<>(conditionDAO.findPatientsOnGoingConditions(ppsn), HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public ResponseEntity<?> addPatientCondition(PatientCondition condition){
        if(condition.getPatient() != null && patientDAO.exists(condition.getPatient().getPpsn())){
            try {
                conditionDAO.save(condition);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            return new ResponseEntity<>(condition, HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }
}
