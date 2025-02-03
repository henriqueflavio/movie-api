# Movie API

This is a RESTful API built with Spring Boot for managing movie information. The project was developed as part of the Xpand IT Backend Challenge.

## Technologies Used

- Java 21
- Spring Boot 3.4.2
- PostgreSQL
- Flyway (Database Migration)
- SpringDoc OpenAPI (API Documentation)
- JUnit 5 & Mockito (Testing)
- Maven
- Docker

## Prerequisites

To run this application, you need to have the following installed:

- Java 21 or higher
- Docker & Docker Compose
- Maven 3.9.4 or higher

## Project Structure

```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── pt/xpandit/movieapi
│   │   │       ├── controller
│   │   │       ├── domain
│   │   │       ├── dto
│   │   │       ├── exception
│   │   │       ├── mapper
│   │   │       ├── repository
│   │   │       └── service
│   │   └── resources
│   │       ├── db/migration
│   │       └── application.properties
│   └── test
└── pom.xml
```

## Running the Application

1. Start the PostgreSQL database using Docker:
```bash
docker-compose up -d
```

2. Build and run the application:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access the API documentation through:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Specification: `http://localhost:8080/v3/api-docs`

## API Endpoints

| Method | URL | Description                        |
|--------|-----|------------------------------------|
| GET | /api/v1/movies | Get all movies                     |
| GET | /api/v1/movies/{id} | Get movie by ID                    |
| POST | /api/v1/movies | Create a new movie                 |
| PUT | /api/v1/movies/{id} | Update an existing movie           |
| DELETE | /api/v1/movies/{id} | Delete a movie                     |
| GET | /api/v1/movies/search | Filter movies by launch date range |


## Sample Data

The application comes with pre-loaded sample movies data for tests:

| Title                    | Launch Date | Rank | Revenue      |
|-------------------------|-------------|------|--------------|
| The Shawshank Redemption| 2010-07-01  | 9    | $58,800,000  |
| Inception               | 2010-07-16  | 8    | $836,836,967 |
| Interstellar           | 2010-08-01  | 8    | $701,729,206 |
| The Dark Knight        | 2008-07-18  | 9    | $1,004,558,444|
| Pulp Fiction           | 1994-10-14  | 8    | $213,928,762 |



## Running Tests

The project include unit tests. To run the tests:

```bash
./mvnw test
```


