#FROM maven:latest AS builder
#WORKDIR /app/dossier
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY /target/dossier-0.0.1-SNAPSHOT.jar dossier.jar
EXPOSE 8083
CMD ["java", "-jar", "dossier.jar"]