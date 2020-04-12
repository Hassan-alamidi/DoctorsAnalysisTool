package com.MTPA.Resources;

import com.MTPA.Objects.Reports.PatientMedication;
import com.MTPA.Services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("medication")
public class MedicationResource {

    private MedicationService medicationService;

    @Autowired
    public MedicationResource(final MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping
    public ResponseEntity<List<PatientMedication>> getAllMedication(@RequestHeader("ppsn") final String ppsn){
        return medicationService.getAllMedication(ppsn);
    }

    @PostMapping
    public ResponseEntity<?> prescribeMedication(@RequestBody final PatientMedication patientMedication){
        return medicationService.prescribeMedication(patientMedication);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentMedication(@RequestHeader("ppsn") final String ppsn){
        return medicationService.getPatientCurrentMedication(ppsn);
    }

    //updating medication only allows for extending the end date anything other than that will need a new prescription
    @PutMapping("/current")
    public ResponseEntity<PatientMedication> extendMedicationTreatment(@RequestBody final PatientMedication medication){
        return medicationService.extendMedicationTreatment(medication);
    }

}
