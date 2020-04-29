package com.MTPA.Utility.Security;

import com.MTPA.Objects.Doctor;
import com.MTPA.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private static final String REGISTER_DOCTOR = "/register";
    private static final String HEALTHCHECK = "/healthCheck";
    private static final String ADMIN_REGISTRATION = "/register/admin";
    private final String SECRET;
    private final String HEADER_STRING;
    private final String TOKEN_PREFIX;
    private final long EXPIRATION_TIME;

    private DoctorService doctorService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(DoctorService doctorService, BCryptPasswordEncoder bCryptPasswordEncoder, @Value("${auth.secret}") final String secret,
                       @Value("${TOKEN_HEADER}") final String headerString, @Value("${TOKEN_PREFIX}") final String tokenPrefix,
                       @Value("${EXPIRATION_TIME}") final long expiration) {
        this.doctorService = doctorService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.SECRET = secret;
        this.HEADER_STRING = headerString;
        this.TOKEN_PREFIX = tokenPrefix;
        this.EXPIRATION_TIME = expiration;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(doctorService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, REGISTER_DOCTOR).hasAuthority("ROLE_Admin")
                .antMatchers(HttpMethod.GET, "/colleague").hasAuthority("ROLE_Admin")
                .antMatchers(HttpMethod.GET, HEALTHCHECK).permitAll()
                .antMatchers(HttpMethod.PUT, "/password").permitAll()
                .antMatchers(HttpMethod.POST, ADMIN_REGISTRATION).permitAll()
                .anyRequest().authenticated().and()
                .addFilter(new AuthenticationFilter(authenticationManager(),SECRET,HEADER_STRING, TOKEN_PREFIX, EXPIRATION_TIME))
                .addFilter(new AuthorizationFilter(authenticationManager(), SECRET,HEADER_STRING, TOKEN_PREFIX))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST","PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));//Arrays.asList("X-Requested-With","X-Auth-Token", "X-HTTP-Method-Override", "Content-Type", "Accept", "Authorization", "Origin","Access-Control-Request-Method","Access-Control-Request-Headers"));
        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addExposedHeader("Authorization");
//        corsConfiguration.addExposedHeader("Set-Cookie");
//        corsConfiguration.addExposedHeader("X-Auth-Token");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

}
