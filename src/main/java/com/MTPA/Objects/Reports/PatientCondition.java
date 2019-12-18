package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Information.Condition;
import com.MTPA.Objects.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
//patientConditions are medical patientConditions like allergies, illnesses
@Entity
public class PatientCondition {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    private String symptoms;

    @Getter
    @Setter
    private String details;

    @Getter
    @Setter
    private Date discovered;

    @Getter
    @Setter
    private Date curedOn;

// don't think we need this as this might cause a circular dependency should just retrieve PPSN
//    @Getter
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
//    private Patient patient;

    @Getter
    @Setter
    @Column(name = "patient_ppsn")
    private String patientPPSN;

    @Getter
    @Setter
    @JoinColumn(name = "id")
    private Encounter encounter;
}
