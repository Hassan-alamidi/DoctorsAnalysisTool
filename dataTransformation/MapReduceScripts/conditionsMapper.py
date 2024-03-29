#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 19:04:48 2020

@author: hassan
"""

from __future__ import print_function
import sys

exclusions = ["whiplash injury to neck","sprain", "fracture", "laceration", "burn", "child attention deficit disorder",
              "acute allergic reaction","dermatitis", "injury", "seasonal","molars", "suicide"]

nonPredictive = ["history", "overdose","sore","infection","rupture","polyp","obesity","smokes","depression","cystitis",
                 "preeclampsia","fetus with unknown complication","wound","bacterial","viral","concussion","pregnancy"]
count = 0
for line in sys.stdin:
    predictive = "true"
    line = line.strip()
    data = line.split(",")
    
    try:
        start = str(data[5])
        stop = str(data[6])
        ppsn = str(data[7])
        description = str(data[1])
        conditionType = str(data[9])
    except ValueError:
        #print("Failed to read condition")
        #print(data)
        continue

    if any(excl in description.lower() for excl in exclusions):
        continue
    elif any(nonPre in description.lower() for nonPre in nonPredictive):
        predictive = "false"
  
    print(ppsn,start, stop, description, conditionType, predictive, sep=',')