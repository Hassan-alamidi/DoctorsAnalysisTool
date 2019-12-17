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

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_code", referencedColumnName = "condition_code")
    private Condition condition;

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

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @JoinColumn(name = "id")
    private Encounter encounter;
}
