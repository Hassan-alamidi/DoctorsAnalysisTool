package com.MTPA.Resources;

import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Services.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<Encounter> getEncounterById(@PathVariable("id") final String id){
        return encounterService.getEncounterById(id);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Encounter>> getRecentEncounters(@RequestHeader("ppsn") final String ppsn){
        return encounterService.getRecentEncounters(ppsn);
    }

    @GetMapping("/open")
    public ResponseEntity<List<Encounter>> getOpenEncounters(@RequestHeader("ppsn") final String ppsn){
        return encounterService.getOpenEncounters(ppsn);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<Encounter>> getRecentEncounters(@RequestHeader("PPSN") final String ppsn, @PathVariable("count") int count){
        return encounterService.getRecentEncounters(ppsn, count);
    }

    @PostMapping
    public ResponseEntity<?> createEncounter(@RequestBody final Encounter encounter){
        return encounterService.createEncounter(encounter);
    }

    @PutMapping
    public ResponseEntity<?> updateEncounter(@RequestBody final Encounter encounter){
        return encounterService.updateEncounter(encounter);
    }
}
