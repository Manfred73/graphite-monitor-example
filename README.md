# Graphite Monitoring Example

This is a small Spring Boot application containing some REST endpoints to demonstrate the use of the [Java Metrics library](http://metrics.dropwizard.io/) and reporting to [Graphite](http://graphite.wikidot.com/).

To use this example, you should have a running Graphite instance. You can use a [graphite docker image](https://github.com/Manfred73/graphite) to start up a Graphite instance.

##Run the application
```
mvn package
java -Dspring.profiles.active=prod -jar target/graphite-monitoring-example.jar
```
