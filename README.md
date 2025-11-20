# 🌐 Job Application Tracker

A modern, full-featured web application built with **Java Spring Boot** and **MySQL** to help you efficiently track and manage your job applications throughout your job search journey.

![Java](https://img.shields.io/badge/Java-11-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## ✨ Features

### 👤 User Management
- **User Registration & Login** - Secure authentication with encrypted passwords (BCrypt)
- **Session Management** - Persistent login sessions
- **Profile Management** - Update user information

### 📊 Kanban-Style Job Board
Track your applications across 6 stages:
1. **Wishlist** - Jobs you're interested in
2. **Applied** - Applications submitted
3. **Interview** - Interview scheduled
4. **Offer** - Received offers
5. **Rejected** - Unsuccessful applications
6. **Accepted** - Jobs you've accepted

### 🎯 Core Functionality
- ✅ **Drag & Drop** - Move jobs between columns easily
- ✅ **Add/Edit/Delete Jobs** - Full CRUD operations
- ✅ **Job Details** - Track company, position, location, salary, type, and more
- ✅ **Application Statistics** - Real-time dashboard metrics
- ✅ **Job Notes** - Add custom notes to each application
- ✅ **Deadline Tracking** - Never miss an application deadline
- ✅ **Custom Labels** - Tag jobs with custom labels
- ✅ **Contact Management** - Store recruiter/HR contact info
- ✅ **Interview Notes** - Track interview feedback
- ✅ **Document Management** - Upload resumes and cover letters
- ✅ **Follow-up Tracker** - Record follow-up emails

### 🎨 Modern UI
- Clean, light-blue gradient design
- Fully responsive (mobile, tablet, desktop)
- Bootstrap 5 components
- Font Awesome icons
- Smooth animations and transitions

## 🛠 Tech Stack

### Backend
- **Java 11**
- **Spring Boot 2.7.14**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **MySQL 8.0**
- **Lombok** - Reduces boilerplate code
- **BCrypt** - Password encryption

### Frontend
- **Thymeleaf** - Server-side templating
- **Bootstrap 5** - UI framework
- **Font Awesome 6** - Icons
- **Sortable.js** - Drag & drop functionality
- **Vanilla JavaScript** - Interactive features

### Build Tool
- **Maven** - Dependency management and build automation

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**
   ```bash
   java -version
   ```

2. **Maven 3.6+**
   ```bash
   mvn -version
   ```

3. **MySQL 8.0+**
   ```bash
   mysql --version
   ```

4. **Git** (optional, for cloning)
   ```bash
   git --version
   ```

## 🚀 Installation & Setup

### Step 1: Clone the Repository (or Download)
```bash
git clone https://github.com/yourusername/job-application-tracker.git
cd job-application-tracker
```

### Step 2: Set Up MySQL Database

1. **Start MySQL Server**
   ```bash
   # macOS
   mysql.server start
   
   # Windows
   net start MySQL80
   
   # Linux
   sudo service mysql start
   ```

2. **Login to MySQL**
   ```bash
   mysql -u root -p
   ```

3. **Run the Schema Script**
   ```sql
   source src/main/resources/schema.sql;
   ```
   
   Or manually create the database:
   ```sql
   CREATE DATABASE job_tracker_db;
   USE job_tracker_db;
   -- Then run the schema.sql contents
   ```

### Step 3: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
# Update these values to match your MySQL setup
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4: Build the Project

```bash
mvn clean install
```

### Step 5: Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/job-application-tracker-1.0-SNAPSHOT.jar
```

### Step 6: Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```

## 📝 Usage

### First Time Setup

1. **Register a New Account**
   - Navigate to `http://localhost:8080/register`
   - Fill in your details
   - Click "Register"

2. **Login**
   - Use your credentials to login
   - You'll be redirected to the dashboard

### Adding Jobs

1. Click the **"Add New Job"** button
2. Fill in the job details:
   - Company Name (required)
   - Position (required)
   - Location
   - Job Type (Full-time, Part-time, Contract, Internship)
   - Salary Range
   - Status (Wishlist, Applied, Interview, etc.)
   - Job URL
   - Deadline
   - Description
   - Notes
3. Click **"Save Job"**

### Managing Jobs

- **Edit**: Click the edit icon on any job card
- **Delete**: Click the trash icon (requires confirmation)
- **Move**: Drag and drop cards between columns to update status
- **View**: Statistics update automatically in the dashboard

## 🗂 Project Structure

```
job-application-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jobtracker/
│   │   │       ├── controller/      # REST & Web Controllers
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── DashboardController.java
│   │   │       │   └── JobController.java
│   │   │       ├── model/           # JPA Entities
│   │   │       │   ├── User.java
│   │   │       │   ├── Job.java
│   │   │       │   ├── Contact.java
│   │   │       │   ├── Document.java
│   │   │       │   ├── FollowUp.java
│   │   │       │   ├── InterviewNote.java
│   │   │       │   └── JobLabel.java
│   │   │       ├── repository/      # Data Access Layer
│   │   │       │   ├── UserRepository.java
│   │   │       │   └── JobRepository.java
│   │   │       ├── service/         # Business Logic
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── UserService.java
│   │   │       │   └── JobService.java
│   │   │       └── Application.java # Main Spring Boot App
│   │   └── resources/
│   │       ├── application.properties  # Configuration
│   │       ├── schema.sql              # Database Schema
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css       # Custom Styles
│   │       │   └── js/
│   │       │       └── main.js         # JavaScript Functions
│   │       └── templates/              # Thymeleaf Templates
│   │           ├── login.html
│   │           ├── register.html
│   │           └── dashboard.html
│   └── test/
│       └── java/
│           └── com/jobtracker/
│               └── ApplicationTests.java
├── pom.xml                             # Maven Dependencies
└── README.md                           # This file
```

## 🔧 Configuration

### Database Configuration

Edit `application.properties` for custom database settings:

```properties
# Database URL
spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker_db

# Database credentials
spring.datasource.username=root
spring.datasource.password=your_password

# Hibernate DDL auto (create, create-drop, update, validate)
spring.jpa.hibernate.ddl-auto=update

# Show SQL queries in console
spring.jpa.show-sql=true

# Server port
server.port=8080
```

### Session Timeout

```properties
# Session timeout (30 minutes)
server.servlet.session.timeout=30m
```

### File Upload (Future Enhancement)

```properties
# Max file size for document uploads
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

## 🔒 Security

- **Password Encryption**: All passwords are hashed using BCrypt
- **Session Management**: HTTP-only session cookies prevent XSS attacks
- **SQL Injection Protection**: JPA/Hibernate parameterized queries
- **CSRF Protection**: Spring Security (can be enabled)

## 📱 API Endpoints

### Authentication
- `GET /` or `GET /login` - Login page
- `POST /login` - Login submission
- `GET /register` - Registration page
- `POST /register` - Registration submission
- `GET /logout` - Logout

### Dashboard
- `GET /dashboard` - Main dashboard view

### REST API (Jobs)
- `GET /api/jobs` - Get all jobs for logged-in user
- `GET /api/jobs/{id}` - Get specific job
- `POST /api/jobs` - Create new job
- `PUT /api/jobs/{id}` - Update job
- `PATCH /api/jobs/{id}/status` - Update job status
- `DELETE /api/jobs/{id}` - Delete job
- `GET /api/jobs/statistics` - Get job statistics

## 🎓 Database Schema

### Main Tables
1. **users** - User accounts
2. **jobs** - Job applications
3. **job_labels** - Custom tags for jobs
4. **contacts** - Recruiter/HR contacts
5. **interview_notes** - Interview feedback
6. **documents** - Uploaded files
7. **follow_ups** - Follow-up emails

See `src/main/resources/schema.sql` for complete schema.

## 🐛 Troubleshooting

### Database Connection Issues
```
Error: Access denied for user 'root'@'localhost'
```
**Solution**: Verify MySQL credentials in `application.properties`

### Port Already in Use
```
Error: Port 8080 is already in use
```
**Solution**: Change port in `application.properties` or kill the process using port 8080

### Maven Build Fails
```
Solution: Run `mvn clean install -U` to force update dependencies
```

## 🚧 Future Enhancements

- [ ] Email notifications for deadlines
- [ ] Resume builder integration
- [ ] Chrome extension for quick job adds
- [ ] Export data to PDF/Excel
- [ ] Analytics dashboard with charts
- [ ] Interview scheduler
- [ ] AI-powered job recommendations
- [ ] Mobile app (React Native)

## 👨‍💻 Contributing

This is a college project, but contributions are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

