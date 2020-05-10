package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Reports.Condition;
import com.MTPA.Objects.Reports.Encounter;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

public class ObservationIT extends BaseIT{

    private final String BASE_ENDPOINT = "/observations";
    private Observation observationForCreation;

    public void setupTest(){
        createTestObservation();
    }

    public void createTestObservation(){
        LocalDate date = LocalDate.parse("2020-05-12");
        observationForCreation = new Observation();
        observationForCreation.setPatient(EXISTING_PATIENT);
        Encounter encounter = createTestEncounter(date);
        encounter.setId("a");
        observationForCreation.setEncounter(encounter);
        observationForCreation.setDateTaken(date);
        observationForCreation.setType("test");
        observationForCreation.setResultValue("val");
        observationForCreation.setUnit("any");
        observationForCreation.setCode("01110100 01100101 01110011 01110100");
    }


    @Test
    public void getAllObservations(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Observation>> responseEntity = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Observation>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Observation> encounters = responseEntity.getBody();
        Assert.assertFalse(encounters.isEmpty());
    }

    @Test
    public void deleteObservation(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT  + "/1" , HttpMethod.DELETE, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<List<Observation>> responseEntityCheck =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Observation>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Observation> observations = responseEntityCheck.getBody();
        Assert.assertNotNull(observations);
        Assert.assertFalse(observations.isEmpty());
        for (Observation observation:observations) {
            Assert.assertNotEquals(1,observation.getId());
        }
    }

    @Test
    public void getRecentObservations(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Observation>> responseEntity = restTemplate.exchange(BASE_ENDPOINT + "/recent", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Observation>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Observation> encounters = responseEntity.getBody();
        Assert.assertFalse(encounters.isEmpty());
    }

    @Test
    public void createObservation(){
        ResponseEntity<Observation> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Observation>(observationForCreation, doctorHeader), Observation.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Observation createdObservation = responseEntity.getBody();
        Assert.assertNotNull(createdObservation);
        Assert.assertEquals(observationForCreation.getCode(), createdObservation.getCode());
    }
}
