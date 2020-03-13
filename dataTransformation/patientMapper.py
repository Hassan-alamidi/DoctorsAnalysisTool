#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 15:50:29 2020

@author: hassan

this script is basically mapping the patients important information to the ppsn (this is really just the row id but it will do)
the values maybe expaned 
"""
    
from __future__ import print_function
import sys

for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        ppsn = str(data[0])
        birthDate = str(data[1])
        deathDate = str(data[2])
        gender = str(data[14])
    except ValueError:
        print("Failed to read patient")
        print(data)
        continue
    
    print(ppsn, birthDate, deathDate, gender, sep=',')
    
    