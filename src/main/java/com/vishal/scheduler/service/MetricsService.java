package com.vishal.scheduler.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final Counter successCounter;
    private final Counter failureCounter;
    private final Counter retryCounter;

    public MetricsService(MeterRegistry registry) {

        successCounter =
                registry.counter("jobs.success");

        failureCounter =
                registry.counter("jobs.failure");

        retryCounter =
                registry.counter("jobs.retry");
    }

    public void incrementSuccess() {
        successCounter.increment();
    }

    public void incrementFailure() {
        failureCounter.increment();
    }

    public void incrementRetry() {
        retryCounter.increment();
    }
}