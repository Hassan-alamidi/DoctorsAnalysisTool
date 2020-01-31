package com.MTPA.Services;

import com.MTPA.DAO.DoctorDAO;
import com.MTPA.Objects.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DoctorService implements UserDetailsService {

    //this will change to a randomly generated password in the future that will be emailed out to the doctor for them to change
    private static final String TEMP_PASSWORD = "ToBeChanged";
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DoctorDAO doctorDAO;

    @Autowired
    public DoctorService(DoctorDAO doctorDAO, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.doctorDAO = doctorDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String licenceNum) {
        Doctor doctor = doctorDAO.findByLicenceNumber(licenceNum);

        if (doctor == null) {
            throw new UsernameNotFoundException(licenceNum);
        }else if(bCryptPasswordEncoder.matches(TEMP_PASSWORD, doctor.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password must be changed");
        }

        return User.builder()
                .username(doctor.getMedicalLicenceNumber())
                .password(doctor.getPassword())
                .authorities(doctor.getPrivilegeLevel())
                .roles(doctor.getPrivilegeLevel())
                .build();
    }

    public ResponseEntity<?> registerDoctor(Doctor doctor){
        doctor.setPassword(bCryptPasswordEncoder.encode(TEMP_PASSWORD));
        doctorDAO.save(doctor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> passwordChange(Doctor doctor, String newPassword){
        Doctor retVal = doctorDAO.findByLicenceNumber(doctor.getMedicalLicenceNumber());
        //if the password is the temp password then we don't care about passed in old password
        if(retVal == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if(bCryptPasswordEncoder.matches(TEMP_PASSWORD, retVal.getPassword()) ||
                (doctor.getPassword() != null && bCryptPasswordEncoder.matches(doctor.getPassword(), retVal.getPassword()))){
            retVal.setPassword(bCryptPasswordEncoder.encode(newPassword));
            doctorDAO.save(retVal);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
