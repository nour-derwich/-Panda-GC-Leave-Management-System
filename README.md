# 🐼 Panda GC - Leave Management System

A comprehensive leave management system designed to streamline employee leave requests, approvals, and tracking processes.

## 📋 Overview

Panda GC is a full-stack web application that enables organizations to efficiently manage employee leave requests, approvals, and balance tracking. The system provides role-based access control for employees, managers, and administrators.

## ✨ Features

- **Employee Portal**
  - Submit leave requests with date ranges and reasons
  - View leave request history and status
  - Check remaining leave balance
  - Receive notifications for request updates

- **Manager Dashboard**
  - Review and approve/reject leave requests
  - View team leave calendar
  - Monitor team availability
  - Generate leave reports

- **Admin Panel**
  - Manage user accounts and roles
  - Configure leave types and policies
  - Set leave quotas and rules
  - Generate organization-wide reports

- **General Features**
  - Real-time leave balance calculation
  - Email notifications
  - Leave calendar visualization
  - Conflict detection for overlapping leaves
  - Responsive design for mobile and desktop

## 🛠️ Technology Stack

### Frontend
- **Angular** - Modern web framework for building the user interface
- **TypeScript** - Type-safe development
- **Bootstrap/CSS** - Responsive styling

### Backend
- **Java 8** - Core programming language
- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database interaction
- **Maven** - Dependency management

### Database
- **MySQL** - Relational database management

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK) 8** or higher
- **Node.js** (v14 or higher) and **npm**
- **Angular CLI** (`npm install -g @angular/cli`)
- **MySQL** (v5.7 or higher)
- **Maven** (v3.6 or higher)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/nour-derwich/-Panda-GC-Leave-Management-System.git
cd -Panda-GC-Leave-Management-System
```

### 2. Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE panda_gc_db;
```

Update the `application.properties` file in the backend:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/panda_gc_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Backend Setup

Navigate to the backend directory:

```bash
cd backend
```

Install dependencies and run:

```bash
mvn clean install
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`

### 4. Frontend Setup

Navigate to the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Update the API endpoint in the environment configuration if needed:

```typescript
// src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

Start the development server:

```bash
ng serve
```

The application will be available at `http://localhost:4200`

## 📁 Project Structure

```
panda-gc-leave-management/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/pandagc/
│   │   │   │       ├── controller/
│   │   │   │       ├── model/
│   │   │   │       ├── repository/
│   │   │   │       ├── service/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/
│   │   │   ├── services/
│   │   │   ├── models/
│   │   │   └── guards/
│   │   ├── assets/
│   │   └── environments/
│   ├── angular.json
│   └── package.json
└── README.md
```

## 🔐 Default Credentials

After initial setup, you can use these default credentials:

- **Admin**: admin@pandagc.com / admin123
- **Manager**: manager@pandagc.com / manager123
- **Employee**: employee@pandagc.com / employee123

**Important**: Change these credentials immediately in production.

## 🧪 Running Tests

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
ng test
```

## 📊 API Documentation

Once the backend is running, API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Nour Derwich** - [GitHub Profile](https://github.com/nour-derwich)

## 📧 Contact

For questions or support, please open an issue on GitHub or contact the maintainer.

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Angular team for the powerful framework
- All contributors who help improve this project

---

Made with ❤️ by the Panda GC Team
