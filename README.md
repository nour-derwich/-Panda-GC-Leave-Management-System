# 🐼 Panda GC - Leave Management System

A comprehensive leave management system with real-time notifications and chat functionality, built to streamline employee leave requests and approvals.

## 🌟 Features

### Core Functionality
- **Leave Management**: Submit, approve, and track leave requests
- **Real-time Notifications**: Instant updates using Socket.IO for leave status changes
- **Real-time Chat**: Built-in chatbot for employee queries and support
- **User Management**: Role-based access control (Admin, Manager, Employee)
- **Leave Balance Tracking**: Automatic calculation of available leave days
- **Dashboard Analytics**: Visual insights into leave patterns and statistics

### Real-time Features
- Live notification system for leave approvals/rejections
- Instant chat responses with Socket.IO integration
- Real-time status updates across the application

## 🛠️ Technology Stack

### Frontend
- **Angular** - Modern web application framework
- **Socket.IO Client** - Real-time bidirectional communication
- **TypeScript** - Type-safe development
- **Angular Material / Bootstrap** - UI components

### Backend
- **Java 8** - Core programming language
- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **Socket.IO Java** - Real-time communication
- **JPA/Hibernate** - ORM for database operations
- **MySQL** - Relational database

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** (v14 or higher) and npm
- **Angular CLI** (`npm install -g @angular/cli`)
- **Java JDK 8** or higher
- **Maven** (v3.6 or higher)
- **MySQL** (v5.7 or higher)
- **Git**

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/nour-derwich/Panda-GC-Leave-Management-System.git
cd Panda-GC-Leave-Management-System
```

### 2. Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE panda_gc_db;
```

Update the database configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/panda_gc_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Backend Setup

Navigate to the backend directory and install dependencies:

```bash
cd backend
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`

### 4. Frontend Setup

Navigate to the frontend directory:

```bash
cd frontend
npm install
```

Update the API endpoint in `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  socketUrl: 'http://localhost:8080'
};
```

Run the Angular development server:

```bash
ng serve
```

The application will be available at `http://localhost:4200`

## 📁 Project Structure

```
Panda-GC-Leave-Management-System/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/pandagc/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── model/
│   │   │   │       ├── config/
│   │   │   │       └── socket/
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── app/
    │   │   ├── components/
    │   │   ├── services/
    │   │   ├── models/
    │   │   └── guards/
    │   ├── assets/
    │   └── environments/
    └── package.json
```

## 🔑 Default Credentials

After initial setup, you can access the system with:

- **Admin**
  - Email: `admin@pandagc.com`
  - Password: `admin123`

- **Manager**
  - Email: `manager@pandagc.com`
  - Password: `manager123`

- **Employee**
  - Email: `employee@pandagc.com`
  - Password: `employee123`

## 📱 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/logout` - User logout

### Leave Management
- `GET /api/leaves` - Get all leaves
- `GET /api/leaves/{id}` - Get leave by ID
- `POST /api/leaves` - Create leave request
- `PUT /api/leaves/{id}` - Update leave request
- `DELETE /api/leaves/{id}` - Delete leave request
- `PUT /api/leaves/{id}/approve` - Approve leave
- `PUT /api/leaves/{id}/reject` - Reject leave

### Notifications
- `GET /api/notifications` - Get user notifications
- `PUT /api/notifications/{id}/read` - Mark as read

### Socket Events
- `notification` - Real-time notification updates
- `chat-message` - Chat message events
- `leave-status-update` - Leave status changes

## 🧪 Testing

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

## 🔧 Configuration

### Socket.IO Configuration

Backend (`SocketConfig.java`):
```java
@Configuration
public class SocketConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = 
            new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(8080);
        return new SocketIOServer(config);
    }
}
```

Frontend (`socket.service.ts`):
```typescript
import { io, Socket } from 'socket.io-client';

this.socket = io('http://localhost:8080');
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Nour Derwich** - *Initial work* - [nour-derwich](https://github.com/nour-derwich)

## 🙏 Acknowledgments

- Spring Boot documentation
- Angular documentation
- Socket.IO documentation
- All contributors who help improve this project

## 📧 Contact

For questions or support, please contact:
- Email: contact@pandagc.com
- GitHub: [@nour-derwich](https://github.com/nour-derwich)

---

⭐ If you find this project useful, please consider giving it a star on GitHub!
