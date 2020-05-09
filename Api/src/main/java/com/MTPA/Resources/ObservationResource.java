package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Observation;
import com.MTPA.Services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Observation>> getAllObservations(@RequestHeader("ppsn") final String ppsn){
        return observationService.getAllObservations(ppsn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientObservation(@PathVariable("id") final int id){
        return observationService.deletePatientObservation(id);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Observation>> getRecentObservations(@RequestHeader("ppsn") final String ppsn){
        return observationService.getRecentObservations(ppsn);
    }

    @PostMapping
    public ResponseEntity<?> createObservation(@RequestBody final Observation observation){
        return observationService.createObservation(observation);
    }
}
