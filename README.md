# About project

A reference project that presents key concepts that you will find in almost all projects that you implement.

Items and tags
Tags are a way of categorizing items. You can create tags and associate them with items. 
Tags are better than categories because you can have multiple tags per item, and you can search items by tags.


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

# Backlog
* Write a test to check if a category delete can be done without role.
* Write a test to check if a new item cannot be created by a CUSTOMER role.
~~* Creating category for items~~
~~* Remove sensitive fields from logs~~
~~* You must have a ADMIN token to execute unit tests.~~
~~* Use spring-security-oauth2-authorization-server to generate JWT tokens~~
~~* Validate if passwords are equals on account creation.~~
* See, specifically, where and when Springboot use virtual threads.
* Test performance difference between using Virtual Threads and regular Threads.