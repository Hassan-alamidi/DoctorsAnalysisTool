package Integration;

import com.MTPA.Objects.Patient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import java.sql.Date;

public class PatientIntegrationTest extends BaseIntegrationTest{

    private static final String BASE_ENDPOINT = "/patient";

    public void setupTest(){}

    @Test
    public void createNonExistingPatient(){
        ResponseEntity<Patient> createdPatient =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(NEW_PATIENT, doctorHeader), Patient.class);
        Assert.assertEquals(HttpStatus.CREATED, createdPatient.getStatusCode());

        doctorHeader.add("ppsn", NEW_PATIENT.getPpsn());
        ResponseEntity<Patient> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), Patient.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(responseEntity.getBody().getAddress(),NEW_PATIENT.getAddress());
    }

    @Test
    public void createExistingPatient_thenPatientIsNotCreated(){
        //check still exists
        doctorHeader.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.OK, testValid.getStatusCode());
        //try to create duplicate
        System.out.println("Attempting to create duplicate");
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(EXISTING_PATIENT, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void createPatientWithPreExistingPPSN_thenPatientIsNotCreatedAndPreExistingPatientIsNotUpdated(){
        //check still exists
        doctorHeader.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.OK, testValid.getStatusCode());
        //try to create duplicate
        System.out.println("Attempting to create patient with PPSN number of which has already been taken");
        NEVER_GETS_ADDED_PATIENT.setPpsn(EXISTING_PATIENT.getPpsn());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(NEVER_GETS_ADDED_PATIENT, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void createPatientWithoutFirstName_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPpsn("93hjoaj");
        patient.setFirstName(null);

        ResponseEntity<?> createdPatient =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(patient, doctorHeader), String.class);

        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        doctorHeader.add("ppsn", patient.getPpsn());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createPatientWithoutLastName_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPpsn("935hjoaj");
        patient.setLastName(null);

        ResponseEntity<?> createdPatient =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(patient, doctorHeader), String.class);

        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        doctorHeader.add("ppsn", patient.getPpsn());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createPatientWithoutDOB_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPpsn("933hjoaj");
        patient.setDob(null);

        ResponseEntity<?> createdPatient =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(patient, doctorHeader), String.class);

        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        doctorHeader.add("ppsn", patient.getPpsn());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createPatientWithoutPPSN_ThenFail(){
        //ensure tests do not interfere with eachother
        Patient patient = NEVER_GETS_ADDED_PATIENT;
        patient.setPpsn("993hjoaj");
        patient.setPpsn(null);

        ResponseEntity<?> createdPatient =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.POST, new HttpEntity<Patient>(patient, doctorHeader), String.class);

        System.out.println(createdPatient.toString());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createdPatient.getStatusCode());

        doctorHeader.add("ppsn", patient.getPpsn());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getExistingPatient(){
        doctorHeader.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.OK, testValid.getStatusCode());
    }

    @Test
    public void getNonExistingPatient_thenPatientNotFound(){
        doctorHeader.add("ppsn", NEVER_GETS_ADDED_PATIENT.getPpsn());
        ResponseEntity<String> testValid =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(testValid.toString());
        Assert.assertEquals(HttpStatus.NOT_FOUND, testValid.getStatusCode());
    }

    @Test
    public void updatePatientDetailsExceptDOBAndPPSN(){
        doctorHeader.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<Patient> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), Patient.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient patient = responseEntity.getBody();
        patient.setAddress("jisoef");
        patient.setFirstName("tst");
        patient.setLastName("Updated");

        responseEntity = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(patient,doctorHeader), Patient.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient updatedPatient = responseEntity.getBody();
        Assert.assertTrue(patient.getFirstName().equals(updatedPatient.getFirstName()));
        Assert.assertTrue(patient.getLastName().equals(updatedPatient.getLastName()));
        Assert.assertTrue(patient.getAddress().equals(updatedPatient.getAddress()));
    }

    @Test
    public void updatePatientDOB_thenDOBIsNotUpdated(){
        doctorHeader.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<Patient> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), Patient.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient patient = responseEntity.getBody();
        patient.setDob(Date.valueOf("2003-02-10"));

        ResponseEntity<String> failedResponse = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(patient, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, failedResponse.getStatusCode());
    }

    @Test
    public void updatePatientPPSN_thenPPSNIsNotUpdated(){
        doctorHeader.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), Patient.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Patient patient = (Patient) responseEntity.getBody();
        patient.setPpsn("2345N");

        ResponseEntity<?> failedResponse = restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(patient, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, failedResponse.getStatusCode());
    }

    @Test
    public void updateNonExistingPatient_thenPatientIsNotUpdatedOrCreated(){
        //validate patient is not in database
        doctorHeader.add("ppsn", NEVER_GETS_ADDED_PATIENT.getPpsn());
        ResponseEntity<?> responseEntity =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        //no need to actually update if code makes it this far as patient is not in database to begin with
        ResponseEntity<?> failedResponse =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.PUT, new HttpEntity<Patient>(NEVER_GETS_ADDED_PATIENT, doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, failedResponse.getStatusCode());

        //validate patient is not in database again
        ResponseEntity<?> responseEntity2 =
                restTemplate.exchange(BASE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(responseEntity2.toString());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity2.getStatusCode());
    }

    @Test
    public void getPatientWhileLoggedOut_thenFail(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("ppsn", EXISTING_PATIENT.getPpsn());
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("/patient", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    //not sure how to get a hold of expiredToken generating manually seems to fail so may need to mock
    public void updatePatientWithExpiredToken(){}
}
