package com.MTPA.Objects;

import com.MTPA.Objects.Reports.PatientMedication;
import com.MTPA.Objects.Reports.PatientCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
public class Patient {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "dob")
    private Date DOB;

    @Getter
    @Column(name = "ppsn")
    private String PPSN;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient_ppsn")
    private List<PatientCondition> patientConditions;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient_ppsn")
    private List<PatientMedication> currentMedication;

    @Getter
    @Setter
    @Column(name = "address")
    private String address;

    @Getter
    @Setter
    @Column(name = "mother_ppsn")
    private int motherPPSN;

    @Getter
    @Setter
    @Column(name = "father_ppsn")
    private int fatherPPSN;

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
