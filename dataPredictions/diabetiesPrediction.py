#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Apr 28 05:11:12 2020

@author: hassan
"""
import subprocess
import tensorflow.keras 
import numpy as np
import pandas as pd
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

cat = subprocess.Popen(["/home/ubuntu/hadoop-2.10.0/bin/hadoop", "fs", "-cat", "mapOutput/diabeties/*"], stdout=subprocess.PIPE, encoding='utf8')
#file = open("/home/hassan/Desktop/forTesting/dataTransformation/diabetiesResult.txt", 'r')
#lines = file.readlines()
dataList = []
for line in cat.stdout:
    line = line.strip()
    data = line.split(",")
    dataList.append(data)
    
data = pd.DataFrame(dataList, columns=['ppsn', 'age', 'diastolic', 'systolic', 'bmi', 'hdl', 'ldl', 'hasDiabeties'])

#age is set to float because numpy can only handle one type and I dont want it to default to string
data["age"] = pd.to_numeric(data["age"], downcast="float")
data["diastolic"] = pd.to_numeric(data["diastolic"], downcast="float")
data["systolic"] = pd.to_numeric(data["systolic"], downcast="float")
data["bmi"] = pd.to_numeric(data["bmi"], downcast="float")
data["hdl"] = pd.to_numeric(data["hdl"], downcast="float")
data["ldl"] = pd.to_numeric(data["ldl"], downcast="float")
data["hasDiabeties"] = data.hasDiabeties.astype(int)

x = data[data.columns.difference(['ppsn','hasDiabeties'])]
y = data.iloc[:,-1].values
inputSize = x.shape[1]
x = x.iloc[:,0:inputSize].values


xTrain, xTest, yTrain, yTest = train_test_split(x,y, test_size=0.2)


model = Sequential()
model.add(Dense(inputSize*1.5, input_dim=6, activation='relu'))
model.add(Dense(inputSize, activation='relu'))
model.add(Dense(1, activation='sigmoid'))

model.compile(loss="binary_crossentropy", optimizer='adam', metrics=['accuracy'])

history = model.fit(xTrain,yTrain, epochs=200, batch_size=64)


print("Begining Testing Phase")
print("Begining Testing Phase")
print("Begining Testing Phase")
print("Begining Testing Phase")
print("Begining Testing Phase")
print("Begining Testing Phase")
yPrediction = model.predict(xTest)

pred = list()
for i in range(len(yPrediction)):
    pred.append(np.argmax(yPrediction[i]))
#Converting one hot encoded test label to label
test = list()
for i in range(len(yTest)):
    test.append(np.argmax(yTest[i]))
    
a = accuracy_score(pred,test)
print('Accuracy is:', a*100)

model.save("diabetiesPredictionModel.h5")