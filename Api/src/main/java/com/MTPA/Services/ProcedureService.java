package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.DAO.ProcedureDAO;
import com.MTPA.Objects.Reports.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProcedureService {

    private final ProcedureDAO procedureDAO;
    private final PatientDAO patientDAO;
    private final EncounterDAO encounterDAO;

    @Autowired
    public ProcedureService(final ProcedureDAO procedureDAO, final PatientDAO patientDAO,
                            final EncounterDAO encounterDAO){
        this.procedureDAO = procedureDAO;
        this.patientDAO = patientDAO;
        this.encounterDAO = encounterDAO;
    }

    public ResponseEntity<List<Procedure>> getAllProcedures(final String ppsn){
        List<Procedure> procedures = procedureDAO.getPatientProcedureHistory(ppsn);
        return new ResponseEntity<List<Procedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<List<Procedure>> getRecentProcedures(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<Procedure> procedures = procedureDAO.findRecentProcedureOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<Procedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<?> deletePatientProcedure(final int id){
        try {
            procedureDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException | EmptyResultDataAccessException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> createProcedure(final Procedure procedure){
        if(procedure.getPatient() != null && patientDAO.exists(procedure.getPatient().getPpsn())) {
            if(procedure.getEncounter() != null && encounterDAO.existsById(procedure.getEncounter().getId())) {
                Procedure patientProcedure = procedureDAO.save(procedure);
                return new ResponseEntity<Procedure>(patientProcedure, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Encounter Not Created", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }
}
