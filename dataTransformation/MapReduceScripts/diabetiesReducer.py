#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Apr 29 04:13:37 2020

@author: hassan
"""
from __future__ import print_function
import sys
import datetime as date
import calendar
import pandas as pd
import numpy as np

patientData = []
currentPatient = None
for line in sys.stdin:
    line = line.strip()
    line = line.split(',')
    ppsn = str(line[0])
    
    if(len(patientData) == 0):
        currentPatient = ppsn
        
    if(currentPatient != ppsn):
        print(",".join(patientData))
        currentPatient = ppsn
        patientData = []

    
    if(int(line[3]) == 1):
        #here check if observation was made before being diagnosed and if so discard it
        observationDate = date.datetime.strptime(line[8], "%Y-%m-%d")
        diagnosedDate = date.datetime.strptime(line[4], "%Y-%m-%d")
        if(observationDate < diagnosedDate):
            continue