package com.craftsmen.graphite.monitoring.example.infrastructure;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteReporter.Builder;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

public abstract class AbstractMonitoringConfiguration extends MetricsConfigurerAdapter {

	protected static final long AMOUNT_OF_TIME_BETWEEN_POLLS = 500;

	@Value("${graphite.host}")
	private String graphiteHost;

	@Value("${graphite.port}")
	private int graphitePort;

	@Inject
	private MetricRegistry registry;

	@Bean
	public JmxReporter jmxReporter() {
		JmxReporter reporter = JmxReporter.forRegistry(registry).build();
		reporter.start();
		return reporter;
	}

	abstract protected GraphiteReporter startGraphiteReporter();

	protected GraphiteReporter startGraphiteReporterWithLocationPrefix(String graphitePrefix) {
		GraphiteReporter graphiteReporter = getGraphiteReporterBuilder().prefixedWith(graphitePrefix).build(getGraphite());
		graphiteReporter.start(AMOUNT_OF_TIME_BETWEEN_POLLS, TimeUnit.MILLISECONDS);
		return graphiteReporter;
	}

	private Builder getGraphiteReporterBuilder() {
		registry.register("gc", new GarbageCollectorMetricSet());
		registry.register("memory", new MemoryUsageGaugeSet());
		registry.register("threads", new ThreadStatesGaugeSet());
		registry.register("os", new OperatingSystemGaugeSet());
		return GraphiteReporter.forRegistry(registry).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL);
	}

	private Graphite getGraphite() {
		return new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
	}
}
