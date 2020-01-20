package com.MTPA.Services;

import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServices {

    PatientDAO patientDAO;

    @Autowired
    public PatientServices(PatientDAO patientDAO){
        this.patientDAO = patientDAO;
    }

    public Patient updatePatient(Patient patient){
        //get patient by PPSN then make sure only the unimportant data is changed like address or phone number
        Patient patientOldDetails = patientDAO.findByPPSN(patient.getPPSN());
        patient.setDOB(patientOldDetails.getDOB());
        return null;
    }
}
