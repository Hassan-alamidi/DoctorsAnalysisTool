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
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from IPython.core.display import display, HTML
display(HTML("<style>.container { width:100% !important; }</style>"))

np.random.seed(1237981)
sc = StandardScaler()
oneHEncoder = OneHotEncoder()
dataset = pd.read_csv("../dataTransformation/MapReduceOutput/PatientCurrentCondition.csv", index_col=[0])
queuedPredictions = pd.read_csv("patientsQueue.csv", index_col=[0])
dataset = dataset.iloc[:0]
dataset = pd.concat([dataset,queuedPredictions], sort=False)
dataset = dataset[dataset.columns.difference(['target', 'condToPredict', 'patient'])]
model = tensorflow.keras.models.load_model("predictionModel.h5")

yPrediction = model.predict(dataset)