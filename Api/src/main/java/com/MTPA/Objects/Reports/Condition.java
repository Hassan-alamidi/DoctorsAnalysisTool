package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

//Conditions are medical Conditions like allergies, illnesses
@Entity
@Table(name = "patient_condition")
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Condition {

    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;

    @Getter
    @Setter
    @Column(name = "description")
    private String name;

    @Getter
    @Setter
    @Column(name = "code")
    private String code;

    @Getter
    @Setter
    @Column(name = "symptoms")
    private String symptoms;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "details")
    private String details;

    @Getter
    @Setter
    @Column(name = "discovered")
    private LocalDate discovered;

    @Getter
    @Setter
    @Column(name = "cured_on")
    private LocalDate curedOn;

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
    @JsonIgnoreProperties(value = {"patient", "conditions","medications", "procedures", "treatments"}, allowSetters = true)
    private Encounter encounter;
}
