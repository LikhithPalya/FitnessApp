# FitFlow - Fitness Tracking & Recommendation Platform

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A comprehensive fitness tracking application built with Spring Boot that enables users to log workouts, track activities, and receive personalized fitness recommendations through a secure RESTful API.

## 🚀 Features

### Core Functionality
- **User Authentication & Authorization**
  - JWT-based stateless authentication
  - Role-based access control (USER/ADMIN)
  - BCrypt password encryption
  - Secure session management

- **Activity Tracking**
  - Log various workout types (RUNNING, CYCLING, SWIMMING, WEIGHTLIFTING, YOGA, etc.)
  - Track duration, distance, and calories burned
  - Retrieve activity history by user
  - Real-time activity monitoring

- **Workout Recommendations**
  - Activity-specific improvement tracking
  - Custom suggestions and safety guidelines
  - User and activity-based recommendation history
  - Structured feedback system

- **API Documentation**
  - Interactive Swagger/OpenAPI UI
  - Comprehensive endpoint documentation
  - Request/response schemas

## 🏗️ Architecture

### Tech Stack
- **Backend Framework:** Spring Boot 4.0.1
- **Language:** Java 21
- **Security:** Spring Security with JWT
- **Database:** MySQL with Spring Data JPA
- **ORM:** Hibernate
- **Build Tool:** Maven
- **Documentation:** SpringDoc OpenAPI 3.0

### Project Structure
```
src/main/java/com/project/fitness/
├── config/              # Configuration classes (OpenAPI, etc.)
├── controller/          # REST API endpoints
├── dto/                 # Data Transfer Objects
├── exceptions/          # Custom exception handlers
├── model/              # JPA Entity classes
├── repository/         # Spring Data JPA repositories
├── security/           # Security configuration & JWT utilities
└── service/            # Business logic layer
```

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.8+
- MySQL 8.0+
- Git

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/LikhithPalya/FitnessApp.git
cd fitness-monolith
```

### 2. Configure Database
Create a MySQL database and update the configuration:

```bash
# Create .env file in the root directory
touch .env
```

Add the following environment variables:
```properties
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/fitness_db
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your_secret_key_here
JWT_EXPIRATION=86400000
```

Or configure `src/main/resources/application.properties`:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access the interactive API documentation at:
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

## 🔐 API Endpoints

### Authentication
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | User login | No |

### Activities
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/activities` | Track new activity | Yes |
| GET | `/api/activities` | Get user activities | Yes |

### Recommendations
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/recommendation/generate` | Generate recommendation | Yes |
| GET | `/api/recommendation/user/{userId}` | Get user recommendations | Yes |
| GET | `/api/recommendation/activity/{activityId}` | Get activity recommendations | Yes |

### Admin Panel
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| * | `/api/admin/**` | Admin operations | Yes (ADMIN role) |

## 📝 Usage Examples

### Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Track Activity
```bash
curl -X POST http://localhost:8080/api/activities \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "activityType": "RUNNING",
    "duration": 30,
    "distance": 5.0,
    "caloriesBurned": 300
  }'
```

### Generate Recommendation
```bash
curl -X POST http://localhost:8080/api/recommendation/generate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "userId": "user-id-here",
    "activityId": "activity-id-here",
    "improvements": "Focus on maintaining consistent pace",
    "suggestions": "Try interval training for better endurance",
    "safety": "Stay hydrated and warm up properly"
  }'
```

## 🗃️ Database Schema

### Key Entities
- **User:** User accounts with authentication credentials
- **Activity:** Workout/exercise records
- **Recommendation:** Personalized fitness suggestions
- **ActivityType:** Enum for activity categories
- **UserRole:** Enum for user roles (USER, ADMIN)

## 🔒 Security Features

- **JWT Authentication:** Stateless token-based authentication
- **Password Encryption:** BCrypt hashing algorithm
- **Role-Based Access Control:** Separate USER and ADMIN permissions
- **CSRF Protection:** Disabled for stateless REST API
- **Custom Authentication Filter:** Pre-authentication JWT validation
- **Security Context:** Thread-local security information

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📦 Build for Production

Create a production-ready JAR:
```bash
mvn clean package -DskipTests
```

Run the JAR:
```bash
java -jar target/fitness-monolith-0.0.1-SNAPSHOT.jar
```

## 🚀 Deployment

### Docker (Optional)
Create a `Dockerfile`:
```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/fitness-monolith-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t fitness-app .
docker run -p 8080:8080 fitness-app
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author [@LikhithPalya](https://github.com/LikhithPalya)

**⭐ Star this repository if you find it helpful!**