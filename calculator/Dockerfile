FROM openjdk:17-jdk-slim
COPY /target/calculator-0.0.1-SNAPSHOT.jar calculator.jar
EXPOSE 8080
CMD ["java", "-jar", "calculator.jar"]