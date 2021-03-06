package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "patient_procedure")
public class Procedure {

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
    @Column(name = "code")
    private String code;

    @Getter
    @Setter
    @Column(name = "carried_out_on")
    private LocalDate carriedOutOn;

    @Getter
    @Setter
    @Column(name = "details")
    private String details;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"conditions", "currentMedication"}, allowSetters = true)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties(value = {"patient", "condition","medications", "procedures", "observations", "treatments"}, allowSetters = true)
    private Encounter encounter;
}
