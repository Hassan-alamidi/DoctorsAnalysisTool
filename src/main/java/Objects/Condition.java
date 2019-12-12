package Objects;

import java.util.Date;
//conditions are medical conditions like allergies, illnesses
public class Condition {
    private int id;
    private String name;
    //change type to enum
    private String type;
    private int conditionCode;
    private String Description;
    private Date discovered;
    private Date cured;
    private String patientPPSN;
    private String encounterId;
}
