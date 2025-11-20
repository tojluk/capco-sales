# capco-sales
Spring Boot 3 Technical Test Starter

## Overview
This is a minimal Spring Boot 3 starter project built with Java 21, featuring a simple RESTful API with unit and integration tests.

## Technologies
- **Java 21** - LTS version with modern Java features
- **Spring Boot 3.2.0** - Latest Spring Boot framework
- **Maven** - Build and dependency management
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework for unit tests

## Prerequisites
- Java 21 (JDK 21)
- Maven 3.6+

## Project Structure
```
capco-sales/
├── src/
│   ├── main/
│   │   ├── java/com/capco/sales/
│   │   │   ├── SalesApplication.java           # Main application class
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java      # REST API endpoint
│   │   │   ├── model/
│   │   │   │   └── Product.java                # Product model
│   │   │   └── service/
│   │   │       └── ProductService.java         # Business logic
│   │   └── resources/
│   │       └── application.properties          # Application configuration
│   └── test/
│       ├── java/com/capco/sales/
│       │   ├── SalesApplicationTests.java      # Context load test
│       │   ├── controller/
│       │   │   └── ProductControllerIT.java    # Integration test
│       │   └── service/
│       │       └── ProductServiceTest.java     # Unit test
│       └── resources/
│           └── application.properties          # Test configuration
└── pom.xml
```

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

### Using Java
```bash
java -jar target/capco-sales-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### Product Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |

### Example Request
```bash
curl -X GET http://localhost:8080/api/products
```

### Example Response
```json
[
  {
    "id": "1"
  },
  {
    "id": "2"
  }
]
```

## Testing

### Unit Tests
Located in `src/test/java/com/capco/sales/service/`
- Tests the service layer in isolation using Mockito
- 1 test case covering getAllProducts method

### Integration Tests
Located in `src/test/java/com/capco/sales/controller/`
- Tests the full application stack (controller → service)
- 1 test case covering the GET /api/products endpoint
- Uses `@SpringBootTest` and `MockMvc`

### Running Specific Tests
```bash
# Run only unit tests
mvn test -Dtest=ProductServiceTest

# Run only integration tests
mvn test -Dtest=ProductControllerIT
```

## Test Coverage
- **Unit Tests**: 1 test - Service layer with no external dependencies
- **Integration Tests**: 1 test - Full stack REST API testing
- **Context Test**: 1 test - Application context loading

## Development
This project follows Spring Boot best practices:
- RESTful API design
- Layered architecture (Controller → Service)
- Dependency injection
- Test-driven development with unit and integration test coverage

## License
This is a technical test project for educational purposes.
