#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 16:52:26 2020

@author: hassan
"""

from __future__ import print_function
import sys

for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        start = str(data[2])
        stop = str(data[3])
        ppsn = str(data[4])
        description = str(data[6])
        medicationType = str(data[1])
    except ValueError:
        #print("Failed to read medication")
        #print(data)
        continue
    
    print(ppsn,start, stop, description, medicationType, sep=',')