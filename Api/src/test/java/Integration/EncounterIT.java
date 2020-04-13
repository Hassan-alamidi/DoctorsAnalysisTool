package Integration;

import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientCondition;
import com.MTPA.Objects.Reports.PatientObservation;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

public class EncounterIT extends BaseIT {

    private final String ENCOUNTER_ENDPOINT = "/encounter";
    private Encounter encounter;
    private Set<PatientObservation> observations;

    @SneakyThrows
    public void setupTest(){
        LocalDate date = LocalDate.parse("2020-05-12");
        observations = new HashSet<>();
        PatientObservation observation = new PatientObservation();
        observation.setDateTaken(date);
        observation.setType("blood test");
        observation.setResultValue("Holy fuck");
        observation.setUnit("Shock value");
        observation.setPatient(EXISTING_PATIENT);
        observations.add(observation);

        PatientCondition condition = new PatientCondition();
        condition.setCode("");
        condition.setDetails("patient has a cold");
        condition.setDiscovered(date);
        condition.setName("cold");
        condition.setSymptoms("runny nose");
        //condition.setPatient(E);

        encounter = new Encounter();
        encounter.setType("checkup");
        encounter.setDescription("some random description");
        encounter.setDateVisited(date);
        encounter.setDateLeft(date);
        encounter.setPatient(EXISTING_PATIENT);
        encounter.setObservations(observations);
        encounter.setCondition(condition);
    }

    //get all patient encounters
    @Test
    public void getAllPatientEncountersWithFakePPSN_thenEmptyListReturned(){
        doctorHeader.add("PPSN", "FAKEPPSN");
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    @Test
    public void getAllPatientEncounters_thenAllEncountersReturned(){
        doctorHeader.add("PPSN", REAL_PATIENT_PPSN);
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
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
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
    public void getRecentEncountersWithPatientThatHasNoEncounters_thenEmptyArrayReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_WITH_NOTHING_PPSN);
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    @Test
    public void getRecentEncountersWithFakePatient_thenEmptyListReturned(){
        doctorHeader.add("ppsn", FAKE_PATIENT_PPSN);
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
    }

    //get specified number of recent encounters
    @Test
    public void getSpecifiedNumberRecentEncountersWithFakePPSN_thenEmptyListReturned(){
        doctorHeader.add("ppsn", "FAKEPPSN");
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/10", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
        Assert.assertTrue(encounters.isEmpty());
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
    public void getSpecifiedNumberRecentEncountersWithPatientThatHasNoEncounters_thenEmptyListReturned(){
        doctorHeader.add("ppsn", REAL_PATIENT_WITH_NOTHING_PPSN);
        ResponseEntity<List<Encounter>> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/recent/15", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Encounter>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Encounter> encounters = responseEntity.getBody();
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

    // TODO figure out how to get hibernate to insert parent ids into child objects before reimplementing these tests
//    @Test
//    public void createEncounterWithObservationNoCondition_thenOK(){
//        encounter.setCondition(null);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

//    @Test
//    public void createEncounterWithObservationAndCondition_thenOK(){
//        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    public void createEncounterWithMultipleObservations_thenOk(){
//        PatientObservation observation = new PatientObservation();
//        observation.setUnit("kg");
//        observation.setResultValue("60");
//        observation.setType("weight check");
//        observation.setDateTaken(encounter.getDateVisited());
//        observation.setPatient(encounter.getPatient());
//        observations.add(observation);
//        encounter.setObservations(observations);
//        System.out.println(encounter.getPatient().getPpsn());
//        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(encounter, doctorHeader), String.class);
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

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