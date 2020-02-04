package com.MTPA.Services;

import com.MTPA.DAO.ObservationDAO;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObservationService {

    private final ObservationDAO observationDAO;

    @Autowired
    public ObservationService(ObservationDAO observationDAO){
        this.observationDAO = observationDAO;
    }

    public List<PatientObservation> saveAllObservations(final List<PatientObservation> observations,
                                                        final Encounter encounter) throws Exception {
        try {
            final List<PatientObservation> savedObservations = new ArrayList<>();
            observations.forEach(ob -> {
                ob.setEncounter(encounter);
                savedObservations.add(observationDAO.save(ob));
            });
            return savedObservations;
        }catch (Exception e){
            throw new Exception("Could not save Observations");
        }
    }


}
