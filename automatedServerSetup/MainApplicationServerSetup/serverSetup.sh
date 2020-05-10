#!/bin/bash

#be sure to scp react build and java jar before launching
cd ~/
echo "installing core system components"
sudo apt-get -y update
sudo apt-get -y upgrade
sudo apt -y install openjdk-11-jdk
sudo apt -y install npm
sudo npm install -g serve

cd ~/DoctorsAnalysisTool/frontend
npm install
nohup serve -s build &
