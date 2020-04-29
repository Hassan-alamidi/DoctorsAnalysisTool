#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar 14 15:53:36 2020

@author: hassan
"""
from __future__ import print_function
import sys
import datetime as date
import calendar
import pandas as pd
import numpy as np
import subprocess

def manipulateMonth(currentDate, months):
    currentDate = date.datetime.strptime(currentDate, "%Y-%m-%d").date()
    month = currentDate.month -1 + months
    year = currentDate.year + month//12
    month = month % 12+1
    day = min(currentDate.day,calendar.monthrange(year,month)[1])
    return date.date(year, month, day)

def calculateAge(birthDate, currentDate):
    return currentDate.year - birthDate.year - ((currentDate.month, currentDate.day) < (birthDate.month, birthDate.day))
'''
#this is testing version
def loadPatientDataFromFile(patientId, fileName):
    file = open(fileName, 'r')
    lines = file.readlines()
    file.close()
    dataList = []
    for line in lines:
        if(patientId in line):
            line = line.strip()
            data = line.split(",")
            dataList.append(data)
    return dataList
'''
#this hadoop version
def loadPatientDataFromFile(patientId, files):
    cat = subprocess.Popen(["/home/ubuntu/hadoop-2.10.0/bin/hadoop", "fs", "-cat", files], stdout=subprocess.PIPE, encoding='utf8')
    #file = sc.textFile('hdfs://localhost:9000/user/ubuntu/' + files)
    #lines = file.readlines()
    dataList = []
    for line in cat.stdout:
        if(patientId in line):
            line = line.strip()
            data = line.split(",")
            dataList.append(data)
    return dataList

#file = open("../MapReduceOutput/patient.txt", 'r')
#lines = file.readlines()
#file.close()
finalData = pd.DataFrame()
observationsList = set()
boolList = set()
for line in sys.stdin:
    '''
    if(count == 0):
        count += 1
        continue
    count += 1
    '''
    line = line.strip()
    line = [line.split(",")]
    #dataframe is overkill and is actually slightly slower but using it for readability
    patient = pd.DataFrame(line,columns=["ppsn", "birthDate", "deathDate", "gender"])
    patient = patient.replace('null', '', regex=True)
    patient["birthDate"] = pd.to_datetime(patient["birthDate"])
    
    patientObservations = loadPatientDataFromFile(patient.iloc[0,0], "mapOutput/observations/part*")
    patientObservations = pd.DataFrame(patientObservations,columns=["ppsn", "date", "description", "value", "units"])
    patientObservations = patientObservations.replace('null', '', regex=True)
    patientObservations["date"] = pd.to_datetime(patientObservations["date"])
    
    patientMedications = loadPatientDataFromFile(patient.iloc[0,0], "mapOutput/medication/part*")
    patientMedications = pd.DataFrame(patientMedications,columns=["ppsn", "start", "stop", "description", "type"])
    patientMedications = patientMedications.replace('null', '', regex=True)
    patientMedications["start"] = pd.to_datetime(patientMedications["start"])
    patientMedications["stop"] = pd.to_datetime(patientMedications["stop"])
    
    patientConditions = loadPatientDataFromFile(patient.iloc[0,0], "mapOutput/conditions/part*")
    patientConditions = pd.DataFrame(patientConditions,columns=["ppsn", "start", "stop", "description", "type", "predictive"])
    patientConditions = patientConditions.replace('null', '', regex=True)
    patientConditions["start"] = pd.to_datetime(patientConditions["start"])
    patientConditions["stop"] = pd.to_datetime(patientConditions["stop"])
    
    predic = patientConditions["predictive"] == "true"
    conditionsToPredict = patientConditions[predic]
    
    for index, rows in conditionsToPredict.iterrows():
#       oneMonthsBefore = manipulateMonth(rows["start"].strftime("%Y-%m-%d"), -1)
        oneMonthsBefore = date.datetime.strptime(rows["start"].strftime("%Y-%m-%d"), "%Y-%m-%d").date()

        #obvBefore = patientObservations['date'] <= pd.to_datetime(oneMonthsBefore)
        observationsBefore = patientObservations[(patientObservations['date'] <= pd.to_datetime(oneMonthsBefore))]
        observationsBefore = observationsBefore.sort_values('date', ascending=False)
        observationsBefore = observationsBefore.drop_duplicates(subset='description')
        
        #medDuring = ((patientMedications['start'] < pd.to_datetime(oneMonthsBefore)) & ((patientMedications['stop'] > pd.to_datetime(oneMonthsBefore)) | (patientMedications['stop'].empty)))
        medicationDuring = patientMedications[((patientMedications['start'] < pd.to_datetime(oneMonthsBefore)) & ((patientMedications['stop'] > pd.to_datetime(oneMonthsBefore)) | (patientMedications['stop'].empty)))]
        medicationDuring = medicationDuring.sort_values('start', ascending=False)
        medicationDuring = medicationDuring.drop_duplicates(subset='description')
        conditionsDuring = pd.DataFrame()
        #condDuring = ((patientConditions['start'] < pd.to_datetime(oneMonthsBefore)) & ((patientConditions['stop'] > pd.to_datetime(oneMonthsBefore)) | (patientConditions['stop'].empty)))
        conditionsDuring = patientConditions[((patientConditions['start'] < pd.to_datetime(oneMonthsBefore)) & ((patientConditions['stop'] > pd.to_datetime(oneMonthsBefore)) | (patientConditions['stop'].empty)))]
        conditionsDuring = conditionsDuring.sort_values('start', ascending=False)
        conditionsDuring = conditionsDuring.drop_duplicates(subset='description')
        
        age = calculateAge(patient.iloc[0,1], oneMonthsBefore)
        
        if(conditionsDuring.shape[0] > 0 and age > 5):
            tmpCond = conditionsDuring[["description"]].copy()
            tmpCond.reset_index(drop=True, inplace=True)
            tmpCond["value"] = tmpCond.apply(lambda x: 1, axis=1)
            tmpCond = pd.concat({k: g.reset_index(drop=True) for k, g in tmpCond.groupby('description')['value']}, axis=1)
            for(columnName, columnData) in tmpCond.iteritems():
                    boolList.add(columnName)
                    
            if(medicationDuring.shape[0] > 0):
                tmpMed = medicationDuring[["description"]].copy()
                tmpMed.reset_index(drop=True, inplace=True)
                tmpMed["value"] = tmpMed.apply(lambda x: 1, axis=1)
                tmpMed = pd.concat({k: g.reset_index(drop=True) for k, g in tmpMed.groupby('description')['value']}, axis=1)
                tmpCond = pd.concat([tmpCond.reset_index(drop=True),tmpMed], axis=1)
                for(columnName, columnData) in tmpMed.iteritems():
                    boolList.add(columnName)
                    
            pData =pd.DataFrame(np.array([[rows["description"],patient.iloc[0,0],age,patient.iloc[0,3]]]),columns=["condToPredict","patient", "age", "gender"])
            tmpCond = pd.concat([pData.reset_index(drop=True),tmpCond], axis=1)
            
            tmpObv = observationsBefore[["description","value"]].copy()
            tmpObv.reset_index(drop=True, inplace=True)
            tmpObv = pd.concat({k: g.reset_index(drop=True) for k, g in tmpObv.groupby('description')['value']}, axis=1)
            
            if(tmpObv.shape[0] > 0):
                for(columnName, columnData) in tmpObv.iteritems():
                    observationsList.add(columnName)
                
                tmpCond = pd.concat([tmpCond.reset_index(drop=True),tmpObv], axis=1)
                finalData = pd.concat([finalData,tmpCond])
    
   
#the below commented out code was the beginning of dealing with null data but not sure if I should do that   
#    for col in observationsList:
#        if(!is_string_dtype(finalData[col])):
#for the neural network I am going to leave missing data as na except conditions as the one and zero are binary

for col in boolList:
    finalData[col].fillna(0, inplace=True)
'''
with open("MapReduceOutput/setOfBool.txt", "a") as file:
    for col in boolList:
        file.write(col + "\n")
'''
finalData.sort_index(axis=1, inplace=True)
print(",".join(str(colName) for colName in finalData))
for index, rows in finalData.iterrows():
        print(",".join(str(colu) for colu in rows))
