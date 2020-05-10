#!/bin/bash

sudo apt -y update
sudo apt install -y python3-pip
yes | sudo -H pip3 install mysql-connector-python
yes | sudo -H pip3 install numpy
yes | sudo -H pip3 install Keras
yes | sudo -H pip3 install tensorflow
yes | sudo -H pip3 install Flask 
