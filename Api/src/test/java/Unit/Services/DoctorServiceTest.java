package Unit.Services;

import com.MTPA.DAO.DoctorDAO;
import com.MTPA.Objects.Doctor;
import com.MTPA.Services.DoctorService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class DoctorServiceTest {

    private static final String DEFAULT_PASSWORD_ENCODED = "$2a$10$N2mxboOvsAn56DA8Q.OeJ.ibE7UlRmQnLgbVvqZSj6YY8AqV.kdDq";
    private static final String DEFAULT_PASSWORD = "ToBeChanged";
    private static final String PASSWORD_ENCODED = "$2a$10$BxBGxoQ7Ui1wiS8p./.kZe3sRpW946z8AZVpE23DVEiBsgV9jVG3u";
    private static final String PASSWORD = "notDefault";
    private Doctor doctor, doctorForLogin;
    private DoctorService doctorService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${auth.secret}")
    private String secret;
    @Value("${TOKEN_PREFIX}")
    private String tokenPrefix;

    @Mock
    private DoctorDAO doctorDAO;

    @SneakyThrows
    @Before
    public void setup(){
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        doctorService = new DoctorService(doctorDAO, bCryptPasswordEncoder, secret, tokenPrefix);
        doctor = Doctor.builder()
                .firstName("testDoc")
                .dob(LocalDate.parse("1972-03-06"))
                .ppsn("ppssn")
                .lastName("t")
                .password("notDefault")
                .medicalLicenceNumber("licence234")
                .address("54 54 54 dew lane")
                .privilegeLevel("User")
                .phoneNumber(78789)
                .build();
        doctorForLogin = Doctor.builder()
                .password("notDefault")
                .medicalLicenceNumber("licence234")
                .build();

    }

    @Test
    public void nonExistingDoctorTriesToLogsIn_thenUsernameNotFoundException(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(null);
        try {
            doctorService.loadUserByUsername(doctor.getMedicalLicenceNumber());
            fail();
        }catch (Exception e){
            Assert.assertEquals(doctor.getMedicalLicenceNumber() + " not found", e.getMessage());
        }
    }

    @Test
    public void doctorWithNewPasswordLogsIn_thenUserDetailsReturned(){
        doctor.setPassword(PASSWORD_ENCODED);
        doctorForLogin.setPassword(PASSWORD);

        UserDetails expectedUser = User.builder()
                .username(doctor.getMedicalLicenceNumber())
                .password(doctor.getPassword())
                .authorities(doctor.getPrivilegeLevel())
                .roles(doctor.getPrivilegeLevel())
                .build();

        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);

        try {
            UserDetails actualUser = doctorService.loadUserByUsername(doctorForLogin.getMedicalLicenceNumber());
            Assert.assertEquals(expectedUser, actualUser);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void registerDoctor_thenDoctorWithDefaultPasswordReturned(){
        doctor.setPassword(null);
        when(doctorDAO.save(any(Doctor.class))).thenReturn(doctor);
        ResponseEntity<?> responseEntity = doctorService.registerDoctor(doctor);

        Doctor result = (Doctor) responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(bCryptPasswordEncoder.matches(DEFAULT_PASSWORD, result.getPassword()));
    }

    @Test
    public void changePasswordNonExistentDoctor_thenNotFound(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(null);
        ResponseEntity<?> responseEntity = doctorService.passwordChange("doctor","", "newPass");
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void changeNonDefaultPasswordWithOriginalPasswordNotSupplied_thenUnauthorized(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        doctor.setPassword(null);
        ResponseEntity<?> responseEntity = doctorService.passwordChange("doctor","", "newPass");
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void changePasswordWithNoNewPasswordSupplied_thenUnprocessableEntity(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        ResponseEntity<?> responseEntity = doctorService.passwordChange("doctor", "oldPassword","");
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());

    }

    @Test
    public void changeNonDefaultPasswordWithIncorrectOriginalPassword_thenUnauthorized(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        //doctorForLogin.setPassword("incorrectPass");
        ResponseEntity<?> responseEntity = doctorService.passwordChange("doctorForLogin","incorrectPass", "newPass");
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void changeDefaultPasswordWithoutOriginalPassword_thenOK(){
        String newPassword = "newPass";
        doctor.setPassword(DEFAULT_PASSWORD_ENCODED);
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        //doctorForLogin.setPassword(null);
        ResponseEntity<?> responseEntity = doctorService.passwordChange("doctorForLogin","", newPassword);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void changeNonDefaultPasswordWithOriginalPassword_thenOk(){
        String newPassword = "newPass";
        doctor.setPassword(PASSWORD_ENCODED);
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        ResponseEntity<?> responseEntity = doctorService.passwordChange("doctorForLogin", PASSWORD, newPassword);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    //failures for this cannot be testes in unit tests but are tested in integration tests
    @Test
    public void getDoctorByLicenceNumber_thenOK(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        ResponseEntity<?> responseEntity = doctorService.getDoctorByLicenceNumber(doctor.getMedicalLicenceNumber());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    public void doctorGetsPersonalDetails_thenOk(){
        when(doctorDAO.findByLicenceNumber(any())).thenReturn(doctor);
        ResponseEntity<?> responseEntity = doctorService.getPersonalDetails("");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    //don't know how to create a semi valid token in tests so failure testing for get personal details will have to wait
}
