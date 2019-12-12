package Objects;

import java.util.Date;

public class TreatmentPlan {
    private int id;
    private Date startDate;
    private Date endDate;
    private String patientPPSN;
    private Encounter encounter;
    private String description;
    private Condition condition;
    //the below is only generated when prediction is requested
    private int successChance;
    private String bestPredictedOutcome;
    private String worstPredictedOutcome;
    private String likelyNegativeSideEffects;
    //effecting chance of success
    private Medication medicationEffecting;
    private Condition conditionEffecting;
}
