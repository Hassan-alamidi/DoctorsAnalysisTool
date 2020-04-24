package com.MTPA.Services;

import com.MTPA.DAO.DoctorDAO;
import com.MTPA.Objects.Doctor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
public class DoctorService implements UserDetailsService {

    //this will change to a randomly generated password in the future that will be emailed out to the doctor for them to change
    private static final String TEMP_PASSWORD = "ToBeChanged";
    private final String secret;
    private final String tokenPrefix;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DoctorDAO doctorDAO;

    @Autowired
    public DoctorService(final DoctorDAO doctorDAO, final BCryptPasswordEncoder bCryptPasswordEncoder,
                         @Value("${auth.secret}") final String secret, @Value("${TOKEN_PREFIX}") final String tokenPrefix){
        this.doctorDAO = doctorDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secret = secret;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    public UserDetails loadUserByUsername(final String licenceNum) {
        Doctor doctor = doctorDAO.findByLicenceNumber(licenceNum);

        if (doctor == null) {
            throw new UsernameNotFoundException(licenceNum + " not found");
        }else if(bCryptPasswordEncoder.matches(TEMP_PASSWORD, doctor.getPassword())){
            throw new UsernameNotFoundException("Must change password");
        }

        return User.builder()
                .username(doctor.getMedicalLicenceNumber())
                .password(doctor.getPassword())
                .authorities(doctor.getPrivilegeLevel())
                .roles(doctor.getPrivilegeLevel())
                .build();
    }

    public ResponseEntity<?> registerDoctor(final Doctor doctor){
        doctor.setPassword(bCryptPasswordEncoder.encode(TEMP_PASSWORD));
        Doctor retVal = doctorDAO.save(doctor);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> passwordChange(final String token, final String oldPassword, final String newPassword){
        if(newPassword == null || newPassword.trim().isEmpty() || oldPassword == null){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Doctor retVal = doctorDAO.findByLicenceNumber(getLicenceNumber(token));

        //if the password is the temp password then we don't care about passed in old password
        if(retVal == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if(bCryptPasswordEncoder.matches(TEMP_PASSWORD, retVal.getPassword()) ||
                (bCryptPasswordEncoder.matches(oldPassword, retVal.getPassword()))){
            retVal.setPassword(bCryptPasswordEncoder.encode(newPassword));
            doctorDAO.save(retVal);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Doctor> getDoctorByLicenceNumber(final String licenceNum){
        Doctor doctorDetails = doctorDAO.findByLicenceNumber(licenceNum);
        //do not return password
        if(doctorDetails == null){
            return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
        }
        doctorDetails.setPassword("");
        return new ResponseEntity<Doctor>(doctorDetails, HttpStatus.OK);
    }

    public ResponseEntity<?> getPersonalDetails(final String token){
        String licenceNumber = getLicenceNumber(token);
        if(licenceNumber != null && !licenceNumber.isEmpty()){
            Doctor doctorDetails = doctorDAO.findByLicenceNumber(licenceNumber);
            //do not return password
            doctorDetails.setPassword("");
            return new ResponseEntity<>(doctorDetails, HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String getAuthority(final String token){
        if (token != null) {
            // parse the token.
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secret.getBytes())).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(tokenPrefix, ""));

            return decodedJWT.getClaim("rol").asString();
        }
        return null;
    }

    private String getLicenceNumber(final String token){
        try{
        if (token != null) {
            // parse the token.
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secret.getBytes())).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(tokenPrefix, ""));

            return decodedJWT.getSubject();
        }
        }catch(JWTDecodeException e){
            System.out.println(e);
        }
        return null;
    }
}
