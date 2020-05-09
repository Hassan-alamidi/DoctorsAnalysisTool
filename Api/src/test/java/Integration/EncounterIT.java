package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.Condition;
import com.MTPA.Objects.Reports.Medication;
import com.MTPA.Objects.Reports.Observation;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HealthApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncounterIT extends BaseIT {

    private final String ENCOUNTER_ENDPOINT = "/encounter";
    private Encounter encounter;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    @SneakyThrows
    public void setupTest(){
        setupBeforeEach(restTemplate, port);
        LocalDate date = LocalDate.parse("2020-05-12");

        encounter = new Encounter();
        encounter.setType("checkup");
        encounter.setDescription("some random description");
        encounter.setDateVisited(date);
        encounter.setDateLeft(date);
        encounter.setPatient(EXISTING_PATIENT);
    }

    //get all patient encounters
    @Test
    public void getAllPatientEncountersWithFakePPSN_thenEmptyListReturned(){
        doctorHeader.add("ppsn", "FAKEPPSN");
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    @Test
    public void getAllPatientEncounters_thenAllEncountersReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getPatientEncounterById_thenEncounterIsReturned(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/a", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getPatientEncounterByFakeId_thenNotFound(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT+ "/34fsd", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getRecentEncountersWithFakePPSN_thenNotFound(){
        doctorHeader.add("ppsn", "fakePPSN");
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    @Test
    public void getRecentEncountersWithPPSN_thenListOfTenEncountersReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertEquals(10, encounters.size());
    }

    @Test
    public void getRecentEncountersWithPatientThatHasNoEncounters_thenEmptyArrayReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_WITH_NOTHING_PPSN);
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    @Test
    public void getRecentEncountersWithFakePatient_thenEmptyListReturned(){
        doctorHeader.add("ppsn", FAKE_PATIENT_PPSN);
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    //get specified number of recent encounters
    @Test
    public void getSpecifiedNumberRecentEncountersWithFakePPSN_thenEmptyListReturned(){
        doctorHeader.add("ppsn", "FAKEPPSN");
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/10", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    @Test
    public void getSpecifiedNumberRecentEncountersWithPPSN_thenListOfEncountersReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/11", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertEquals(11, encounters.size());
    }

    @Test
    public void getSpecifiedNumberRecentEncountersWithPatientThatHasMostOfTheEncounters_thenReturnAllExisting(){
        //this patient has only got 11 encounters so looking for 15 should still only return 11
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/15", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertEquals(11, encounters.size());
    }

    @Test
    public void getSpecifiedNumberRecentEncountersWithPatientThatHasNoEncounters_thenEmptyListReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_WITH_NOTHING_PPSN);
        ResponseEntity<?> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/15", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = (List<Encounter>) responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    //create encounters
    @Test
    public void createEncounterWithFakePPSN_thenNotFound(){
        encounter.setPatient(NEVER_GETS_ADDED_PATIENT);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void tryToUpdateEncounterThroughCreateEncounter_thenUnprocessable(){
        encounter.setId("a");
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

}