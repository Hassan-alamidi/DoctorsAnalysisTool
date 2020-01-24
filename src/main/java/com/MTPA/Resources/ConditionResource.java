package com.MTPA.Resources;

import com.MTPA.Objects.Reports.PatientCondition;
import com.MTPA.Services.ConditionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conditions")
public class ConditionResource {

    ConditionServices conditionServices;

    @Autowired
    public ConditionResource(ConditionServices conditionServices){
        this.conditionServices = conditionServices;
    }

    //this should only have basic detail as it will be used to display as a list
    @GetMapping
    public List<PatientCondition> getAllPatientConditions(@RequestHeader("PPSN") String ppsn){
        return conditionServices.getAllPatientConditions(ppsn);
    }

    //this will give a more detailed view of a patients conditions (id is retrieved after selecting from list on page)
    @GetMapping("/{id}")
    public PatientCondition getPatientCondition(@RequestParam("id") int id){
        return conditionServices.getPatientCondition(id);
    }

    @PostMapping
    public PatientCondition addPatientCondition(@RequestBody PatientCondition condition){
        return conditionServices.addPatientCondition(condition);
    }

}
