package com.craftsmen.graphite.monitoring.example.infrastructure;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

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

	@Value("${graphite.host}")
	private String graphiteHost;

	@Value("${graphite.port}")
	private int graphitePort;

	@Value("${graphite.amount.of.time.between.polls}")
	private long graphiteAmnountOfTimeBetweenPolls;
	
	@Inject
	private MetricRegistry registry;

	private String graphitePrefix;

	abstract protected void configureReporters();

	protected void configureReporters(String graphitePrefix) {
		this.graphitePrefix = graphitePrefix;
		configureReporters(registry);
	}

	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		registerReporter(JmxReporter.forRegistry(metricRegistry).build()).start();
		GraphiteReporter graphiteReporter = getGraphiteReporterBuilder(metricRegistry).build(getGraphite());
		registerReporter(graphiteReporter);
		graphiteReporter.start(graphiteAmnountOfTimeBetweenPolls, TimeUnit.MILLISECONDS);
	}

	private Builder getGraphiteReporterBuilder(MetricRegistry metricRegistry) {
		metricRegistry.register("gc", new GarbageCollectorMetricSet());
		metricRegistry.register("memory", new MemoryUsageGaugeSet());
		metricRegistry.register("threads", new ThreadStatesGaugeSet());
		metricRegistry.register("os", new OperatingSystemGaugeSet());
		return GraphiteReporter.forRegistry(registry).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).prefixedWith(graphitePrefix);
	}

	private Graphite getGraphite() {
		return new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
	}
}
