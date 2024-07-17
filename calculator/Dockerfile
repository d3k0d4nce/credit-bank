#FROM maven:latest AS builder
#WORKDIR /app/calculator
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY /target/calculator-0.0.1-SNAPSHOT.jar calculator.jar
EXPOSE 8080
CMD ["java", "-jar", "calculator.jar"]