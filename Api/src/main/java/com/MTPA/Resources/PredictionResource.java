package com.MTPA.Resources;

import com.MTPA.Services.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prediction")
public class PredictionResource {

    private PredictionService predictionService;

    @Autowired
    public PredictionResource(final PredictionService predictionService){
        this.predictionService = predictionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllGeneratedReports(@RequestHeader("ppsn") final String ppsn){
        return predictionService.getAllPredictionReports(ppsn);
    }

    @PostMapping
    public ResponseEntity<?> generatePredictionReport(@RequestHeader("ppsn") final String ppsn){
        return predictionService.requestGeneratedReport(ppsn);
    }
}
