package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private PatientCondition condition;
    //the below is only generated when prediction is requested

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
