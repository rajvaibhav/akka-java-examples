This example demonstrate usage of Akka with Spring MVC (async controllers using DeferredResult<T>).

It uses Spring-Data-Mongodb for mongodb.

You can modify mongodb connection settings in application.properties.

Default setting for mongo connection is: mongo://localhost:27017/akka-java-async

To build this example: mvn clean install

It will produce: target/akka-spring-mongo-0.1.0.jar

To run this: java -jar target/akka-spring-mongo-0.1.0.jar

----------------

It exposes 3 operations on Customer resource: create, find one and find all.

1. Create:

Request type: POST
Content Type: application/json
URL: http://localhost:8080/customer
Request body: {"firstName": "Some-name", "lastName": "Some-surname", "email": "abc@somemail.com", "contactNumber": "283745287345"}

2. Find One:

Request type: GET
URL: http://localhost:8080/customer/{id of customer in mongodb document.}

3. Find all:

Request type: GET
URL: http://localhost:8080/customer
