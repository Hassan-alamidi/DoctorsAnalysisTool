package com.MTPA.Objects.Reports;

import com.MTPA.Objects.Organization;
import com.MTPA.Objects.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Encounter {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_ppsn", referencedColumnName = "ppsn")
    private Patient patient;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "encounter")
    private List<PatientObservation> observations;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;
}