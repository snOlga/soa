@echo off
set MULE_HOME=.\mule-enterprise-standalone-4.10.2

echo Building Mule Project...
call mvn -f ./mule-app/teams/pom.xml clean package -DskipTests

echo Cleaning apps directory...
if exist "%MULE_HOME%\apps" rd /s /q "%MULE_HOME%\apps"
mkdir "%MULE_HOME%\apps"

echo Deploying artifact...
if exist ".\mule-app\teams\target\teams-1.0.0-SNAPSHOT-mule-application.jar" (
    copy ".\mule-app\teams\target\teams-1.0.0-SNAPSHOT-mule-application.jar" "%MULE_HOME%\apps"
) else (
    echo [ERROR] Build artifact not found! Check your pom.xml version and packaging.
    pause
    exit /b
)

echo Requesting Admin privileges to start Mule...
"%MULE_HOME%\bin\mule.bat" -c -M-Danypoint.platform.gatekeeper=disabled