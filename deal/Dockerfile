FROM eclipse-temurin:17-jdk-alpine
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar /agent.jar
COPY /target/deal-0.0.1-SNAPSHOT.jar deal.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -javaagent:/agent.jar -jar deal.jar"]