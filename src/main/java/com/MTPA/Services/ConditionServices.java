package com.MTPA.Services;

import com.MTPA.DAO.ConditionDAO;
import com.MTPA.Objects.Information.Condition;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.PatientCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionServices {

    ConditionDAO conditionDAO;

    @Autowired
    public ConditionServices(ConditionDAO conditionDAO){
        this.conditionDAO = conditionDAO;
    }

    public List<PatientCondition> getAllPatientConditions(String ppsn){
        return conditionDAO.findAllPatientConditions(ppsn);
    }

    public PatientCondition getPatientCondition(int id){
        return conditionDAO.findSpecificPatientCondition(id);
    }

    public PatientCondition addPatientCondition(PatientCondition condition){
        conditionDAO.save(condition);
        return condition;
    }
}
