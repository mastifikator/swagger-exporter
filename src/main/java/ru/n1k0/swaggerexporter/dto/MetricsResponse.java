package ru.n1k0.swaggerexporter.dto;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.Data;

@Data
public class MetricsResponse {
    private String serviceName;
    private OpenAPI openAPI;

    public MetricsResponse(String serviceName, OpenAPI openAPI) {
        this.serviceName = serviceName;
        this.openAPI = openAPI;
    }
}
