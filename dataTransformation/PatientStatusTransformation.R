#this script should take all the data and transform it into a series of patient rows stating their statuses before and after a procedure along with any conditions a patient may have
library(dplyr)
library(tidyr)
library(lubridate)
library(stringr)

setwd("~/Documents/CollegeProjects/DoctorsAnalysisTool/dataTransformation")

test <- read.csv("MapReduceOutput/PatientCurrentCondition.csv", stringsAsFactors = FALSE)
medications <- read.csv("syntheaOutput/medications.csv", stringsAsFactors = FALSE)
patients <- read.csv("syntheaOutput/patients.csv", stringsAsFactors = FALSE)
observations <- read.csv("syntheaOutput/observations.csv", stringsAsFactors = FALSE)
conditions <- read.csv("syntheaOutput/conditions.csv", stringsAsFactors = FALSE)
immunizations <- read.csv("syntheaOutput/immunizations.csv", stringsAsFactors = FALSE)

print("Starting to transform data")
patients$BIRTHDATE <- as.Date(patients$BIRTHDATE, format = "%Y-%m-%d")
patients$DEATHDATE <- as.Date(patients$DEATHDATE, format = "%Y-%m-%d")

observations$DATE <- as.Date(observations$DATE, format = "%Y-%m-%d")
observations <- arrange(observations, DATE)

medications$START <- as.Date(medications$START, format = "%Y-%m-%d")
medications$STOP <- as.Date(medications$STOP, format = "%Y-%m-%d")
medications <- select(medications, -c(4,8,9,10,11,12,13))

conditions$START <- as.Date(conditions$START, format = "%Y-%m-%d")
conditions$STOP <- as.Date(conditions$STOP, format = "%Y-%m-%d")


conditions <- conditions[!(grepl("Whiplash injury to neck", conditions$DESCRIPTION, ignore.case = TRUE) | 
                                           grepl("Sprain", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Fracture", conditions$DESCRIPTION, ignore.case = TRUE) |
                                           grepl("Laceration", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("burn", conditions$DESCRIPTION, ignore.case = TRUE) |
                                           grepl("Child attention deficit disorder", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Acute allergic reaction", conditions$DESCRIPTION, ignore.case = TRUE) |
                                           grepl("dermatitis", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Injury", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Seasonal", conditions$DESCRIPTION, ignore.case = TRUE) | 
                                           grepl("molars", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("suicide", conditions$DESCRIPTION, ignore.case = TRUE)),]

#anything to do with mental health I believe cannot be predicted as life is unpredictible 
conditionsToPredict <- conditions[!(grepl("History", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("overdose", conditions$DESCRIPTION, ignore.case = TRUE) | 
                                grepl("sore", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("infection", conditions$DESCRIPTION, ignore.case = TRUE) |
                                grepl("Rupture", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Polyp", conditions$DESCRIPTION, ignore.case = TRUE) |
                                grepl("obesity", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Smokes", conditions$DESCRIPTION, ignore.case = TRUE) |
                                grepl("depression", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Cystitis", conditions$DESCRIPTION, ignore.case = TRUE) |
                                grepl("Preeclampsia", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Fetus with unknown complication", conditions$DESCRIPTION, ignore.case = TRUE) | 
                                grepl("wound", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("bacterial", conditions$DESCRIPTION, ignore.case = TRUE) |
                                grepl("viral", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("Concussion", conditions$DESCRIPTION, ignore.case = TRUE) | grepl("pregnancy", conditions$DESCRIPTION, ignore.case = TRUE)),]
#todo use abuse status to predict mental health problems
#note removing body height and body weight (if below 30+) as we already have body mass index and I do not want to over fit the model more than it already is
observations <- observations[!(grepl("Abuse Status", observations$DESCRIPTION, ignore.case = TRUE) | observations$DESCRIPTION == "Albumin" | observations$DESCRIPTION == "Globulin" |
                                         grepl("insurance", observations$DESCRIPTION, ignore.case = TRUE) | grepl("Body Height", observations$DESCRIPTION, ignore.case = TRUE) |
                                         grepl("Per age and", observations$DESCRIPTION, ignore.case = TRUE) | grepl("temperature", observations$DESCRIPTION, ignore.case = TRUE) | 
                                         grepl("Body Weight", observations$DESCRIPTION, ignore.case = TRUE) | grepl("Head Occipital-frontal circumference", observations$DESCRIPTION, ignore.case = TRUE) |
                                         grepl("Housing", observations$DESCRIPTION, ignore.case = TRUE) | grepl("American", observations$DESCRIPTION, ignore.case = TRUE) | 
                                         grepl("Sexual", observations$DESCRIPTION, ignore.case = TRUE)),]

names(immunizations)[names(immunizations) == "DATE"] <- "START"
immunizations$STOP <- NA
immunizations$START <- as.Date(immunizations$START, format = "%Y-%m-%d")
immunizations$STOP <- as.Date(immunizations$STOP, format = "%Y-%m-%d")
immunizations <- select(immunizations, -c(6))
#influenza vaccine is thought to last only six months
for (im in 1:nrow(immunizations)) {
   ifelse(grepl("Influenza", immunizations[im,"DESCRIPTION"], ignore.case = TRUE), immunizations[im, "STOP"] <- (immunizations[im, "START"] %m+% months(6)), immunizations[im, "STOP"] <- NA)
}
#immunizations <- immunizations %>% mutate(STOP=ifelse(grepl("Influenza", immunizations$DESCRIPTION, ignore.case = TRUE), (START %m+% months(2)), NA))
medications <- rbind(medications, immunizations)
immunizations <- NA
#for (i in 1:nrow(patients)) {
 # patientProcedures <- select(filter(procedures, PATIENT == patients[i, 1]), c(DATE,CODE,DESCRIPTION))
#  patientObservations <- select(filter(observations, PATIENT == patients[i,1]), c(DATE, CODE, DESCRIPTION, VALUE, UNITS))
#  patientConditions <-select(filter(conditions, PATIENT == patients[i,1]), c(START, STOP, CODE, DESCRIPTION))
#  patientMedications <- select(filter(medications, PATIENT == patients[i,1]), c(START, STOP, CODE, DESCRIPTION))
#  patientCarePlans <- select(filter(careplans, PATIENT == patients[i,1]), START, STOP, CODE, DESCRIPTION, REASONDESCRIPTION)
#  for(j in 1:nrow(patientProcedures)){
#    observationsBefore <- arrange(select(filter(patientObservations, DATE <= patientProcedures[j, "DATE"]), c(DATE, CODE, DESCRIPTION, VALUE, UNITS)), desc(DATE))
#    observationsAfter <- arrange(select(filter(patientObservations, DATE > patientProcedures[j, "DATE"]), c(DATE, CODE, DESCRIPTION, VALUE, UNITS)), DATE)
#    distinctObservBefore <- distinct(observationsBefore, CODE, .keep_all = TRUE)
#    distinctObservAfter <- distinct(observationsAfter, CODE, .keep_all = TRUE)
    #stop is to predict if condition is cured by the end of treatment
#    condDuringProcedure <- select(filter(patientConditions, START < patientProcedures[j,"DATE"] & (STOP > patientProcedures[j, "DATE"] | is.na(STOP))), c(STOP, CODE, DESCRIPTION))
#    age <- as.period(interval(start = patients[i, "BIRTHDATE"], end = patientProcedures[j, "DATE"]))$year
#    eightMonthFromProcedure <- (patientProcedures[j, "DATE"] %m+% months(8))
#    isDead <- ifelse((!is.na(patients[i,"DEATHDATE"]) & patients[i,"DEATHDATE"] < eightMonthFromProcedure), TRUE, FALSE)
 
#    print(age)

 # }
#}
#for Predicting patient condition
print("Starting to reduce data")
df <- data.frame()
for (i in 1:nrow(patients)) {
  patientObservations <- select(filter(observations, PATIENT == patients[i,1]), c(DATE, CODE, DESCRIPTION, VALUE, UNITS))
  patientConditions <-select(filter(conditions, PATIENT == patients[i,1]), c(START, STOP, CODE, DESCRIPTION))
  condToPredict <-select(filter(conditionsToPredict, PATIENT == patients[i,1]), c(START, STOP, CODE, DESCRIPTION))
  #not sure if I will include medication and care plans in this version
  patientMedications <- distinct(select(filter(medications, PATIENT == patients[i,1]), c(START, STOP, CODE, DESCRIPTION)), CODE, .keep_all = TRUE )
  
  if(nrow(condToPredict) > 0){
    for(j in 1:nrow(condToPredict)){
      print(paste0("patient: ",i))
      print(paste0("scenario: ",j))
      #print("patient "+ i + " scenario " + j)
      fourMonthsBefore <- (condToPredict[j, "START"] %m-% months(4))
      observationsBefore <- arrange(select(filter(patientObservations, DATE <= fourMonthsBefore), c(DATE, CODE, DESCRIPTION, VALUE, UNITS)), desc(DATE))
      distinctObservBefore <- distinct(observationsBefore, DESCRIPTION, .keep_all = TRUE)
      distinctObservBefore <- subset(distinctObservBefore, select = -c(DATE, CODE, UNITS))
      medDuring <- select(filter(patientMedications, START < fourMonthsBefore & (STOP > fourMonthsBefore | is.na(STOP))), c(START, STOP, CODE, DESCRIPTION))
      condDuring <- select(filter(patientConditions, START < fourMonthsBefore & (STOP > fourMonthsBefore | is.na(STOP))), c(STOP, CODE, DESCRIPTION))
      age <- as.period(interval(start = patients[i, "BIRTHDATE"], end = fourMonthsBefore))$year
      tmp <- data.frame()
      med <- data.frame()
      if(nrow(condDuring) > 0){
        tmp <- data.frame(CONDITION=condDuring$DESCRIPTION, stringsAsFactors = FALSE)
        tmp <- spread(tmp, key=CONDITION, value = CONDITION, drop = TRUE)
        if(nrow(medDuring) > 0){
          med <- data.frame(MEDICATION=medDuring$DESCRIPTION, stringsAsFactors = FALSE)
          med <- spread(med, key=MEDICATION, value = MEDICATION)
          tmp <- cbind(tmp, med)
        }
        
        tmp[] <- 1
        tmp <- cbind(PPSN=patients[i, 1],tmp)
        tmp <- cbind(age=age,tmp)
        tmp <- cbind(condPatientSoonToHave=condToPredict[j, "DESCRIPTION"],tmp)
   
        obv <- spread(distinctObservBefore, key = DESCRIPTION, value = VALUE)
        if(nrow(obv) > 0 & nrow(tmp) > 0){
          tmp <- cbind(tmp, obv)
          df <- bind_rows(df, tmp)
        }
      }
    }
  }
}
df[is.na(df)] <- 0
names(df) <- str_replace_all(names(df), c(" "="_"))
write.csv(df, "scriptOutput/PatientCurrentCondition.csv")
