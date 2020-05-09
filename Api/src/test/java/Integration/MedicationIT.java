package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Reports.Condition;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.Medication;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HealthApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicationIT extends BaseIT{

    private static final String BASE_ENDPOINT = "/medication";

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    @Override
    public void setupTest() {
        setupBeforeEach(restTemplate, port);
    }

    private Medication createTestMedication(){
        Encounter encounter = new Encounter();
        encounter.setId("a");
        Medication medication = new Medication();
        medication.setTreatmentStart(LocalDate.now());
        medication.setTreatmentEnd(LocalDate.now());
        medication.setDescription("paracetamol");
        medication.setEncounter(encounter);
        medication.setPatient(EXISTING_PATIENT);
        medication.setType("medication");
        medication.setCode("9020");
        return medication;
    }

    @Test
    public void getAllMedication(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Medication>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Medication>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Medication> medications = responseEntity.getBody();
        Assert.assertNotNull(medications);
        Assert.assertFalse(medications.isEmpty());
    }

    @Test
    public void deleteMedication(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT  + "/2" , HttpMethod.DELETE, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<List<Medication>> responseEntityCheck =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Medication>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Medication> medications = responseEntityCheck.getBody();
        Assert.assertNotNull(medications);
        Assert.assertFalse(medications.isEmpty());
        for (Medication medication:medications) {
            Assert.assertNotEquals(2,medication.getId());
        }
    }

    @Test
    public void getImmunization(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Medication>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT + "/immunization", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Medication>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Medication> medications = responseEntity.getBody();
        Assert.assertNotNull(medications);
        for (Medication medication: medications) {
            Assert.assertEquals("Immunization",medication.getType());
        }
    }

    @Test
    public void getPrescribeMedication(){
        Medication medication = createTestMedication();
        ResponseEntity<Medication> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Medication>(medication, doctorHeader), Medication.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Medication createdMedication = responseEntity.getBody();
        Assert.assertNotNull(createdMedication);
        Assert.assertEquals(medication.getTreatmentStart(), createdMedication.getTreatmentStart());
    }

    @Test
    public void getCurrentMedication(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<List<Medication>> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT+"/current", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Medication>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Medication> medications = responseEntity.getBody();
        Assert.assertNotNull(medications);
        Assert.assertFalse(medications.isEmpty());
        for (Medication medication: medications) {
            Assert.assertNull(medication.getTreatmentEnd());
        }
    }

    @Test
    public void markMedicationAsEnded(){
        doctorHeader.add("ppsn", REAL_PATIENT_PPSN);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT  + "/current/end/3" , HttpMethod.PUT, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<List<Medication>> responseEntityCheck =
                restTemplate.exchange(BASE_ENDPOINT + "/current", HttpMethod.GET, new HttpEntity<>(doctorHeader), new ParameterizedTypeReference<List<Medication>>() {});
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Medication> medications = responseEntityCheck.getBody();
        Assert.assertNotNull(medications);
        for (Medication medication: medications) {
            Assert.assertNotEquals(3,medication.getId());
        }
    }
}
