package com.MTPA.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//this object represents a medical institute like GP,Hospital etc.
//NOTE this object is not created by this application
@Entity
@Table(name = "organization")
@AllArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organization implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private int phoneNumber;

    //TODO find better solution to jsonIgnoreProperties as it still loads all of the ignored properties
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workPlace")
    @JsonIgnoreProperties("workPlace")
    private List<Doctor> hiredDoctors;

    public Organization(){}

    public Organization(Integer id){
        this.id = id;
    }

    public Organization(Integer id, String name, String address, int phoneNumber){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
