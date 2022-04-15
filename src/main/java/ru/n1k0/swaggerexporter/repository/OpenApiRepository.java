package ru.n1k0.swaggerexporter.repository;

import io.swagger.v3.oas.models.OpenAPI;

import java.util.Map;

public interface OpenApiRepository {
    Map<String, OpenAPI> findAll();

    void save(String serviceName, OpenAPI openAPI);

    OpenAPI findByService(String serviceName);
}
