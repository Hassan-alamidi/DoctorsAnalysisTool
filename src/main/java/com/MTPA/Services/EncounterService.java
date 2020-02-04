package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.ObservationDAO;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class EncounterService {

    private final EncounterDAO encounterDAO;
    private final ObservationService observationService;

    @Autowired
    public EncounterService(final EncounterDAO encounterDAO, final ObservationService observationService){
        this.encounterDAO = encounterDAO;
        this.observationService = observationService;
    }

    public ResponseEntity<List<Encounter>> getAllEncounters(final String ppsn){
        List<Encounter> encounters = encounterDAO.findAllEncountersOrderedByDate(ppsn);
        if(encounters.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<Encounter> getEncounterById(final int id){
        Optional<Encounter> encounter = encounterDAO.findById(id);
        return encounter.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Encounter>> getRecentEncounters(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 20);
        List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
        if(encounters.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<List<Encounter>> getRecentEncounters(final String ppsn, int count){
        Pageable pageable = (Pageable) PageRequest.of(0, count);
        List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
        if(encounters.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<?> createEncounter(final Encounter encounter){
        if(encounter.getId() == 0) {
            List<PatientObservation> observations = encounter.getObservations();
            Encounter savedEncounter = encounterDAO.save(encounter);
            try {
                //TODO finish off or find a better way of saving objects linked to an encounter lik observations or conditions
                savedEncounter.setObservations(observationService.saveAllObservations(observations, savedEncounter));

            } catch (Exception e){
                encounterDAO.delete(savedEncounter);
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }

            return new ResponseEntity<Encounter>(savedEncounter, HttpStatus.OK);
        }
        return new ResponseEntity<String>("You cannot update an encounter",HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
