# Flight Bookings Management System

This project is a Spring Boot-based backend system for managing flight bookings. It includes RESTful APIs for interacting with flight, seat, passenger, and booking data. The system is designed with security features and incorporates exception handling and thorough documentation via Swagger and [Javadoc](https://pythonisamgm.github.io/flight-bookings/).

## Project Structure

### Configuration Files
- **ApplicationConfig.java**: General configuration for the application context.
- **SwaggerConfig.java**: Configures Swagger for API documentation.
- **WebSecurityConfig.java**: Configures security, authentication, and authorization settings using Spring Security.

### Controllers
The controllers provide REST API endpoints for managing different entities in the system:
- **AirportController**: Manages airport information.
- **AuthController**: Handles authentication and user login.
- **BookingController**: Manages bookings for flights.
- **FlightController**: Handles flight data management.
- **PassengerController**: Manages passenger-related data.
- **SeatController**: Manages seat reservations and availability.
- **UserController**: Manages user data and administration.

### Data Transfer Objects (DTOs) and Converters
DTOs are used to define the data structure sent to and from the client:
- **BookingDTO**: Contains booking information sent to and from the client.
- **BookingConverter**: Contains methods to convert between `Booking` entities and `BookingDTO` objects.

### Exception Handling
Custom exceptions are implemented to provide specific error handling in the application:
- **BookingNotFoundException**: Thrown when a booking is not found.
- **FlightNotFoundException**: Thrown when a flight is not found.
- **NoSeatAssignedException**: Thrown when a booking is attempted without assigning a seat.
- **PassengerNotFoundException**: Thrown when a passenger is not found.
- **ResourceNotFoundException**: A generic exception for when any resource is not found.
- **SeatAlreadyBookedException**: Thrown when a seat is already booked.
- **SeatNotFoundException**: Thrown when a specific seat is not found.
- **UnauthorizedAccessException**: Thrown when an unauthorized user attempts to access a resource.
- **UserNotFoundException**: Thrown when a user is not found.

### Services and Interfaces
The application implements a service layer with interfaces and classes for each primary entity. Interfaces are defined for separation of concerns, and implementations handle business logic.

#### Service Interfaces
- **AuthService**: Interface for authentication-related operations.
- **BookingService**: Interface for booking-related operations.
- **FlightService**: Interface for managing flight-related operations.
- **PassengerService**: Interface for managing passenger information.
- **SeatService**: Interface for seat reservation and management.
- **UserService**: Interface for user-related operations.

#### Service Implementations
- **AirportServiceImpl**: Implementation of airport-related operations.
- **BookingServiceImpl**: Manages business logic for booking creation, updating, and deletion.
- **FlightServiceImpl**: Manages flights.
- **PassengerServiceImpl**: Manages passenger information.
- **SeatServiceImpl**: Manages seat reservations and availability.
- **UserServiceImpl**: Manages user data and interactions.

### Models
Entities represent tables in the database and are the core data structure of the application:
- **Airport**: Represents airport data.
- **Booking**: Represents a booking record.
- **Flight**: Represents flight information.
- **Passenger**: Represents a passenger.
- **Seat**: Represents seat information, including booking status.
- **User**: Represents a user in the system.

### Enums
Enums are used to define specific data constants within the system:
- **ECountry**: List of countries.
- **EFlightAirplane**: Different airplane models.
- **ERole**: Different user roles.
- **ESeatLetter**: Defines seat letters.

## Security
The application uses Spring Security for authentication and authorization:
- **JwtService**: Provides functionality for creating and validating JSON Web Tokens (JWT) for user authentication.
- **AuthTokenFilter**: Filters incoming requests and validates JWT tokens.
## DevOps and Docker
The project includes continuous integration pipelines and Docker configurations for seamless deployment and testing:

### GitHub Actions
The project utilizes GitHub Actions for CI/CD with the following workflows:
- **Build and Automated Testing**: Triggers on `push` and `pull_request` for `testingDev`, `dev`, and `main` branches. This workflow:
    - Sets up MySQL using `mirromutth/mysql-action`.
    - Builds the application with Maven and runs unit tests.
- **Build and Deploy Docker Image to Docker Hub**: This workflow is triggered on `push` to `dev` and `testingDev` branches. It builds the Docker image and pushes it to Docker Hub.
- **Generate and Publish Documentation**: Generates and publishes Javadoc documentation to GitHub Pages on `push` to `dev` and `testingDev`.

### Docker Compose
The project includes a `docker-compose.yml` file for setting up MySQL and the Spring Boot application in separate containers:
- **mysql_service**: A MySQL container for the database, initialized with `db_flight-bookings`.
- **springboot_app**: The Spring Boot application, linked to the MySQL container.

### Testing Overview
Comprehensive tests are in place for both controllers and services to ensure reliable operation and data integrity:
- **Controller Tests**:
    - `BookingControllerTest`
    - `FlightControllerTest`
    - `PassengerControllerTest`
    - `SeatControllerTest`
    - `UserControllerTest`
- **Service Tests**:
    - `BookingServiceImplTest`
    - `FlightServiceImplTest`
    - `PassengerServiceImplTest`
    - `SeatServiceImplTest`
    - `UserServiceImplTest`

## Configuration
1. Clone the repository.
2. Ensure you have Java and Maven installed.
3. Configure your `application.properties` file for database access.
4. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
5. Access Swagger documentation at http://localhost:8080/swagger-ui.html for API details.
   
