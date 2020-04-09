package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.ObservationDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientCondition;
import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        if(patientDAO.exists(ppsn)){
            List<Encounter> encounters = encounterDAO.findAllEncountersOrderedByDate(ppsn);
            return new ResponseEntity<>(encounters, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Encounter> getEncounterById(final int id){
        Optional<Encounter> encounter = encounterDAO.findById(id);
        return encounter.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Encounter>> getRecentEncounters(final String ppsn){
        if(patientDAO.exists(ppsn)){
            Pageable pageable = (Pageable) PageRequest.of(0, 10);
            List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
            return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Encounter>> getRecentEncounters(final String ppsn, int count){
        if(patientDAO.exists(ppsn)){
            Pageable pageable = (Pageable) PageRequest.of(0, count);
            List<Encounter> encounters = encounterDAO.findRecentEncountersOrderedByDate(ppsn, pageable);
            return new ResponseEntity<List<Encounter>>(encounters, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<?> createEncounter(final Encounter encounter){
        if(!patientDAO.exists(encounter.getPatient().getPPSN())){
            return new ResponseEntity<String>("Patient Not Found",HttpStatus.NOT_FOUND);
        }

        //TODO before going forward with this database restructure needed, more thought needs to be put into the database
        if(encounter.getId() == 0 && encounter.getObservations() != null && !encounter.getObservations().isEmpty()) {
            Set<PatientObservation> observations = encounter.getObservations();
            Encounter savedEncounter = encounterDAO.save(encounter);
            //observations is mandatory
            observationService.saveAllObservations(observations, savedEncounter);
            if(encounter.getCondition() != null){
                PatientCondition condition = encounter.getCondition();
                condition.setEncounter(savedEncounter);
                condition.setPatient(encounter.getPatient());
                conditionServices.addPatientCondition(condition).getBody();
            }
            return new ResponseEntity<Encounter>(savedEncounter, HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
