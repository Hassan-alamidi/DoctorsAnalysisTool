package com.MTPA.Utility.Security;

import com.MTPA.Objects.Doctor;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String secret;
    private final String headerString;
    private final String tokenPrefix;
    private final long expirationTime;
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(final AuthenticationManager authenticationManager, final String secret,
                                final String headerString, final String tokenPrefix, final long expiration){
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.headerString = headerString;
        this.tokenPrefix = tokenPrefix;
        this.expirationTime = expiration;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Doctor creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Doctor.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getMedicalLicenceNumber(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Optional<GrantedAuthority> authority = ((User) auth.getPrincipal()).getAuthorities().stream().findAny();
        String role = authority.isPresent() ? authority.get().getAuthority() : "ROLE_User";
        String token = JWT.create()
                .withClaim("rol", role)
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(HMAC512(secret.getBytes()));
        res.addHeader(headerString, tokenPrefix + token);
    }
}
