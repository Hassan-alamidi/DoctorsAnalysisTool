package Objects;

import java.util.Date;

public class Medication {
    private int id;
    private String name;
    private int medicationId;
    private Date treatmentStart;
    private Date treatmentEnd;
    private String patientPPSN;
    private Encounter encounter;
    private String description;
    private Condition targetCondition;
    private double prescribedAmount;
    private String unitType;
}
