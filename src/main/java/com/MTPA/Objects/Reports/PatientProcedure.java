package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Information.Procedure;
import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.MTPA.Objects.Reports.PatientCondition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

public class PatientProcedure {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_code", referencedColumnName = "procedure_code")
    private Procedure procedure;

    @Getter
    @Setter
    private Date carriedOutOn;

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
    private String details;

    @Getter
    @Setter
    @JoinColumn(name = "reason_for_procedure")
    private PatientCondition reasonForProcedure;
}
