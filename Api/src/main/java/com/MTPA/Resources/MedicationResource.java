package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Medication;
import com.MTPA.Services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.List;

@RestController
@RequestMapping("medication")
public class MedicationResource {

    private MedicationService medicationService;

    @Autowired
    public MedicationResource(final MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedication(@RequestHeader("ppsn") final String ppsn){
        return medicationService.getAllMedication(ppsn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientMedication(@PathVariable("id") final int id){
        return medicationService.deletePatientMedication(id);
    }

    @PostMapping
    public ResponseEntity<?> prescribeMedication(@RequestBody final Medication medication){
        return medicationService.prescribeMedication(medication);
    }

    @GetMapping("/immunization")
    public ResponseEntity<?> getImmunizations(@RequestHeader("ppsn") final String ppsn){
        return medicationService.getPatientImmunization(ppsn);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentMedication(@RequestHeader("ppsn") final String ppsn){
        return medicationService.getPatientCurrentMedication(ppsn);
    }

    //updating medication only allows for extending the end date anything other than that will need a new prescription
    @PutMapping("/current")
    public ResponseEntity<Medication> extendMedicationTreatment(@RequestBody final Medication medication){
        return medicationService.extendMedicationTreatment(medication);
    }

    @PutMapping("/current/end/{id}")
    public ResponseEntity<?> markMedicationAsEnded(@PathVariable("id") final int id){
        return medicationService.markAsEnded(id);
    }

}
