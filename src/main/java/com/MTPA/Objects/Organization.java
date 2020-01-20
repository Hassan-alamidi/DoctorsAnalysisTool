package com.MTPA.Objects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//this object represents a medical institute like GP,Hospital etc.
@Entity
public class Organization {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workPlace")
    private List<Doctor> hiredDoctors;
}
