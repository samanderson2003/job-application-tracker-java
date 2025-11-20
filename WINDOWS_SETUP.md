# Windows Setup Guide - Job Application Tracker

## Quick Start for Windows

### Step 1: Install Prerequisites

#### 1.1 Install Java 17
1. Download from [Adoptium](https://adoptium.net/temurin/releases/?version=17)
2. Choose "Windows x64" installer
3. Run installer and select "Set JAVA_HOME variable" during installation
4. Verify installation:
   ```cmd
   java -version
   ```

#### 1.2 Install Maven
1. Download from [Maven Downloads](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Apache\maven`
3. Add to System PATH:
   - Right-click "This PC" → Properties → Advanced System Settings
   - Environment Variables → System Variables → Path → Edit
   - Add: `C:\Program Files\Apache\maven\bin`
4. Verify installation:
   ```cmd
   mvn -version
   ```

#### 1.3 Install MySQL
1. Download from [MySQL Downloads](https://dev.mysql.com/downloads/installer/)
2. Choose "MySQL Installer for Windows"
3. Install MySQL Server
4. Remember the root password you set (use `root123` for simplicity)
5. Start MySQL:
   - Press `Win + R`, type `services.msc`
   - Find "MySQL" and start it

---

### Step 2: Setup Database

Open Command Prompt (cmd) as Administrator and run:

```cmd
REM Login to MySQL (use the password you set during installation)
mysql -u root -p

REM In MySQL console, run these commands:
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root123';
CREATE DATABASE job_tracker_db;
EXIT;

REM Load the schema
cd C:\path\to\job-application-tracker
mysql -uroot -proot123 job_tracker_db < src\main\resources\schema.sql

REM Load sample data
mysql -uroot -proot123 job_tracker_db < src\main\resources\data.sql
```

---

### Step 3: Set Environment Variables

1. Right-click "This PC" → Properties → Advanced System Settings
2. Click "Environment Variables"
3. Under "System Variables", click "New"

**Add JAVA_HOME:**
- Variable name: `JAVA_HOME`
- Variable value: `C:\Program Files\Eclipse Adoptium\jdk-17.0.x` (your Java path)

**Add MAVEN_HOME:**
- Variable name: `MAVEN_HOME`
- Variable value: `C:\Program Files\Apache\maven`

**Update PATH:**
- Edit "Path" variable
- Add: `%JAVA_HOME%\bin`
- Add: `%MAVEN_HOME%\bin`

---

### Step 4: Build the Application

Open Command Prompt in the project directory:

```cmd
cd C:\path\to\job-application-tracker
mvn clean install -DskipTests
```

---

### Step 5: Run the Application

**Option 1: Use the batch script**
```cmd
run.bat
```

**Option 2: Run manually**
```cmd
mvn spring-boot:run
```

---

### Step 6: Access the Application

Open your browser and go to:
```
http://localhost:8080
```

**Login with:**
- Username: `demo`
- Password: `password123`

---

## Troubleshooting Windows Issues

### Issue 1: "java is not recognized"

**Solution:**
1. Open Command Prompt and run:
   ```cmd
   where java
   ```
2. If not found, add Java to PATH:
   - Find Java installation (usually `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`)
   - Add `C:\Program Files\Eclipse Adoptium\jdk-17.x.x\bin` to PATH
3. Restart Command Prompt

### Issue 2: "mvn is not recognized"

**Solution:**
1. Download Maven zip from [Maven Downloads](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Apache\maven`
3. Add `C:\Program Files\Apache\maven\bin` to PATH
4. Restart Command Prompt

### Issue 3: MySQL Connection Failed

**Solution 1 - Check if MySQL is running:**
```cmd
sc query MySQL
```

**Solution 2 - Start MySQL:**
```cmd
net start MySQL
```

**Solution 3 - Reset MySQL password:**
1. Stop MySQL service:
   ```cmd
   net stop MySQL
   ```
2. Start MySQL without password:
   - Edit MySQL config: `C:\ProgramData\MySQL\MySQL Server 8.0\my.ini`
   - Add `skip-grant-tables` under `[mysqld]`
   - Save and restart MySQL:
     ```cmd
     net start MySQL
     ```
3. Reset password:
   ```cmd
   mysql -u root
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'root123';
   FLUSH PRIVILEGES;
   EXIT;
   ```
4. Remove `skip-grant-tables` from `my.ini` and restart MySQL

### Issue 4: Port 8080 Already in Use

**Solution:**
```cmd
REM Find process using port 8080
netstat -ano | findstr :8080

REM Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

### Issue 5: Permission Denied Errors

**Solution:**
Run Command Prompt as Administrator:
1. Press `Win + X`
2. Select "Command Prompt (Admin)" or "Windows PowerShell (Admin)"

### Issue 6: "JAVA_HOME not set"

**Solution:**
Set JAVA_HOME manually in Command Prompt:
```cmd
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.x
set PATH=%JAVA_HOME%\bin;%PATH%
```

Or add permanently through Environment Variables (see Step 3 above)

---

## Configuration File Paths (Windows)

- **MySQL Config:** `C:\ProgramData\MySQL\MySQL Server 8.0\my.ini`
- **Application Config:** `src\main\resources\application.properties`
- **Java:** Usually `C:\Program Files\Eclipse Adoptium\` or `C:\Program Files\Java\`
- **Maven:** Usually `C:\Program Files\Apache\maven\`

---

## Quick Commands Reference

### Start MySQL:
```cmd
net start MySQL
```

### Stop MySQL:
```cmd
net stop MySQL
```

### Build Project:
```cmd
mvn clean install -DskipTests
```

### Run Application:
```cmd
mvn spring-boot:run
```

### Check Java Version:
```cmd
java -version
```

### Check Maven Version:
```cmd
mvn -version
```

### MySQL Login:
```cmd
mysql -uroot -proot123
```

---

## Common File Locations

**Project Structure on Windows:**
```
C:\Users\YourName\job-application-tracker\
├── src\
│   ├── main\
│   │   ├── java\
│   │   └── resources\
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test\
├── pom.xml
├── run.bat
└── SETUP_GUIDE.md
```

---

## Creating Desktop Shortcut

1. Right-click on Desktop → New → Shortcut
2. Location: `C:\Windows\System32\cmd.exe /k "cd C:\path\to\job-application-tracker && run.bat"`
3. Name: "Job Tracker"
4. Click Finish

---

## Support

If you encounter any issues:
1. Check this troubleshooting guide
2. Make sure all prerequisites are installed correctly
3. Verify MySQL is running: `sc query MySQL`
4. Check application logs in Command Prompt
5. Ensure JAVA_HOME and PATH are set correctly

---

## Technology Requirements

- **Operating System:** Windows 10/11
- **Java:** Version 17 or higher
- **Maven:** Version 3.6 or higher
- **MySQL:** Version 8.0 or higher
- **RAM:** Minimum 4GB (8GB recommended)
- **Disk Space:** Minimum 500MB free space
