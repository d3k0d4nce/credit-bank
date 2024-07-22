FROM openjdk:17-jdk-slim
COPY /target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8888
CMD ["java", "-jar", "gateway.jar"]