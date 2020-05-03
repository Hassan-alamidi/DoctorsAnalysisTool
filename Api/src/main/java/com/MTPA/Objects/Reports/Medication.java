package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "patient_medication")
public class Medication {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column
    private String code;

    @Getter
    @Setter
    @Column(name = "treatment_start")
    private LocalDate treatmentStart;

    @Getter
    @Setter
    @Column(name = "treatment_end")
    private LocalDate treatmentEnd;

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
    private String reasonDescription;

    @Getter
    @Setter
    @Column
    private String reasonCode;

    @Getter
    @Setter
    @Column(name = "prescribed_amount")
    private Integer prescribedAmount;

}
