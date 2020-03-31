package com.MTPA.Objects.Information;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Procedure {

    @Id
    @Getter
    @Column(name = "procedure_code")
    private int procedureCode;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "procedureList")
    private List<Condition> treatableConditions;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;
}
