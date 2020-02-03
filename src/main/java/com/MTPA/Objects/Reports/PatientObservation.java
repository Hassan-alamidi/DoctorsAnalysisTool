package com.MTPA.Objects.Reports;

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
    private String type;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Date dateTaken;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id")
    @JsonIgnoreProperties("observation")
    private Encounter encounter;

    //value could be an amount, true or false and so on, so string is best for this
    @Getter
    @Setter
    private String resultValue;

    @Getter
    @Setter
    @Column(name = "result_unit")
    private String unit;
}
