package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.Objects.Reports.Encounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class EncounterService {

    private final EncounterDAO encounterDAO;

    @Autowired
    public EncounterService(final EncounterDAO encounterDAO){
        this.encounterDAO = encounterDAO;
    }

    public ResponseEntity<List<Encounter>> getAllEncounters(final String ppsn){
        List<Encounter> encounters = encounterDAO.findAllPatientEncounter(ppsn);
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
            return new ResponseEntity<Encounter>(encounterDAO.save(encounter), HttpStatus.OK);
        }
        return new ResponseEntity<String>("You cannot update an encounter",HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
