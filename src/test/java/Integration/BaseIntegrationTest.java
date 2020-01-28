package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Doctor;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    private static final String BASE_URI = "http://localhost:";
    protected static final String HEADER_STRING = "Authorization";
    protected static String ADMIN_TOKEN;
    protected static String NON_ADMIN_TOKEN;

    protected Patient EXISTING_PATIENT;
    protected Patient NEW_PATIENT;
    protected Patient NEVER_GETS_ADDED_PATIENT;
    protected HttpHeaders adminHeader = new HttpHeaders();
    protected HttpHeaders doctorHeader = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setupBeforeEach(){
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(BASE_URI + port);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        if(ADMIN_TOKEN == null) {
            Doctor doctor = new Doctor();
            doctor.setMedicalLicenceNumber("num1");
            doctor.setPassword("ToBeChanged");
            ResponseEntity<String> responseEntity =
                    restTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(doctor), String.class);
            NON_ADMIN_TOKEN = responseEntity.getHeaders().get(HEADER_STRING).get(0);

            doctor.setMedicalLicenceNumber("num331");
            responseEntity = restTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(doctor), String.class);
            ADMIN_TOKEN = responseEntity.getHeaders().get(HEADER_STRING).get(0);

        }
        adminHeader = generateHeader(ADMIN_TOKEN);
        doctorHeader = generateHeader(NON_ADMIN_TOKEN);
        patientCreation();
    }

    private HttpHeaders generateHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_STRING, token);
        return headers;
    }

    @SneakyThrows
    private void patientCreation(){
        EXISTING_PATIENT = Patient.builder()
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

    @Test
    public void checkServer(){
        ResponseEntity<String> response = restTemplate.getForEntity("/healthCheck", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
