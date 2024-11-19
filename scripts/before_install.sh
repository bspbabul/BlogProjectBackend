#!/bin/bash

echo "Updating package list..."
sudo apt update

echo "Installing Java..."
sudo apt install openjdk-22-jdk -y

echo "Installing MySQL..."
sudo apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql

echo "Cleaning old application files..."
sudo rm -rf /home/ubuntu/blogbackend.jar


echo "Securing MySQL installation..."
sudo mysql_secure_installation <<EOF
n
y
y
y
y
EOF

echo "Configuring MySQL user and database..."
sudo mysql -u root -p <<EOF
CREATE DATABASE IF NOT EXISTS blog_app;
CREATE USER IF NOT EXISTS 'babul'@'localhost' IDENTIFIED BY 'Babul@7873';
GRANT ALL PRIVILEGES ON blog_app.* TO 'babul'@'localhost';
FLUSH PRIVILEGES;
EOF

echo "System prepared for application deployment."

