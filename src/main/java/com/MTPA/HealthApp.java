package com.MTPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories("com.MTPA.DAO")
public class HealthApp {

    public static void main(String[] args){
        SpringApplication.run(HealthApp.class, args);
    }

    @RestController
    public class healthCheck{
        @RequestMapping("/healthCheck")
        public ResponseEntity healthCheck(){
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
