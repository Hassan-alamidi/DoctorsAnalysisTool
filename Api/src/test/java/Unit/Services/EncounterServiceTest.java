package Unit.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientCondition;
import com.MTPA.Objects.Reports.PatientMedication;
import com.MTPA.Objects.Reports.PatientObservation;
import com.MTPA.Services.ConditionServices;
import com.MTPA.Services.EncounterService;
import com.MTPA.Services.ObservationService;
import com.MTPA.Services.PatientServices;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EncounterServiceTest {

    @Mock
    PatientDAO patientDAO;
    @Mock
    EncounterDAO encounterDAO;
    @Mock
    ObservationService observationService;
    @Mock
    PatientServices patientServices;
    @Mock
    ConditionServices conditionServices;
    EncounterService encounterService;

    Encounter encounter;
    List<Encounter> encounters;
    Date date;
    Patient patient;
    PatientObservation observation;
    Set<PatientObservation> observations;

    @Before
    @SneakyThrows
    public void setup(){
        date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-12");
        patient = new Patient(1,"fistName","secondName","Male", date,null, "ppsn","address",
                "ajd", "jsoidja", new HashSet<PatientCondition>(), new HashSet<>());
        observation = new PatientObservation();
        observation.setDescription("");
        observation.setDateTaken(date);
        observation.setType("blood test");
        observation.setResultValue("blood everywhere");
        observation.setUnit("mistakes");
        observations = new HashSet<PatientObservation>(Collections.singleton(observation));
        encounterService = new EncounterService(patientDAO,encounterDAO,conditionServices,observationService,patientServices);
        encounter = new Encounter();
        encounter.setDateVisited(date);
        encounter.setPatient(patient);
        encounter.setObservations(observations);
        encounters = new ArrayList<Encounter>(Collections.singleton(encounter));

    }

    @Test
    public void getAllEncounters_thenOk(){
        when(encounterDAO.findAllEncountersOrderedByDate(any(String.class))).thenReturn(encounters);
        ResponseEntity<List<Encounter>> entity = encounterService.getAllEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertFalse(entity.getBody().isEmpty());
    }

    @Test
    public void getAllEncountersWherePPSNHasNone_thenEmptyListReturned(){
        when(encounterDAO.findAllEncountersOrderedByDate(any(String.class))).thenReturn(new ArrayList<>());
        ResponseEntity<List<Encounter>> entity = encounterService.getAllEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().isEmpty());
    }

    @Test
    public void getEncountersById_thenOk(){
        when(encounterDAO.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(encounter));
        ResponseEntity<Encounter> entity = encounterService.getEncounterById(1);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void getEncountersByFakeId_thenNotFound(){
        when(encounterDAO.findById(anyInt())).thenReturn(Optional.empty());
        ResponseEntity<Encounter> entity = encounterService.getEncounterById(1);
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void getRecentEncounters_thenOk(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(encounters);
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertFalse(entity.getBody().isEmpty());
    }

    @Test
    public void getRecentEncountersWherePPSNHasNone_thenEmptyListReturned(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().isEmpty());
    }

    @Test
    public void getANumberOfRecentEncounters_thenOk(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(encounters);
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN", 2);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertFalse(entity.getBody().isEmpty());
    }

    @Test
    public void getANumberOfRecentEncountersWherePPSNHasNone_thenEmptyListReturned(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN", 1);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().isEmpty());
    }

    @Test
    public void createEncounterWithValidPatient_thenOk(){
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        when(encounterDAO.save(any())).thenReturn(encounter);
        when(observationService.saveAllObservations(any(), any())).thenReturn(observations);
        ResponseEntity entity = encounterService.createEncounter(encounter);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void createEncounterWithFakePatient_thenNotFound(){
        when(patientDAO.exists(any(String.class))).thenReturn(false);
        ResponseEntity entity = encounterService.createEncounter(encounter);
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void createEncounterWithValidPatientAndWithNoObservations_thenFail(){
        encounter.setObservations(null);
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        when(encounterDAO.save(any())).thenReturn(encounter);
        when(observationService.saveAllObservations(any(), any())).thenReturn(observations);
        ResponseEntity entity = encounterService.createEncounter(encounter);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, entity.getStatusCode());
    }

    @Test
    public void createEncounterWithValidPatientAndWithInvalidObservation_thenFail(){
//        observation.setUnit(null);
//        observation.setType(null);
//        encounter.setObservations(Arrays.asList(observation));
//        when(patientDAO.exists(any(String.class))).thenReturn(true);
//        when(encounterDAO.save(any())).thenReturn(encounter);
//        when(observationService.saveAllObservations(any(), any())).thenThrow(new Exception());
//        try {
//            ResponseEntity entity = encounterService.createEncounter(encounter);
//            fail();
//        }catch (Exception e){
//
//        }
    }


}
