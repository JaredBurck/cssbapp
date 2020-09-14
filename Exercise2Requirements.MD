# Exercise 2
## Description
We want web services that allow retrieving, adding, updating, and removing customers. In addition, we want the ability 
to add, update, and remove customer phone numbers.  We also want the ability to search for customers based on area code, 
prefix, or phone number.  The decision has been made to use CITP back end services for this by implementing HTTP GET, 
PUT, POST, and DELETE operations for customer and phone data, which will be saved to an in memory H2 database.

*__Note:__ all changes from exercise 1 should be in italics*

## Setup
Make sure you apply Fix_Build.patch first.

When looking at Markdown files (.MD), in the upper right hand corner of the source window are buttons 
that allow you to change what you see.  Try the `Show Preview Only` button.

---

## Business Requirements
###  required:
#### Domain:
* Customer attributes
    * first name
    * last name
    * date of birth formatted "YYYY/MM/DD"
    * *phones: set of Phone*
    * Duplicate defined as having the same first and last names and the same date of birth
* *Phone attributes*
    * *area code*
    * *prefix*
    * *line*
    * *type: one of HOME, MOBILE, WORK, SATELLITE*
    * *extension: optional*
    * *Duplicate defined as having the same area code, prefix, line, and extension*

#### Web services:
* retrieve a list of customers
    * success returns 200 with a list *including phones* for each customer
    * none found, returns 200 with an empty list
* add a customer
    * success returns 201 with the updated customer information
    * missing field returns 400
    * invalid date for date of birth returns 400
    * duplicate customer returns 409
    * *adding a phone at the same time is not allowed (can return 400 or ignore)*
* retrieve a customer by id
    * success returns 200 with customer information including phones
    * invalid id returns 400
    * customer not found returns 404
* update a customer
    * success returns 201 with updated customer information
    * missing field returns 400
    * invalid date for date of birth returns 400
    * updating data that would cause a duplicate customer returns 409
    * when customer is not found to update, return a 404
    * *adding/updating phones at the same time is not allowed (can return 400 or ignore)*
* remove a customer
    * success returns 200
    * invalid id returns 400
    * customer not found returns 404
    * *all phones should be removed with the customer*
* *add a phone to a customer*
    * *should only be allowed to add to a specific customer*
    * *success returns 201 with the updated phone information*
    * *customer not found returns 404*
    * *missing field returns 400*
    * *duplicate phone for the customer returns 409*
* *update a phone for a customer*
    * *should be specific to the customer*
    * *success returns 201 with the updated phone information*
    * *customer not found returns 404*
    * *missing field returns 400*
    * *trying to update a phone such that it would create a duplicate returns 409*
* *remove a phone for a customer*
    * *should be specific to the customer*
    * *success returns 200*
    * *customer not found returns 404*
* *retrieve a list of phones for a customer id*
    * *success returns 200 with a list of phones for the provided customer*
    * *invalid customer id returns 400*
    * *customer not found returns 404*
    * *no phones found returns 200 and empty list*

### optional:
#### Web services:
* Do not allow system fields to be updated via web services
* id and audit information is returned but not used to add or update customer *or phone* information
* return 405 for all http methods not implemented for an entity
    * e.g.  if you do not implement PUT for Customer, calling that url should return the HTTP response code 405
* *add a service that returns a list of customers with the provided area code*
* *add a service that returns a list of customers with the provided area code and prefix*
* *add a service that returns a list of customers with the provided phone*

---

## Spring Java Requirements
###  required:
* package names should start with com.compsourcemutual.train.sfw.exercise2
    * use the following as needed: aspects, configuration, controllers, domain, exceptions, repositories, services
* use specific annotations for mapping controller methods; each method should have a specific annotation rather than just the class
* *all web service urls should start with sfw/exercise2*
* Use the best form of dependency injection based on the training materials
* Javadoc
    * all methods in controllers and services
    * other methods where it makes sense
* use package private scope for all supplemental methods (ones that assist with functionality)
* All code should be green and clean
    * all code should have a green check in the upper right corner except for domain objects (potentially unused getter and setter methods)
    * unused code should be deleted
    * empty packages should be removed
* user and copyright information should be at the bottom of each created class (but not for Initializr generated classes)
* demonstrate appropriate use of lambda functions
* use Java LocalDate and LocalDateTime for all dates
* use PUT when adding/updating to a known id; use POST when the id is unknown

### optional:
* use optional objects where appropriate to facilitate the way code is written
* change web services to use DTO's
    * return all data but only take in data that can be updated via services
    * create mapping function classes to transform objects and inject them where possible
    * consider carefully where the transformation takes place
* use defined exceptions that extend ResponseStatusException
* create a scratch http file called tests.http or Postman collection that contains all requests needed to test your application
    * it should add data to populate the table
    * it should make every call needed to test
    * it should be documented
    * add it to git under src/test/resources

---

## JPA Requirements
### required:
* use h2 in memory database
* add h2 console support using application.properties
* *database name: exercise2*
* *user name: exercise2*
* *password: Ex3rc1$e2*
* all entities have an auto generated id attribute of type Long
* each entity has a JPARepository created that allows CRUD operations on that entity
* all checks for duplicate entries need to occur in a service component
    * name should be <entity>Service
    * once created, all access to that entity's JPARepository should be only within that service
* Date of birth should be stored as the appropriate Java date but is in the format "YYYY/MM/DD" for web service and json purposes
* *use an Enumeration for the phone type*
* *customer to phone relationship should use the best OneToMany relationship*

### optional:
* All entities have spring framework auditing applied
    * createdDate shows the date and time the entity instance was created
    * lastModifiedDate shows the last date and time the entity instance was updated
* *use embedded object for audit data that is used by both Customer and Phone*
* web service retrieval methods that return lists allow pagination and sorting

---

## Logging Requirements
### required:
* all controller and service methods
* TRACE logged at the start of each method should look like -> `<method name>: start`
* TRACE logged at the end of each method -> `<method name>: end`
* DEBUG -> `<method name>: parameters: <"<parameter name>": "<parameter value in json format>" [, additional parameters]> || none if a no parameter method`
    * e.g.  retrieveCustomer() -> `retrieveCustomer: parameters: none`
    * e.g.  retrieveCustomer(String firstName, String lastName) -> `retrieveCustomer: parameters: "firstName": "Frank", "lastName": "Goforth"`
    * e.g. retrieveCustomer(Customer customer) -> `retrieveCustomer: parameters: "customer": { "firstName": "Frank", "lastName": "Goforth", "dateOfBirth": "1955/01/01"}`
* WARN for all exceptions
* INFO as needed

### optional:
* Add capability to turn on and off logging through Spring admin (this allows you to turn on trace and debug without having to update properties files)
    * disable all endpoints except for loggers
    * expose loggers endpoint with HTTP only; disable jmx
* Use Aspect Oriented Programming for TRACE and DEBUG logging
* include repository methods

