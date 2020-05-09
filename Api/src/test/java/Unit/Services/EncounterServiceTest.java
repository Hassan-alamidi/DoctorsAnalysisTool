package Unit.Services;

import com.MTPA.DAO.EncounterDAO;
import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.Condition;
import com.MTPA.Objects.Reports.Observation;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
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
    LocalDate date;
    Patient patient;
    Observation observation;
    Set<Observation> observations;

    @Before
    @SneakyThrows
    public void setup(){
        date = LocalDate.parse("2020-02-12");
        patient = new Patient(1,"fistName","secondName","Male", date,null, "ppsn","address",
                 new HashSet<Condition>(), new HashSet<>());

        observation = new Observation();
        observation.setDateTaken(date);
        observation.setType("blood test");
        observation.setResultValue("blood everywhere");
        observation.setUnit("mistakes");
        observations = new HashSet<Observation>(Collections.singleton(observation));
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
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        ResponseEntity<List<Encounter>> entity = encounterService.getAllEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertFalse(entity.getBody().isEmpty());
    }

    @Test
    public void getAllEncountersWherePPSNHasNone_thenEmptyListReturned(){
        when(encounterDAO.findAllEncountersOrderedByDate(any(String.class))).thenReturn(new ArrayList<>());
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        ResponseEntity<List<Encounter>> entity = encounterService.getAllEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().isEmpty());
    }

    @Test
    public void getEncountersById_thenOk(){
        when(encounterDAO.findById(any())).thenReturn(java.util.Optional.ofNullable(encounter));
        ResponseEntity<Encounter> entity = encounterService.getEncounterById("1");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void getEncountersByFakeId_thenNotFound(){
        when(encounterDAO.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<Encounter> entity = encounterService.getEncounterById("1");
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void getRecentEncounters_thenOk(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(encounters);
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertFalse(entity.getBody().isEmpty());
    }

    @Test
    public void getRecentEncountersWherePPSNHasNone_thenEmptyListReturned(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(new ArrayList<>());
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN");
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().isEmpty());
    }

    @Test
    public void getANumberOfRecentEncounters_thenOk(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(encounters);
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN", 2);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertFalse(entity.getBody().isEmpty());
    }

    @Test
    public void getANumberOfRecentEncountersWherePPSNHasNone_thenEmptyListReturned(){
        when(encounterDAO.findRecentEncountersOrderedByDate(any(String.class), any())).thenReturn(new ArrayList<>());
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        ResponseEntity<List<Encounter>> entity = encounterService.getRecentEncounters("anyPPSN", 1);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().isEmpty());
    }

    @Test
    public void createEncounterWithValidPatient_thenOk(){
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(patient);
        when(encounterDAO.save(any())).thenReturn(encounter);
        ResponseEntity entity = encounterService.createEncounter(encounter);
        Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    public void createEncounterWithFakePatient_thenNotFound(){
        when(patientDAO.exists(any(String.class))).thenReturn(false);
        ResponseEntity entity = encounterService.createEncounter(encounter);
        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void createEncounterWithValidPatientAndWithNoChildren_thenCreated(){
        encounter.setObservations(null);
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(patient);
        when(encounterDAO.save(any())).thenReturn(encounter);
        ResponseEntity entity = encounterService.createEncounter(encounter);
        Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }
}
