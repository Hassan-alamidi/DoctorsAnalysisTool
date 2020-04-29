#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Apr 28 03:35:42 2020

@author: hassan
"""
from __future__ import print_function
import sys
import datetime as date
import calendar
import pandas as pd
import numpy as np
import subprocess

def calculateAge(birthDate, currentDate):
    return currentDate.year - birthDate.year - ((currentDate.month, currentDate.day) < (birthDate.month, birthDate.day))
'''
#this hadoop version
def loadPatientDataFromFile(uniqueId, files):
    cat = subprocess.Popen(["/home/ubuntu/hadoop-2.10.0/bin/hadoop", "fs", "-cat", files], stdout=subprocess.PIPE, encoding='utf8')
    #file = sc.textFile('hdfs://localhost:9000/user/ubuntu/' + files)
    #lines = file.readlines()
    dataList = []
    for line in cat.stdout:
        if(uniqueId in line):
            line = line.strip()
            data = line.split(",")
            dataList.append(data)
    return dataList
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

for line in sys.stdin:
    observationList = pd.DataFrame()
    hasDiabeties = 0
    startDate = 'null'

    line = line.strip()
    line = [line.split(",")]
    
    patient = pd.DataFrame(line,columns=["ppsn", "birthDate", "deathDate", "gender"])
    patient = pd.DataFrame(patient,columns=["ppsn", "birthDate", "deathDate", "gender"])
    patient = patient.replace('null', '', regex=True)
    patient["birthDate"] = pd.to_datetime(patient["birthDate"])
    
    patientObservations = loadPatientDataFromFile(patient.iloc[0,0], "mapOutput/observations/part*")
    patientObservations = pd.DataFrame(patientObservations,columns=["ppsn", "date", "description", "value", "units"])
    patientObservations = patientObservations.replace('null', '', regex=True)
    patientObservations["date"] = pd.to_datetime(patientObservations["date"])
    
    patientConditions = loadPatientDataFromFile(patient.iloc[0,0], "mapOutput/conditions/part*")
    if(len(patientConditions) > 0):
        patientConditions = pd.DataFrame(patientConditions,columns=["ppsn", "start", "stop", "description", "type", "predictive"])
        for index, rows in patientConditions.iterrows():
            if(rows["description"] == "Diabetes"):
                hasDiabeties = 1
                startDate = rows["start"]
    
    for index, rows in patientObservations.iterrows():
        if('Diastolic Blood Pressure' in rows['description'] or
           'Systolic Blood Pressure' in rows['description'] or
           rows['description'] == 'Body Mass Index' or 
           'High Density Lipoprotein Cholesterol' in rows['description'] or
           'Low Density Lipoprotein Cholesterol' in rows['description']):
            observationList = pd.concat([observationList,rows])
    
    groupedByDate = patientObservations.groupby('date')
    
    for groupName, group in groupedByDate:
        tmpObv = group[["description", "value"]].copy()
        tmpObv.reset_index(drop=True, inplace=True)
        tmpObv = pd.concat({k: g.reset_index(drop=True) for k, g in tmpObv.groupby('description')['value']}, axis=1)
        if(len(tmpObv) != 5):
            continue
        age = calculateAge(patient.iloc[0,1], group.iloc[0,1])
        #these are only named so they can be soted and so if I ever forget the order I can come back and look
        tmpObv['Age'] = age
        tmpObv['ObservationDate'] = group.iloc[0,1]
        tmpObv['DiagnosedDate'] = startDate
        tmpObv["Diabeties"] = hasDiabeties
        #zero is to ensure ppsn is placed first after sorting
        tmpObv['0Ppsn'] = patient.iloc[0,0]
        tmpObv.sort_index(axis=1, inplace=True)
        #column order should be 0Ppsn, Age, Body, Diabeties, DiagnosedDate, Diastolic, High, Low, ObservationDate, Systolic
        for index, row in tmpObv.iterrows():
            print(",".join(str(colu) for colu in row))


