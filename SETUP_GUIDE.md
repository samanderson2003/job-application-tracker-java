# Job Application Tracker - Complete Setup Guide

## Prerequisites

Before running this application, make sure you have the following installed:

### 1. **Java 17**
- **Check if installed:** Open terminal and run:
  ```bash
  java -version
  ```
- **Install if needed:**
  - **Mac:** `brew install openjdk@17`
  - **Windows:** Download from [Oracle](https://www.oracle.com/java/technologies/downloads/#java17) or [Adoptium](https://adoptium.net/)
  - **Linux:** `sudo apt install openjdk-17-jdk`

### 2. **Maven**
- **Check if installed:**
  ```bash
  mvn -version
  ```
- **Install if needed:**
  - **Mac:** `brew install maven`
  - **Windows:** Download from [Maven Downloads](https://maven.apache.org/download.cgi)
  - **Linux:** `sudo apt install maven`

### 3. **MySQL**
- **Check if installed:**
  ```bash
  mysql --version
  ```
- **Install if needed:**
  - **Mac:** `brew install mysql`
  - **Windows:** Download from [MySQL Downloads](https://dev.mysql.com/downloads/installer/)
  - **Linux:** `sudo apt install mysql-server`

---

## Step-by-Step Setup

### Step 1: Start MySQL Server

#### Mac:
```bash
brew services start mysql
```

#### Windows:
- Open Services app and start "MySQL" service
- Or use MySQL Workbench

#### Linux:
```bash
sudo systemctl start mysql
```

### Step 2: Set MySQL Root Password

Open terminal and run:
```bash
mysql -u root
```

Then in MySQL console:
```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root123';
FLUSH PRIVILEGES;
EXIT;
```

### Step 3: Create Database

Run this command:
```bash
mysql -uroot -proot123 -e "CREATE DATABASE IF NOT EXISTS job_tracker_db;"
```

### Step 4: Load Database Schema

From the project directory:
```bash
mysql -uroot -proot123 job_tracker_db < src/main/resources/schema.sql
```

### Step 5: Load Sample Data (Optional)

```bash
mysql -uroot -proot123 job_tracker_db < src/main/resources/data.sql
```

### Step 6: Build the Project

Navigate to project directory and run:
```bash
mvn clean install -DskipTests
```

### Step 7: Run the Application

```bash
mvn spring-boot:run
```

Or if you prefer using Java directly:
```bash
java -jar target/job-application-tracker-1.0-SNAPSHOT.jar
```

### Step 8: Access the Application

Open your browser and go to:
```
http://localhost:8080
```

**Default Login Credentials:**
- Username: `demo`
- Password: `password123`

---

## Troubleshooting

### Issue 1: "Access denied for user 'root'@'localhost'"

**Solution:** Reset MySQL password
```bash
# Stop MySQL
brew services stop mysql  # Mac
# or
sudo systemctl stop mysql  # Linux

# Start MySQL in safe mode (Mac/Linux)
sudo mysqld_safe --skip-grant-tables &

# Login without password
mysql -u root

# Reset password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root123';
FLUSH PRIVILEGES;
EXIT;

# Restart MySQL normally
brew services start mysql  # Mac
# or
sudo systemctl start mysql  # Linux
```

### Issue 2: "Port 8080 already in use"

**Solution:** Kill the process using port 8080
```bash
# Mac/Linux
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

### Issue 3: "JAVA_HOME not set"

**Solution:** Set JAVA_HOME environment variable

**Mac/Linux:**
```bash
# Find Java path
/usr/libexec/java_home -V  # Mac
# or
which java  # Linux

# Set JAVA_HOME (add to ~/.bash_profile or ~/.zshrc)
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```

**Windows:**
1. Right-click "This PC" → Properties → Advanced System Settings
2. Environment Variables → New (System Variable)
3. Variable name: `JAVA_HOME`
4. Variable value: `C:\Program Files\Java\jdk-17` (your Java installation path)

### Issue 4: "Table doesn't exist"

**Solution:** Reload the schema
```bash
mysql -uroot -proot123 job_tracker_db < src/main/resources/schema.sql
```

### Issue 5: "Connection refused" to MySQL

**Solution:** Make sure MySQL is running
```bash
# Mac
brew services list
brew services start mysql

# Linux
sudo systemctl status mysql
sudo systemctl start mysql

# Windows - Check Services app
```

### Issue 6: Build fails with "mvn: command not found"

**Solution:** Install Maven or use Maven wrapper
```bash
# If Maven wrapper exists in project
./mvnw clean install -DskipTests  # Mac/Linux
mvnw.cmd clean install -DskipTests  # Windows
```

---

## Quick Start Script (Mac/Linux)

Save this as `quick-start.sh` in the project directory:

```bash
#!/bin/bash

echo "Starting MySQL..."
brew services start mysql

echo "Creating database..."
mysql -uroot -proot123 -e "CREATE DATABASE IF NOT EXISTS job_tracker_db;"

echo "Loading schema..."
mysql -uroot -proot123 job_tracker_db < src/main/resources/schema.sql

echo "Loading sample data..."
mysql -uroot -proot123 job_tracker_db < src/main/resources/data.sql

echo "Building application..."
mvn clean install -DskipTests

echo "Starting application..."
mvn spring-boot:run
```

Make it executable:
```bash
chmod +x quick-start.sh
./quick-start.sh
```

---

## Configuration

If you want to use different credentials, edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker_db
spring.datasource.username=root
spring.datasource.password=root123
```

---

## Creating New User Account

1. Login with demo account
2. Or register at: `http://localhost:8080/register`
3. Or manually insert into database:

```sql
INSERT INTO users (username, email, password, full_name, created_at, updated_at) 
VALUES ('yourname', 'your@email.com', 'password123', 'Your Full Name', NOW(), NOW());
```

---

## Support

If you encounter any issues not covered here:
1. Check application logs in the terminal
2. Check MySQL logs: `mysql -uroot -proot123 -e "SHOW VARIABLES LIKE 'log_error';"`
3. Ensure all prerequisites are properly installed

---

## Technology Stack

- **Backend:** Spring Boot 2.7.14
- **Database:** MySQL 9.5
- **Build Tool:** Maven 3.9
- **Java Version:** 17
- **ORM:** Hibernate/JPA
- **Frontend:** Thymeleaf, Bootstrap 5

---

## Project Structure

```
job-application-tracker/
├── src/
│   ├── main/
│   │   ├── java/com/jobtracker/
│   │   │   ├── Application.java
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       ├── data.sql
│   │       ├── static/
│   │       └── templates/
│   └── test/
├── pom.xml
└── README.md
```
