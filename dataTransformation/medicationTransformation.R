setwd("~/Documents/CollegeProjects/DoctorsAnalysisTool/dataTransformation")

unprocessedMedications <- read.csv("syntheaOutput/medications.csv", stringsAsFactors = FALSE)

unprocessedMedications$START <- as.Date(unprocessedMedications$START, format = "%Y-%m-%d")
unprocessedMedications$STOP <- as.Date(unprocessedMedications$STOP, format = "%Y-%m-%d")

#reason for medication can be retrieved from conditions by looking up encounter
patientsMedication <- data.frame(patient_ppsn=character(), name=character(), treatmentStart=character(), treatmentEnd=character(), encounter=character(), 
                                medicationCode=character(), stringsAsFactors = FALSE)

for (i in 1:nrow(unprocessedMedications)) {
  
  condition <- data.frame(ppsn, name, discovered, cured_on, encounter,conditionCode)
  patientsCondition <- rbind(patientsCondition, condition)
}

write.csv(patientsCondition, "scriptOutput/processedMedication.csv")