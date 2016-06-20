package com.craftsmen.graphite.monitoring.example.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.graphite.GraphiteReporter;

@Configuration
@Profile({ "test" })
public class MonitoringConfigurationTestEnvironment extends AbstractMonitoringConfiguration {

	private static final String GRAPHITE_PREFIX = "collectd/graphite-monitoring-example/test";

	@Bean
	@Override
	public GraphiteReporter startGraphiteReporter() {
		return startGraphiteReporterWithLocationPrefix(GRAPHITE_PREFIX);
	}
}
