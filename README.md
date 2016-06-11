# Graphite Monitoring Example

This is a small Spring Boot application containing some REST endpoints to demonstrate the use of the [Java Metrics library](http://metrics.dropwizard.io/) and reporting to [Graphite](http://graphite.wikidot.com/).

To use this example, you should have a running Graphite instance. You can use a [graphite docker image](https://github.com/Manfred73/graphite) to start up a Graphite instance.

##Run the application
```
mvn package
java -Dspring.profiles.active=prod -jar target/graphite-monitoring-example.jar
```

##H2 Database
To access the in memory H2 database you can navigate to http://localhost:8080/h2console (no password required to connect).
To connect use the following settings:
* Generic H2 (Embedded) for Saved Settings and Setting Name.
* Driver Class: org.h2.Driver
* JDBC URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
* User Name: sa
* Password: <leave empty>
