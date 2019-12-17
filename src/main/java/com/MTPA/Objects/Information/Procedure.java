package com.MTPA.Objects.Information;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

public class Procedure {
    @Id
    @Getter
    private int procedureCode;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "conditions", referencedColumnName = "condition_code")
    private List<Condition> treatableConditions;

    @Getter
    @Setter
    private String description;
}
