package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.ObservationDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ObservationService {

    private final ObservationDAO observationDAO;
    private final EncounterDAO encounterDAO;
    private final PatientDAO patientDAO;

    @Autowired
    public ObservationService(final ObservationDAO observationDAO, final EncounterDAO encounterDAO,
                              final PatientDAO patientDAO){
        this.observationDAO = observationDAO;
        this.encounterDAO = encounterDAO;
        this.patientDAO = patientDAO;
    }

    public ResponseEntity<List<Observation>> getAllObservations(final String ppsn){
        List<Observation> observations = observationDAO.getPatientObservationHistory(ppsn);
        return new ResponseEntity<List<Observation>>(observations, HttpStatus.OK);
    }

    public ResponseEntity<List<Observation>> getRecentObservations(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<Observation> observations = observationDAO.findRecentObservationsOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<Observation>>(observations, HttpStatus.OK);
    }

    public ResponseEntity<?> deletePatientObservation(final int id){
        try {
            observationDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException | EmptyResultDataAccessException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> createObservation(final Observation observation){
        if(observation.getPatient() != null && patientDAO.exists(observation.getPatient().getPpsn())) {
            if(observation.getEncounter() != null && encounterDAO.existsById(observation.getEncounter().getId())) {
                Observation patientObservation = observationDAO.save(observation);
                return new ResponseEntity<Observation>(patientObservation, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Encounter not created", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }
}
