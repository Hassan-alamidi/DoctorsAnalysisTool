package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Patient;
import com.MTPA.Objects.Reports.Encounter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "patient_observation")
public class Observation {

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
    private LocalDate dateTaken;

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
    @JoinColumn(name = "encounter_id",nullable = true)
    @JsonIgnoreProperties(value = {"patient", "condition","medications", "procedures", "observations", "treatments"}, allowSetters = true)
    //this is only used as the importation of dataset to database ends up in some data loss and in turn caused parent objects to be dropped but child objects stayed
    @NotFound(action = NotFoundAction.IGNORE)
    private Encounter encounter;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"conditions", "currentMedication"}, allowSetters = true)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;
}
