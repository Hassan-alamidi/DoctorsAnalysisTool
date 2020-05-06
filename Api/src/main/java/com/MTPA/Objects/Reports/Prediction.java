package com.MTPA.Objects.Reports;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prediction_reports")
public class Prediction {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(name = "patient_ppsn")
    private String ppsn;

    @Getter
    @Setter
    @Column
    private String result;

    @Getter
    @Setter
    @Column
    private String basedOn;

    @Getter
    @Setter
    @Column
    private LocalDate dateGenerated;

    @Getter
    @Setter
    @Column
    private String confidence;
}
