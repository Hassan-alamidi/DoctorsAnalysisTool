package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Condition;
import com.MTPA.Services.ConditionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conditions")
public class ConditionResource {

    private final ConditionServices conditionServices;

    @Autowired
    public ConditionResource(final ConditionServices conditionServices){
        this.conditionServices = conditionServices;
    }

    //this should only have basic detail as it will be used to display as a list
    @GetMapping
    public ResponseEntity<List<Condition>> getAllPatientConditions(@RequestHeader("ppsn") final String ppsn){
        return conditionServices.getAllPatientConditions(ppsn);
    }

    @GetMapping("/current")
    public ResponseEntity<List<Condition>> getOnGoingConditions(@RequestHeader("ppsn") final String ppsn){
        return conditionServices.getOnGoingPatientConditions(ppsn);
    }

    @PutMapping("current/cured/{id}")
    public ResponseEntity<?> markConditionAsCured(@PathVariable("id") final int id){
        return conditionServices.markAsCured(id);
    }

    //this will give a more detailed view of a patients conditions (id is retrieved after selecting from list on page)
    @GetMapping("/{id}")
    public ResponseEntity<Condition> getPatientCondition(@PathVariable("id") int id){
        return conditionServices.getPatientCondition(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientCondition(@PathVariable("id") final int id){
        return conditionServices.deletePatientCondition(id);
    }

    @PostMapping
    public ResponseEntity<?> addPatientCondition(@RequestBody Condition condition){
        return conditionServices.addPatientCondition(condition);
    }

    @PutMapping
    public ResponseEntity<?> updatePatientCondition(@RequestBody final Condition condition){
        return conditionServices.updatePatientCondition(condition);
    }
}
