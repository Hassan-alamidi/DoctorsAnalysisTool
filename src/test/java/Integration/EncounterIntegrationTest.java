package Integration;

import com.MTPA.Objects.Organization;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientCondition;
import com.MTPA.Objects.Reports.PatientObservation;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

public class EncounterIntegrationTest extends BaseIntegrationTest {

    private final String ENCOUNTER_ENDPOINT = "/encounter";
    private Encounter encounter;
    private Set<PatientObservation> observations;

    @SneakyThrows
    public void setupTest(){
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-12");
        observations = new HashSet<>();
        PatientObservation observation = new PatientObservation();
        observation.setDateTaken(date);
        observation.setDescription("patient is sick as a dog");
        observation.setType("blood test");
        observation.setResultValue("Holy fuck");
        observation.setUnit("Shock value");
        observations.add(observation);

        PatientCondition condition = new PatientCondition();
        condition.setConditionCode(1);
        condition.setDetails("patient has a cold");
        condition.setDiscovered(date);
        condition.setName("cold");
        condition.setSymptoms("runny nose");

        encounter = new Encounter();
        encounter.setType("checkup");
        encounter.setDateVisited(date);
        encounter.setDateLeft(date);
        encounter.setPatient(EXISTING_PATIENT);
        encounter.setObservations(observations);
        encounter.setCondition(condition);
        encounter.setOrganization(organization);
    }

    //get all patient encounters
    @Test
    public void getAllPatientEncountersWithFakePPSN_thenNotFound(){
        doctorHeader.add("PPSN", "FAKEPPSN");
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getAllPatientEncounters_thenAllEncountersReturned(){
        doctorHeader.add("PPSN", REAL_PATIENT_PPSN);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getPatientEncounterById_thenEncounterIsReturned(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/1", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getPatientEncounterByFakeId_thenNotFound(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT+ "/45", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getRecentEncountersWithFakePPSN_thenNotFound(){
        doctorHeader.add("ppsn", "fakePPSN");
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getRecentEncountersWithPPSN_thenListOfTenEncountersReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.size() == 10);
    }

    @Test
    public void getRecentEncountersWithPatientThatHasNoEncounters_thenNotFound(){
        doctorHeader.add("ppsn", REAL_PATIENT_WITH_NOTHING_PPSN);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    //get specified number of recent encounters
    @Test
    public void getSpecifiedNumberRecentEncountersWithFakePPSN_thenNotFound(){
        doctorHeader.add("ppsn", "FAKEPPSN");
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/10", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getSpecifiedNumberRecentEncountersWithPPSN_thenListOfEncountersReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/11", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.size() == 11);
    }

    @Test
    public void getSpecifiedNumberRecentEncountersWithPatientThatHasMostOfTheEncounters_thenReturnAllExisting(){
        //this patient has only got 11 encounters so looking for 15 should still only return 11
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/15", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.size() == 11);
    }

    @Test
    public void getSpecifiedNumberRecentEncountersWithPatientThatHasNoEncounters_thenNotFound(){
        doctorHeader.add("ppsn", REAL_PATIENT_WITH_NOTHING_PPSN);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/15", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
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
        encounter.setId(1);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithoutObservations_thenUnprocessable(){
        encounter.setObservations(new HashSet<>());
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithObservationNoCondition_thenOK(){
        encounter.setCondition(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithObservationAndCondition_thenOK(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithMultipleObservations_thenOk(){
        PatientObservation observation = new PatientObservation();
        observation.setUnit("kg");
        observation.setResultValue("60");
        observation.setType("weight check");
        observation.setDateTaken(encounter.getDateVisited());
        observation.setDescription("random words");
        observations.add(observation);
        encounter.setObservations(observations);
        System.out.println(encounter.getPatient().getPPSN());
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithMultipleObservationsOneWithMissingValue_thenNothingIsSaved(){
        PatientObservation observation = new PatientObservation();
        observation.setDateTaken(encounter.getDateVisited());
        observations.add(observation);
        encounter.setObservations(observations);

        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithObservationMissingMandatoryFields_thenEncounterIsNotSaved(){
        encounter.setDateVisited(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void createEncounterWithConditionWithoutMandatoryFields_thenEncounterIsNotSaved(){
        encounter.getCondition().setSymptoms(null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}