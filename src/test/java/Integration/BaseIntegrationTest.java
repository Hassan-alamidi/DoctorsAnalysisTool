package Integration;

import com.MTPA.HealthApp;
import com.MTPA.Objects.Patient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    private static final String BASE_URI = "http://localhost:";
    protected final Patient EXISTING_PATIENT;
    protected final Patient NEVER_GETS_ADDED_PATIENT;

    @LocalServerPort
    private int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected BaseIntegrationTest(){
        EXISTING_PATIENT = Patient.builder()
                .firstName("Test")
                .lastName("Subject")
                .DOB(Date.valueOf("1990-10-19"))
                .PPSN("87937M")
                .address("Our Lab")
                .fatherPPSN("UNKNOWN")
                .motherPPSN("UNKNOWN")
                .build();
        NEVER_GETS_ADDED_PATIENT = Patient.builder()
                .firstName("OffThe")
                .lastName("Books")
                .DOB(Date.valueOf("1995-12-28"))
                .PPSN("5889M")
                .address("Test Tube")
                .fatherPPSN("UNKNOWN")
                .motherPPSN("UNKNOWN")
                .build();
    }

    @Before
    public void setup(){
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(BASE_URI + port);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);

    }

    @Test
    public void checkServer(){
        ResponseEntity<String> response = restTemplate.getForEntity("/healthCheck", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
