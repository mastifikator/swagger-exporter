package ru.n1k0.swaggerexporter.schedule;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.n1k0.swaggerexporter.config.ConnectionSettings;
import ru.n1k0.swaggerexporter.repository.OpenApiRepository;
import ru.n1k0.swaggerexporter.service.BeerService;

import java.util.Map;

@Configuration
@EnableScheduling
public class ScheduledSwaggerPuller {

    @Autowired
    OpenApiRepository openApiRepository;

    @Autowired
    ConnectionSettings connectionSettings;

    @Autowired
    BeerService beerService;

    @Scheduled(initialDelay = 2000, fixedRateString = "${connection.scrapeInterval}")
    public void schedulePullSwaggerDocs(){
        Map<String, String> swaggerAddresses = connectionSettings.getSwaggerAddresses();

        for (Map.Entry<String, String> entry: swaggerAddresses.entrySet()) {
            SwaggerParseResult result = new OpenAPIParser().readLocation(entry.getValue(), null, null);
            OpenAPI openAPI = result.getOpenAPI();

            openApiRepository.save(entry.getKey(), openAPI);

            beerService.orderBeer();

            System.out.println("saved " + entry.getKey() + " " + openAPI.getInfo().getVersion());
        }
    }
}
