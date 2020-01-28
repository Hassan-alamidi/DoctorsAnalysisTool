package com.MTPA.Services;

import com.MTPA.DAO.DoctorDAO;
import com.MTPA.Objects.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class DoctorService implements UserDetailsService {

    private static final String TEMP_PASSWORD = "ToBeChanged";
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DoctorDAO doctorDAO;

    @Autowired
    public DoctorService(DoctorDAO doctorDAO, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.doctorDAO = doctorDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String licenceNum) throws UsernameNotFoundException {
        Doctor doctor = doctorDAO.findByLicenceNumber(licenceNum);
        if (doctor == null) {
            throw new UsernameNotFoundException(licenceNum);
        }

        return User.builder()
                .username(doctor.getMedicalLicenceNumber())
                .password(doctor.getPassword())
                .authorities(doctor.getPrivilegeLevel())
                .roles(doctor.getPrivilegeLevel())
                .build();
    }

}
