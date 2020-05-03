package com.MTPA.Resources;

import com.MTPA.Objects.Doctor;
import com.MTPA.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@RestController
public class DoctorsResource {

    private DoctorService doctorService;

    @Autowired
    public DoctorsResource(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    //this endpoint is used to register the very first doctor as an admin afterwards this endpoint will do nothing
    @PostMapping("/register/admin")
    public ResponseEntity<Doctor> registerAdmin(@RequestBody final Doctor doctor){
        return doctorService.registerAdmin(doctor);
    }

    //this can only be done by doctor with administrative privileges
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor doctor){
        return doctorService.registerDoctor(doctor);
    }

    @PutMapping("/password")
    public ResponseEntity<?> passwordChange(@CookieValue("Authorization") final String token, @RequestHeader String oldPassword, @RequestHeader String newPassword){
        return doctorService.passwordChange(token, oldPassword, newPassword);
    }

    @GetMapping("/colleague")
    public ResponseEntity<?> getDoctorByLicenceNumber(@RequestHeader("licenceNumber") final String licenceNumber){
        return doctorService.getDoctorByLicenceNumber(licenceNumber);
    }

    @GetMapping("/personal-details")
    public ResponseEntity<?> getDoctorsPersonalDetails(@CookieValue("Authorization") final String token){
        return doctorService.getPersonalDetails(token);
    }

}
