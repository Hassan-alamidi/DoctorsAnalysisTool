#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar 14 14:28:16 2020

@author: hassan
"""

from __future__ import print_function
import sys
import datetime as date
import calendar

def manipulateMonth(currentDate, months):
    currentDate = date.datetime.strptime(currentDate, "%Y-%m-%d").date()
    month = currentDate.month -1 + months
    year = currentDate.year + month//12
    month = month % 12+1
    day = min(currentDate.day,calendar.monthrange(year,month)[1])
    return date.date(year, month, day)

dataType = sys.argv[1]
count = 0
for line in sys.stdin:
    line = line.strip()
    data = line.split(",")
    
    try:
        ppsn = str(data[0])
        start = str(data[1])
        if(dataType == "medication"):
            stop = str(data[2])
            description = str(data[3])
        else:
            description = str(data[2])
            if("Influenza" in description):
                stop = manipulateMonth(start, 6)
            else:
                stop = ""
                
            if(count == 0):
                count += 1
                #skip as medication should be run first meaning we dont need column names
                continue
    except ValueError:
        continue
    
    print(ppsn,start, stop, description, sep=',')