#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 13 19:04:48 2020

@author: hassan
"""

from __future__ import print_function
import sys

for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        start = str(data[1])
        stop = str(data[2])
        ppsn = str(data[3])
        description = str(data[6])
    except ValueError:
        print("Failed to read condition")
        print(data)
        continue
    
    print(ppsn,start, stop, description, sep=',')