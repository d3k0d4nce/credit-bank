#FROM maven:latest AS builder
#WORKDIR /app/deal
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY /target/deal-0.0.1-SNAPSHOT.jar deal.jar
EXPOSE 8081
CMD ["java", "-jar", "deal.jar"]