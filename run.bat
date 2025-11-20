@echo off
REM Simple script to run the application on Windows
REM Make sure you've run setup.bat first!

echo ==========================================
echo Job Application Tracker - Starting...
echo ==========================================
echo.

REM Check if running as Administrator
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo WARNING: Not running as Administrator
    echo MySQL service may not start automatically
    echo.
    echo To fix: Right-click Command Prompt and select "Run as Administrator"
    echo.
)

REM Check if MySQL service exists and is running
sc query MySQL >nul 2>&1
if %errorlevel% equ 0 (
    sc query MySQL | find "RUNNING" >nul
    if %errorlevel% neq 0 (
        echo MySQL is not running. Attempting to start...
        net start MySQL >nul 2>&1
        if %errorlevel% neq 0 (
            echo Failed to start MySQL. Please start it manually:
            echo   1. Press Win + R
            echo   2. Type: services.msc
            echo   3. Find "MySQL" and click Start
            echo.
            echo Or run this script as Administrator
            pause
        ) else (
            echo MySQL started successfully!
            timeout /t 2 /nobreak >nul
        )
    ) else (
        echo MySQL is already running!
    )
) else (
    echo MySQL service not found. Please install MySQL first.
    echo See WINDOWS_SETUP.md for instructions.
    pause
    exit /b 1
)

echo.
echo Checking if project is built...
if not exist "target\job-application-tracker-1.0-SNAPSHOT.jar" (
    echo Project not built yet. Building now...
    echo This may take a few minutes on first run...
    echo.
    call mvn clean install -DskipTests
    if %errorlevel% neq 0 (
        echo.
        echo BUILD FAILED!
        echo Please check the errors above.
        pause
        exit /b 1
    )
)

echo.
echo Starting application...
echo Press Ctrl+C to stop
echo.
echo Once started, open: http://localhost:8080
echo Login: demo / password123
echo.

REM Run the application
mvn spring-boot:run
