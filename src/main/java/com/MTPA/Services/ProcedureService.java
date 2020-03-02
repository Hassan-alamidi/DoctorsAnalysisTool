package com.MTPA.Services;

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

    private ProcedureDAO procedureDAO;

    @Autowired
    public ProcedureService(ProcedureDAO procedureDAO){
        this.procedureDAO = procedureDAO;
    }

    public ResponseEntity<List<PatientProcedure>> getAllProcedures(final String ppsn){
        List<PatientProcedure> procedures = procedureDAO.getPatientProcedureHistory(ppsn);
        if(procedures.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<PatientProcedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<List<PatientProcedure>> getRecentProcedures(final String ppsn){
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<PatientProcedure> procedures = procedureDAO.findRecentProcedureOrderedByDate(ppsn, pageable);
        if(procedures.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<PatientProcedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<PatientProcedure> createProcedure(final PatientProcedure procedure){
        PatientProcedure patientProcedure = procedureDAO.save(procedure);
        return new ResponseEntity<PatientProcedure>(patientProcedure, HttpStatus.OK);
    }
}
