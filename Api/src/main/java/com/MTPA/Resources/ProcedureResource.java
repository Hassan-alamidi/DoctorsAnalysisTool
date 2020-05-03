package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Procedure;
import com.MTPA.Services.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("procedure")
public class ProcedureResource {

    private ProcedureService procedureService;

    @Autowired
    public void ProcedureResource(final ProcedureService procedureService){
        this.procedureService = procedureService;
    }

    @GetMapping
    public ResponseEntity<List<Procedure>> getAllProcedures(@RequestHeader("ppsn") final String ppsn){
        return procedureService.getAllProcedures(ppsn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientProcedure(@PathVariable("id") final int id){
        return procedureService.deletePatientProcedure(id);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Procedure>> getRecentProcedures(@RequestHeader("ppsn") final String ppsn){
        return procedureService.getRecentProcedures(ppsn);
    }

    @PostMapping
    public ResponseEntity<?> createProcedure(@RequestBody final Procedure procedure){
        return procedureService.createProcedure(procedure);
    }

}
