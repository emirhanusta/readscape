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

### Security

- **JWT**: JSON Web Tokens are used for authentication and authorization.
- **Spring Security**: Provides security features such as authentication and authorization.
- **Vault**: Used for storing sensitive information such as database credentials and JWT secret

![vault 2024-06-09 154922](https://github.com/emirhanusta/readscape/assets/83432342/1446feee-0882-49ad-bb87-496a7e8dda12)

### Monitoring

- **Zipkin**: Used for distributed tracing.

![zipkin 2024-06-09 151433](https://github.com/emirhanusta/readscape/assets/83432342/da0c431c-6c66-4538-9fcc-532119e40568)

## Endpoints

### Auth Service

#### /auth/login

```json
{
  "username": "emir",
  "password": "pass"
}
```
if account does not exist response will be like this:
```json
{
  "timestamp": "Sun, 09 Jun 2024 10:33:04 GMT",
  "status": 404,
  "error": "Not Found",
  "message": "Account not found with username: emir",
  "path": "http://account-service/api/v1/accounts/username/emir"
}
```
if login is successful response will be like this:
```json
{
  "user": {
    "id": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
    "username": "emir",
    "email": "emirhan@gmail.com",
    "role": "ADMIN"
  },
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJlbWlyIiwiaWF0IjoxNzE3OTI5MzE3LCJleHAiOjE3MTc5MzI5MTd9.2PwvuO_qMbKyhHcRgnGC1t2Vb0upRDx1Ohawh4EAnnU"
}
```

#### /auth/register

```json
{
  "username": "emir",
  "password": "pass",
  "email": "emirhan@gmail.com",
  "role": "ADMIN"
}
```
if account already exists response will be like this:
```
  account already exists
```

if register is successful response will be like this:
```json
{
  "id": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "username": "emir",
  "email": "emirhan@gmail.com",
  "role": "ADMIN"
}
```
### Account Service

#### /accounts/{id} GET

if account does not exist response will be like this:
```
Account not found with id: 86abedd0-6c83-4b6a-93f8-0a33ced3fff2
```

if account exists response will be like this:
```json
{
  "id": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "username": "emir",
  "email": "emirhan@gmail.com",
  "role": "ADMIN"
}
```

#### /accounts/username/{username} GET

if account does not exist response will be like this:
```
Account not found with username: emir
```

if account exists response will be like this:
```json
{
  "id": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "username": "emir",
  "email": "emirhan@gmail.com",
  "role": "ADMIN"
}
```

#### /accounts/{id} PUT

```json
{
  "username": "emirhan",
  "email": "emirhan@gmail.com",
  "password": "pass",
  "role": "ADMIN"
}
```

if account does not exist response will be like this:
```
Account not found with id: 86abedd0-6c83-4b6a-93f8-0a33ced3fff2
```

if account updated successfully response will be like this:
```json
{
  "id": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "username": "emirhan",
  "email": "emirhan@gmail.com",
  "role": "ADMIN"
}
```

#### /accounts/{id} DELETE

this endpoint needs authentication token in header

if account does not exist response will be like this:
```
Account not found with id: 86abedd0-6c83-4b6a-93f8-0a33ced3fff2
```

if account deleted successfully response will be like this:
```
Account deleted successfully
```

#### /accounts/reviews/{id} GET

if account does not exist response will be like this:
```
Account not found with id: 86abedd0-6c83-4b6a-93f8-0a33ced3fff2
```

if account has reviews response will be like this:
```json
[
  {
    "id": "9ad9984c-16e9-46a4-9d13-f93fce92ec61",
    "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
    "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
    "rating": 7.5,
    "review": "this book is amazing"
  }
]
```

### Author Service

#### /authors POST

this endpoint needs authentication token in header and role should be ADMIN

```json
{
  "name": "Roberto Bolaño",
  "biography": "Roberto Bolaño Ávalos was a Chilean novelist.",
  "birthDate": "1953-04-28"
}
```

if author saved successfully response will be like this:
```json
{
  "id": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "name": "Roberto Bolaño",
  "biography": "Roberto Bolaño Ávalos was a Chilean novelist.",
  "birthDate": "1953-04-28"
}
```

#### /authors GET

if there are authors response will be like this:
```json
[
  {
    "id": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
    "name": "Roberto Bolaño",
    "biography": "Roberto Bolaño Ávalos was a Chilean novelist.",
    "birthDate": "1953-04-28"
  }
]
```

#### /authors/{id} GET

if author does not exist response will be like this:
```
Author not found with id: 5e8e2dd8-e69b-41f5-bd92-8989c846226a
```

if author exists response will be like this:
```json
{
  "id": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "name": "Roberto Bolaño",
  "biography": "Roberto Bolaño Ávalos was a Chilean novelist.",
  "birthDate": "1953-04-28"
}
```

#### /authors/{id} PUT

this endpoint needs authentication token in header and role should be ADMIN

```json
{
  "name": "Roberto Bolaño",
  "biography": "Roberto Bolaño Ávalos was a Chilean novelist. He was born on 28 April 1953.",
  "birthDate": "1953-04-28"
}
```

if author does not exist response will be like this:
```
Author not found with id: 5e8e2dd8-e69b-41f5-bd92-8989c846226a
```

if author updated successfully response will be like this:
```json
{
  "id": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "name": "Roberto Bolaño",
  "biography": "Roberto Bolaño Ávalos was a Chilean novelist. He was born on 28 April 1953.",
  "birthDate": "1953-04-28"
}
```

#### /authors/{id} DELETE

this endpoint needs authentication token in header and role should be ADMIN

if author does not exist response will be like this:
```
Author not found with id: 5e8e2dd8-e69b-41f5-bd92-8989c846226a
```

if author deleted successfully response will be like this:
```
Author deleted successfully
```

#### /authors/books/{id} GET

if author does not exist response will be like this:
```
Author not found with id: 5e8e2dd8-e69b-41f5-bd92-8989c846226a
```

if author has no books response will be like this:
```
[]
```

if author has books response will be like this:
```json
[
  {
    "id": "67858f86-941d-4a23-8c54-664fff46e878",
    "title": "2666",
    "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
    "description": "2666 is a novel written by the Chilean author Roberto Bolaño. It is divided into five parts and explores a wide range of themes, including the nature of evil, the power of literature, and the legacy of European colonialism in Latin America.",
    "genres": [
      "[Mystery, Novel]"
    ],
    "publishedDate": "2004-11-11"
  },
  {
    "id": "faccb813-2ba1-4fb0-9a15-a1bde43a6d7b",
    "title": "The Savage Detectives",
    "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
    "description": "The Savage Detectives is a novel by Roberto Bolaño. It is narrated by over 50 different characters and follows two poets as they travel across Mexico, Europe, and Africa in search of a mysterious poet named Cesárea Tinajero.",
    "genres": [
      "[Novel, Mystery]"
    ],
    "publishedDate": "1998-10-01"
  }
]
```

### Book Service

#### /books POST

this endpoint needs authentication token in header and role should be ADMIN

```json
  {
  "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "title": "The Savage Detectives",
  "description": "The Savage Detectives is a novel by Roberto Bolaño. It is narrated by over 50 different characters and follows two poets as they travel across Mexico, Europe, and Africa in search of a mysterious poet named Cesárea Tinajero.",
  "genres": ["Novel", "Mystery"],
  "publishedDate": "1998-10-01"
}
```

if book saved successfully response will be like this:
```json
{
  "id": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "title": "The Savage Detectives",
  "description": "The Savage Detectives is a novel by Roberto Bolaño. It is narrated by over 50 different characters and follows two poets as they travel across Mexico, Europe, and Africa in search of a mysterious poet named Cesárea Tinajero.",
  "genres": [
    "[Novel, Mystery]"
  ],
  "publishedDate": "1998-10-01"
}
```

#### /books GET

if there are books response will be like this:
```json
[
  {
    "id": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
    "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
    "title": "The Savage Detectives",
    "description": "The Savage Detectives is a novel by Roberto Bolaño. It is narrated by over 50 different characters and follows two poets as they travel across Mexico, Europe, and Africa in search of a mysterious poet named Cesárea Tinajero.",
    "genres": [
      "[Novel, Mystery]"
    ],
    "publishedDate": "1998-10-01"
  },
  {
    "id": "1a59aefc-b9fa-4aef-a76c-f0e00b1e58d7",
    "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
    "title": "2666",
    "description": "2666 is a novel written by the Chilean author Roberto Bolaño. It is divided into five parts and explores a wide range of themes, including the nature of evil, the power of literature, and the legacy of European colonialism in Latin America.",
    "genres": [
      "[Mystery, Novel]"
    ],
    "publishedDate": "2004-11-11"
  }
]
```

#### /books/{id} GET

if book does not exist response will be like this:
```
Book not found with id: d8e13ab5-d303-4c1c-b281-f4480ec0366d
```

if book exists response will be like this:
```json
{
  "id": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "title": "The Savage Detectives",
  "description": "The Savage Detectives is a novel by Roberto Bolaño. It is narrated by over 50 different characters and follows two poets as they travel across Mexico, Europe, and Africa in search of a mysterious poet named Cesárea Tinajero.",
  "genres": [
    "[Novel, Mystery]"
  ],
  "publishedDate": "1998-10-01"
}
```

#### /books/{id} PUT

this endpoint needs authentication token in header and role should be ADMIN

```json
{
  "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "title": "The Savage Detectives",
  "description": "The Savage Detectives is a novel by Roberto Bolaño.",
  "genres": ["Novel", "Mystery"],
  "publishedDate": "1998-10-01"
}
```

if book does not exist response will be like this:
```
Book not found with id: d8e13ab5-d303-4c1c-b281-f4480ec0366d
```

if book updated successfully response will be like this:
```json
{
  "id": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "authorId": "5e8e2dd8-e69b-41f5-bd92-8989c846226a",
  "title": "The Savage Detectives",
  "description": "The Savage Detectives is a novel by Roberto Bolaño.",
  "genres": [
    "[Novel, Mystery]"
  ],
  "publishedDate": "1998-10-01"
}
```

#### /books/{id} DELETE

this endpoint needs authentication token in header and role should be ADMIN

if book does not exist response will be like this:
```
Book not found with id: d8e13ab5-d303-4c1c-b281-f4480ec0366d
```

if book deleted successfully response will be like this:
```
Book deleted successfully
```
and books image will be deleted

#### /books/reviews/{id} GET

if book does not exist response will be like this:
```
Book not found with id: d8e13ab5-d303-4c1c-b281-f4480ec0366d
```

if book has no reviews response will be like this:
```
[]
```

if book has reviews response will be like this:
```json
[
  {
    "id": "25958ffb-b3c1-4651-845e-125a6966d8ba",
    "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
    "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
    "rating": 7.5,
    "review": "this book is amazing"
  }
]
```

#### /books/images/{id} POST

this endpoint needs authentication token in header and role should be ADMIN

```form data

  "file": "image.png"

```

if image saved successfully response will be image.jpg

#### /books/images/{id} GET

if book does not exist response will be like this:
```
Book not found with id: d8e13ab5-d303-4c1c-b281-f4480ec0366d
```

if book has no image response will be like this:
```
No image found
```

if book has image response will be image.jpg

### Review Service

#### /reviews POST

this endpoint needs authentication token in header

```json
{
  "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "rating": 7.5,
  "review": "this book is amazing"
}
```

if review saved successfully response will be like this:
```json
{
  "id": "287a0ddf-6ff3-4e58-96f3-185697a96158",
  "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "rating": 7.5,
  "review": "this book is amazing"
}
```

#### /reviews GET

if there are reviews response will be like this:
```json
[
  {
    "id": "287a0ddf-6ff3-4e58-96f3-185697a96158",
    "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
    "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
    "rating": 7.5,
    "review": "this book is amazing"
  }
]
```

#### /reviews/{id} PUT

this endpoint needs authentication token in header

```json
{
  "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "rating": 5.5,
  "review": "this book is amazing"
}
```

if review does not exist response will be like this:
```
Review not found with id: 287a0ddf-6ff3-4e58-96f3-185697a96158
```

if review updated successfully response will be like this:
```json
{
  "id": "287a0ddf-6ff3-4e58-96f3-185697a96158",
  "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "rating": 7.5,
  "review": "this book is not bad"
}
```

#### /reviews/{id} DELETE

this endpoint needs authentication token in header

if review does not exist response will be like this:
```
Review not found with id: 287a0ddf-6ff3-4e58-96f3-185697a96158
```

if review deleted successfully response will be like this:
```
Review deleted successfully
```

#### /reviews/{id} GET

if review does not exist response will be like this:
```
Review not found with id: 287a0ddf-6ff3-4e58-96f3-185697a96158
```

if review exists response will be like this:
```json
{
  "id": "287a0ddf-6ff3-4e58-96f3-185697a96158",
  "accountId": "86abedd0-6c83-4b6a-93f8-a33ceed3fff2",
  "bookId": "d8e13ab5-d303-4c1c-b281-f4480ec0366d",
  "rating": 7.5,
  "review": "this book is amazing"
}
```

### Notification Service

This service sends email to user when a review is added to a book. It listens to review added event. 

