package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PatientObservation {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Date dateTaken;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties(value = {"patient", "condition","medication", "procedure", "observations"}, allowSetters = true)
    private Encounter encounter;

    //value could be an amount, true or false and so on, so string is best for this
    @Getter
    @Setter
    private String resultValue;

    @Getter
    @Setter
    @Column(name = "result_unit")
    private String unit;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"patientConditions", "currentMedication"}, allowSetters = true)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;
}
