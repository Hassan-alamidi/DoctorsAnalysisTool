#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 18:54:03 2020

@author: hassan
"""

from __future__ import print_function
import sys

for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        date = str(data[3])
        ppsn = str(data[4])
        description = str(data[2])
    except ValueError:
        #print("Failed to read procedure")
        #print(data)
        continue
    
    print(ppsn, date, description, sep=',')