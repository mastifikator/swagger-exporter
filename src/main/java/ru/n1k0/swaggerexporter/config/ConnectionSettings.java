package ru.n1k0.swaggerexporter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@ConfigurationProperties(prefix = "connection")
@Component
@Data
public class ConnectionSettings {
    Map<String, String> swaggerAddresses;
    private String scrapeInterval;

    public ConnectionSettings() {
        swaggerAddresses = new HashMap<>();
        swaggerAddresses.put("tvh-billing", "http://ingressinside.loadtest-tvh.mts-corp.ru/tvh-billing/desc/swagger.json");
        swaggerAddresses.put("tvh-billing-v2", "http://ingressinside.loadtest-tvh.mts-corp.ru/tvh-billing-v2/swagger");
    }
}
