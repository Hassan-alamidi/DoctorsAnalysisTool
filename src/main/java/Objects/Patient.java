package Objects;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Builder
public class Patient {
    @Id
    private int id;
    private String name;
    private int age;
    private String PPSN;
    private List<Condition> conditions;
    private List<Medication> currentMedication;
    private String address;
    private int motherPPSN;
    private int fatherPPSN;

}
