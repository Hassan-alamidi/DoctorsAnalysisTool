#!/bin/bash

echo "Cleaning env"
rm MapReduceOutput/setOfBool.txt
echo "mapping patient"
cat syntheaOutput/patients.csv | python MapReduceScripts/patientMapper.py > MapReduceOutput/patient.txt
echo "mapping medication"
cat syntheaOutput/medications.csv | python MapReduceScripts/medicationMapper.py > MapReduceOutput/medication.txt
echo "mapping observations"
cat syntheaOutput/observations.csv | python MapReduceScripts/observationsMapper.py > MapReduceOutput/observations.txt
echo "mapping procedure"
cat syntheaOutput/procedures.csv | python MapReduceScripts/procedureMapper.py > MapReduceOutput/procedures.txt
echo "mapping immunizations"
cat syntheaOutput/immunizations.csv | python MapReduceScripts/immunizationsMapper.py > MapReduceOutput/immunizations.txt
echo "mapping conditions"
cat syntheaOutput/conditions.csv | python MapReduceScripts/conditionsMapper.py > MapReduceOutput/conditions.txt
echo "combining medication with immunizations"
cat MapReduceOutput/medication.txt | python MapReduceScripts/medicationAndImmunizationCombiner.py medication > MapReduceOutput/medAndImmun.txt
cat MapReduceOutput/immunizations.txt | python MapReduceScripts/medicationAndImmunizationCombiner.py immunization >> MapReduceOutput/medAndImmun.txt
echo "getting number of patients and calculating file split"
numOfLines=$(sed -n '$='$1 MapReduceOutput/patient.txt)
echo "number of patients being checked is $numOfLines"
oneQuaterNum=$(expr $numOfLines / 4)
twoQuaterNum=$((oneQuaterNum+oneQuaterNum))
echo "$twoQuaterNum"
threeQuaterNum=$((oneQuaterNum+twoQuaterNum))
echo "$threeQuaterNum"

echo "Mapping patients records for predictions"
sed -n 1,"$oneQuaterNum"p MapReduceOutput/patient.txt | python MapReduceScripts/conditionPredictionMapper.py > MapReduceOutput/predictionSplit1.txt &
sed -n "$oneQuaterNum","$twoQuaterNum"p MapReduceOutput/patient.txt | python MapReduceScripts/conditionPredictionMapper.py > MapReduceOutput/predictionSplit2.txt &
sed -n "$twoQuaterNum","$threeQuaterNum"p MapReduceOutput/patient.txt | python MapReduceScripts/conditionPredictionMapper.py > MapReduceOutput/predictionSplit3.txt &
sed -n "$threeQuaterNum","$numOfLines"p MapReduceOutput/patient.txt | python MapReduceScripts/conditionPredictionMapper.py > MapReduceOutput/predictionSplit4.txt &
wait
echo "Reducing patients records for predictions"
python MapReduceScripts/conditionPredictionReducer.py
echo "finished"
