# 🍽️ Restaurant Management System — Backend (REST API Service)

Welcome to the **Backend Repository** of the Enterprise Restaurant Management System! This service provides a robust, scalable, and secure RESTful Web Service architecture built using **Spring Boot**, **Spring Data JPA**, and **MySQL**. It handles core business logic, database transactions, order workflows, menu persistence, and administrative features.

---

## 🏗️ Architecture & Design Patterns

The backend follows a strict **N-Tier Layered Architecture** to maintain clear separation of concerns, high testability, and enterprise-level maintainability:

┌─────────────────────────────────────────────────────────┐
│              Presentation Layer (REST Controllers)      │
└───────────────────────────┬─────────────────────────────┘
│ (DTOs)
┌───────────────────────────▼─────────────────────────────┐
│              Business Logic Layer (Service Interfaces)  │
└───────────────────────────┬─────────────────────────────┘
│ (Entities)
┌───────────────────────────▼─────────────────────────────┐
│              Data Access Layer (Spring Data JPA)        │
└───────────────────────────┬─────────────────────────────┘
│ (SQL Queries)
┌───────────────────────────▼─────────────────────────────┐
│              Relational Database (MySQL)                │
└─────────────────────────────────────────────────────────┘


* **Controller Layer**: Exposes REST endpoints (`@RestController`), handles HTTP requests/responses, and maps JSON payloads.
* **Service Layer**: Encapsulates core business rules, custom validation, and transaction management (`@Service`, `@Transactional`).
* **Repository Layer**: Abstraction over database operations using Spring Data JPA Repositories (`@Repository`).
* **DTO Layer (Data Transfer Objects)**: Encapsulates incoming and outgoing data, isolating entity internal structures from API consumers.
* **Entity Layer**: Maps Relational Database tables to Object-Oriented Java classes using Jakarta Persistence Annotations (`@Entity`, `@Table`).

---

## 🛠️ Tech Stack & Dependencies

* **Language**: Java 17+ / Java 21
* **Framework**: Spring Boot 3.x
* **Core Modules**:
  * **Spring Web**: Building RESTful APIs with MVC semantics.
  * **Spring Data JPA**: Data persistence with Hibernate ORM.
  * **Spring Validation**: Request body validation (`@Valid`, `@NotNull`, `@Size`).
* **Database**: MySQL 8.x
* **Build Tool**: Apache Maven
* **Utilities**: Project Lombok
* **API Testing**: Postman Collection

---

## 📂 Backend Project Structure

backend-project/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/restaurant/
│   │   │   ├── 📁 config/          # CORS & Configurations
│   │   │   ├── 📁 controller/      # REST API Endpoints
│   │   │   ├── 📁 dto/             # Data Transfer Objects
│   │   │   ├── 📁 entity/          # 15 Domain Entity Classes
│   │   │   ├── 📁 exception/       # Custom Exception Handling
│   │   │   ├── 📁 repository/     # Spring Data JPA Repositories
│   │   │   ├── 📁 service/        # Business Logic & Service Interfaces
│   │   │   └── RestaurantApp.java  # Main Application Class
│   │   └── 📁 resources/
│   │       └── application.properties # DB Configurations
│   └── 📁 test/
├── 📄 pom.xml                         # Maven Dependencies
└── 📄 README.md                       # Documentation


---

## 🗄️ Database & Domain Entities

The system contains 15 domain entity classes managing core domain models:

* **Users & Auth**: `User`, `Role`, `CustomerProfile`
* **Menu & Inventory**: `Category`, `MenuItem`, `Ingredient`, `InventoryItem`
* **Orders & Sales**: `Order`, `OrderItem`, `Payment`, `Invoice`
* **Table & Delivery**: `DiningTable`, `Reservation`, `DeliveryDetail`, `Feedback`

---

## 🔌 API Endpoints Summary

| Module | Endpoint | Method | Description |
| :--- | :--- | :--- | :--- |
| **Auth** | `/api/v1/auth/register` | `POST` | Register a new customer account |
| **Auth** | `/api/v1/auth/login` | `POST` | Authenticate user & retrieve session |
| **Menu** | `/api/v1/menu` | `GET` | Retrieve all menu items |
| **Menu** | `/api/v1/menu` | `POST` | Add a new menu item |
| **Orders** | `/api/v1/orders` | `POST` | Create a new food order |
| **Orders** | `/api/v1/orders/{id}` | `GET` | Get detailed order status |
| **Orders** | `/api/v1/orders/{id}/status` | `PUT` | Update order state |
| **Users** | `/api/v1/users` | `GET` | Retrieve list of registered customers |

---

## ⚙️ Configuration & Local Setup

### Database Setup
```sql
CREATE DATABASE IF NOT EXISTS restaurant_db;
Application Properties Configuration
Properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Execution Steps
Clone the repository:

Bash
git clone [https://github.com/thakshila1219/AAD-course-work-BE.git](https://github.com/thakshila1219/AAD-course-work-BE.git)
Open in IntelliJ IDEA.

Run the application via RestaurantApp.java or Maven:

Bash
mvn spring-boot:run
👩‍💻 Author
Thakshila Madushani

GitHub: @thakshila1219

Coursework: Higher Diploma in Software Engineering / AAD Module
