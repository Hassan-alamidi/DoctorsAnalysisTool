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

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"patientConditions", "currentMedication"}, allowSetters = true)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties(value = {"patient", "condition","medication", "procedure"}, allowSetters = true)
    private Encounter encounter;
}
