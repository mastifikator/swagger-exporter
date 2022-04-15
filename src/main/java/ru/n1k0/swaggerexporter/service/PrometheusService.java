package ru.n1k0.swaggerexporter.service;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.n1k0.swaggerexporter.repository.OpenApiRepository;

import java.util.Map;

@Service
public class PrometheusService {

    @Autowired
    OpenApiRepository openApiRepository;

    public Map<String, OpenAPI> getAllMetrics(){
        return openApiRepository.findAll();
    }

}
