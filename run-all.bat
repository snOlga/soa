start mvn spring-boot:run -f "d:\ITMO\4_year\soa\soa\teams-service\pom.xml" -Dspring-boot.run.arguments=--server.port=18081
start mvn spring-boot:run -f "d:\ITMO\4_year\soa\soa\teams-service\pom.xml" -Dspring-boot.run.arguments=--server.port=18082

start PowerShell -NoExit -Command "${env:JAVA_HOME}='C:\Program Files\Java\jdk-17'; mvn spring-boot:run -f 'd:\ITMO\4_year\soa\soa\humans-service\pom.xml' '-Dspring-boot.run.arguments=--server.port=18018'"
start PowerShell -NoExit -Command "${env:JAVA_HOME}='C:\Program Files\Java\jdk-17'; mvn spring-boot:run -f 'd:\ITMO\4_year\soa\soa\humans-service\pom.xml' '-Dspring-boot.run.arguments=--server.port=18019'"