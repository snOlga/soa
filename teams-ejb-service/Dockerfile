FROM eclipse-temurin:17-jre

WORKDIR /app
COPY  target/ejb-0.0.3-SNAPSHOT.jar /app/ejb-0.0.3-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/ejb-0.0.3-SNAPSHOT.jar"]
