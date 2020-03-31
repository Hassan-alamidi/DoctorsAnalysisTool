package com.MTPA.Objects.Information;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Condition {

    @Id
    @Getter
    @Column(name = "condition_code")
    private int conditionCode;

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
    @Column(name = "common_causes")
    private String commonCauses;

    @Getter
    @Setter
    @Column(name = "common_symptoms")
    private String commonSymptoms;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "can_progresses_to", referencedColumnName = "condition_code")
    private Condition progressesTo;

    //a list of procedures that can treat this condition if any
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "condition_procedure", joinColumns = @JoinColumn(name = "condition_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<Procedure> procedureList;

    //a list of medications that can treat this condition if any
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "condition_medication", joinColumns = @JoinColumn(name = "condition_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private Set<Medication> medicationList;
}
