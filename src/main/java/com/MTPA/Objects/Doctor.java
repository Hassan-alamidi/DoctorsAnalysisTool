package com.MTPA.Objects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

public class Doctor {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private int MedicalLicenceNumber;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private int phoneNumber;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Organization workPlace;
}
