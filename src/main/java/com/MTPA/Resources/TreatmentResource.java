package com.MTPA.Resources;

import com.MTPA.Objects.Reports.TreatmentPlan;
import com.MTPA.Services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/treatment")
public class TreatmentResource {

    private TreatmentService treatmentService;

    @Autowired
    public TreatmentResource(TreatmentService treatmentService){
        this.treatmentService = treatmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPatientTreatments(@RequestHeader("PPSN") final String ppsn){
        return treatmentService.getAllTreatments(ppsn);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getAllOnGoingPatientTreatments(@RequestHeader("PPSN") final String ppsn){
        return treatmentService.getAllOnGoingTreatments(ppsn);
    }

    @GetMapping("/finished")
    public ResponseEntity<?> getAllCompletedPatientTreatments(@RequestHeader("PPSN") final String ppsn){
        return treatmentService.getAllCompletedTreatments(ppsn);
    }

    @PostMapping
    public ResponseEntity<TreatmentPlan> createTreatmentPlan(@RequestBody final TreatmentPlan treatmentPlan){
        return treatmentService.createTreatmentPlan(treatmentPlan);
    }

    @PutMapping
    public ResponseEntity<?> updateTreatmentPlan(@RequestBody final TreatmentPlan treatmentPlan){
        return treatmentService.updateTreatmentPlan(treatmentPlan);
    }
}
