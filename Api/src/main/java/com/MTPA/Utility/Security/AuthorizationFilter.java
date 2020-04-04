package com.MTPA.Utility.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final String SECRET;
    private final String HEADER_STRING;
    private final String TOKEN_PREFIX;

    @Autowired
    public AuthorizationFilter(AuthenticationManager authManager, final String secret,
                               final String headerString, final String tokenPrefix) {
        super(authManager);
        this.SECRET = secret;
        this.HEADER_STRING = headerString;
        this.TOKEN_PREFIX = tokenPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        String header = req.getHeader(HEADER_STRING);
        //if authorization is not present in header check cookies
        if(header == null && cookies != null){
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals(HEADER_STRING)){
                    header = cookie.getValue();
                }
            }
        }
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(header);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        System.out.println(token);
        if (token != null) {
            // parse the token.
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));

            String subject = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("rol").asString();

            if (subject != null) {
                return new UsernamePasswordAuthenticationToken(subject, null, Arrays.asList(new SimpleGrantedAuthority(role)));
            }
            return null;
        }
        return null;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            // parse the token.
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));

            String subject = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("rol").asString();

            if (subject != null) {
                return new UsernamePasswordAuthenticationToken(subject, null, Arrays.asList(new SimpleGrantedAuthority(role)));
            }
            return null;
        }
        return null;
    }
}
