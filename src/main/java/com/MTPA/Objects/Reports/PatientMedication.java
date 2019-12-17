package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

public class PatientMedication {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Date treatmentStart;

    @Getter
    @Setter
    private Date treatmentEnd;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Encounter encounter;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private PatientCondition targetCondition;

    @Getter
    @Setter
    private double prescribedAmount;

    @Getter
    @Setter
    private String unitType;
}
