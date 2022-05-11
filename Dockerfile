FROM openjdk:8-jdk-alpine
ARG JAR_FILE=./target/*.jar
ARG CONFIG_FILE=./config/application.yaml
COPY ${JAR_FILE} ${CONFIG_FILE} ./
CMD ["java", "-jar", "/swagger-exporter-1.0.0.jar"]