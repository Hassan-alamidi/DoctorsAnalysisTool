package com.MTPA.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
    private Date DOB;

    //I don't want a setter here but jackson seems to insist on it must find a solution later
    @Setter
    @Getter
    @Column(name = "ppsn")
    private String PPSN;

    @Getter
    @Setter
    private String privilegeLevel;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private int phoneNumber;

    @Getter
    @Setter
    private String password;

    //TODO find better solution to jsonIgnoreProperties as it still loads all of the ignored properties
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @JsonIgnoreProperties("hiredDoctors")
    private Organization workPlace;
}
