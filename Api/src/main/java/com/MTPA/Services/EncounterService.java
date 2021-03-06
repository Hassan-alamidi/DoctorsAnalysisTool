package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EncounterService {

    private final PatientDAO patientDAO;
    private final EncounterDAO encounterDAO;
    private final ObservationService observationService;
    private final ConditionServices conditionServices;
    private final PatientServices patientServices;

    @Autowired
    public EncounterService(final PatientDAO patientDAO, final EncounterDAO encounterDAO, final ConditionServices conditionServices,
                            final ObservationService observationService, final PatientServices patientServices){
        this.patientDAO = patientDAO;
        this.encounterDAO = encounterDAO;
        this.conditionServices = conditionServices;
        this.observationService = observationService;
        this.patientServices = patientServices;
    }

    public ResponseEntity<List<Encounter>> getAllEncounters(final String ppsn){
        List<Encounter> encounters = encounterDAO.findAllEncountersOrderedByDate(ppsn);
        return new ResponseEntity<>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<List<Encounter>> getOpenEncounters(final String ppsn){
        List<Encounter> encounters = encounterDAO.findOpenEncounters(ppsn);
        return new ResponseEntity<>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<Encounter> getEncounterById(final String id){
        Optional<Encounter> encounter = encounterDAO.findById(id);
        return encounter.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Encounter>> getRecentEncounters(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<List<Encounter>> getRecentEncounters(final String ppsn, int count){
        Pageable pageable = (Pageable) PageRequest.of(0, count);
        List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);
    }

    public ResponseEntity<?> createEncounter(final Encounter encounter){
        Patient patient = patientDAO.findByPPSN(encounter.getPatient().getPpsn());
        if(patient == null){
            return new ResponseEntity<String>("Patient Not Found",HttpStatus.NOT_FOUND);
        }

        if(encounter.getId() == null) {
            encounter.setDateVisited(LocalDate.now());
            Encounter savedEncounter = encounterDAO.save(encounter);
            return new ResponseEntity<Encounter>(savedEncounter, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity<?> finalizeEncounter(final String id){
        Optional<Encounter> currentStateOpt = encounterDAO.findById(id);
        if(currentStateOpt.isPresent()){
            Encounter encounter = currentStateOpt.get();
            encounter.setDateLeft(LocalDate.now());
            Encounter savedEncounter = encounterDAO.save(encounter);
            return new ResponseEntity<Encounter>(savedEncounter, HttpStatus.OK);
        }
        return new ResponseEntity<>("cannot find encounter to finalize", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateEncounter(final Encounter encounter){
        Optional<Encounter> currentStateOpt = encounterDAO.findById(encounter.getId());
        if(currentStateOpt.isPresent()){
            Encounter currentState = currentStateOpt.get();
            //ensure values that should never be updated do not get updated
            encounter.setDateVisited(currentState.getDateVisited());
            encounter.setPatient(currentState.getPatient());
            Encounter savedEncounter = encounterDAO.save(encounter);
            return new ResponseEntity<Encounter>(savedEncounter, HttpStatus.OK);
        }
        return new ResponseEntity<>("cannot find encounter to update", HttpStatus.NOT_FOUND);
    }
}
