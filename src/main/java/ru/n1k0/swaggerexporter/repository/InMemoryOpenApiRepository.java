package ru.n1k0.swaggerexporter.repository;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryOpenApiRepository implements OpenApiRepository{
    private final Map<String, OpenAPI> openAPIMap = new HashMap<>();

    @Override
    public Map<String, OpenAPI> findAll() {
        return openAPIMap;
    }

    @Override
    public void save(String serviceName, OpenAPI openAPI) {
        openAPIMap.put(serviceName, openAPI);
    }

    @Override
    public void clear() {
        openAPIMap.clear();
    }

    @Override
    public OpenAPI findByService(String serviceName) {
        return openAPIMap.get(serviceName);
    }
}
