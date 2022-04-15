package ru.n1k0.swaggerexporter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "connection")
@Component
@Data
public class ConnectionSettings {
    Map<String, String> swaggerAddresses;
    private String scrapeInterval;
}
