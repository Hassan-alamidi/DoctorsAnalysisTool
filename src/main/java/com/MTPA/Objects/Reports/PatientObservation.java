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
    @JoinColumn(name = "id")
    private Encounter encounter;

    @Getter
    @Setter
    private double resultValue;

    @Getter
    @Setter
    private String Unit;
}
