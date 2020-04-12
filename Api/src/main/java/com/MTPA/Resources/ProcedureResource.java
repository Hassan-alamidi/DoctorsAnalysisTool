package com.MTPA.Resources;

import com.MTPA.Objects.Reports.PatientProcedure;
import com.MTPA.Services.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("procedure")
public class ProcedureResource {

    private ProcedureService procedureService;

    @Autowired
    public void ProcedureResource(final ProcedureService procedureService){
        this.procedureService = procedureService;
    }

    @GetMapping
    public ResponseEntity<List<PatientProcedure>> getAllProcedures(@RequestHeader("ppsn") final String ppsn){
        return procedureService.getAllProcedures(ppsn);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PatientProcedure>> getRecentProcedures(@RequestHeader("ppsn") final String ppsn){
        return procedureService.getRecentProcedures(ppsn);
    }

    @PostMapping
    public ResponseEntity<?> createProcedure(@RequestBody final PatientProcedure procedure){
        return procedureService.createProcedure(procedure);
    }

}
