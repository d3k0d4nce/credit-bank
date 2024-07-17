#FROM maven:latest AS builder
#WORKDIR /app/statement
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true
#
FROM openjdk:17-jdk-slim
COPY /target/statement-0.0.1-SNAPSHOT.jar statement.jar
EXPOSE 8082
CMD ["java", "-jar", "statement.jar"]