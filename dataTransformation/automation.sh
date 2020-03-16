#!/bin/bash

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
echo "Mapping everything for neural network"
cat MapReduceOutput/patient.txt | python MapReduceScripts/conditionPredictionMapper.py > MapReduceOutput/conditionPrediction.txt
