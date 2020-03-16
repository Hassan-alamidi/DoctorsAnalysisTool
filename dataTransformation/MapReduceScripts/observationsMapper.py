#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 18:02:38 2020

@author: hassan
NOTE REDUCER NEEDED FOR THIS ONE
"""

from __future__ import print_function
import sys

grepExclusions = ["abuse status", "insurance","body height","per age and","temperature","body weight","head occipital-frontal circumference",
              "housing","american","sexual"]
#exclude only exact matches
exactExclusions = ["albumin","globulin",]
for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        date = str(data[0])
        ppsn = str(data[1])
        description = str(data[4])
        value = str(data[5])
        units = str(data[6])
    except ValueError:
        #print("Failed to read observation")
        #print(data)
        continue
    
    if any(excl in description.lower() for excl in grepExclusions) or any(excl == description.lower() for excl in exactExclusions):
        continue
    
    print(ppsn, date, description, value, units, sep=',')