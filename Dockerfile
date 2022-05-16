FROM registry.services.mts.ru/docker/java:15-jdk-buster

ENV SERVICE_NAME='swagger-exporter'
ENV JAVA_OPTS='-XX:+UseContainerSupport -XX:MaxRAMPercentage=50.0'

COPY target/${SERVICE_NAME}.jar /application/${SERVICE_NAME}.jar
COPY ./src/main/resources/application.properties /etc/application/application.properties

ENTRYPOINT ["sh", "-c","java $JAVA_OPTS  -javaagent:/application/opentelemetry-javaagent-all.jar  -Dspring.config.location=/etc/application/application.properties  -jar /application/${SERVICE_NAME}.jar"]