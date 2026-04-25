FROM eclipse-temurin:17-jdk-alpine
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar /agent.jar
COPY /target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8888
ENTRYPOINT ["sh", "-c", "java -javaagent:/agent.jar -jar gateway.jar"]