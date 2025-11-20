#!/bin/bash

# Simple script to run the application
# Make sure you've run setup.sh first!

echo "Starting Job Application Tracker..."
echo "Press Ctrl+C to stop"
echo ""

# Check if MySQL is running
if ! pgrep -x mysqld > /dev/null; then
    echo "Starting MySQL server..."
    if command -v brew &> /dev/null; then
        brew services start mysql
    else
        sudo systemctl start mysql
    fi
    sleep 2
fi

# Set JAVA_HOME if using Homebrew Java on Mac
if [ -d "/opt/homebrew/opt/openjdk@17" ]; then
    export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
fi

# Run the application
mvn spring-boot:run
