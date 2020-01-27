package Unit.Services;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PatientServiceTest {

    public void patientNotInDatabaseGetsUpdated_thenResponse404(){}
    public void patientInDatabaseGetsUpdated_thenResponseUpdatedPatientAnd200(){}
    public void patientInDatabaseHasDOBUpdated_thenResponsePatientWithDOBUnchangedAnd200(){}
    public void patientInDatabaseHasPPSNUpdated_thenResponsePatientWithPPSNUnchangedAnd200(){}
    public void createPatientWithUniquePPSN_thenPatientIsCreatedAnd201(){}
    public void createPatientWithNonUniquePPSN_thenPatientIsNotCreatedAnd422(){}

}
