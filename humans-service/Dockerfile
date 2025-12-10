FROM eclipse-temurin:17-jre

WORKDIR /app
COPY  target/humans-1.0-SNAPSHOT.jar /app/humans-service.jar

ENTRYPOINT ["java", "-jar", "/app/humans-service.jar"]
