package Integration;

import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientObservation;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EncounterIntegrationTest extends BaseIntegrationTest {

    private final String ENCOUNTER_ENDPOINT = "/encounter";
    private Encounter encounter;
    private List<PatientObservation> observations;

    @SneakyThrows
    @Before
    public void setup(){
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-12");
        observations = new ArrayList<>();
        PatientObservation observation = new PatientObservation();
        observation.setDateTaken(date);
        observation.setDescription("patient is sick as a dog");
        observation.setType("blood test");
        observation.setResultValue("Holy fuck");
        observation.setUnit("Shock value");
        encounter = new Encounter();
        encounter.setType("checkup");
        encounter.setDateVisited(date);
        encounter.setDateLeft(date);
        encounter.setPatient(EXISTING_PATIENT);
        encounter.setObservations(observations);
    }

    //get all patient encounters
    @Test
    public void getAllPatientEncountersWithFakePPSN_thenNotFound(){
        doctorHeader.add("PPSN", "837M");
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getAllPatientEncounters_thenAllEncountersReturned(){
        doctorHeader.add("PPSN", "87937M");
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getAllPatientEncountersById_thenEncounterIsReturned(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT + "/1", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    public void getAllPatientEncountersByFakeId_thenNotFound(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(ENCOUNTER_ENDPOINT+ "/45", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    //get default number of recent encounters
    public void getRecentEncountersWithFakePPSN_thenNotFound(){}
    public void getRecentEncountersWithPPSN_thenListOfTenEncountersReturned(){}
    public void getRecentEncountersWithPatientThatHasNoEncounters_thenNotFound(){}

    //get specified number of recent encounters
    public void getSpecifiedNumberRecentEncountersWithFakePPSN_thenNotFound(){}
    public void getSpecifiedNumberRecentEncountersWithPPSN_thenListOfTenEncountersReturned(){}
    public void getSpecifiedNumberRecentEncountersWithPatientThatHasNoEncounters_thenNotFound(){}

    //create encounters
    public void createEncounterWithFakePPSN_thenNotFound(){}
    public void tryToUpdateEncounterThroughCreateEncounter_thenUnprocessable(){}
    public void createEncounterWithoutObservations_thenUnprocessable(){}
    public void createEncounterWithObservation_thenOK(){}
    public void createEncounterWithMultipleObservations_thenOk(){}
    public void createEncounterWithMultipleObservationsOneWithMissingValue_thenNothingIsSaved(){}
    public void createEncounterWithObservationMissingMandatoryFields_thenEncounterIsNotSaved(){}
    public void createEncounterWithConditionWithoutMandatoryFields_thenEncounterIsNotSaved(){}
    public void createEncounterWithCondition_thenOk(){}

}
