package ru.n1k0.swaggerexporter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
public class ConnectionSettings {

    @Value("#{${swaggerUrls}}")
    Map<String, String> swaggerAddresses;
}
