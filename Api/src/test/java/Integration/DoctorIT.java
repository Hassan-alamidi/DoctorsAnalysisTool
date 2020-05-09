package Integration;

import com.MTPA.Objects.Doctor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

public class DoctorIT extends BaseIT {

    private static final String REGISTER_ENDPOINT = "/register";
    private static final String PASSWORD_CHANGE_ENDPOINT = "/password";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String DOCTOR_LICENCE_NUM_WITH_DEFAULT_PASSWORD = "num4";
    private static final String NON_DEFAULT_PASSWORD = "notDefault";
    private static final String DEFAULT_PASSWORD = "ToBeChanged";

    public void setupTest(){}

    @Test
    public void registerDoctorWithoutAdminPrivileges_thenDoctorNotCreatedAndForbiddenReturned(){
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(REGISTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(NEVER_GETS_ADDED_DOCTOR, doctorHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void registerDoctorWithAdminPrivileges_ThenDoctorIsCreated(){
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(REGISTER_ENDPOINT, HttpMethod.POST, new HttpEntity<>(DOCTOR_GETS_ADDED_TO_DB, adminHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void registerAdminThroughFirstUserEndpointWhenNotFirstUser_ThenForbiddenReturned(){
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(REGISTER_ENDPOINT + "/admin", HttpMethod.POST, new HttpEntity<>(NEVER_GETS_ADDED_DOCTOR, adminHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

//    @Test
//    public void changeDefaultPasswordWithoutSupplyingCurrentPassword(){
//        Doctor doctor = new Doctor();
//        doctor.setMedicalLicenceNumber(DOCTOR_LICENCE_NUM_WITH_DEFAULT_PASSWORD);
//        doctorHeader.add("newPassword", "newPass");
//        ResponseEntity<String> responseEntity =
//                restTemplate.exchange("/setup/" + PASSWORD_CHANGE_ENDPOINT, HttpMethod.PUT, new HttpEntity<>(doctorHeader), String.class);
//        System.out.println(responseEntity.toString());
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

    @Test
    public void changePasswordWithoutSupplyingCurrentPassword_thenFail(){
        doctorHeader.add("newPassword", "newPass");
        doctorHeader.add("oldPassword", "");
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(PASSWORD_CHANGE_ENDPOINT, HttpMethod.PUT, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void changePasswordWithIncorrectCurrentPassword_thenFail(){
        doctorHeader.add("newPassword", "newPass");
        doctorHeader.add("oldPassword", "incorrectPass");
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(PASSWORD_CHANGE_ENDPOINT, HttpMethod.PUT, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void changePasswordWithCorrectCurrentPassword(){
        doctorHeader.add("newPassword", "newPass");
        doctorHeader.add("oldPassword", NON_DEFAULT_PASSWORD);
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(PASSWORD_CHANGE_ENDPOINT, HttpMethod.PUT, new HttpEntity<>(doctorHeader), String.class);
        System.out.println(responseEntity.toString());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

//    @Test
//    public void changePasswordWithLicenceNumberThatDoesNotExist_thenFail(){
//        Doctor doctor = new Doctor();
//        doctor.setMedicalLicenceNumber("jiseknulkk");
//        //HttpHeaders headers = new HttpHeaders();
//        headers.add("newPassword", "newPass");
//        ResponseEntity<String> responseEntity =
//                restTemplate.exchange(PASSWORD_CHANGE_ENDPOINT, HttpMethod.PUT, new HttpEntity<>(doctor, headers), String.class);
//        System.out.println(responseEntity.toString());
//        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//    }

    @Test
    public void loginToAccountWithIncorrectPassword_thenNotLoggedIn(){
        Doctor doctor = new Doctor();
        doctor.setMedicalLicenceNumber(NON_ADMIN_LICENCE_NUM);
        doctor.setPassword("incorrectPass");
        ResponseEntity<String> responseEntity = restTemplate.exchange(LOGIN_ENDPOINT, HttpMethod.POST, new HttpEntity<>(doctor), String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void loginToAccount(){
        Doctor doctor = new Doctor();
        doctor.setMedicalLicenceNumber(NON_ADMIN_LICENCE_NUM);
        doctor.setPassword(NON_DEFAULT_PASSWORD);
        ResponseEntity<String> responseEntity = restTemplate.exchange(LOGIN_ENDPOINT, HttpMethod.POST, new HttpEntity<>(doctor), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void doctorGetsTheirOwnInformation(){
        doctorHeader.add("Cookie", "Authorization="+ NON_ADMIN_TOKEN);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/personal-details", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void doctorTriesToGetColleaguesInformation(){
        doctorHeader.add("licenceNumber", ADMIN_LICENCE_NUM);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/colleague", HttpMethod.GET, new HttpEntity<>(doctorHeader), String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void AdminGetsColleaguesInformation(){
        adminHeader.add("licenceNumber", NON_ADMIN_LICENCE_NUM);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/colleague", HttpMethod.GET, new HttpEntity<>(adminHeader), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void AdminTriesToGetNonExistentColleaguesInformation(){
        adminHeader.add("licenceNumber", "FakeLicence");
        ResponseEntity<String> responseEntity = restTemplate.exchange("/colleague", HttpMethod.GET, new HttpEntity<>(adminHeader), String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
