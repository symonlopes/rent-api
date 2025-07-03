# About project

A reference project that presents key concepts that you will find in almost all projects that you implement.

# Concepts to learn

## CRUD operations
## File send/recover

Send files as stream, not using entire file size on memory.
Using temp folders to process file.

## Relational crud operations
 * Save/delete/update entities with relationship

Transaction

## Tests
* Endpoint tests (integration test), 
* Unitary tests (if applicable)
* Load test:
  * How to do load test Junit like?

Security
* Different roles
* Production ready configuration


This project have to be more like in production project instead a tutorial like.


This is a rent items project. Made for brazilian market.
As a business, you can rent things to customers.

The process is simple: you offer things to locate, customers rent multiple items, you deliver and pickup these items.


# Tech

Is a monolith architecture.

The project is built using Gradle, 
* Java 21 
* Spring Boot 3.5.x 
* Spring JDBC 
* H2 Database
* Flyway
* Lombok
* Junit

# TODO

* See, specifically, where and when Springboot use virtual threads.
* Test performance difference between using Virtual Threads and regular Threads.