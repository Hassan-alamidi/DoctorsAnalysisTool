package com.MTPA.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@Table(name = "doctor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "medical_licence_number")
    private String medicalLicenceNumber;

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "dob")
    private LocalDate dob;

    //I don't want a setter here but jackson seems to insist on it must find a solution later
    @Setter
    @Getter
    @Column(name = "ppsn")
    private String ppsn;

    @Getter
    @Setter
    @Column
    private String privilegeLevel;

    @Getter
    @Setter
    @Column
    private String address;

    @Getter
    @Setter
    @Column
    private int phoneNumber;

    @Getter
    @Setter
    @Column
    private String password;

    @Getter
    @Setter
    @Transient
    private String accountMessages;

}
