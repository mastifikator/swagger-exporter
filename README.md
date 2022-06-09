swagger-exporter - приложение, которое позволяет генерировать метрики в виде понятном Prometheus
Приложение принимает на вход (через application.properties) список URL на JSON формируемый Swagger, получает из них:  
Версию приложения, кол-во endpoint и info (возможно доработать для получения другой информации из Swagger)  
На основе полученной информации формируются метрики для Prometheus серверов, по адресу localhost/actuator/prometheus  

Следующего вида:

Element
swagger_service_info{description="Сервис публикует ресурс TV Maintenance",  
instance="swagger-exporter.loadtest-tvh.mts-corp.ru:80",  
job="Swagger_exporter",  
paths="11",  
service_name="tvh-maintenance",  
title="API сервиса 'TV Maintenance'",  
version="1.12.0-1"}  

value  
1  

value 1 обозначает что URL доступен и опрашивается, если URL будет недоступен то значение установится в 0  
Так же поддерживается установка периода опроса scrapeInterval=15000 (мс) 
Реализована возможность передавать в application.properties массив URL Swagger  
