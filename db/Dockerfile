#FROM maven:latest AS builder
#WORKDIR /app/db
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY /target/db-0.0.1-SNAPSHOT.jar db.jar
CMD ["java", "-jar", "db.jar"]