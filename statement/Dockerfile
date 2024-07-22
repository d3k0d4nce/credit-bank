FROM openjdk:17-jdk-slim
COPY /target/statement-0.0.1-SNAPSHOT.jar statement.jar
EXPOSE 8082
CMD ["java", "-jar", "statement.jar"]