# eCommerce Backend Project

This eCommerce backend project is built with a robust and modern tech stack to provide a scalable, secure, and efficient solution for managing an online store. The project leverages Spring Boot, MySQL, Spring Data JPA, Maven, JUnit, Mockito, Redis, Swagger, and Spring Security to offer a comprehensive set of features, ensuring high performance and reliability.

## Tech Stack

- **Spring Boot**: A powerful framework for building Java-based applications, providing a wide range of functionalities out-of-the-box.
- **MySQL**: A reliable and widely-used relational database management system.
- **Spring Data JPA**: Simplifies database interactions using the Java Persistence API, providing a rich repository layer.
- **Maven**: A build automation tool used for managing project dependencies and build lifecycle.
- **JUnit**: A testing framework for writing and running unit tests in Java.
- **Mockito**: A mocking framework for unit tests in Java.
- **Redis**: An in-memory data structure store, used for caching and session management.
- **Swagger**: A tool for generating API documentation and exploring API endpoints.
- **Spring Security**: A comprehensive security framework for authentication, authorization, and more.
- **Lombok**: A Java library that helps reduce boilerplate code by automatically generating getters, setters, constructors, and other   common methods through annotations.
- **RestClient**: A tool for consuming external service APIs, such as a sales tax API.

## Features
- **Modern Java Features**:
  - **`var`**: Utilizes type inference to make the code more concise and readable.
  - **Streams API**: Leverages streams for efficient and expressive data processing, enabling operations like filtering, mapping, and reducing.
  - **Lambda Expressions**: Implements lambda expressions for more concise and functional-style code, particularly in collections and streams.
  - **Optionals**: Uses `Optional` to handle potentially null values in a safe and expressive manner, reducing the risk of `NullPointerException`.
  - **Text Blocks**: Employs text blocks for easier handling and readability of multi-line strings, especially in SQL queries.

- **Role-Based Access Control (RBAC)**: Implemented at the method level to ensure fine-grained security for different user roles.
- **Session Management**: Efficient handling of user sessions to maintain security and performance.
- **Entrypoint Customization**: Customization of security entry points to provide a flexible and secure authentication mechanism.
- **Profiling**: Allows the application to run with different profiles, enabling environment-specific configurations.
- **JPA Auditing**: Automatically tracks and logs entity changes, providing an audit trail for database operations.
- **Pagination, Filtering, Searching, and Sorting**: Comprehensive support for managing large datasets with ease.
- **REST API Best Practices**: Follows industry standards to ensure robust, maintainable, and scalable API design.
- **External API Consumption**: Integrates with external service APIs (e.g., sales tax API) using RestClient to retrieve and process data from third-party services.
- **Comprehensive Exception Handling**: Ensures that the application gracefully handles and logs exceptions, providing meaningful error messages.
- **Detailed Swagger Documentation**: Automatically generated API documentation for easy exploration and testing of endpoints.
- **Unit Testing**: Extensive use of JUnit and Mockito for reliable and maintainable tests.
- **CSV Exporting**: Ability to export reports into CSV files based on SQL querying retrieval, enabling easy data analysis and sharing.
- **CSV Import and Persistence**: Supports uploading CSV files and automatically persists the data into the database, streamlining data management and integration.
- **Data Validation**: Ensures that data integrity and correctness are maintained through comprehensive validation mechanisms at various layers.
- **Data Caching**: Implements efficient caching strategies to enhance application performance and reduce the load on the database by storing frequently accessed data in memory.
