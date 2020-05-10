package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Condition;
import com.MTPA.Objects.Reports.Encounter;
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
import java.util.List;

public class ConditionIT extends BaseIT{

    private static final String BASE_ENDPOINT = "/conditions";

    @Override
    public void setupTest() {

    }

    private Condition createTestCondition(){
        Encounter encounter = new Encounter();
        encounter.setId("a");
        Condition condition = new Condition();
        condition.setDiscovered(LocalDate.now());
        condition.setCuredOn(LocalDate.now());
        condition.setName("Appendicitis");
        condition.setEncounter(encounter);
        condition.setPatient(EXISTING_PATIENT);
        condition.setType("condition");
        condition.setCode("9020");
        return condition;
    }

    @Test
    public void getAllConditions(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Condition>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Condition>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Condition> conditions = responseEntity.getBody();
        Assert.assertNotNull(conditions);
        Assert.assertFalse(conditions.isEmpty());
    }

    @Test
    public void getAllConditionsWithFakePPSN_thenEmptyListReturned(){
        doctorHeader.add("ppsn", FAKE_PATIENT_PPSN);
        ResponseEntity<List<Condition>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Condition>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Condition> conditions = responseEntity.getBody();
        Assert.assertNotNull(conditions);
        Assert.assertTrue(conditions.isEmpty());
    }

    @Test
    public void getOnGoingConditions(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Condition>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT + "/current", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Condition>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Condition> conditions = responseEntity.getBody();
        Assert.assertNotNull(conditions);
        Assert.assertFalse(conditions.isEmpty());
        for (Condition condition:conditions) {
            Assert.assertNull(condition.getCuredOn());
        }
    }

    @Test
    public void getOnGoingConditionsWithFakePPSN_thenEmptyListReturned(){
        doctorHeader.add("ppsn", FAKE_PATIENT_PPSN);
        ResponseEntity<List<Condition>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT + "/current", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Condition>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Condition> conditions = responseEntity.getBody();
        Assert.assertNotNull(conditions);
        Assert.assertTrue(conditions.isEmpty());
    }

    @Test
    public void markConditionAsCured_thenPatientShouldHaveThatConditionAsOnGoing(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT  + "/current/cured/4" , HttpMethod.PUT, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<List<Condition>> responseEntityCheck =
                restTemplate.exchange(BASE_ENDPOINT + "/current", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Condition>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Condition> conditions = responseEntityCheck.getBody();
        Assert.assertNotNull(conditions);
        for (Condition condition: conditions) {
            Assert.assertNotEquals(4,condition.getId());
        }
    }

    @Test
    public void getConditionById(){
        ResponseEntity<Condition> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT + "/2", HttpMethod.GET, new HttpEntity<>(doctorHeader), Condition.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Condition condition = responseEntity.getBody();
        Assert.assertNotNull(condition);
        Assert.assertEquals(2,condition.getId());
    }

    @Test
    public void getConditionByIdWithFakeId_thenNotFound(){
        ResponseEntity<Condition> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT + "/1000", HttpMethod.GET, new HttpEntity<>(doctorHeader), Condition.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void deletePatientCondition(){
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT  + "/1" , HttpMethod.DELETE, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Condition>> responseEntityCheck =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Condition>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Condition> conditions = responseEntityCheck.getBody();
        Assert.assertNotNull(conditions);
        Assert.assertFalse(conditions.isEmpty());
        for (Condition condition:conditions) {
            Assert.assertNotEquals(1,condition.getId());
        }
    }

    @Test
    public void deletePatientConditionWithFakeId_thenNotFound(){
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT  + "/1000" , HttpMethod.DELETE, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addCondition(){
        Condition condition = createTestCondition();
        ResponseEntity<Condition> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Condition>(condition, doctorHeader), Condition.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Condition createdCondition = responseEntity.getBody();
        Assert.assertNotNull(createdCondition);
        Assert.assertEquals(condition.getDiscovered(), createdCondition.getDiscovered());
    }

    @Test
    public void updateCondition(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<Condition> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT + "/5", HttpMethod.GET, new HttpEntity<>(doctorHeader), Condition.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Condition condition = responseEntity.getBody();
        Assert.assertNotNull(condition);
        condition.setSymptoms("TestCondition Updated");
        condition.setCuredOn(LocalDate.now());

        ResponseEntity<Condition> responseEntityUpdate =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Condition>(condition, doctorHeader), Condition.class);
        Assert.assertEquals(HttpStatus.OK, responseEntityUpdate.getStatusCode());
        Condition updatedCondition = responseEntityUpdate.getBody();
        Assert.assertNotNull(updatedCondition);
        Assert.assertEquals(condition.getCuredOn(), updatedCondition.getCuredOn());
        Assert.assertEquals(condition.getSymptoms(), updatedCondition.getSymptoms());
    }
}
