package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Information.Condition;
import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
//patientConditions are medical patientConditions like allergies, illnesses
@Entity
@Table(name = "patient_condition")
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PatientCondition {

    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;

//    this might make retrieval of patient data bloated so just get reference to condition and name
//    and if further details needed another request can be made(may change back but unlikely)
//    @Getter
//    @Setter
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "condition_code", referencedColumnName = "condition_code")
//    private Condition condition;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "condition_code")
    private int conditionCode;

    @Getter
    @Setter
    @Column(name = "symptoms")
    private String symptoms;

    @Getter
    @Setter
    @Column(name = "details")
    private String details;

    @Getter
    @Setter
    @Column(name = "discovered")
    private Date discovered;

    @Getter
    @Setter
    @Column(name = "cured_on")
    private Date curedOn;

// don't think we need this as this might cause a circular dependency should just retrieve PPSN
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "patientConditions", allowSetters = true)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "PPSN")
    private Patient patient;

//    @Getter
//    @Setter
//    @Column(name = "patient_ppsn")
//    private String patientPPSN;

    //think the below should be a one to many as you can have many encounters for one condition
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties(value = {"patient","condition"}, allowSetters = true)
    private Encounter encounter;
}
