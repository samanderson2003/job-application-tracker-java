# 🚀 Quick Start Guide - Job Application Tracker

## 📦 One-Command Setup (After Prerequisites)

### Prerequisites Check
```bash
# Check Java (needs 11+)
java -version

# Check Maven (needs 3.6+)
mvn -version

# Check MySQL (needs 8.0+)
mysql --version
```

### 🏃‍♂️ Quick Start Steps

#### 1. Setup Database (2 minutes)
```bash
# Start MySQL
mysql.server start  # macOS
# OR
sudo service mysql start  # Linux

# Login to MySQL
mysql -u root -p

# Run these commands in MySQL
CREATE DATABASE job_tracker_db;
USE job_tracker_db;
source src/main/resources/schema.sql;
exit;
```

#### 2. Configure Application (30 seconds)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD_HERE
```

#### 3. Build & Run (2 minutes)
```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

#### 4. Access Application
Open browser: **http://localhost:8080**

---

## 🎯 First Time Use

### Register Account
1. Click "Register here"
2. Fill in:
   - Username: `demo`
   - Email: `demo@test.com`
   - Password: `password123`
3. Click "Register"

### Login & Add Jobs
1. Login with your credentials
2. Click "Add New Job"
3. Fill in job details
4. Click "Save Job"
5. **Drag & drop** jobs between columns!

---

## 📊 Default Demo Account
Already created in database:
- **Username**: `demo`
- **Password**: `password123`

---

## 🐛 Common Issues

### "Access Denied" Error
```bash
# Update password in application.properties
spring.datasource.password=your_actual_mysql_password
```

### "Port 8080 Already in Use"
```bash
# Option 1: Stop the process
lsof -ti:8080 | xargs kill -9

# Option 2: Change port in application.properties
server.port=8081
```

### Maven Build Fails
```bash
# Force update dependencies
mvn clean install -U

# Skip tests
mvn clean install -DskipTests
```

### Database Not Found
```bash
# Manually create database
mysql -u root -p
CREATE DATABASE job_tracker_db;
```

---

## 📱 Features to Try

1. **Drag & Drop** - Move jobs between Wishlist → Applied → Interview → Offer
2. **Add Jobs** - Click "Add New Job" button
3. **Edit Jobs** - Click edit icon on any job card
4. **Delete Jobs** - Click trash icon (with confirmation)
5. **View Statistics** - See real-time counts at the top

---

## 🔧 Configuration

### Change Database Name
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DB_NAME
```

### Change Port
```properties
server.port=3000
```

### Disable SQL Logging
```properties
spring.jpa.show-sql=false
```

---

## 📂 Project Structure Overview

```
src/main/
├── java/com/jobtracker/
│   ├── controller/     ← Web & REST APIs
│   ├── model/          ← Database entities
│   ├── repository/     ← Data access
│   ├── service/        ← Business logic
│   └── Application.java ← Main app
└── resources/
    ├── templates/      ← HTML pages
    ├── static/         ← CSS & JS
    ├── application.properties ← Config
    └── schema.sql      ← Database setup
```

---

## 🎓 Tech Stack Summary

| Layer      | Technology       |
|------------|------------------|
| Frontend   | Bootstrap 5, Thymeleaf |
| Backend    | Spring Boot 2.7  |
| Database   | MySQL 8.0        |
| Build      | Maven            |
| Language   | Java 11          |

---

## 📖 Full Documentation

See [README.md](README.md) for complete documentation.

---

## 💡 Tips

- **Hot Reload**: Spring Boot DevTools auto-reloads on code changes
- **Database Reset**: Drop and recreate `job_tracker_db` to start fresh
- **IDE Support**: Import as Maven project in IntelliJ or Eclipse
- **Debug Mode**: Run with `-Ddebug` flag for detailed logs

---

## ✅ Verification Checklist

- [ ] Java 11+ installed
- [ ] Maven 3.6+ installed
- [ ] MySQL 8.0+ running
- [ ] Database created
- [ ] Password configured
- [ ] Build successful
- [ ] App running on port 8080
- [ ] Can access http://localhost:8080
- [ ] Can register/login
- [ ] Can add/edit/delete jobs

---

**Happy Job Tracking! 🎉**
