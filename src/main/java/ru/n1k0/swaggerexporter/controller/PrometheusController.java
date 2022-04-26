package ru.n1k0.swaggerexporter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.n1k0.swaggerexporter.dto.MetricsResponse;
import ru.n1k0.swaggerexporter.mapper.MetricsMapper;
import ru.n1k0.swaggerexporter.service.PrometheusService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PrometheusController {

    @Autowired
    private MetricsMapper metricsMapper;

    @Autowired
    private PrometheusService prometheusService;

    @GetMapping("/swagger")
    public List<MetricsResponse> getMetrics(){
        return prometheusService.getAllMetrics().entrySet().stream()
                .map(e -> metricsMapper.mapOpenApiToMetricsResponse(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
