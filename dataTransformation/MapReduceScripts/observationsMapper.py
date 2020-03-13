#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 18:02:38 2020

@author: hassan
NOTE REDUCER NEEDED FOR THIS ONE
"""

from __future__ import print_function
import sys

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
        print("Failed to read observation")
        print(data)
        continue
    
    print(ppsn, date, description, value, units, sep=',')