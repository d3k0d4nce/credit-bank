#FROM maven:latest AS builder
#WORKDIR /app/api
#COPY pom.xml .
#COPY src ./src
#COPY ../pom.xml ../pom.xml
#RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
COPY /target/api-0.0.1-SNAPSHOT.jar api.jar
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh
CMD ["/app/start.sh"]
