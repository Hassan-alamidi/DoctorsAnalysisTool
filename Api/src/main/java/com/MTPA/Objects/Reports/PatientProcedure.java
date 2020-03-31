package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Information.Procedure;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientCondition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PatientProcedure {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

// explained commented out code in patient condition the same reason applies
//    @Getter
//    @Setter
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "procedure_code", referencedColumnName = "procedure_code")
//    private Procedure procedure;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "procedure_code")
    private int procedureCode;

    @Getter
    @Setter
    @Column(name = "carried_out_on")
    private Date carriedOutOn;

    @Getter
    @Setter
    @Column(name = "details")
    private String details;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    private Encounter encounter;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_for_procedure")
    private PatientCondition reasonForProcedure;
}
