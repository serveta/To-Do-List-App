# To-Do List App (Spring Boot REST API)

This project was my internship project. A Spring Boot-based REST API service architected to handle hierarchical task management. It implements Spring Security with JWT to ensure authentication, managing relational data between users, lists, and items via an H2 database.

## Features

- User registration and user management
- JWT-based authentication (`/authenticate`)
- CRUD operations for:
  - users
  - to-do lists (user-based)
  - items (to-do-list-based)
- H2 database and H2 console support

## Tech Stack

- Java 18
- Spring Boot 2.7.2
- Spring Web
- Spring Security
- Spring Data JPA
- H2 Database
- Maven
- Lombok
- ModelMapper
- springdoc-openapi

## Project Structure

- `controller` -> REST endpoints
- `service` / `service.impl` -> business logic
- `repository` -> data access layer
- `model` -> JPA entities
- `dto` -> request/response payload models
- `security` -> JWT utility, filter, and security config

## Prerequisites

- Java 18
- Maven 3.8+

## Configuration

Main configuration file: `src/main/resources/application.properties`

Default values:

- Port: `8080`
- H2 URL: `jdbc:h2:file:./memory_persist/appdb`
- H2 username: `root`
- H2 password: empty
- JWT secret is configured via `jwt.secret`

## Run the Application

With Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or with Maven:

```bash
mvn spring-boot:run
```

The API starts on: `http://localhost:8080`

## Build and Test

```bash
./mvnw clean test
./mvnw clean package
```

## Authentication Flow (JWT)

Only these endpoints are public:

- `POST /authenticate`
- `POST /api/v1/users/register`
- Swagger/OpenAPI endpoints
- H2 console endpoints

All other endpoints require:

```text
Authorization: Bearer <token>
```

### 1) Register user

`POST /api/v1/users/register`

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "username": "johndoe",
  "mail": "john@example.com",
  "password": "123456"
}
```

### 2) Get token

`POST /authenticate`

```json
{
  "username": "johndoe",
  "password": "123456"
}
```

Response:

```json
{
  "token": "..."
}
```

## API Endpoints

Base path: `/api/v1`

### Users

- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`
- `POST /users/register` (public)

### To-Do Lists

- `POST /users/{userId}/todolists`
- `GET /users/{userId}/todolists`
- `GET /users/{userId}/todolists/{toDoListId}`
- `PUT /users/{userId}/todolists/{toDoListId}`
- `DELETE /users/{userId}/todolists/{toDoListId}`

### Items

- `POST /users/{userId}/todolists/{toDoListId}/items`
- `GET /users/{userId}/todolists/{toDoListId}/items`
- `GET /users/{userId}/todolists/{toDoListId}/items/{itemId}`
- `PUT /users/{userId}/todolists/{toDoListId}/items/{itemId}`
- `DELETE /users/{userId}/todolists/{toDoListId}/items/{itemId}`

## Quick cURL Example

```bash
# 1) Register
curl -X POST http://localhost:8080/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","username":"johndoe","mail":"john@example.com","password":"123456"}'

# 2) Authenticate
TOKEN=$(curl -s -X POST http://localhost:8080/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","password":"123456"}' | jq -r '.token')

# 3) Create To-Do List (replace userId)
curl -X POST http://localhost:8080/api/v1/users/1/todolists \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"My First List"}'
```

## Swagger and H2 Console

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI docs: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

