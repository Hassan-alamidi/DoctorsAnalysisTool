package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PatientMedication {

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
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "treatment_start")
    private Date treatmentStart;

    @Getter
    @Setter
    @Column(name = "treatment_end")
    private Date treatmentEnd;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    private Encounter encounter;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_for_medication")
    private PatientCondition reasonForMedication;

    @Getter
    @Setter
    @Column(name = "prescribed_amount")
    private double prescribedAmount;

    @Getter
    @Setter
    @Column(name = "unit_type")
    private String unitType;
}
