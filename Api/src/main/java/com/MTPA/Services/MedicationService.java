package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.MedicationDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Reports.PatientMedication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MedicationService {

    private final MedicationDAO medicationDAO;
    private final EncounterDAO encounterDAO;
    private final PatientDAO patientDAO;

    @Autowired
    public MedicationService(final MedicationDAO medicationDAO, final EncounterDAO encounterDAO,
                             final PatientDAO patientDAO){
        this.medicationDAO = medicationDAO;
        this.encounterDAO = encounterDAO;
        this.patientDAO = patientDAO;
    }

    public ResponseEntity<List<PatientMedication>> getAllMedication(final String ppsn){
        List<PatientMedication> medications = medicationDAO.getPatientMedicationHistory(ppsn);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    public ResponseEntity<?> prescribeMedication(final PatientMedication patientMedication) {
        if (patientMedication.getPatient() != null && patientDAO.exists(patientMedication.getPatient().getPpsn())){
            if (encounterDAO.findById(patientMedication.getEncounter().getId()).isPresent()) {
                PatientMedication prescribed = medicationDAO.save(patientMedication);
                return new ResponseEntity<>(prescribed, HttpStatus.OK);
            }
            return new ResponseEntity<String>("must have valid encounter linked", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<String>("Patient Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getPatientCurrentMedication(final String ppsn){
        LocalDate currentDate = LocalDate.now();
        List<PatientMedication> currentMedication = medicationDAO.getCurrentPatientMedication(currentDate, ppsn);
        return new ResponseEntity<>(currentMedication, HttpStatus.OK);
    }

    public ResponseEntity<PatientMedication> extendMedicationTreatment(final PatientMedication medication){
        Optional<PatientMedication> med = medicationDAO.findById(medication.getId());
        if(med.isPresent()){
            PatientMedication medDetail = med.get();
            if(medication.getTreatmentEnd().compareTo(medDetail.getTreatmentEnd()) > 0){
                medDetail.setTreatmentEnd(medication.getTreatmentEnd());
                medDetail = medicationDAO.save(medDetail);
                return new ResponseEntity<>(medDetail, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
