package Integration;

import com.MTPA.Objects.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import java.sql.Date;
import java.time.LocalDate;

public class PatientIntegrationTest extends BaseIntegrationTest{

    private static final String BASE_ENDPOINT = "/patient";

    @Test
    public void createNonExistingPatient(){
        ResponseEntity<Patient> createdPatient = restTemplate.postForEntity(BASE_ENDPOINT, NEW_PATIENT, Patient.class);
        Assert.assertEquals(HttpStatus.CREATED, createdPatient.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", "123N");
        ResponseEntity<Patient> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Patient.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(responseEntity.getBody().getAddress(),NEW_PATIENT.getAddress());
    }

    @Test
    public void createExistingPatient_thenPatientIsNotCreated(){
        //check still exists
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPPSN());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.OK, testValid.getStatusCode());
        //try to create duplicate
        System.out.println("Attempting to create duplicate");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_ENDPOINT,EXISTING_PATIENT,String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void createPatientWithPreExistingPPSN_thenPatientIsNotCreatedAndPreExistingPatientIsNotUpdated(){
        //check still exists
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPPSN());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.OK, testValid.getStatusCode());
        //try to create duplicate
        System.out.println("Attempting to create patient with PPSN number of which has already been taken");
        NEVER_GETS_ADDED_PATIENT.setPPSN(EXISTING_PATIENT.getPPSN());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_ENDPOINT,NEVER_GETS_ADDED_PATIENT,String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void createPatientWithoutFirstName_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPPSN("93hjoaj");
        patient.setFirstName(null);

        ResponseEntity<?> createdPatient = restTemplate.postForEntity(BASE_ENDPOINT, patient, String.class);
        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", patient.getPPSN());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createPatientWithoutLastName_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPPSN("935hjoaj");
        patient.setLastName(null);

        ResponseEntity<?> createdPatient = restTemplate.postForEntity(BASE_ENDPOINT, patient, String.class);
        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", patient.getPPSN());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createPatientWithoutDOB_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPPSN("933hjoaj");
        patient.setDOB(null);

        ResponseEntity<?> createdPatient = restTemplate.postForEntity(BASE_ENDPOINT, patient, String.class);
        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", patient.getPPSN());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createPatientWithoutPPSN_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPPSN("993hjoaj");
        patient.setPPSN(null);

        ResponseEntity<?> createdPatient = restTemplate.postForEntity(BASE_ENDPOINT, patient, String.class);
        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", patient.getPPSN());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getExistingPatient(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPPSN());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.OK, testValid.getStatusCode());
    }

    @Test
    public void getNonExistingPatient_thenPatientNotFound(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", NEVER_GETS_ADDED_PATIENT.getPPSN());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.NOT_FOUND, testValid.getStatusCode());
    }

    @Test
    public void updatePatientDetailsExceptDOBAndPPSN(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPPSN());
        ResponseEntity<Patient> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Patient.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient patient = responseEntity.getBody();
        patient.setAddress("jisoef");
        patient.setFirstName("tst");
        patient.setLastName("Updated");

        responseEntity = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(patient), Patient.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient updatedPatient = responseEntity.getBody();
        Assert.assertTrue(patient.getFirstName().equals(updatedPatient.getFirstName()));
        Assert.assertTrue(patient.getLastName().equals(updatedPatient.getLastName()));
        Assert.assertTrue(patient.getAddress().equals(updatedPatient.getAddress()));
    }

    @Test
    public void updatePatientDOB_thenDOBIsNotUpdated(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPPSN());
        ResponseEntity<Patient> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Patient.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient patient = responseEntity.getBody();
        patient.setDOB(Date.valueOf("2003-02-10"));

        responseEntity = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(patient), Patient.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient updatedPatient = responseEntity.getBody();
        Assert.assertTrue(!patient.getDOB().equals(updatedPatient.getDOB()));
    }

    @Test
    public void updatePatientPPSN_thenPPSNIsNotUpdated(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPPSN());
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Patient.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient patient = (Patient) responseEntity.getBody();
        patient.setPPSN("2345N");

        ResponseEntity<?> failedResponse = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(patient), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, failedResponse.getStatusCode());
    }

    @Test
    public void updateNonExistingPatient_thenPatientIsNotUpdatedOrCreated(){
        //validate patient is not in database
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", NEVER_GETS_ADDED_PATIENT.getPPSN());
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        //no need to actually update if code makes it this far as patient is not in database to begin with
        ResponseEntity<?> failedResponse = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(NEVER_GETS_ADDED_PATIENT), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, failedResponse.getStatusCode());

        //validate patient is not in database again
        headers = new HttpHeaders();
        headers.add("ppsn", NEVER_GETS_ADDED_PATIENT.getPPSN());
        ResponseEntity<?> responseEntity2 =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(responseEntity2.toString());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }
}
