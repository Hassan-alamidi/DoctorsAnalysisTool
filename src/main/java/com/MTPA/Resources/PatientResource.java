package com.MTPA.Resources;

import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patient")
public class PatientResource {
    //this entire endpoint will be restricted to doctors only
    PatientServices patientServices;

    @Autowired
    public PatientResource(PatientServices patientServices){
        this.patientServices = patientServices;
    }

    @GetMapping
    public ResponseEntity<?> getPatientRecords(@RequestHeader("ppsn") String ppsn){
        return patientServices.getPatient(ppsn);
    }

    @PutMapping
    public ResponseEntity<?> updatePatientRecords(@RequestBody Patient patient){
        return patientServices.updatePatient(patient);
    }

    @PostMapping
    public ResponseEntity<?> createPatientRecords(@RequestBody Patient patient){
        return patientServices.createPatient(patient);
    }
}
