package ru.n1k0.swaggerexporter.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryStatusRepository implements statusRepository {
    private final Map<String, List<String>> openAPIMap = new HashMap<>();

    @Override
    public Map<String, List<String>> findAll() {
        return openAPIMap;
    }

    @Override
    public void save(String serviceName, List<String> list) {
        openAPIMap.put(serviceName, list);
    }

    @Override
    public void clear() {
        openAPIMap.clear();
    }

    @Override
    public List<String> findByService(String serviceName) {
        return openAPIMap.get(serviceName);
    }
}
