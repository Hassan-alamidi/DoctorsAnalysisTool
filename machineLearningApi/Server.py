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

model = tensorflow.keras.models.load_model("diabetiesPredictionModel.h5")
database = None
insertPredictionQuery = ("INSERT INTO prediction_reports (patient_ppsn, result, based_on) VALUES (%(patient_ppsn)s, %(result)s, %(based_on)s)")

app = Flask(__name__)

@app.route('/diabeties', methods=['GET'])
def predictDiabeties():
    if not request.json or not 'ppsn' in request.json:
        abort(400)
    
    #ppsn is not used yet but it will be used to store results in database
    ppsn = request.json['ppsn']
    age = request.json['age']
    bmi = request.json['bmi']
    sbp = request.json['sbp']
    dbp = request.json['dbp']
    hdl = request.json['hdl']
    ldl = request.json['ldl']
    #order should be 'ppsn', 'age', 'diastolic', 'systolic', 'bmi', 'hdl', 'ldl'
    
    tobePredicted = np.array([[age,dbp,sbp,bmi,hdl,ldl]])
    prediction = model.predict(tobePredicted)
    print(prediction[0,0], flush=True);
    if(prediction[0,0] == 0.0):
        message = "This Patient appears to be Healthy"
    else:
        message = "This Patient appears to have or atleast is soon to have Diabeties"
     
    insertPrediction(ppsn, "12345", "Based on: once I caught a fish alive")
    response = {
        'message': message,
        'prediction': int(prediction[0,0])
    }
    return jsonify(response), 200

def insertPrediction(ppsn, result, basedOn):
    global database    
    global insertPredictionQuery
    cursor = database.cursor()
    record = {
        'patient_ppsn':ppsn,
        'result':result,
        'based_on':basedOn
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
    app.run(debug=True)
