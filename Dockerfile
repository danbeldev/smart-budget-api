FROM openjdk:17.0.2-jdk-slim
COPY build/libs/smart-budget-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]