package Unit.Services;

import com.MTPA.DAO.PatientDAO;
import com.MTPA.Objects.Patient;
import com.MTPA.Services.PatientServices;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PatientServiceTest {

    private Patient patient;
    private Patient patientDuplicate;

    @Mock
    PatientDAO patientDAO;

    @InjectMocks
    PatientServices patientServices;

    @SneakyThrows
    @Before
    public void setup() {
        patient = Patient.builder()
                .firstName("test")
                .address("anyAddress")
                .ppsn("ppsn")
                .lastName("test")
                .dob(new SimpleDateFormat("yyyy-MM-dd").parse("1984-02-12"))
                .id(1)
                .build();
        patientDuplicate = Patient.builder()
                .firstName("test")
                .address("anyAddress")
                .ppsn("ppsn")
                .lastName("test")
                .dob(new SimpleDateFormat("yyyy-MM-dd").parse("1984-02-12"))
                .id(1)
                .build();
    }

    @Test
    public void patientNotInDatabaseGetsUpdated_thenResponse404(){
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(null);
        ResponseEntity<?> responseEntity = patientServices.updatePatient(patient);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void patientInDatabaseGetsUpdated_thenResponseUpdatedPatientAnd200(){
        patientDuplicate.setFirstName("hello");
        when(patientDAO.findByPPSN(patient.getPpsn())).thenReturn(patient);
        when(patientDAO.save(patientDuplicate)).thenReturn(patientDuplicate);
        try{
            ResponseEntity<?> responseEntity = patientServices.updatePatient(patient);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }catch (ResponseStatusException ex){
            fail();
        }
    }

    @Test
    public void patientGetsPPSNUpdated_thenNotFound(){
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(null);
        try{
            ResponseEntity<?> responseEntity = patientServices.updatePatient(patient);
            Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }catch (ResponseStatusException ex){
            fail();
        }
    }

    @Test
    @SneakyThrows
    public void patientGetsDOBUpdated_thenUnprocessableEntity(){
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(patient);
        patientDuplicate.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1803-02-02"));
        try{
            ResponseEntity<?> responseEntity = patientServices.updatePatient(patientDuplicate);
            Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        }catch (ResponseStatusException ex){
            fail();
        }
    }

    @Test
    public void createPatientWithUniquePPSN_thenPatientIsCreatedAnd201(){
        when(patientDAO.exists(any(String.class))).thenReturn(false);
        when(patientDAO.save(any(Patient.class))).thenReturn(patient);
        try{
            ResponseEntity<?> responseEntity = patientServices.createPatient(patient);
            Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        }catch (ResponseStatusException ex){
            fail();
        }
    }

    @Test
    public void createPatientWithNonUniquePPSN_thenPatientIsNotCreatedAnd422(){
        when(patientDAO.exists(any(String.class))).thenReturn(true);
        try{
            ResponseEntity<?> responseEntity = patientServices.createPatient(patient);
            Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        }catch (ResponseStatusException ex){
            fail();
        }
    }

    @Test
    public void getExistingPatient(){
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(patient);
        ResponseEntity<?> responseEntity = patientServices.getPatient(patient.getPpsn());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void getNonExistingPatient(){
        when(patientDAO.findByPPSN(any(String.class))).thenReturn(null);
        ResponseEntity<?> responseEntity = patientServices.getPatient(patient.getPpsn());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
