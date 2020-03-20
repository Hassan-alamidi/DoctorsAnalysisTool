setwd("~/Documents/CollegeProjects/DoctorsAnalysisTool/dataTransformation")

unprocessedConditions <- read.csv("syntheaOutput/conditions.csv", stringsAsFactors = FALSE)

unprocessedConditions$START <- as.Date(unprocessedConditions$START, format = "%Y-%m-%d")
unprocessedConditions$STOP <- as.Date(unprocessedConditions$STOP, format = "%Y-%m-%d")

patientsCondition <- data.frame(patient_ppsn=character(), name=character(), discovered=character(), cured_on=character(), encounter=character(), 
                       conditionCode=character(), stringsAsFactors = FALSE)

for (i in 1:nrow(unprocessedConditions)) {
  ppsn <- unprocessedConditions[i, "PATIENT"]
  name <- unprocessedConditions[i, "DESCRIPTION"]
  discovered <- unprocessedConditions[i,"START"]
  cured_on <- unprocessedConditions[i, "STOP"]
  encounter <- unprocessedConditions[i,"ENCOUNTER"]
  conditionCode <- unprocessedConditions[i, "CODE"]
  condition <- data.frame(ppsn, name, discovered, cured_on, encounter,conditionCode)
  patientsCondition <- rbind(patientsCondition, condition)
}

write.csv(patientsCondition, "scriptOutput/processedCondition.csv")
