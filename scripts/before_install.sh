#!/bin/bash

echo "Updating the instance..."
sudo apt update -y

echo "Installing MySQL and Java..."
sudo apt install -y mysql-server openjdk-22-jdk

echo "Stopping existing application..."
sudo pkill -f 'java -jar' || true

echo "Cleaning old application files..."
sudo rm -rf /home/ubuntu/blogbackend.jar
