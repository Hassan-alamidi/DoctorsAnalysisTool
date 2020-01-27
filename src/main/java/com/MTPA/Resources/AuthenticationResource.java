package com.MTPA.Resources;

import com.MTPA.DAO.DoctorDAO;
import com.MTPA.Objects.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Authenticate")
public class AuthenticationResource {
//
//    private static final String TEMP_PASSWORD = "ToBeChanged";
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private DoctorDAO doctorDAO;
//
//    @Autowired
//    public AuthenticationResource(DoctorDAO doctorDAO, BCryptPasswordEncoder bCryptPasswordEncoder){
//        this.doctorDAO = doctorDAO;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    //this can only be done by doctor with administrative privileges
//    @PostMapping("/register")
//    public ResponseEntity<?> registerDoctor(@RequestBody Doctor doctor){
//        doctor.setPassword(bCryptPasswordEncoder.encode(TEMP_PASSWORD));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginDoctor(@RequestBody Doctor doctor){
//        if(doctor.getPassword().equals(TEMP_PASSWORD)){
//            return new ResponseEntity<>("Password must be changed", HttpStatus.UNAUTHORIZED);
//        }
//        Doctor retVal = doctorDAO.findByLicenceNumber(doctor.getMedicalLicenceNumber());
//        if(retVal != null && (doctor.getMedicalLicenceNumber() == retVal.getMedicalLicenceNumber() &&
//                bCryptPasswordEncoder.matches(doctor.getPassword(), retVal.getPassword()))){
//            return new ResponseEntity<>(retVal, HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Credentials Incorrect", HttpStatus.UNAUTHORIZED);
//    }
//
//    @PutMapping("/password")
//    public ResponseEntity<?> passwordReset(@RequestBody Doctor doctor, @RequestHeader String newPassword){
//        Doctor retVal = doctorDAO.findByLicenceNumber(doctor.getMedicalLicenceNumber());
//        //if the password is the temp password then we don't care about passed in old password
//        if(retVal == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }else if(bCryptPasswordEncoder.matches(TEMP_PASSWORD, retVal.getPassword())){
//            retVal.setPassword(bCryptPasswordEncoder.encode(newPassword));
//            doctorDAO.save(retVal);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }else if(bCryptPasswordEncoder.matches(doctor.getPassword(), retVal.getPassword())){
//            retVal.setPassword(bCryptPasswordEncoder.encode(newPassword));
//            doctorDAO.save(retVal);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }

}
