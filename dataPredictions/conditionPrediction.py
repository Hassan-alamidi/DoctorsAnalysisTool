#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
import tensorflow.keras 
import numpy as np
import pandas as pd
from pandas.api.types import is_string_dtype
from tensorflow.keras.models import Sequential
from sklearn.preprocessing import OneHotEncoder
from tensorflow.keras.layers import Dense
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.layers import Input, Dense, Dropout, Embedding, LSTM, Flatten
from tensorflow.keras.models import Model
from tensorflow.keras.utils import to_categorical
from tensorflow.keras.callbacks import ModelCheckpoint
from sklearn.preprocessing import StandardScaler
from tensorflow.keras.layers import BatchNormalization
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
plt.style.use('ggplot')
from IPython.core.display import display, HTML
display(HTML("<style>.container { width:100% !important; }</style>"))

np.random.seed(1237981)
sc = StandardScaler()
oneHEncoder = OneHotEncoder()
dataset = pd.read_csv("../dataTransformation/MapReduceOutput/PatientCurrentCondition.csv", index_col=[0])
y = dataset.iloc[:,0:1].values
tst = y
y = oneHEncoder.fit_transform(y).toarray()

print(oneHEncoder.get_feature_names())

numClass = len(np.unique(dataset.condToPredict.values))


x = dataset[dataset.columns.difference(['target', 'condToPredict', 'patient'])]

for col in x.columns:
    if(is_string_dtype(x[col])):
        #remove value add to new array one hot code after normalization add back to original input array
        x[col] = x[col].astype('category').cat.codes
numCol = len(x.columns)
#fix issues with this and change codes to onehotencoding
#convert to numpy array
#noooooooo
x = x.iloc[:,0:numCol].values
#for now I will not be normalizing the data, maybe after I get the neural network working and I am trying to improve I might reimplement
#x = sc.fit_transform(x)



xTrain, xTest, yTrain, yTest = train_test_split(x,y, test_size=0.1)

#input = Input(shape=(numCol, ))

#cannot find difinitive answer on number of hidden layers/neurons so plan is to use two hidden layers 
#first the same size of input and the second double the size of the output
#although I believe the first hidden layer would probably work best at 2/3 the size of input
outputSize = numClass
print(outputSize)
model = Sequential()
model.add(Dense((numCol*1.8),input_dim=numCol,activation='relu'))
#model.add(Dense((numCol*1.3),activation='relu'))
model.add(Dense((outputSize*1.8),activation='relu'))
#model.add(Dense((outputSize*1.2),activation='relu'))
#model.add(BatchNormalization())
#model.add(Dropout(0.5))
model.add(Dense((outputSize*1.4),activation='tanh'))
model.add(Dense(outputSize,activation='softmax'))
#model.compile(loss='mean_squared_error', optimizer='sgd', metrics=['accuracy'])
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

history = model.fit(xTrain,yTrain, epochs=400, batch_size=128)


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
