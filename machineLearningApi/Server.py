#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Apr 30 02:43:29 2020

@author: hassan
"""

import sys
from flask import Flask, jsonify
from flask import request
from flask import abort
import numpy as np
import tensorflow.keras 
from tensorflow.keras.models import Model
import mysql.connector
from mysql.connector import errorcode
from datetime import datetime, date

model = tensorflow.keras.models.load_model("diabetiesPredictionModel.h5")
database = None
insertPredictionQuery = ("INSERT INTO prediction_reports (patient_ppsn, result, based_on, date_generated, confidence) VALUES (%(patient_ppsn)s, %(result)s, %(based_on)s, %(date_generated)s, %(confidence)s)")
getPatientDOB = ("SELECT dob FROM patient WHERE ppsn = %s")
getValueQuery = ("SELECT type AS observ, result_value FROM patient_observation WHERE encounter_id = %s")
app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predictCondition():
    if not request.json or not 'ppsn' in request.json and not 'encounterId' in request.json:
        abort(400)
    
    #get ppsn and encounterID then find values from database
    ppsn = request.json['ppsn']
    encounterId = request.json['encounterId']

    diabetiesPred = predictDiabeties(ppsn, encounterId)

    return diabetiesPred

def predictDiabeties(ppsn, encounterId):
    age = None
    dbp = None
    sbp = None
    bmi = None
    hdl = None
    ldl = None
    #order should be 'ppsn', 'age', 'diastolic', 'systolic', 'bmi', 'hdl', 'ldl'
    age = getAge(ppsn)
    if(age == None):
        return jsonify({'message':"Patient not found"}), 404
    
    global database    
    global getValueQuery
    cursor = database.cursor()
    cursor.execute(getValueQuery,(encounterId,))
    for (observ, resultValue) in cursor:
      if("Diastolic" in observ):
          dbp = float(resultValue)
      elif("Systolic" in observ):
          sbp = float(resultValue)
      elif(observ =="Body Mass Index"):
          bmi = float(resultValue)
      elif("High Density Lipoprotein" in observ):
          hdl = float(resultValue)
      elif("Low Density Lipoprotein" in observ):
          ldl = float(resultValue)
    
    cursor.close()
    
    if(dbp is None or sbp is None or bmi is None or hdl is None or ldl is None):
        response = {
            'message':"Not enough observations taken"
            }
        return jsonify(response), 422
    
    tobePredicted = np.array([[age,dbp,sbp,bmi,hdl,ldl]])
    prediction = model.predict(tobePredicted)
    if(prediction[0,0] < 0.5):
        message = "This Patient does not appear to have diabetes"
        confidence = round((1 - prediction[0,0]) * 100,2) 
    else:
        message = "This Patient appears to have or atleast is soon to have diabetes"
        confidence = round(prediction[0,0] * 100,2) 
    
    basedOn = "Age:{}, Diastolic:{}, Systolic:{}, BMI:{}, HDL:{}, LDL:{}".format(age,dbp,sbp,bmi,hdl,ldl)
    insertPrediction(ppsn, message, basedOn, confidence)
    response = {
        'ppsn': ppsn,
        'result': message,
        'basedOn': basedOn,
        'confidence': str(confidence) + '%'
        }
    return jsonify(response), 200


def getValue(encounterId, typeOfValue):
    global database    
    global getValueQuery
    typeOfValue = '{}%'.format(typeOfValue)
    cursor = database.cursor()
    cursor.execute(getValueQuery,(encounterId, typeOfValue))
    value = cursor.fetchone()[0]
    cursor.close()
    return value
    
def calculateAge(birthDate):
    #birthDate = datetime.strptime(birthDate, "%Y-%m-%d")
    currentDate = date.today()
    return currentDate.year - birthDate.year - ((currentDate.month, currentDate.day) < (birthDate.month, birthDate.day))
    
def getAge(ppsn):
    global database    
    global getPatientDOB
    cursor = database.cursor()
    cursor.execute(getPatientDOB,(ppsn,))
    dob = cursor.fetchone()
    cursor.close()
    if(dob != None):
        return calculateAge(dob[0])
    return None
    
def insertPrediction(ppsn, result, basedOn, confidence):
    global database    
    global insertPredictionQuery
    cursor = database.cursor()
    currentDate = datetime.date(datetime.now())
    record = {
        'patient_ppsn':ppsn,
        'result':result,
        'based_on':basedOn,
        'date_generated':currentDate,
        'confidence': confidence
        }
    
    cursor.execute(insertPredictionQuery,record)
    database.commit()
    cursor.close()

def databaseConnection():
    print("connecting to database")
    try:
        global database
        database = mysql.connector.connect(user='root', password='password',
                                  host='127.0.0.1',
                                  database='MTPA',
                                  use_pure=False)
    except mysql.connector.Error as err:
      if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
        print("User name or password incorrect")
      elif err.errno == errorcode.ER_BAD_DB_ERROR:
        print("Database Not Setup")
      else:
        print(err)

if __name__ == '__main__':
    databaseConnection()
    app.run(host='0.0.0.0', port=8000, debug=False, threaded=False)
