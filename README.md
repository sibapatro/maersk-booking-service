# Maersk Container Booking Service

## Overview
This is a reactive Spring WebFlux microservice that allows customers to check container availability and book containers with Maersk. The service uses MongoDB for storing booking information and implements reactive programming paradigms with retry mechanisms.

## Features
- Reactive container availability checking (proxy to external service)
- Container booking with MongoDB persistence
- Comprehensive validation
- Error handling with proper logging
- REST API with OpenAPI specification
- Docker and Docker Compose support

## Prerequisites
- Java 17
- Maven 3.6+
- Docker & Docker Compose (for containerized deployment)

## Project Structure
```
maersk-container-booking/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/maersk/booking/
│   │   │       ├── MaerskBookingApplication.java
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── openapi/
│   └── test/
│       └── java/
│           └── com/maersk/booking/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Build and Run Instructions

### 1. Local Development

#### Prerequisites
- Java 17 installed
- MongoDB running locally on default port (27017)

#### Build and Run
```bash
# Clone the repository
git clone <repository-url>
cd maersk-container-booking

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 2. Docker Compose (Recommended)

#### Run with Docker Compose
```bash
# Start the application with MongoDB
docker-compose up -d

# View logs
docker-compose logs -f
```

#### Access the Services
- Application: http://localhost:8080
- MongoDB: mongodb://localhost:27017

## API Endpoints

### 1. Check Container Availability
**Endpoint**: `POST /api/bookings/check`  
**Description**: Checks if there are enough containers of specified type and size at the container yard

**Request Body**:
```json
{
  "containerType": "DRY",
  "containerSize": 20,
  "origin": "Southampton",
  "destination": "Singapore",
  "quantity": 5
}
```

**Response**:
- Success (200): `{"available": true}` or `{"available": false}`
- Validation Error (400): Error details
- Internal Server Error (500): Generic error message

### 2. Create Booking
**Endpoint**: `POST /api/bookings`  
**Description**: Creates a new booking and stores it in MongoDB

**Request Body**:
```json
{
  "containerType": "DRY",
  "containerSize": 20,
  "origin": "Southampton",
  "destination": "Singapore",
  "quantity": 5,
  "timestamp": "2020-10-12T13:53:09Z"
}
```

**Response**:
- Success (200): `{"bookingRef": "957000001"}`
- Validation Error (400): Error details
- Internal Server Error (500): Generic error message


## Running Tests
```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn jacoco:prepare-agent test jacoco:report
```

## OpenAPI Documentation
The API documentation is available at:
- http://localhost:8080/v3/api-docs
- http://localhost:8080/swagger-ui.html (if Swagger UI is included)

## Postman Collection
A Postman collection is provided in the `postman/` directory with sample requests for all endpoints.

## Security Considerations
This basic implementation can be enhanced with:
- Spring Security for authentication/authorization
- JWT token-based security
- API rate limiting
- Request/response encryption
- CORS configuration

## AWS/Azure Deployment
The service can be deployed to cloud platforms:
- Containerized with Docker
- Deployed to AWS ECS/EKS or Azure AKS
- Using cloud MongoDB services (AWS DocumentDB, Azure Cosmos DB)
- With CI/CD pipelines using GitHub Actions, Jenkins, etc.

## Error Handling
- Input validation errors return 400 Bad Request with detailed messages
- MongoDB errors are logged internally and return 500 with generic message
- External service errors are handled with retry logic
- All errors are properly logged for debugging

## Technologies Used
- Java 17
- Spring WebFlux (Reactive)
- Spring Data MongoDB
- Maven
- JUnit 5 + Mockito (Testing)
- Docker & Docker Compose
- MongoDB

## Validation Rules
- containerSize: Must be 20 or 40
- containerType: Must be "DRY" or "REEFER"
- origin: 5-20 characters
- destination: 5-20 characters
- quantity: 1-100
- timestamp: Valid ISO-8601 format (for booking endpoint)

## Retry Logic
The external service call implements retry logic with exponential backoff:
- Max retries: 3
- Initial backoff: 1 second
- Uses Spring WebFlux retry mechanisms

This implementation follows TDD principles with comprehensive test coverage and implements all required features using reactive programming patterns with Spring WebFlux.