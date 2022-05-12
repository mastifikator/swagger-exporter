FROM registry.services.mts.ru/docker/java:15-jdk-buster

ARG SERVICE_NAME='swagger-exporter'
ARG CONFIG_PATH='./src/main/resources/application.properties'
ARG ADDITIONAL_CONFIG_PATH='./config/application.yaml'
ARG RUNTIME_OPTIONS='-XX:+UseContainerSupport -XX:MaxRAMPercentage=50.0'

ENV SERVICE_NAME=${SERVICE_NAME}
ENV CONFIG_PATH=${CONFIG_PATH}
ENV ADDITIONAL_CONFIG_PATH=${ADDITIONAL_CONFIG_PATH}
ENV JAVA_OPTS=${RUNTIME_OPTIONS}

COPY target/${SERVICE_NAME}.jar /application/${SERVICE_NAME}.jar
COPY ${CONFIG_PATH} /etc/application/config.yaml
COPY ${ADDITIONAL_CONFIG_PATH} /etc/application/config/application.yaml

ENTRYPOINT ["sh", "-c","java $JAVA_OPTS  -javaagent:/application/opentelemetry-javaagent-all.jar  -Dspring.config.location=/etc/application/config.yaml  -jar /application/${SERVICE_NAME}.jar"]