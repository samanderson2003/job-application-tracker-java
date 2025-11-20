# 🚀 EASY START - No MySQL Needed!

## Quick Setup (Under 2 minutes!)

Your project has build issues due to Lombok. Here's the **fastest way** to get running:

### Option 1: Use the Pre-configured H2 Profile (RECOMMENDED)

H2 is an in-memory database - **NO installation needed!**

```bash
# 1. Just run the app with H2 profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2"
```

That's it! Access at **http://localhost:8080**

- **Username**: demo
- **Password**: password123

### Option 2: Fix Lombok and Build Properly

The issue is Lombok isn't generating getters/setters. To fix:

```bash
# 1. Clean Maven cache
rm -rf ~/.m2/repository/org/projectlombok

# 2. Update Maven compiler plugin in pom.xml
# Add this inside <build><plugins>:

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>

# 3. Rebuild
mvn clean install -DskipTests

# 4. Run
mvn spring-boot:run
```

### Option 3: Skip Lombok - Use Plain Java

Remove `@Data` annotations and add getters/setters manually (tedious but works)

## What's Different with H2?

- ✅ **No database installation** required
- ✅ **Data resets** on restart (perfect for testing)
- ✅ **Built-in console** at http://localhost:8080/h2-console
- ✅ **Zero configuration**

## H2 Console Access

After starting with H2 profile:

1. Go to: http://localhost:8080/h2-console
2. JDBC URL: `jdbc:h2:mem:job_tracker_db`
3. Username: `sa`
4. Password: (leave empty)
5. Click "Connect"

## Switching to MySQL Later

When ready for MySQL:

```bash
# 1. Install MySQL
brew install mysql
brew services start mysql

# 2. Create database
mysql -u root << 'SQL'
CREATE DATABASE job_tracker_db;
USE job_tracker_db;
SOURCE src/main/resources/schema.sql;
SQL

# 3. Update application.properties password
# 4. Run without profile:
mvn spring-boot:run
```

---

**TL;DR**: Run `mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=h2"` and open http://localhost:8080
