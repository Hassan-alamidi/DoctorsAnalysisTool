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
    PatientServices patientServices;
    @Autowired
    public ProcedureService(ProcedureDAO procedureDAO, PatientServices patientServices){
        this.procedureDAO = procedureDAO;
        this.patientServices = patientServices;
    }

    public ResponseEntity<List<PatientProcedure>> getAllProcedures(final String ppsn){
        patientServices.getPatient(ppsn);
        List<PatientProcedure> procedures = procedureDAO.getPatientProcedureHistory(ppsn);
        return new ResponseEntity<List<PatientProcedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<List<PatientProcedure>> getRecentProcedures(final String ppsn){
        patientServices.getPatient(ppsn);
        Pageable pageable = (Pageable) PageRequest.of(0, 10);
        List<PatientProcedure> procedures = procedureDAO.findRecentProcedureOrderedByDate(ppsn, pageable);
        return new ResponseEntity<List<PatientProcedure>>(procedures, HttpStatus.OK);
    }

    public ResponseEntity<PatientProcedure> createProcedure(final PatientProcedure procedure){
        PatientProcedure patientProcedure = procedureDAO.save(procedure);
        return new ResponseEntity<PatientProcedure>(patientProcedure, HttpStatus.OK);
    }
}
