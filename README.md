# Graphite Monitoring Example
This is a small Spring Boot application containing some REST endpoints to demonstrate the use of the [Java Metrics library](http://metrics.dropwizard.io/) and reporting to [Graphite](http://graphite.wikidot.com/).

To use this example, you should have:

 - Maven installed.
 - Java 8 JDK installed.
 - A running Graphite instance. You can use a [graphite docker image](https://github.com/Manfred73/graphite) to start up a Graphite instance.

##Contents
1. [Application configuration](#application-configuration)
2. [Spring profiles](#spring-profiles)
3. [Run the application](#run-the-application)
4. [Generate data for Graphite](#generate-data-for-graphite)
5. [H2 Database](#h2-database)
6. [Customer API](#customer-api)

###Application configuration
To actually see any result in Graphite, you need to set the correct host for Graphite in the ```application.properties```. To do this go to ```application.properties``` and change the ```graphite.host property```, e.g. ```192.168.0.15```.
The application runs on port ```8090```.

###Spring profiles

 - By default the following profiles are active when the application is started: ```h2,test``` (see ```application.properties```). The ```h2``` profile is needed in order to use the in memory H2 database with customer data.
 - The ```test``` profile will push data to graphite under entry: ```collectd/graphite-monitoring-example/test``` (see ```MonitoringConfigurationTestEnvironment```).
 - The ```prod``` profile will push data to graphite under entry: ```collectd/graphite-monitoring-example/production``` (see ```MonitoringConfigurationProductionEnvironment```).

###Run the application
To run the application, first run the following command:
```
mvn package
```

The application can then be run as follows:
```
java -jar target/graphite-monitoring-example.jar
```
which uses the profiles ```h2,test```, which is similar to running the application as:
```
java -Dspring.profiles.active=h2,test -jar target/graphite-monitoring-example.jar
```
To run with profile ```prod``` you can do:
```
java -Dspring.profiles.active=h2,prod -jar target/graphite-monitoring-example.jar
```

###Generate data for Graphite
To see some actual results in Graphite, you can make some REST calls to the Customer-API, e.g. by using the Advanced REST Client in Chrome.
The application also offers an option to generate some data by automaically executing some REST calls to the Customer-API. To trigger this, you can run the application as described in the section above ([Run the application](#run-the-application)) and by adding the following argument: ```--generateGraphiteData```, for example:
```
mvn package
java -jar target/graphite-monitoring-example.jar --generateGraphiteData
```

###H2 Database
This application runs an in memory H2 database which inserts 300 customers with first name and last name on startup of the application. To access the in memory H2 database you can navigate to http://localhost:8090/console. To connect use the following settings:

 - Generic H2 (Embedded) for Saved Settings and Setting Name.
 - Driver Class: org.h2.Driver
 - JDBC URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
 - User Name: sa
 - Password: [leave empty]

###Customer API
For demonstration purposes the [Customer-API](Customer-API.md) can be used.
