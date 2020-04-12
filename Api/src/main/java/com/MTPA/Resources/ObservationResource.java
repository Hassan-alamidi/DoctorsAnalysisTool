package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import com.MTPA.Services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("observations")
public class ObservationResource {

    private final ObservationService observationService;

    @Autowired
    public ObservationResource(ObservationService observationService){
        this.observationService = observationService;
    }

    @GetMapping
    public ResponseEntity<List<PatientObservation>> getAllObservations(@RequestHeader("ppsn") final String ppsn){
        return observationService.getAllObservations(ppsn);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PatientObservation>> getRecentObservations(@RequestHeader("ppsn") final String ppsn){
        return observationService.getRecentObservations(ppsn);
    }

//    @GetMapping("/recent/{count}")
//    public ResponseEntity<List<PatientObservation>> getRecentObservations(@RequestHeader("ppsn") final String ppsn, @RequestParam("count") int count){
//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
//    }

    @PostMapping
    public ResponseEntity<?> createObservation(@RequestBody final PatientObservation observation){
        return observationService.createObservation(observation);
    }
}
