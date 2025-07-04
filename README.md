# About project

A reference project that presents key concepts that you will find in almost all projects that you implement.

# Concepts to learn

## CRUD operations
## File send/recover

Send files as stream, not using entire file size on memory.
Using temp folders to process file.

## Relational crud operations
 * Save/delete/update entities with relationship

## Transactions

## Logging

Log is you friend on production.

## Tests
* Endpoint tests (integration test), 
* Unitary tests (if applicable)
* Load test:
  * How to do load test Junit like?

## Security

JWT based security. System have a user table that control active users and roles attached to them.


* Different roles
* Production ready configuration


This project have to be more like in production project instead a tutorial like.


This is a rent items project. Made for brazilian market.
As a business, you can rent things to customers.

The process is simple: you offer things to locate, customers rent multiple items, you deliver and pickup these items.


# Tech

Is a monolith architecture.

The project is built using Gradle, 
* Java 21: records, virtual threads
* Spring Boot 3.5.x 
* Spring JDBC 
* H2 Database
* Flyway
* Lombok
* Junit

## Questions
### How can you enable DEBUG log level without have to restart application?
### How to remove sensible information from log like this:
 2025-07-03T19:01:02.490-03:00  INFO 45289 ---  b.c.s.rentapi.controller.UserController  : Registering a new user [UserRegistrationRequest(email=jhon@email.com, name=jhon doe, password=senha@valida112, passwordConfirmation=senha@valida112)]

# Changelog
* You must have a ADMIN token to execute unit tests.
~~* Use spring-security-oauth2-authorization-server to generate JWT tokens~~
* Move to constants solution:  user.getRoles().add(Role.builder().description("CUSTOMER").build());
* Validate if passwords are equals on account creation.
* Security configuration
* * Update unit tests
* * /h2-console endpoint security must be permited only on non-prd scenarios
* * h2 database file configuration must be present only for debug
* * Hot to control ratelimit to /auth endpoint
* See, specifically, where and when Springboot use virtual threads.
* Test performance difference between using Virtual Threads and regular Threads.