#FROM maven:latest AS builder
#WORKDIR /app/gateway
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY /target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
EXPOSE 8888
CMD ["java", "-jar", "gateway.jar"]