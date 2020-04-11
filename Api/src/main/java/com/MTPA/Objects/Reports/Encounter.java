package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Organization;
import com.MTPA.Objects.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
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
    @Column(name = "date_visited")
    private Date dateVisited;

    @Getter
    @Setter
    @Column(name = "date_left")
    private Date dateLeft;

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
    @JsonIgnoreProperties(value = {"patientConditions", "currentMedication"})
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "PPSN")
    private Patient patient;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter")
    @JsonIgnoreProperties(value = "encounter", allowSetters = true)
    private Set<PatientObservation> observations;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "encounter")
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private PatientCondition condition;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "encounter")
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private PatientMedication medication;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "encounter")
    @JsonIgnoreProperties(value = {"encounter", "patient"}, allowSetters = true)
    private PatientProcedure procedure;
// TODO remove
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @JsonIgnoreProperties(value = "hiredDoctors", allowSetters = true)
    private Organization organization;
}
