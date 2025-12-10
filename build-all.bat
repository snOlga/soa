start PowerShell -NoExit -Command "mvn clean package -DskipTests -pl teams-service; docker build -t teams-service ./teams-service"
start mvn clean package -DskipTests -pl teams-ejb-service
start PowerShell -NoExit -Command "${env:JAVA_HOME}='C:\Program Files\Java\jdk-17'; mvn clean package -DskipTests -pl humans-service; docker build -t humans-service ./humans-service