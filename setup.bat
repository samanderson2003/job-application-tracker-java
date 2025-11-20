@echo off
REM Setup script for Windows
REM Run this ONCE before using run.bat

echo ==========================================
echo Job Application Tracker - Setup Script
echo ==========================================
echo.

REM Check if running as Administrator
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo ERROR: This script must be run as Administrator!
    echo.
    echo To fix:
    echo   1. Right-click on Command Prompt
    echo   2. Select "Run as administrator"
    echo   3. Navigate to this folder
    echo   4. Run: setup.bat
    echo.
    pause
    exit /b 1
)

echo Step 1: Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH!
    echo.
    echo Please install Java 17:
    echo   1. Download from: https://adoptium.net/temurin/releases/?version=17
    echo   2. Choose "Windows x64" MSI installer
    echo   3. Install and make sure to check "Set JAVA_HOME variable"
    echo   4. Restart Command Prompt and run this script again
    echo.
    pause
    exit /b 1
)
echo Java is installed!
java -version

echo.
echo Step 2: Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH!
    echo.
    echo Please install Maven:
    echo   1. Download from: https://maven.apache.org/download.cgi
    echo   2. Extract to C:\Program Files\Apache\maven
    echo   3. Add C:\Program Files\Apache\maven\bin to PATH
    echo   4. Restart Command Prompt and run this script again
    echo.
    pause
    exit /b 1
)
echo Maven is installed!
mvn -version | findstr "Apache Maven"

echo.
echo Step 3: Checking MySQL installation...
sc query MySQL >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: MySQL service not found!
    echo.
    echo Please install MySQL:
    echo   1. Download from: https://dev.mysql.com/downloads/installer/
    echo   2. Install MySQL Server
    echo   3. Set root password to: root123
    echo   4. Make sure MySQL service is installed
    echo   5. Run this script again
    echo.
    pause
    exit /b 1
)
echo MySQL service is installed!

echo.
echo Step 4: Starting MySQL service...
sc query MySQL | find "RUNNING" >nul
if %errorlevel% neq 0 (
    echo Starting MySQL...
    net start MySQL
    if %errorlevel% neq 0 (
        echo WARNING: Failed to start MySQL
        echo Please start it manually from Services
    ) else (
        echo MySQL started successfully!
    )
) else (
    echo MySQL is already running!
)

echo.
echo Step 5: Setting up database...
set /p MYSQL_PASSWORD=Enter MySQL root password (default: root123): 
if "%MYSQL_PASSWORD%"=="" set MYSQL_PASSWORD=root123

echo Testing MySQL connection...
mysql -uroot -p%MYSQL_PASSWORD% -e "SELECT 1;" >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Cannot connect to MySQL!
    echo.
    echo Possible solutions:
    echo   1. Check if password is correct
    echo   2. Reset MySQL password:
    echo      - Open MySQL Command Line Client
    echo      - Run: ALTER USER 'root'@'localhost' IDENTIFIED BY 'root123';
    echo   3. Run this script again
    echo.
    pause
    exit /b 1
)
echo MySQL connection successful!

echo.
echo Creating database...
mysql -uroot -p%MYSQL_PASSWORD% -e "CREATE DATABASE IF NOT EXISTS job_tracker_db;" 2>nul
if %errorlevel% equ 0 (
    echo Database created successfully!
) else (
    echo WARNING: Failed to create database
    pause
    exit /b 1
)

echo.
echo Loading database schema...
if exist "src\main\resources\schema.sql" (
    mysql -uroot -p%MYSQL_PASSWORD% job_tracker_db < src\main\resources\schema.sql
    if %errorlevel% equ 0 (
        echo Schema loaded successfully!
    ) else (
        echo WARNING: Failed to load schema
    )
) else (
    echo WARNING: schema.sql not found!
)

echo.
echo Loading sample data...
if exist "src\main\resources\data.sql" (
    mysql -uroot -p%MYSQL_PASSWORD% job_tracker_db < src\main\resources\data.sql
    if %errorlevel% equ 0 (
        echo Sample data loaded successfully!
    ) else (
        echo WARNING: Failed to load sample data
    )
) else (
    echo WARNING: data.sql not found!
)

echo.
echo Step 6: Building the application...
echo This may take a few minutes on first run...
echo.
mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed!
    echo Please check the errors above.
    pause
    exit /b 1
)

echo.
echo ==========================================
echo Setup completed successfully!
echo ==========================================
echo.
echo To start the application, run:
echo   run.bat
echo.
echo Then open your browser to:
echo   http://localhost:8080
echo.
echo Login with:
echo   Username: demo
echo   Password: password123
echo.
echo Press any key to exit...
pause >nul
