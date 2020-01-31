package com.MTPA.Resources;

import com.MTPA.Objects.Reports.PatientObservation;
import com.MTPA.Objects.Reports.PatientProcedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("procedure")
public class ProcedureResource {

    @GetMapping
    public ResponseEntity<List<PatientProcedure>> getAllProcedures(@RequestHeader("PPSN") final String ppsn){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/recent")
    public ResponseEntity<PatientProcedure> getRecentProcedures(@RequestHeader("PPSN") final String ppsn){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<PatientProcedure>> getRecentProcedure(@RequestHeader("PPSN") final String ppsn, @RequestParam("count") int count){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping
    public ResponseEntity<PatientProcedure> createProcedure(@RequestBody final PatientProcedure procedure){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
