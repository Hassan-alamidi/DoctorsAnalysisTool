package com.MTPA.Objects.Information;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Condition {

    @Id
    @Getter
    private int conditionCode;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String commonCauses;

    @Getter
    @Setter
    private String commonSymptoms;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "can_progresses_to", referencedColumnName = "condition_code")
    private Condition progressesTo;

    //a list of procedures that can treat this condition if any
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedures", referencedColumnName = "procedure_code")
    private List<Procedure> procedureList;

    //a list of medications that can treat this condition if any
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication", referencedColumnName = "medication_code")
    private List<Medication> medicationList;

    @Getter
    @Setter
    private String description;
}
