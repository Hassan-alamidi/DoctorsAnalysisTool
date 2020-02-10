package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Services.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("encounter")
public class EncounterResource {

    private final EncounterService encounterService;

    @Autowired
    public EncounterResource(EncounterService encounterService){
        this.encounterService = encounterService;
    }

    @GetMapping
    public ResponseEntity<List<Encounter>> getAllEncounters(@RequestHeader("ppsn") final String ppsn){
        return encounterService.getAllEncounters(ppsn);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounterById(@RequestParam("id") final int id){
        return encounterService.getEncounterById(id);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Encounter>> getRecentEncounters(@RequestHeader("ppsn") final String ppsn){
        return encounterService.getRecentEncounters(ppsn);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<Encounter>> getRecentEncounters(@RequestHeader("PPSN") final String ppsn, @PathVariable("count") int count){
        return encounterService.getRecentEncounters(ppsn, count);
    }

    @PostMapping
    public ResponseEntity<?> createEncounter(@RequestBody final Encounter encounter){
        return encounterService.createEncounter(encounter);
    }
}
