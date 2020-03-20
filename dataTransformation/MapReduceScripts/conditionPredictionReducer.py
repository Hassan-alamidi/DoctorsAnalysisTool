#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Mar 18 16:12:21 2020

@author: hassan
"""
from __future__ import print_function
import pandas as pd

#todo pass in argument stating number of splits
#booleanColumns are columns in the dataset of which are boolean and should not be left null but rather default to 0 (false)
booleanColumns = set()
with open("MapReduceOutput/setOfBool.txt", "r") as file:
    for boolColumns in file:
        booleanColumns.add(boolColumns.strip())

output = pd.read_csv("MapReduceOutput/predictionSplit1.txt")
split = pd.read_csv("MapReduceOutput/predictionSplit2.txt")

output = pd.concat([output,split])
split = pd.read_csv("MapReduceOutput/predictionSplit3.txt")

output = pd.concat([output,split])
split = pd.read_csv("MapReduceOutput/predictionSplit4.txt")
output = pd.concat([output,split])

for col in booleanColumns:
    output[col].fillna(0, inplace=True)
    
output.to_csv(r"MapReduceOutput/PatientCurrentCondition.csv", header=True)