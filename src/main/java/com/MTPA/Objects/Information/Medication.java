package com.MTPA.Objects.Information;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

public class Medication {

    @Id
    @Getter
    private int medicationCode;

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
