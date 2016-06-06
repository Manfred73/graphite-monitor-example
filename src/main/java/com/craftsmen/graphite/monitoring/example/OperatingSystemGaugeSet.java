package com.craftsmen.graphite.monitoring.example;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

/**
 * A set of gauges for operating system settings.
 */
public class OperatingSystemGaugeSet implements MetricSet {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperatingSystemGaugeSet.class);

  private static final String EXCEPTION_INVOKING_MXBEAN = "Exception invoking mxBean";

  private final OperatingSystemMXBean mxBean;
  private final Optional<Method> committedVirtualMemorySize;
  private final Optional<Method> totalSwapSpaceSize;
  private final Optional<Method> freeSwapSpaceSize;
  private final Optional<Method> processCpuTime;
  private final Optional<Method> freePhysicalMemorySize;
  private final Optional<Method> totalPhysicalMemorySize;
  private final Optional<Method> openFileDescriptorCount;
  private final Optional<Method> maxFileDescriptorCount;
  private final Optional<Method> systemCpuLoad;
  private final Optional<Method> processCpuLoad;

  /**
   * Creates new gauges using the platform OS bean.
   */
  public OperatingSystemGaugeSet() {
    this(ManagementFactory.getOperatingSystemMXBean());
  }

  /**
   * Creates a new gauges using the given OS bean.
   *
   * @param mxBean
   *          an {@link OperatingSystemMXBean}
   */
  public OperatingSystemGaugeSet(OperatingSystemMXBean mxBean) {
    this.mxBean = mxBean;

    committedVirtualMemorySize = getMethod("getCommittedVirtualMemorySize");
    totalSwapSpaceSize = getMethod("getTotalSwapSpaceSize");
    freeSwapSpaceSize = getMethod("getFreeSwapSpaceSize");
    processCpuTime = getMethod("getProcessCpuTime");
    freePhysicalMemorySize = getMethod("getFreePhysicalMemorySize");
    totalPhysicalMemorySize = getMethod("getTotalPhysicalMemorySize");
    openFileDescriptorCount = getMethod("getOpenFileDescriptorCount");
    maxFileDescriptorCount = getMethod("getMaxFileDescriptorCount");
    systemCpuLoad = getMethod("getSystemCpuLoad");
    processCpuLoad = getMethod("getProcessCpuLoad");
  }

  @SuppressWarnings("boxing")
  @Override
  public Map<String, Metric> getMetrics() {
    final Map<String, Metric> gauges = new HashMap<>();

    gauges.put("committedVirtualMemorySize", (Gauge<Long>) () -> invokeLong(committedVirtualMemorySize));
    gauges.put("totalSwapSpaceSize", (Gauge<Long>) () -> invokeLong(totalSwapSpaceSize));
    gauges.put("freeSwapSpaceSize", (Gauge<Long>) () -> invokeLong(freeSwapSpaceSize));
    gauges.put("processCpuTime", (Gauge<Long>) () -> invokeLong(processCpuTime));
    gauges.put("freePhysicalMemorySize", (Gauge<Long>) () -> invokeLong(freePhysicalMemorySize));
    gauges.put("totalPhysicalMemorySize", (Gauge<Long>) () -> invokeLong(totalPhysicalMemorySize));
    gauges.put("fd.usage", (Gauge<Double>) () -> invokeRatio(openFileDescriptorCount, maxFileDescriptorCount));
    gauges.put("systemCpuLoad", (Gauge<Double>) () -> invokeDouble(systemCpuLoad));
    gauges.put("processCpuLoad", (Gauge<Double>) () -> invokeDouble(processCpuLoad));

    return gauges;
  }

  private Optional<Method> getMethod(String name) {
    try {
      final Method method = mxBean.getClass().getDeclaredMethod(name);
      method.setAccessible(true);
      return Optional.of(method);
    } catch (NoSuchMethodException e) {
      LOGGER.warn("Method " + name + " does not exist.", e);
      return Optional.empty();
    }
  }

  @SuppressWarnings("boxing")
  private long invokeLong(Optional<Method> method) {
    if (method.isPresent()) {
      try {
        return (long) method.get().invoke(mxBean);
      } catch (IllegalAccessException | InvocationTargetException ite) {
        LOGGER.error(EXCEPTION_INVOKING_MXBEAN, ite);
        return 0L;
      }
    }
    return 0L;
  }

  @SuppressWarnings("boxing")
  private double invokeDouble(Optional<Method> method) {
    if (method.isPresent()) {
      try {
        return (double) method.get().invoke(mxBean);
      } catch (IllegalAccessException | InvocationTargetException ite) {
        LOGGER.warn(EXCEPTION_INVOKING_MXBEAN, ite);
        return 0.0;
      }
    }
    return 0.0;
  }

  @SuppressWarnings("boxing")
  private double invokeRatio(Optional<Method> numeratorMethod, Optional<Method> denominatorMethod) {
    if (numeratorMethod.isPresent() && denominatorMethod.isPresent()) {
      try {
        long numerator = (long) numeratorMethod.get().invoke(mxBean);
        long denominator = (long) denominatorMethod.get().invoke(mxBean);
        if (0 == denominator) {
          return Double.NaN;
        }
        return 1.0 * numerator / denominator;
      } catch (IllegalAccessException | InvocationTargetException ite) {
        LOGGER.warn(EXCEPTION_INVOKING_MXBEAN, ite);
        return Double.NaN;
      }
    }
    return Double.NaN;
  }
}
