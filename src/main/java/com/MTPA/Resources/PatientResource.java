package com.MTPA.Resources;

import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.PatientCondition;
import com.MTPA.Services.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("patient")
public class PatientResource {
    //this entire endpoint will be restricted to doctors only
    PatientDAO patientDAO;
    PatientServices patientServices;

    @Autowired
    public PatientResource(PatientDAO patientDAO, PatientServices patientServices){
        this.patientDAO = patientDAO;
        this.patientServices = patientServices;
    }

    @GetMapping
    public Patient getPatientRecords(@RequestHeader("ppsn") String PPSN){
        return patientDAO.findByPPSN(PPSN);
    }

    @PutMapping
    public Patient updatePatientRecords(Patient patient){
        return patientServices.updatePatient(patient);
    }

    @PostMapping
    public Patient createPatientRecords(@RequestBody Patient patient){
        patientDAO.save(patient);
        return patient;
    }
}
