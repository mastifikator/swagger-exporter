package ru.n1k0.swaggerexporter.repository;

import java.util.List;
import java.util.Map;

public interface statusRepository {

    Map<String, List<String>> findAll();

    void save(String serviceName, List<String> list);

    void clear();

    List<String> findByService(String serviceName);
}
