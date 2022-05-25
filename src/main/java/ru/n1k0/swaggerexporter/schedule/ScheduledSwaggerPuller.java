package ru.n1k0.swaggerexporter.schedule;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.n1k0.swaggerexporter.config.ConnectionSettings;
import ru.n1k0.swaggerexporter.repository.statusRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class ScheduledSwaggerPuller {

    @Autowired
    private statusRepository statusRepository;

    @Autowired
    private ConnectionSettings connectionSettings;

    @Autowired
    private MeterRegistry meterRegistry;

    @PostConstruct
    public void initializeRepository() {
        Map<String, String> swaggerAddresses = connectionSettings.getSwaggerAddresses();

        for (Map.Entry<String, String> entry : swaggerAddresses.entrySet()) {
            statusRepository.save(entry.getKey(), new ArrayList<>());
        }
    }

    @Scheduled(initialDelay = 2000, fixedRateString = "${scrapeInterval}")
    public void schedulePullSwaggerDocs() throws IOException {
        Map<String, String> swaggerAddresses = connectionSettings.getSwaggerAddresses();

        for (Map.Entry<String, String> entry : swaggerAddresses.entrySet()) {
            URL url = new URL(entry.getValue());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            int responseCode;

            try{
                responseCode = huc.getResponseCode();
            }catch (ConnectException cex){
                responseCode = 404;
            }

            List<String> connectStatusList = statusRepository.findByService(entry.getKey());

            if (responseCode == HttpURLConnection.HTTP_OK) {
                SwaggerParseResult result = new OpenAPIParser().readLocation(entry.getValue(), null, null);
                OpenAPI openAPI = result.getOpenAPI();
                System.out.println("Get info from " + entry.getKey() + " " + entry.getValue());

                Gauge gauge = Gauge.builder("swagger_service_info", connectStatusList, List::size)
                        .tag("service_name", entry.getKey())
                        .tag("version", openAPI.getInfo().getVersion() != null ? openAPI.getInfo().getVersion() : "")
                        .tag("title", openAPI.getInfo().getTitle() != null ? openAPI.getInfo().getTitle() : "")
                        .tag("description", openAPI.getInfo().getDescription() != null ? openAPI.getInfo().getDescription() : "")
                        .tag("paths", Integer.toString(openAPI.getPaths().size()))
                        .description("Service Info from swagger documentation")
                        .register(meterRegistry);

                if (gauge.value() == 0) {
                    connectStatusList.add("connect");
                }
            } else {
                System.out.println("Failed get info from " + entry.getKey() + " " + entry.getValue());

                Gauge.builder("swagger_service_info", connectStatusList, List::size)
                        .tag("service_name", entry.getKey())
                        .tag("version", "?")
                        .tag("title", entry.getKey())
                        .tag("description", "Unavailable service")
                        .tag("paths", "0")
                        .description("Service Info unavailable")
                        .register(meterRegistry);

                Gauge existGauge = meterRegistry.get("swagger_service_info").tag("service_name", entry.getKey()).gauge();
                if (existGauge.value() == 1) {
                    connectStatusList.remove("connect");
                }
            }

            if (meterRegistry.get("swagger_service_info").tag("service_name", entry.getKey()).gauges().size() > 1) {
                meterRegistry.clear();
            }
        }
    }
}
