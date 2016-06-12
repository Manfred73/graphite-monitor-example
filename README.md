# Graphite Monitoring Example
This is a small Spring Boot application containing some REST endpoints to demonstrate the use of the [Java Metrics library](http://metrics.dropwizard.io/) and reporting to [Graphite](http://graphite.wikidot.com/).

To use this example, you should have a running Graphite instance. You can use a [graphite docker image](https://github.com/Manfred73/graphite) to start up a Graphite instance.

##Contents
1. [Run the application](#run-the-application)
2. [H2 Database](#h2-database)
3. [Customer API](#customer-api)

###Run the application
```
mvn package
java -jar target/graphite-monitoring-example.jar
```

###H2 Database
This application runs an in memory H2 database which inserts 300 customers with first name and last name on startup of the application. To access the in memory H2 database you can navigate to http://localhost:8080/console. To connect use the following settings:

 - Generic H2 (Embedded) for Saved Settings and Setting Name.
 - Driver Class: org.h2.Driver
 - JDBC URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
 - User Name: sa
 - Password: [leave empty]

###Customer API
For demonstration purposes the [Customer-API](Customer-API.md) can be used.
