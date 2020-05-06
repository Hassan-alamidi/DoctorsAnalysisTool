#!/bin/bash

echo "clean previous job"
hadoop fs -rm -r mapOutput

echo "import data"
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table patient;
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table encounter;
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table patient_condition;
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table patient_medication;
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table patient_observation;
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table patient_procedure;
sqoop import -Dorg.apache.sqoop.splitter.allow_text_splitter=true --delete-target-dir --driver com.mysql.cj.jdbc.Driver --connect jdbc:mysql://mtpa-db.ci0btbk0kema.us-east-1.rds.amazonaws.com:3306/MTPA?characterEncoding=utf8 --password 7nPpNMzIYd4lRF5B3Wen --username admin --table treatment_plan;

echo "mapping medication"
~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -file ~/MapReduceScripts/medicationMapper.py -mapper ~/MapReduceScripts/medicationMapper.py -input patient_medication/* -output mapOutput/medication
echo "mapping observations"
~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -file ~/MapReduceScripts/observationsMapper.py -mapper ~/MapReduceScripts/observationsMapper.py -input patient_observation/* -output mapOutput/observations
echo "mapping procedure"
~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -file ~/MapReduceScripts/procedureMapper.py -mapper ~/MapReduceScripts/procedureMapper.py -input patient_procedure/* -output mapOutput/procedures
echo "mapping conditions"
~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -file ~/MapReduceScripts/conditionsMapper.py -mapper ~/MapReduceScripts/conditionsMapper.py -input patient_condition/* -output mapOutput/conditions
echo "mapping patient"
#condition prediction mapper should be used as reducer for patient
~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -file ~/MapReduceScripts/patientMapper.py -mapper ~/MapReduceScripts/patientMapper.py -input patient/* -output mapOutput/patient

echo "mapping and reducing observations for diabeties prediction"
~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -mapper ~/MapReduceScripts/diabetiesMapper.py -reducer ~/MapReduceScripts/diabetiesReducer.py -input mapOutput/patient/* -output mapOutput/diabeties
#echo "Mapping patients records for predictions"
#~/hadoop-2.10.0/bin/hadoop jar ~/hadoop-2.10.0/share/hadoop/tools/lib/hadoop-*streaming*.jar -files ~/MapReduceScripts/conditionPredictionMappert.py -mapper conditionPredictionMappert.py -input mapOutput/patient/* -output mapOutput/conditionsPred
hadoop fs -cat mapOutput/patient/* | ~/MapReduceScripts/conditionPredictionMappert.py > PatientCurrentCondition.csv
echo "finished"

