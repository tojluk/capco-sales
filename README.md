# Capco Sales - Shopping Cart API

Spring Boot 3 Shopping Cart Calculation API with Java 21

## Overview

This is a Spring Boot 3 REST API for calculating shopping cart totals for an online product sales company. The API supports different pricing strategies based on client type (individual or professional) and annual revenue.

## Technologies

- **WebMVC** - As the fastest way to build web applications on Spring Boot
- **Java 21** - LTS version with modern features (records, sealed interfaces, pattern matching)
- **Spring Boot 3.2.0** - Latest Spring Boot framework
- **Spring Validation** - Bean validation with Jakarta annotations
- **SpringDoc OpenAPI 3** - API documentation with Swagger UI
- **Lombok** - Reduces boilerplate code
- **Maven** - Build and dependency management
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework for tests

## Prerequisites

- Java 21 (JDK 21)
- Maven 3.6+

## Business Rules
### Product Pricing

The system sells three types of products with different prices based on client type:

#### Individual Clients
- High-end phone: €1,500
- Mid-range phone: €800
- Laptop: €1,200

#### Professional Clients (Annual Revenue > €10M)
- High-end phone: €1,000
- Mid-range phone: €550
- Laptop: €900

#### Professional Clients (Annual Revenue ≤ €10M)
- High-end phone: €1,150
- Mid-range phone: €600
- Laptop: €1,000

## Building the Project

### Compile
```bash
mvn clean compile
```

### Run Tests
```bash
# Run unit tests only
mvn test

# Run all tests (unit + integration)
mvn verify
```

### Package
```bash
mvn clean package
```

## Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## API Endpoints
### Calculate Cart Total
**Endpoint**: `POST /api/v1/cart/calculate`
See details at: 
```
/capco-sales/src/test/resources/http-client/generated-requests.http
```
