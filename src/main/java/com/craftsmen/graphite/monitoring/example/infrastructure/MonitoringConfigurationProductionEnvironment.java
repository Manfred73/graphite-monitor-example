package com.craftsmen.graphite.monitoring.example.infrastructure;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({ "prod" })
public class MonitoringConfigurationProductionEnvironment extends AbstractMonitoringConfiguration {

	private static final String GRAPHITE_PREFIX = "collectd/graphite-monitoring-example/production";

	@Override
	public void configureReporters() {
		configureReporters(GRAPHITE_PREFIX);
	}
	
	@PostConstruct()
	public void init() {
		configureReporters();
	}
}
