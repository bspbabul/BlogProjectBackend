#!/bin/bash

echo "Starting Spring Boot application..."
nohup java -jar /home/ubuntu/blogbackend.jar > /home/ubuntu/application.log 2>&1 &
