#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 19:10:56 2020

@author: hassan
map immunizations, this requires annother mapper to mix with medication, followed by a reducer
"""
#nolonger have immunization table its now in with medication
from __future__ import print_function
import sys

for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        date = str(data[0])
        ppsn = str(data[1])
        description = str(data[4])
    except ValueError:
        #print("Failed to read observation")
        #print(data)
        continue
        
    print(ppsn, date, description, sep=',')
