package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.PredictionDAO;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PredictionService {

    private final EncounterDAO encounterDAO;
    private final PredictionDAO predictionDAO;
    private final String predictionServer;
    private final RestTemplate restTemplate;

    @Autowired
    public PredictionService(final EncounterDAO encounterDAO, PredictionDAO predictionDAO,
                             @Value("${prediction.server}") String predictionServer){
        this.encounterDAO = encounterDAO;
        this.predictionDAO = predictionDAO;
        this.predictionServer = predictionServer;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<List<Prediction>> getAllPredictionReports(final String ppsn){
        List<Prediction> predictions = predictionDAO.getPatientAllPredictionReports(ppsn);
        return new ResponseEntity<>(predictions, HttpStatus.OK);
    }

    public ResponseEntity<?> requestGeneratedReport(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 1);
        List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
        if(!encounters.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requestBody = "{\"ppsn\":\"" + ppsn + "\", \"encounterId\":\""+ encounters.get(0).getId() +"\"}";
            ResponseEntity<Prediction> responseEntity = restTemplate.exchange(predictionServer+"/predict", HttpMethod.POST, new HttpEntity<>(requestBody,headers), Prediction.class);
            //this may seem strange not just returning the response from the python server but it seems
            //something goes wrong between the two servers when just passing on requests, the json seems to get messed up along the way
            return new ResponseEntity<Prediction>(responseEntity.getBody(),responseEntity.getStatusCode());
        }
        return new ResponseEntity<>("This Patient has no encounters to analyse",HttpStatus.NOT_FOUND);
    }

}
