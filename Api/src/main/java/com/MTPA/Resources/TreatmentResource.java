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
    public ResponseEntity<?> getAllTreatments(@RequestHeader("PPSN") final String ppsn){
        return treatmentService.getAllTreatments(ppsn);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getAllOnGoingTreatments(@RequestHeader("PPSN") final String ppsn){
        return treatmentService.getAllOnGoingTreatments(ppsn);
    }

    @PutMapping("current/end/{id}")
    public ResponseEntity<?> markTreatmentAsEnded(@PathVariable("id") final int id){
        return treatmentService.markAsEnded(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTreatment(@PathVariable("id") final int id){
        return treatmentService.deleteTreatment(id);
    }

    @GetMapping("/finished")
    public ResponseEntity<?> getAllCompletedTreatments(@RequestHeader("PPSN") final String ppsn){
        return treatmentService.getAllCompletedTreatments(ppsn);
    }

    @PostMapping
    public ResponseEntity<?> createTreatmentPlan(@RequestBody final TreatmentPlan treatmentPlan){
        return treatmentService.createTreatmentPlan(treatmentPlan);
    }

    @PutMapping
    public ResponseEntity<?> updateTreatmentPlan(@RequestBody final TreatmentPlan treatmentPlan){
        return treatmentService.updateTreatmentPlan(treatmentPlan);
    }
}
