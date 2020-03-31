package com.MTPA.Services;

import com.MTPA.DAO.ObservationDAO;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ObservationService(ObservationDAO observationDAO){
        this.observationDAO = observationDAO;
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


}
