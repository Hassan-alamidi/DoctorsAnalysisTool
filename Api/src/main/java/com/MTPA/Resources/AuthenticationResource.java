package com.MTPA.Resources;

import com.MTPA.Objects.Doctor;
import com.MTPA.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationResource {

    private DoctorService doctorService;

    @Autowired
    public AuthenticationResource(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    //this can only be done by doctor with administrative privileges
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody Doctor doctor){
        return doctorService.registerDoctor(doctor);
    }

    @PutMapping("/password")
    public ResponseEntity<?> passwordChange(@RequestBody Doctor doctor, @RequestHeader String newPassword){
        return doctorService.passwordChange(doctor,newPassword);
    }

    @GetMapping("/colleague")
    public ResponseEntity<?> getDoctorByLicenceNumber(@RequestHeader("licenceNumber") final String licenceNumber){
        return doctorService.getDoctorByLicenceNumber(licenceNumber);
    }

    @GetMapping("/personal-details")
    public ResponseEntity<?> getDoctorsPersonalDetails(@RequestHeader("Authorization") final String token){
        return doctorService.getPersonalDetails(token);
    }

}
