FROM registry.services.mts.ru/docker/java:15-jdk-buster

ENV SERVICE_NAME='swagger-exporter'
ENV ADDITIONAL_CONFIG_PATH='./config/application.yaml'
ENV JAVA_OPTS='-XX:+UseContainerSupport -XX:MaxRAMPercentage=50.0'

COPY target/${SERVICE_NAME}.jar /application/${SERVICE_NAME}.jar
COPY ./src/main/resources/application.properties /etc/application/config.yaml
COPY ${ADDITIONAL_CONFIG_PATH} /etc/application/config/application.yaml

ENTRYPOINT ["sh", "-c","java $JAVA_OPTS  -javaagent:/application/opentelemetry-javaagent-all.jar  -Dspring.config.location=/etc/application/config.yaml  -jar /application/${SERVICE_NAME}.jar"]