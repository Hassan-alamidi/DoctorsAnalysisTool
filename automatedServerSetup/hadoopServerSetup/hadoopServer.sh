#!/bin/bash

cd ~/
echo "installing core system components"
sudo apt-get -y update
sudo apt-get -y upgrade
sudo apt -y install openjdk-8-jdk
sudo apt -y install software-properties-common
sudo apt -y install python3
sudo apt -y install awscli

echo "setting up ssh key for hadoop"
yes "" | ssh-keygen -t rsa -P ""
cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys

echo "downloading and configuring hadoop"
wget https://downloads.apache.org/hadoop/common/hadoop-2.10.0/hadoop-2.10.0.tar.gz
tar xzf hadoop-2.10.0.tar.gz
sudo rm hadoop-2.10.0.tar.gz

echo 'export HADOOP_HOME=~/hadoop-2.10.0' >> .bashrc
echo 'export HADOOP_INSTALL=$HADOOP_HOME' >> .bashrc
echo 'export HADOOP_MAPRED_HOME=$HADOOP_HOME' >> .bashrc
echo 'export HADOOP_COMMON_HOME=$HADOOP_HOME' >> .bashrc
echo 'export HADOOP_HDFS_HOME=$HADOOP_HOME' >> .bashrc
echo 'export YARN_HOME=$HADOOP_HOME' >> .bashrc
echo 'export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native' >> .bashrc
echo 'export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin' >> .bashrc
echo 'export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:jre/bin/java::")' >> .bashrc

mkdir hadoopData
cp ~/hadoopServerSetup/core-site.xml ~/hadoop-2.10.0/etc/hadoop/
cp ~/hadoopServerSetup/hdfs-site.xml ~/hadoop-2.10.0/etc/hadoop/
cp ~/hadoopServerSetup/mapred-site.xml ~/hadoop-2.10.0/etc/hadoop/
cp ~/hadoopServerSetup/yarn-site.xml ~/hadoop-2.10.0/etc/hadoop/

echo "Downloading and Installing Sqoop"

wget https://downloads.apache.org/sqoop/1.4.7/sqoop-1.4.7.bin__hadoop-2.6.0.tar.gz
tar -xvf sqoop-1.4.7.bin__hadoop-2.6.0.tar.gz
rm sqoop-1.4.7.bin__hadoop-2.6.0.tar.gz
mv sqoop-1.4.7.bin__hadoop-2.6.0 sqoop-1.4.7
cp ~/hadoopServerSetup/sqoop-env.sh ~/sqoop-1.4.7/conf

echo 'export SQOOP_HOME=/home/ubuntu/sqoop-1.4.7' >> .bashrc
echo 'export PATH=$PATH:$SQOOP_HOME/bin' >> .bashrc

echo "downloading and installing mysql connector"

wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.19.tar.gz
tar -xvf mysql-connector-java-8.0.19.tar.gz
rm mysql-connector-java-8.0.19.tar.gz
cp mysql-connector-java-8.0.19/mysql-connector-java-8.0.19.jar /home/ubuntu/sqoop-1.4.7/lib

echo "installing python with required packages"
#python 3 should already be installed so pip and packages are to be installed here
sudo apt install -y python3-pip
yes | sudo -H pip3 install pandas
yes | sudo -H pip3 install numpy
yes | sudo -H pip3 install Keras
yes | sudo -H pip3 install tensorflow
yes | sudo -H pip3 install sklearn
yes | sudo -H pip3 install rope

echo "complete"

#all the below must be done manually
#source ~/.bashrc
#hdfs namenode -format

# echo "setup complete now starting services"
#yes yes | bash start-dfs.sh
#start-yarn.sh
