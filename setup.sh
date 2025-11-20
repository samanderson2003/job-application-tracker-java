#!/bin/bash

# Job Application Tracker - Setup Script
# This script helps you set up the application quickly

echo "🌐 Job Application Tracker - Setup Script"
echo "=========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Check Java
echo "📋 Checking prerequisites..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo -e "${GREEN}✓${NC} Java found: $JAVA_VERSION"
else
    echo -e "${RED}✗${NC} Java not found. Please install Java 11 or higher."
    exit 1
fi

# Check Maven
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
    echo -e "${GREEN}✓${NC} Maven found: $MVN_VERSION"
else
    echo -e "${RED}✗${NC} Maven not found. Please install Maven 3.6 or higher."
    exit 1
fi

# Check MySQL
if command -v mysql &> /dev/null; then
    MYSQL_VERSION=$(mysql --version | awk '{print $5}' | sed 's/,//')
    echo -e "${GREEN}✓${NC} MySQL found: $MYSQL_VERSION"
else
    echo -e "${RED}✗${NC} MySQL not found. Please install MySQL 8.0 or higher."
    exit 1
fi

echo ""
echo "✅ All prerequisites met!"
echo ""

# Database setup
echo "🗄️  Setting up database..."
read -p "Enter MySQL root password: " -s MYSQL_PASSWORD
echo ""

# Create database and run schema
mysql -u root -p"$MYSQL_PASSWORD" <<EOF
CREATE DATABASE IF NOT EXISTS job_tracker_db;
USE job_tracker_db;
SOURCE src/main/resources/schema.sql;
EOF

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Database setup completed!"
else
    echo -e "${RED}✗${NC} Database setup failed. Please check your MySQL password and try again."
    exit 1
fi

echo ""

# Update application.properties
echo "⚙️  Configuring application..."
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    sed -i '' "s/spring.datasource.password=.*/spring.datasource.password=$MYSQL_PASSWORD/" src/main/resources/application.properties
else
    # Linux
    sed -i "s/spring.datasource.password=.*/spring.datasource.password=$MYSQL_PASSWORD/" src/main/resources/application.properties
fi

echo -e "${GREEN}✓${NC} Configuration updated!"
echo ""

# Build project
echo "🔨 Building project..."
mvn clean install -q

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓${NC} Build successful!"
else
    echo -e "${RED}✗${NC} Build failed. Check the error messages above."
    exit 1
fi

echo ""
echo "=========================================="
echo -e "${GREEN}✅ Setup Complete!${NC}"
echo "=========================================="
echo ""
echo "To start the application, run:"
echo "  mvn spring-boot:run"
echo ""
echo "Then open your browser to:"
echo "  http://localhost:8080"
echo ""
echo "Default demo account:"
echo "  Username: demo"
echo "  Password: password123"
echo ""
echo "Happy job tracking! 🎉"
