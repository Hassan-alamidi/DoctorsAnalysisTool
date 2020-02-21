package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Doctor;
import com.MTPA.Objects.Organization;
import com.MTPA.Objects.Patient;
import com.auth0.jwt.JWT;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Properties;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    protected static final String BASE_URI = "http://localhost:";
    protected static final String LOGIN_ENDPOINT = "/login";
    protected static final String HEADER_STRING = "Authorization";
    protected static final String ADMIN_LICENCE_NUM = "num1";
    protected static final String NON_ADMIN_LICENCE_NUM = "num331";
    protected static final String REAL_PATIENT_PPSN = "87937M";
    protected static final String REAL_PATIENT_WITH_NOTHING_PPSN = "87937N";
    protected static String ADMIN_TOKEN;
    protected static String NON_ADMIN_TOKEN;

    protected Patient EXISTING_PATIENT;
    protected Patient NEW_PATIENT;
    protected Patient NEVER_GETS_ADDED_PATIENT;
    protected Doctor NEVER_GETS_ADDED_DOCTOR;
    protected Doctor DOCTOR_GETS_ADDED_TO_DB;
    protected Organization organization;
    protected HttpHeaders adminHeader = new HttpHeaders();
    protected HttpHeaders doctorHeader = new HttpHeaders();

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setupBeforeEach(){
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(BASE_URI + port);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        if(ADMIN_TOKEN == null) {
            Doctor doctor = Doctor.builder()
                    .MedicalLicenceNumber(ADMIN_LICENCE_NUM)
                    .password("notDefault")
                    .build();
            ADMIN_TOKEN = getToken(doctor);

            doctor.setMedicalLicenceNumber(NON_ADMIN_LICENCE_NUM);
            NON_ADMIN_TOKEN = getToken(doctor);
        }
        adminHeader = generateHeader(ADMIN_TOKEN);
        doctorHeader = generateHeader(NON_ADMIN_TOKEN);
        patientCreation();
        doctorCreation();
        setupOrganization();
        setupTest();
    }

    abstract void setupTest();

    private HttpHeaders generateHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_STRING, token);
        return headers;
    }

    @SneakyThrows
    private void patientCreation(){
        EXISTING_PATIENT = Patient.builder()
                .id(1)
                .firstName("Test")
                .lastName("Subject")
                .DOB(new SimpleDateFormat("yyyy-MM-dd").parse("1990-10-19"))
                .PPSN("87937M")
                .address("Our Lab")
                .fatherPPSN("UNKNOWN")
                .motherPPSN("UNKNOWN")
                .build();
        NEVER_GETS_ADDED_PATIENT = Patient.builder()
                .firstName("OffThe")
                .lastName("Books")
                .DOB(new SimpleDateFormat("yyyy-MM-dd").parse("1995-12-28"))
                .PPSN("5889M")
                .address("Test Tube")
                .fatherPPSN("UNKNOWN")
                .motherPPSN("UNKNOWN")
                .build();
        NEW_PATIENT = Patient.builder()
                .firstName("john")
                .lastName("doe")
                .DOB(new SimpleDateFormat("yyyy-MM-dd").parse("1995-12-28"))
                .PPSN("123N")
                .address("123 easy street")
                .build();
    }

    private void doctorCreation(){
        Organization organization = new Organization();
        organization.setId(1);
        NEVER_GETS_ADDED_DOCTOR = Doctor.builder()
                .address("iajd")
                .DOB(Date.valueOf("1999-08-09"))
                .firstName("Tom")
                .lastName("jife")
                .MedicalLicenceNumber("986987M")
                .privilegeLevel("User")
                .PPSN("PPPPPPSSSSSNNNNN")
                .phoneNumber(9899)
                .password("anyPass")
                .workPlace(organization)
                .build();
        DOCTOR_GETS_ADDED_TO_DB = Doctor.builder()
                .address("iajd")
                .DOB(Date.valueOf("1999-08-09"))
                .firstName("Tim")
                .lastName("jife")
                .MedicalLicenceNumber("72648987N")
                .privilegeLevel("User")
                .PPSN("PP1224454SSSNNN")
                .phoneNumber(9899)
                .password("anyPass")
                .workPlace(organization)
                .build();

    }

    public void setupOrganization(){
        organization = new Organization();
        organization.setId(1);
        organization.setAddress("pain in the ass");
        organization.setName("testHospt");
        organization.setPhoneNumber(9774);
    }

    private String getToken(Doctor doctor){
        ResponseEntity<String> responseEntity = restTemplate.exchange(LOGIN_ENDPOINT, HttpMethod.POST, new HttpEntity<>(doctor), String.class);
        return responseEntity.getHeaders().get(HEADER_STRING).get(0);
    }

    @Test
    public void checkServer(){
        ResponseEntity<String> response = restTemplate.getForEntity("/healthCheck", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
