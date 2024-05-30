

# Microservices-Based Book Management System

## About the Project

This project aims to create a comprehensive book management system using microservices architecture. The system includes services for user authentication, user management, book management, author management, review management, and notification management.


<p align="center">
  <img src="https://github.com/emirhanusta/readscape/assets/83432342/36628296-1f6d-415a-b7bc-ef4bf0069159" alt="System Architecture Diagram"/>
</p>


## Technologies Used

- **Spring Boot (Kotlin and Java)**
- **Spring Security**
- **Apache Kafka**
- **Redis**
- **PostgreSQL**
- **Amazon S3**
- **Zipkin**
- **Vault**
- **Docker**

## Architecture

The system consists of several microservices, each handling a specific domain of the application:

- **Auth Service**: Handles user authentication and communicates with the Account Service for account details.
- **Account Service**: Manages user registration, profiles, and user data.
- **Book Service**: Manages book details, including adding, updating, deleting, and listing books.
- **Author Service**: Manages author details, including adding, updating, deleting, and listing author books..
- **Review Service**: Allows users to write and view book reviews.
- **Notification Service**: Sends notifications to users about important events.

### Communication

- Synchronous communication between services is handled using Feign Client.
- Asynchronous communication between Review and Notification services is managed using Kafka.

