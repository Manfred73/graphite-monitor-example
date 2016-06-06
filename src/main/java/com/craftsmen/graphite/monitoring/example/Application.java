package com.craftsmen.graphite.monitoring.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static final String APPLICATION_PID = "graphite-monitoring-example.pid";
	
	public static void main(String[] args) {
	    SpringApplication springApplication = new SpringApplication(Application.class);
	    springApplication.addListeners(new ApplicationPidFileWriter(APPLICATION_PID));
	    springApplication.run(args);
	}
}
