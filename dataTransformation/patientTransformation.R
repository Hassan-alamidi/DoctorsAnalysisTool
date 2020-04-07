setwd("~/Documents/CollegeProjects/DoctorsAnalysisTool/dataTransformation")

unprocessedMedications <- read.csv("syntheaOutput/medications.csv", stringsAsFactors = FALSE)
unProcessedPatients <- read.csv("syntheaOutput/patients.csv", stringsAsFactors = FALSE)
observations <- read.csv("syntheaOutput/observations.csv", stringsAsFactors = FALSE)
encounters <- read.csv("syntheaOutput/encounters.csv", stringsAsFactors = FALSE)
careplans <- read.csv("syntheaOutput/careplans.csv", stringsAsFactors = FALSE)
procedures <- read.csv("syntheaOutput/procedures.csv", stringsAsFactors = FALSE)
conditions <- read.csv("syntheaOutput/conditions.csv", stringsAsFactors = FALSE)
patients <- data.frame(ppsn=character(), firstName=character(), lastName=character(), dob=character(), dod=character(), 
                       gender=character(), address=character(), stringsAsFactors = FALSE)

unProcessedPatients$BIRTHDATE <- as.Date(unProcessedPatients$BIRTHDATE, format = "%Y-%m-%d")
unProcessedPatients$DEATHDATE <- as.Date(unProcessedPatients$DEATHDATE, format = "%Y-%m-%d")

for (i in 1:nrow(unProcessedPatients)) {
  dob <- unProcessedPatients[i,"BIRTHDATE"]
  dod <- unProcessedPatients[i,"DEATHDATE"]
  firstName <-unProcessedPatients[i, "FIRST"]
  lastName <- unProcessedPatients[i, "LAST"]
  ppsn <- unProcessedPatients[i, "Id"]
  gender <- unProcessedPatients[i, "GENDER"]
  gender <- ifelse(gender == "M", "Male", "Female")
  address <- unProcessedPatients[i,"ADDRESS"]
  patient <- data.frame(ppsn, firstName, lastName, dob, dod,gender,address)
  patients <- rbind(patients, patient)
}

write.csv(patients, "scriptOutput/processedPatient.csv")
