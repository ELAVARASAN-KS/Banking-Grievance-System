# Banking Grievance Redressal System

## 📌 Project Overview

The **Banking Grievance Redressal System** is a full-stack web application designed to streamline the process of raising, managing, and resolving customer complaints in a banking environment. The system provides role-based access for **Users**, **Staff**, and **Admins**, ensuring transparency, accountability, and efficient grievance handling.

This project is built using **Spring Boot**, **JDBC**, **MySQL**, and a **HTML + Tailwind CSS** frontend, following a modular and RESTful API-based architecture.

---

## 🎯 Objectives

* Allow users to raise banking-related complaints online
* Enable admins to manage complaints and assign staff
* Allow staff to resolve assigned complaints
* Collect feedback after complaint resolution
* Maintain complaint lifecycle with proper status tracking

---

## 🛠️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Web (REST APIs)
* JDBC (JdbcTemplate)
* Maven

### Frontend

* HTML5
* Tailwind CSS
* JavaScript (Fetch API)

### Database

* MySQL

### Tools

* IntelliJ IDEA
* MySQL Workbench
* Postman (API Testing)

---

## 👥 User Roles & Responsibilities

### 1. User

* Register and login
* Raise complaints
* View complaint status
* Submit feedback after resolution

### 2. Staff

* Login
* View assigned complaints
* Update complaint status
* Resolve complaints

### 3. Admin

* Login
* View all complaints
* Add and manage staff
* Assign staff to complaints
* Monitor complaint status

---

## 📂 Project Structure

```
Banking_Grievance/
│
├── controller/        # REST Controllers
├── service/           # Business Logic
├── dao/               # Database Access (JDBC)
├── model/             # Entity / Model Classes
├── config/            # CORS & App Configurations
├── resources/
│   ├── application.properties
│
└── frontend/          # HTML, CSS, JS files
```

---

## 🧩 Core Modules

### 🔐 Authentication Module

* User Registration
* User Login
* Admin Login
* Staff Login

### 📝 Complaint Management Module

* Raise complaint
* View complaints
* Update complaint status
* Assign staff to complaints

### 👨‍💼 Staff Management Module

* Add staff
* View staff list
* Assign staff to complaints

### ⭐ Feedback Module

* Submit feedback
* Store feedback linked to complaints

---

## 🔗 API Endpoints (Sample)

### Authentication

* `POST /api/auth/register`
* `POST /api/auth/login`

### Complaints

* `POST /api/complaints`
* `GET /api/complaints`
* `PUT /api/complaints/{id}/assign`
* `PUT /api/complaints/{id}/status`

### Staff

* `GET /api/staff`
* `POST /api/staff`

### Feedback

* `POST /api/feedback`
* `GET /api/feedback/{complaintId}`

---

## 🗄️ Database Tables

* `users`
* `admins`
* `staff`
* `complaints`
* `feedback`

All tables are connected using proper **foreign key relationships** to maintain data integrity.

---

## ▶️ How to Run the Project

### 1️⃣ Backend Setup

1. Open project in IntelliJ IDEA
2. Configure MySQL database
3. Update `application.properties`
4. Run the Spring Boot application

### 2️⃣ Database Setup

1. Open MySQL Workbench
2. Create database
3. Execute provided SQL scripts

### 3️⃣ Frontend Setup

1. Open frontend folder
2. Run HTML files in browser
3. Ensure backend is running on configured port

---

## 🧪 Testing

* APIs tested using **Postman**
* Manual testing done for frontend workflows

---

## 🚀 Future Enhancements

* JWT-based authentication
* Email notifications
* Dashboard analytics
* File upload support
* Deployment on cloud (AWS / Render)

---

## 👨‍💻 Developed By

**Elavarasan**
3rd Year IT Student
Skilled in Java, MySQL, JDBC, Spring Boot

---

## 📜 License

This project is for **academic and learning purposes only**.
