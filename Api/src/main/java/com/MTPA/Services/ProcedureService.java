package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.DAO.ProcedureDAO;
import com.MTPA.Objects.Reports.PatientProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public ResponseEntity<List<PatientProcedure>> getAllProcedures(final String ppsn){
        List<PatientProcedure> procedures = procedureDAO.getPatientProcedureHistory(ppsn);
        return new ResponseEntity<List<PatientProcedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<List<PatientProcedure>> getRecentProcedures(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<PatientProcedure> procedures = procedureDAO.findRecentProcedureOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<PatientProcedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<?> createProcedure(final PatientProcedure procedure){
        if(procedure.getPatient() != null && patientDAO.exists(procedure.getPatient().getPpsn())) {
            if(procedure.getEncounter() != null && encounterDAO.existsById(procedure.getEncounter().getId())) {
                PatientProcedure patientProcedure = procedureDAO.save(procedure);
                return new ResponseEntity<PatientProcedure>(patientProcedure, HttpStatus.OK);
            }
            return new ResponseEntity<>("Encounter not created", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Patient Not Found", HttpStatus.NOT_FOUND);
    }
}
