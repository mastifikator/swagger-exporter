package ru.n1k0.swaggerexporter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;
import ru.n1k0.swaggerexporter.repository.OpenApiRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PrometheusMetricService {

    private final OpenApiRepository openApiRepository;
    private final MeterRegistry meterRegistry;

    public PrometheusMetricService(MeterRegistry meterRegistry, OpenApiRepository openApiRepository) {
        this.openApiRepository = openApiRepository;
        this.meterRegistry = meterRegistry;
    }

   public Map<String, Counter> createServiceCounters(){
        Map<String, Counter> counterMap = new HashMap<>();

        for(Map.Entry<String, OpenAPI> entryMap : openApiRepository.findAll().entrySet()){

            counterMap.put(entryMap.getKey(), Counter.builder("swagger_service_info")
                    .tag("service_name", entryMap.getKey())
                    .tag("version", entryMap.getValue().getInfo().getVersion() != null ? entryMap.getValue().getInfo().getVersion() : "")
                    .tag("title", entryMap.getValue().getInfo().getTitle() != null ? entryMap.getValue().getInfo().getTitle() : "")
                    .tag("description", entryMap.getValue().getInfo().getDescription() != null ? entryMap.getValue().getInfo().getDescription() : "")
                    .tag("paths", Integer.toString(entryMap.getValue().getPaths().size()))
                    .description("Service Info from swagger documentation")
                    .register(meterRegistry));
        }

        return counterMap;
    }

}
