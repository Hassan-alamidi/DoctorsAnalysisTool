package com.MTPA.Services;

import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Optional;

@Service
public class PatientServices {

    PatientDAO patientDAO;

    @Autowired
    public PatientServices(PatientDAO patientDAO){
        this.patientDAO = patientDAO;
    }

    public ResponseEntity<?> updatePatient(Patient patient){
        //get patient by PPSN then make sure only the unimportant data is changed like address or phone number
        Patient patientOldDetails = patientDAO.findByPPSN(patient.getPpsn());
        if(patientOldDetails != null && patient.getId() == patientOldDetails.getId() && patient.getPpsn().equals(patientOldDetails.getPpsn())){
            if(patient.getDob().compareTo(patientOldDetails.getDob()) == 0){
                patient = patientDAO.save(patient);
                return new ResponseEntity<Patient>(patient, HttpStatus.OK);
            }
            return new ResponseEntity<>("You cannot update the patient's Date of birth", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Patient Details not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> createPatient(Patient patient){
        if(!patientDAO.exists(patient.getPpsn())){
            patient = patientDAO.save(patient);
            return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Patient already exists", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity<?> getPatient(final String ppsn){
        Patient patient = patientDAO.findByPPSN(ppsn);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }
        return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
    }
}
