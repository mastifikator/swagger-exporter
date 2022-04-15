package ru.n1k0.swaggerexporter.mapper;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;
import ru.n1k0.swaggerexporter.dto.MetricsResponse;

@Component
public class MetricsMapper {

    public MetricsResponse mapOpenApiToMetricsResponse(String serviceName, OpenAPI openApi){
        return new MetricsResponse(serviceName, openApi);
    }
}
