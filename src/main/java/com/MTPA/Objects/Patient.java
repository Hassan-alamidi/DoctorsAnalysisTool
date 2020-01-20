package com.MTPA.Objects;

import com.MTPA.Objects.Reports.PatientMedication;
import com.MTPA.Objects.Reports.PatientCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "dob")
    private Date DOB;

    //I don't want a setter here but jackson seems to insist on it must find a solution later
    @Setter
    @Getter
    @Column(name = "ppsn")
    private String PPSN;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patientPPSN")
    private List<PatientCondition> patientConditions;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private List<PatientMedication> currentMedication;

    @Getter
    @Setter
    @Column(name = "address")
    private String address;

    @Getter
    @Setter
    @Column(name = "mother_ppsn")
    private String motherPPSN;

    @Getter
    @Setter
    @Column(name = "father_ppsn")
    private String fatherPPSN;

    public Patient(final String PPSN){
        this.PPSN = PPSN;
        patientConditions = new ArrayList<PatientCondition>();
        currentMedication = new ArrayList<PatientMedication>();
    }

    public Patient(){
        patientConditions = new ArrayList<PatientCondition>();
        currentMedication = new ArrayList<PatientMedication>();
    }

    public void addCondition(PatientCondition condition){
        patientConditions.add(condition);
    }

    public void addMedication(PatientMedication medication){
        currentMedication.add(medication);
    }

}
