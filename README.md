
# Modsen Library

Welcome to the Modsen Library project! This guide will help you get started with running the project and accessing its services.

## Requirements
- **JDK 21**: Ensure you have JDK 21 installed on your machine.

## Getting Started

### Clone the Repository
First, clone the project repository to your local machine:

```bash
git clone https://github.com/anycart/modsen-library.git
```

### Configure Environment
- **Database Password**: Update the database password in the `application.yml` files for the services that require database access. Specifically, edit the `application.yml` files in the following services:
  - Book Service
  - Library Service
  
Locate the `${DB_PASSWORD}` field in these files and set it to your desired password.

## Launching Microservices
You need to start each microservice in the following order:

1. **Discovery Service**: This service is responsible for service discovery and should be started first.
2. **API Gateway**: This service handles routing and gateway functionalities.
3. **Registration Service**: Manages user registration and authentication.
4. **Book Service**: Handles CRUD operations for books and book registries.
5. **Library Service**: Provides additional library functionalities.

## Access the API
Once the microservices are up and running, you can view the OpenAPI documentation for each service at the following links:

- **Registration Service**: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
- **Book Service**: [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)
- **Library Service**: [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html)

Explore these links to interact with the available API endpoints and test the service functionalities.

Some endpoints require a JWT token for access. In Swagger documentation, these endpoints are marked with a lock icon. To use these endpoints, you need to first authenticate and obtain a JWT token.

## Functionality

### Book Service
The Book Service provides the following functionalities:

- **Get All Books**: Retrieve a list of all books in the library.
- **Get Available Books**: Retrieve a list of books that are currently available.
- **Get Book by ID**: Retrieve a book using its unique identifier (ID).
- **Get Book by ISBN**: Retrieve a book using its International Standard Book Number (ISBN).
- **Add New Book**: Add a new book to the library.
- **Update Existing Book**: Modify information about an existing book.
- **Delete Book**: Remove a book from the library.

### Author Service
The Author Service provides the following functionalities:

- **Get All Authors**: Retrieve a list of all authors in the system.
- **Get Author by ID**: Retrieve an author using their unique identifier (ID).
- **Add New Author**: Add a new author to the system.
- **Update Existing Author**: Modify information about an existing author.
- **Delete Author**: Remove an author from the system.
