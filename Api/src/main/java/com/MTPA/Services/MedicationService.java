package com.MTPA.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.MedicationDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Reports.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<Medication>> getAllMedication(final String ppsn){
        List<Medication> medications = medicationDAO.getPatientMedicationHistory(ppsn);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    public ResponseEntity<?> prescribeMedication(final Medication medication) {
        if (medication.getPatient() != null && patientDAO.exists(medication.getPatient().getPpsn())){
            if (encounterDAO.findById(medication.getEncounter().getId()).isPresent()) {
                Medication prescribed = medicationDAO.save(medication);
                return new ResponseEntity<>(prescribed, HttpStatus.CREATED);
            }
            return new ResponseEntity<String>("Must have valid encounter linked", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<String>("Patient Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getPatientCurrentMedication(final String ppsn){
        LocalDate currentDate = LocalDate.now();
        List<Medication> currentMedication = medicationDAO.getCurrentPatientMedication(currentDate, ppsn);
        return new ResponseEntity<>(currentMedication, HttpStatus.OK);
    }

    public ResponseEntity<?> getPatientImmunization(final String ppsn){
        LocalDate currentDate = LocalDate.now();
        List<Medication> currentMedication = medicationDAO.getPatientImmunizations(currentDate, ppsn);
        return new ResponseEntity<>(currentMedication, HttpStatus.OK);
    }

    public ResponseEntity<?> markAsEnded(final int id){
        Optional<Medication> optMedication = medicationDAO.findById(id);
        if(optMedication.isPresent()){
            Medication medication = optMedication.get();
            medication.setTreatmentEnd(LocalDate.now());
            medicationDAO.save(medication);
            return new ResponseEntity<>(medication, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deletePatientMedication(final int id){
        try {
            medicationDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException | EmptyResultDataAccessException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Medication> extendMedicationTreatment(final Medication medication){
        Optional<Medication> med = medicationDAO.findById(medication.getId());
        if(med.isPresent()){
            Medication medDetail = med.get();
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
