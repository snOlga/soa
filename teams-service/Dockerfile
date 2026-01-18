FROM eclipse-temurin:17-jre

WORKDIR /app
COPY  target/teams-0.0.1-SNAPSHOT.jar /app/teams-service.jar

ENTRYPOINT ["java", "-jar", "/app/teams-service.jar"]
