# Real-Time Transaction & Audit Log System

## Project Overview

This project is a **Real-Time Transaction & Audit Log System** developed as part of a Full Stack assignment. It simulates a **peer-to-peer fund transfer system** with a mandatory, immutable audit trail. The application allows users to **sign up, log in, transfer money, and view transaction history** in a sortable table.

The system is built with a **Spring Boot backend**, **Angular frontend**, and **MySQL database**, following RESTful API principles, transaction safety, and clean UI practices.

---

## Technology Stack

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (JWT Authentication)
- MySQL
- Hibernate

### Frontend

- Angular (Standalone + Module-based components)
- TypeScript
- HTML, CSS

### Database

- MySQL

### Tools

- Git & GitHub
- MySQL Workbench
- Postman

---

## Features

- User Signup & Login (JWT-based authentication)
- Secure money transfer between users
- Atomic database transaction handling (debit & credit)
- Immutable transaction audit log
- Transaction history view with sortable columns
- Clean UI with consistent fonts and backgrounds
- Centralized logging and error handling

---

## Database Schema

### 1. `user_login`

Stores authentication credentials.

| Column   | Type        | Description            |
| -------- | ----------- | ---------------------- |
| id       | BIGINT (PK) | Auto-generated user ID |
| username | VARCHAR     | Unique username        |
| password | VARCHAR     | Encrypted password     |

---

### 2. `user`

Stores user profile and balance.

| Column | Type        | Description             |
| ------ | ----------- | ----------------------- |
| id     | BIGINT (PK) | Same as user\_login ID  |
| name   | VARCHAR     | Username                |
| amount | INT         | Current account balance |

---

### 3. `transaction_history`

Stores immutable audit log of all transactions.

| Column       | Type        | Description        |
| ------------ | ----------- | ------------------ |
| id           | BIGINT (PK) | Transaction ID     |
| sender\_id   | BIGINT      | Sender user ID     |
| receiver\_id | BIGINT      | Receiver user ID   |
| amount       | INT         | Transferred amount |
| status       | VARCHAR     | SUCCESS / FAILED   |
| timestamp    | DATETIME    | Transaction time   |

---

## API Documentation

### Authentication APIs

#### Signup

`POST /api/auth/signup`

Request:

```json
{
  "username": "user1",
  "password": "password123"
}
```

#### Login

`POST /api/auth/login`

Response:

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "JWT_TOKEN",
    "userId": 1
  }
}
```

---

### Transaction APIs

#### Transfer Money

`POST /api/transactions/transfer`

Request:

```json
{
  "senderId": 1,
  "receiverId": 2,
  "amount": 100
}
```

#### Transaction History

`GET /api/transactions/history/{userId}`

---

## Setup / Run Instructions


### Prerequisites

### Before running the project, ensure the following software is installed:

Java Development Kit (JDK) 17

Verify installation:

java -version


### Should display java version "17.x.x".

Node.js & npm

Recommended version: Node.js 18+

Verify installation:

node -v
npm -v


### Angular CLI

Install globally if not already installed:

npm install -g @angular/cli


Verify installation:

ng version


### MySQL Server & Workbench

Ensure MySQL service is running.

Create a database (e.g., transaction_db).



### Backend (Spring Boot) Setup

[1] Clone / Download the Repository

git clone https://github.com/Iram-lab/RealTimeTransaction-And-AuditLogSystem.git

cd backend


[2] Configure MySQL Database

Open src/main/resources/application.properties.

Update the following properties with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/transaction_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true


[3] Build & Run the Application

Using Maven (from project root):

mvn clean install
mvn spring-boot:run


OR directly run from IDE (IntelliJ/Eclipse):

Right-click Application.java → Run

Verify Backend Running

By default, Spring Boot runs on http://localhost:8080.

[4]Test with Postman or browser:

GET http://localhost:8080/actuator/health

### Frontend

[1] Navigate to Angular project folder
[2] Run:

```bash
npm install
ng serve
```

[3] Open browser at `http://localhost:4200`

---

3. Open browser at `http://localhost:4200`

---

## Demo Video (Mandatory)

A complete walkthrough video demonstrating the working of the application has been recorded and uploaded.

**Video Includes:**

- User signup and login flow
- JWT authentication handling
- Fund transfer between users
- Real-time balance update
- Transaction history with sorting
- Database entries verification

🔗 **Demo Video Link:**\
https://drive.google.com/file/d/1lvaue0OcNCQJ-Kz2Y0eWOSId4Gnn69vs/view?usp=drivesdk

---

## Error Handling & Logging

- Centralized exception handling using try-catch and validation
- Meaningful API error messages
- SLF4J logging in controllers and services
- Transaction rollback using `@Transactional`

---

## AI Tool Usage Log (Mandatory)

### AI Tools Used

- **ChatGPT**
- **GitHub Copilot**

### AI-Assisted Tasks

- Generated initial Spring Boot controller and service structure
- Assisted in implementing JWT authentication flow
- Helped design database schema and entity relationships
- Generated Angular components for login, signup, transfer, and history
- Implemented sortable transaction history table logic
- Added logging and error handling patterns
- Helped write this README documentation

### Effectiveness Score

**Score: 5 / 5**

**Justification:** AI tools significantly reduced development time by generating boilerplate code, improving error handling, and accelerating debugging. Manual understanding and customization were applied on top of AI-generated suggestions.

---

## Evaluation Criteria Coverage

- RESTful API design with JWT security ✔
- Atomic transactions and audit logging ✔
- Clean, responsive frontend UI ✔
- Proper code structure and logging ✔
- Complete documentation & AI usage log ✔

---

## Conclusion

This project demonstrates a complete **full-stack transaction system** with real-time operations, secure authentication, and audit logging. It follows industry best practices in backend design, frontend usability, database integrity, and documentation.

---

**Developed for academic and learning purposes**

