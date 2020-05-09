package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Encounter {

    @Id
    @Getter
    @Setter
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_visited")
    private LocalDate dateVisited;

    @Getter
    @Setter
    @Column(name = "date_left", columnDefinition = "DATE")
    private LocalDate dateLeft;

    @Getter
    @Setter
    @Column
    private String description;

    @Getter
    @Setter
    @Column
    private String reasonDescription;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"conditions", "currentMedication"})
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter", cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = "encounter", allowSetters = true)
    private Set<Observation> observations = new HashSet<>();

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter", cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private Set<Condition> conditions = new HashSet<>();;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter", cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private Set<Medication> medications = new HashSet<>();;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter", cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private Set<Procedure> procedures = new HashSet<>();;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter", cascade = {CascadeType.ALL})
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private Set<TreatmentPlan> treatments = new HashSet<>();;

}
