# Movie Ticket and Cinema Management System

OOAD Mini Project - A web-based movie ticket booking system built with Java Spring Boot, following the class diagram and use case specifications.

## Requirements

- Java 17 or higher
- Maven 3.6+

## Run the Application

```bash
mvn spring-boot:run
```

Or with Maven wrapper (if present):
```bash
./mvnw spring-boot:run
```

Then open: http://localhost:8080

## Default Login Credentials

| Role  | Email             | Password |
|-------|-------------------|----------|
| Admin | admin@cinema.com  | admin123 |
| User  | user@test.com     | user123  |

## Features Implemented

### User Use Cases
- **Register** - Create new account
- **Login / Logout** - Authentication
- **Search Movies** - By title or genre
- **View Show Timings** - For a selected movie
- **Book Ticket** - Select seats, make payment (includes Select Seats + Make Payment)
- **View Booking** - See all bookings
- **Cancel Booking** - Cancel confirmed bookings

### Admin Use Cases
- **Manage Theatres** - Add theatres, add screens
- **Manage Movies** - Add/update movies
- **Manage Shows** - Add shows (movie + screen + time + price)
- **View Reports** - Sales report with date range

## Architecture & Design

### MVC Architecture
- **Model**: Entities (Theatre, Screen, Movie, Show, Seat, User, Booking, Payment)
- **View**: Thymeleaf templates
- **Controller**: Spring MVC controllers

### Design Patterns
1. **Singleton** - PaymentGateway (external payment gateway simulation)
2. **Factory** - UserFactory (create User/Admin instances)
3. **Singleton** - PaymentGateway (single instance for payment processing)
4. **(Your Chosen Pattern)** - (Describe the fourth implemented pattern here)
5. **Facade** - BookingFacade (simplifies booking process)
6. **Repository** - Spring Data JPA repositories

### Design Principles
- **SRP** - Single Responsibility (each class has one purpose)
// ...existing code...
- **DIP** - Dependency Inversion (depend on abstractions)
- **LSP** - Liskov Substitution (proper inheritance in entities)

## Database

H2 in-memory/file database. Data persists in `./data/cinemadb`.  
H2 Console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/cinemadb`)
