package com.MTPA.Services;

import com.MTPA.DAO.MedicationDAO;
import com.MTPA.Objects.Reports.PatientMedication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MedicationService {

    private MedicationDAO medicationDAO;

    @Autowired
    public MedicationService(MedicationDAO medicationDAO){
        this.medicationDAO = medicationDAO;
    }

    public ResponseEntity<List<PatientMedication>> getAllMedication(String ppsn){
        List<PatientMedication> medications = medicationDAO.getPatientMedicationHistory(ppsn);
        if(medications.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    public ResponseEntity<?> getPatientCurrentMedication(String ppsn){
        LocalDate currentDate = LocalDate.now();
        List<PatientMedication> currentMedication = medicationDAO.getCurrentPatientMedication(currentDate, ppsn);
        if(currentMedication.isEmpty()){
            return new ResponseEntity<String>("No medication found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentMedication, HttpStatus.OK);
    }



}
