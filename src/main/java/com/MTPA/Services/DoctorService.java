package com.MTPA.Services;

import com.MTPA.DAO.DoctorDAO;
import com.MTPA.Objects.Doctor;
import com.MTPA.Utility.Security.AuthorizationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final String SECRET;
    private final String TOKEN_PREFIX;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DoctorDAO doctorDAO;

    @Autowired
    public DoctorService(DoctorDAO doctorDAO, BCryptPasswordEncoder bCryptPasswordEncoder, @Value("${auth.secret}") final String secret,
                         @Value("${TOKEN_PREFIX}") final String tokenPrefix){
        this.doctorDAO = doctorDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.SECRET = secret;
        this.TOKEN_PREFIX = tokenPrefix;
    }

    @Override
    public UserDetails loadUserByUsername(String licenceNum) {
        Doctor doctor = doctorDAO.findByLicenceNumber(licenceNum);

        if (doctor == null) {
            throw new UsernameNotFoundException(licenceNum + " not found");
        }else if(bCryptPasswordEncoder.matches(TEMP_PASSWORD, doctor.getPassword())){
            throw new UsernameNotFoundException("Must change password");
            //throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password must be changed");
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
        Doctor retVal = doctorDAO.save(doctor);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    public ResponseEntity<?> passwordChange(Doctor doctor, String newPassword){
        if(newPassword == null || newPassword.trim().isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
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

    public ResponseEntity<?> getDoctorByLicenceNumber(String licenceNum){
        Doctor doctorDetails = doctorDAO.findByLicenceNumber(licenceNum);
        return new ResponseEntity<>(doctorDetails, HttpStatus.OK);
    }

    public ResponseEntity<?> getPersonalDetails(String token){
        String licenceNumber = getLicenceNumber(token);
        if(licenceNumber != null && !licenceNumber.isEmpty()){
            Doctor doctorDetails = doctorDAO.findByLicenceNumber(licenceNumber);
            return new ResponseEntity<>(doctorDetails, HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String getAuthority(String token){
        if (token != null) {
            // parse the token.
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));

            return decodedJWT.getClaim("rol").asString();
        }
        return null;
    }

    private String getLicenceNumber(String token){
        if (token != null) {
            // parse the token.
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));

            return decodedJWT.getSubject();
        }
        return null;
    }
}
