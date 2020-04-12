package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.ObservationDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import com.MTPA.Objects.Reports.PatientProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional(propagation = Propagation.MANDATORY)
    public Set<PatientObservation> saveAllObservations(final Set<PatientObservation> observations,
                                                       final Encounter encounter) {
        final Set<PatientObservation> savedObservations = new HashSet<>();
        observations.stream().forEach(ob -> {
            ob.setEncounter(encounter);
            savedObservations.add(observationDAO.save(ob));
        });
        return savedObservations;
    }

    public ResponseEntity<List<PatientObservation>> getAllObservations(final String ppsn){
        List<PatientObservation> observations = observationDAO.getPatientObservationHistory(ppsn);
        return new ResponseEntity<List<PatientObservation>>(observations, HttpStatus.OK);
    }

    public ResponseEntity<List<PatientObservation>> getRecentObservations(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<PatientObservation> observations = observationDAO.findRecentObservationsOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<PatientObservation>>(observations, HttpStatus.OK);
    }

    public ResponseEntity<?> createObservation(final PatientObservation observation){
        if(observation.getPatient() != null && patientDAO.exists(observation.getPatient().getPpsn())) {
            if(observation.getEncounter() != null && encounterDAO.existsById(observation.getEncounter().getId())) {
                PatientObservation patientObservation = observationDAO.save(observation);
                return new ResponseEntity<PatientObservation>(patientObservation, HttpStatus.OK);
            }
            return new ResponseEntity<>("Encounter not created", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }
}
