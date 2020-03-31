package com.MTPA.Objects.Reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

//this class needs to be extended
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class TreatmentPlan {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private Date startDate;

    @Getter
    @Setter
    private Date endDate;

    @Getter
    @Setter
    @Column(name = "patient_ppsn")
    private String patientPPSN;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties(value = {"patient", "condition"}, allowSetters = true)
    private Encounter encounter;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_condition_id")
    private PatientCondition condition;
    //the below is only generated when prediction is requested might move to a separate class

    @Getter
    @Setter
    private int successChance;

    @Getter
    @Setter
    private String bestPredictedOutcome;

    @Getter
    @Setter
    private String worstPredictedOutcome;

    @Getter
    @Setter
    private String likelyNegativeSideEffects;
    //effecting chance of success

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_code")
    private PatientMedication medicationEffecting;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private PatientCondition conditionEffecting;
}