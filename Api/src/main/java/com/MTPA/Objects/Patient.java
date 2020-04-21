package com.MTPA.Objects;

import com.MTPA.Objects.Reports.PatientMedication;
import com.MTPA.Objects.Reports.PatientCondition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "patient")
public class Patient implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

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
    @Column(name = "gender")
    private String gender;

    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
    private LocalDate dob;

    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dod")
    private LocalDate dod;

    //I don't want a setter here but jackson seems to insist on it must find a solution later
    @Setter
    @Getter
    @Column(name = "ppsn")
    private String ppsn;

    @Getter
    @Setter
    @Column(name = "address")
    private String address;

    @Getter
    @JsonIgnoreProperties(value = "patient", allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @Where(clause = "cured_on IS NULL")
    private Set<PatientCondition> patientConditions;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @JsonIgnoreProperties(value = {"patient", "reasonForMedication"}, allowSetters = true)
    @Where(clause = "(treatment_end > CURRENT_DATE() OR treatment_end IS NULL) AND type NOT LIKE 'immunization'")
    private Set<PatientMedication> currentMedication;

    public Patient(final String ppsn){
        this.ppsn = ppsn;
        patientConditions = new HashSet<PatientCondition>();
        currentMedication = new HashSet<PatientMedication>();
    }

    public Patient(){
        patientConditions = new HashSet<PatientCondition>();
        currentMedication = new HashSet<PatientMedication>();
    }

    public void addCondition(PatientCondition condition){
        patientConditions.add(condition);
    }

    public void addMedication(PatientMedication medication){
        currentMedication.add(medication);
    }

}
