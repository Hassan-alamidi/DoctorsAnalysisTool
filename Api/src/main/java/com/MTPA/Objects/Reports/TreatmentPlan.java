package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

//this class needs to be extended
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class TreatmentPlan {
//TODO need to add reasonCode and reasonDescription
    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private LocalDate startDate;

    @Getter
    @Setter
    private LocalDate endDate;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"conditions", "currentMedication"}, allowSetters = true)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties(value = {"patient", "condition","medication", "procedures", "observations", "treatments"}, allowSetters = true)
    private Encounter encounter;

    @Getter
    @Setter
    @Column
    private String description;

    @Getter
    @Setter
    @Column
    private String code;

    @Getter
    @Setter
    @Column
    private String reasonCode;

    @Getter
    @Setter
    @Column
    private String reasonDescription;
}
