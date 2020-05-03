#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Apr 29 04:13:37 2020

@author: hassan
"""
from __future__ import print_function
import sys
import datetime as date

patientData = []
currentPatient = None
count = 0
for line in sys.stdin:
    line = line.strip()
    line = line.split(',')
    ppsn = str(line[0])
    #observationDate = date.datetime.strptime(line[8], "%Y-%m-%d").date()
    obY, obM, obD = line[8].split('-')
    obD = obD.split(' ')[0]
    
    observationDate = date.date(int(obY),int(obM),int(obD))
    if(int(line[3]) == 1):
        #check if observation was made before being diagnosed and if so discard it
        diagY, diagM, diagD = line[4].split('-')
        diagD = diagD.split(' ')[0]
        diagnoseDate = date.date(int(diagY), int(diagM), int(diagD))
        #diagnosedDate = date.datetime.strptime(line[4], "%Y-%m-%d").date()
        if(observationDate < diagnoseDate):
            continue
    
    #check if first row
    if(count == 0):
        currentPatient = ppsn
        patientData = line
    
    if(currentPatient != ppsn):
        print(patientData[0],patientData[1], patientData[5], patientData[9], patientData[2],patientData[6],patientData[7],patientData[3], sep=',')
        currentPatient = ppsn
        patientData = line
    elif(count != 0):
        #previousDate = date.datetime.strptime(patientData[8], "%Y-%m-%d").date()
        prevY, prevM, prevD = patientData[8].split('-')
        prevD = prevD.split(' ')[0]
        previousDate = date.date(int(prevY), int(prevM), int(prevD))
        if(observationDate < previousDate):
            patientData = line
            
    count += 1
        
#print last row but only the values we need in the order we want
#desired order is 'ppsn', 'age', 'diastolic', 'systolic', 'bmi', 'hdl', 'ldl', 'hasDiabeties'
print(patientData[0],patientData[1], patientData[5], patientData[9], patientData[2],patientData[6],patientData[7],patientData[3], sep=',')