package ru.n1k0.swaggerexporter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class BeerService {
    private final MeterRegistry meterRegistry;
    Counter lightBeerCounter;
    Counter aleBeerCounter;

    public BeerService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        initOrderCounters();
    }

    private void initOrderCounters(){
        lightBeerCounter = this.meterRegistry.counter("beer.orders", "type", "light");
        aleBeerCounter = Counter.builder("beer.orders")
                .tag("type", "ale")
                .description("The number of orders placed ale")
                .register(meterRegistry);
    }

    public void orderBeer(){
        lightBeerCounter.increment();
        aleBeerCounter.increment();
    }
}
