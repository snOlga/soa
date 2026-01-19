start PowerShell -NoExit -Command "cd ./teams-ejb-service; mvn clean install -DskipTests; docker build -t teams-soap ."
start PowerShell -NoExit -Command "cd ./teams-service; sleep 10; mvn clean package -DskipTests; docker build -t teams-rest ."
start PowerShell -NoExit -Command "cd ./humans-service; mvn clean package -DskipTests; docker build -t humans-service ."