#!/bin/bash

echo "mapping patient"
cat syntheaOutput/patients.csv | python patientMapper.py > MapReduceOutput/patient.txt
echo "mapping medication"
cat syntheaOutput/medications.csv | python medicationMapper.py > MapReduceOutput/medication.txt
echo "mapping observations"
cat syntheaOutput/observations.csv | python MapReduceScripts/observationsMapper.py > MapReduceOutput/observations.txt
echo "mapping procedure"
cat syntheaOutput/procedures.csv | python MapReduceScripts/procedureMapper.py > MapReduceOutput/procedures.txt
