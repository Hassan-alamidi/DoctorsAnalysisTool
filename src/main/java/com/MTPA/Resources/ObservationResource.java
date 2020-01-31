package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("observations")
public class ObservationResource {

    @GetMapping
    public ResponseEntity<List<PatientObservation>> getAllObservations(@RequestHeader("PPSN") final String ppsn){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/recent")
    public ResponseEntity<PatientObservation> getRecentObservations(@RequestHeader("PPSN") final String ppsn){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<PatientObservation>> getRecentObservations(@RequestHeader("PPSN") final String ppsn, @RequestParam("count") int count){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping
    public ResponseEntity<PatientObservation> createObservation(@RequestBody final PatientObservation observation){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
