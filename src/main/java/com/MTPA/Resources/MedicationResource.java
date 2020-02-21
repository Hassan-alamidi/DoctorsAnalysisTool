package com.MTPA.Resources;

import com.MTPA.Objects.Reports.PatientMedication;
import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medication")
public class MedicationResource {

    @GetMapping
    public ResponseEntity<List<PatientMedication>> getAllMedication(@RequestHeader("PPSN") final String ppsn){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/current")
    public ResponseEntity<PatientMedication> getCurrentMedication(@RequestHeader("PPSN") final String ppsn){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping
    public ResponseEntity<PatientMedication> createObservation(@RequestBody final PatientMedication medication){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    //NOTE Probably shouldn't allow update of medication as will want to keep a history of medication
    @PutMapping
    public ResponseEntity<PatientMedication> updateCurrentMedication(@RequestBody final PatientMedication medication){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
