package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Reports.Encounter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
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
    private Encounter encounter;

    //value could be an amount, true or false and so on, so string is best for this
    @Getter
    @Setter
    private String resultValue;

    @Getter
    @Setter
    private String Unit;
}
