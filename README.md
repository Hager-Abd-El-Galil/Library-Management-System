# Library Management System

## Overview

This project is a **Library Management System API** built using **Spring Boot**. It allows librarians to manage books, patrons, and borrowing records efficiently. The system provides RESTful API endpoints for various operations related to library management.

## Features
- **Database and Entity Relationships**: The system utilizes `MySQL` for data persistence, establishing proper relationships between entities such as books, patrons, and borrowing records to maintain data integrity and support complex queries.
- **Security**: Basic authentication with JWT-based is implemented to protect API endpoints.
- **Transaction Management**: Declarative transaction management using Spring's `@Transactional` annotation ensures data integrity during critical operations.
- **Input Validation**: The system ensures the correctness of API requests by implementing robust input validation.
- **Exception Handling**: The system is designed to handle exceptions gracefully.
- **Unit Testing**: Unit tests are provided to validate the functionality of the API endpoints.
- **Auditing**: Automatic tracking of entity creation and modification details, including timestamps and user information.
- **Aspect-Oriented Programming (AOP)**: Logging of method execution times and exception logging using AOP is implemented to monitor system performance.
- **Caching**: Improved performance through caching of frequently accessed data, such as books.


## Technologies
- **Spring Boot**: Backend framework for building the RESTful API.
- **Maven**: Dependency management and build automa
- **MySQL**: Relational database for persisting library data.
- **JUnit & Mockito**: Testing frameworks for unit tests.

## Entities
- **Book**: Represents the library's books, including attributes like ID, title, author, publication year, ISBN.
- **Patron**: Contains details of library patrons, including ID, name, and contact information.
- **Borrowing Record**: Tracks the association between books and patrons, including borrowing and return dates.

## API Endpoints
The system includes the following endpoints:

### Authentication Management
- `POST /login`: Login to manage the system.

### Book Management
- `GET /api/books`: Retrieve a list of all books.
- `GET /api/books/filter`: Retrieve a list of filtered books.
- `GET /api/books/{id}`: Retrieve details of a specific book by ID.
- `POST /api/books`: Add a new book to the library.
- `PUT /api/books/{id}`: Update an existing book's information.
- `DELETE /api/books/{id}`: Remove a book from the library.

### Patron Management
- `GET /api/patrons`: Retrieve a list of all patrons.
- `GET /api/patrons`: Retrieve a list of filtered patrons.
- `GET /api/patrons/{id}`: Retrieve details of a specific patron by ID.
- `POST /api/patrons`: Add a new patron to the system.
- `PUT /api/patrons/{id}`: Update an existing patron's information.
- `DELETE /api/patrons/{id}`: Remove a patron from the system.

### Borrowing Record Management
- `POST /api/borrow/{bookId}/patron/{patronId}`: Allow a patron to borrow a book.
- `PUT /api/return/{bookId}/patron/{patronId}`: Record the return of a borrowed book by a patron.

## AOP (Aspect-Oriented Programming)
- The application uses AOP to implement cross-cutting concerns such as logging and performance monitoring.
- An aspect called `TimeLoggingAspect` measures the execution time of methods, providing insights into the application's performance.

## Auditing
- The application employs auditing to automatically capture creation and modification details of entities.
- The `BaseEntity` class contains fields for `createDate`, `changeDate`, `creator`, and `changer`, which are populated using Spring Data JPA's auditing features.

## Caching
- Caching is implemented to improve performance, particularly for frequently accessed data like books.
- The `@Cacheable` annotation is used for retrieving books, while `@CacheEvict` is applied for creating, updating, or deleting books to ensure data consistency.

## Getting Started

### Prerequisites
- **Java 8 or higher**
- **Spring Boot 2.5.3**
- **Maven**
- **MySQL** database

### Running the Application
1. Clone the repository `git clone  https://github.com/Hager-Abd-El-Galil/Library-Management-System.git`
2. Update the `application.properties` file with your MySQL database configration.
```
spring.datasource.url=jdbc:mysql://<your_db_url>:<port>/<your_db_schema>
spring.datasource.username=<your_db_username>
spring.datasource.password=<your_db_password>
```
3.Update JWT Secret Token in `application.properties` file
```token.secret=<your_secret_key>```

4. Build and run the application using your preferred IDE or command line:
   ``` mvn spring-boot:run```
   
5. Run Unit Test Using Maven
   ``` mvn Test```
   
## Documentation
- API documentation is available via **Swagger** at the following URL: 
  - `http://localhost:8080/swagger-ui.html`


