# capco-sales
Spring Boot 3 Technical Test Starter

## Overview
This is a Spring Boot 3 starter project built with Java 21, featuring a RESTful API for product management with comprehensive unit and integration tests.

## Technologies
- **Java 21** - LTS version with modern Java features
- **Spring Boot 3.2.0** - Latest Spring Boot framework
- **Spring Data JPA** - For database operations
- **H2 Database** - In-memory database for development and testing
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework for unit tests
- **Maven** - Build and dependency management

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
│   │   │   │   └── ProductController.java      # REST API endpoints
│   │   │   ├── model/
│   │   │   │   └── Product.java                # Product entity
│   │   │   ├── repository/
│   │   │   │   └── ProductRepository.java      # JPA repository
│   │   │   └── service/
│   │   │       └── ProductService.java         # Business logic
│   │   └── resources/
│   │       └── application.properties          # Application configuration
│   └── test/
│       ├── java/com/capco/sales/
│       │   ├── SalesApplicationTests.java      # Context load test
│       │   ├── controller/
│       │   │   └── ProductControllerIT.java    # Integration tests
│       │   └── service/
│       │       └── ProductServiceTest.java     # Unit tests
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
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create a new product |
| PUT | `/api/products/{id}` | Update product by ID |
| DELETE | `/api/products/{id}` | Delete product by ID |
| GET | `/api/products/search?name={name}` | Search products by name |

### Example Request (Create Product)
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 1299.99
  }'
```

### Example Response
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 1299.99
}
```

## H2 Console
Access the H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:salesdb`
- Username: `sa`
- Password: (leave empty)

## Testing

### Unit Tests
Located in `src/test/java/com/capco/sales/service/`
- Tests the service layer in isolation using Mockito
- 9 test cases covering all service methods

### Integration Tests
Located in `src/test/java/com/capco/sales/controller/`
- Tests the full application stack (controller → service → repository → database)
- 10 test cases covering all API endpoints
- Uses `@SpringBootTest` and `MockMvc`

### Running Specific Tests
```bash
# Run only unit tests
mvn test -Dtest=ProductServiceTest

# Run only integration tests
mvn test -Dtest=ProductControllerIT
```

## Test Coverage
- **Unit Tests**: 9 tests - Service layer with mocked dependencies
- **Integration Tests**: 10 tests - Full stack REST API testing
- **Context Test**: 1 test - Application context loading

## Development
This project follows Spring Boot best practices:
- RESTful API design
- Layered architecture (Controller → Service → Repository)
- Dependency injection
- Exception handling
- Test-driven development with comprehensive test coverage

## License
This is a technical test project for educational purposes.
